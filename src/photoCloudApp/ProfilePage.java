package photoCloudApp;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JScrollPane;

import logging.Logger;
import photo.*;
import user.*;
/**
 * The ProfilePage class represents the user's profile page in the photo cloud application.
 * It displays the user's information, uploaded photos, and provides options for editing or deleting photos.
 * The class handles user interactions like editing the user's information or deleting photos.
 */
public class ProfilePage extends JPanel {
    private User user;
    private JLabel profilePhotoLabel;
    
	private JTextField nameField; // Name of user 
	private JTextField nicknameField; // Nickname of user
	private JTextField surnameField; // Surname of user
	private JTextField ageField; // Age of user
	private JPasswordField passwordField; // Password of user
	private JTextField userTypeField;
	private JTextField emailField;
	
	private JLabel userTypeLabel;
    private JLabel nicknameLabel;
    private JLabel nameLabel;
    private JLabel surnameLabel;
    private JLabel ageLabel;
    private JLabel passwordLabel;
	private UserTier userType;
    private JLabel emailLabel;
    private JButton editButton;
    private JButton deletePhotoButton;
    private JPanel photoGridPanel;
    private List<JButton> photoLabels;
	private JButton uploadButton;
	private JButton discoverPageButton;
	private JButton logoutButton;
	private JButton showPass;
	private JButton sharePhotoButton;
	
	// BU GİDECEK:
	private JLabel profilePhotoLabel2;
	
    private JPanel topPanel; // New JPanel for the top area
    private JPanel centerPanel;
    
    private ImageIcon defaultImageIcon;
   
    private JPanel userPhotosPanel; // Panel to display user photos
    private List<Photo> userPhotos;


    /**
     * Creates a new ProfilePage instance with the given user.
     *
     * @param user the User object representing the current user.
     */
    public ProfilePage(User user) {
        this.user = user;
        this.photoLabels = new ArrayList<>();
        this.userPhotos = new ArrayList<>();
        long startTime = System.currentTimeMillis();


        	// PASSED USER DATA:
        String name = user.getRealName();
        String surname = user.getSurname();
        String nickname = user.getNickname();
        String email = user.getEmail();
        int ageD = user.getAge();
        String age = String.valueOf(ageD);
        String password = user.getPassword();
        UserTier userType = user.getUserType();
        
        String userTypeStr;
        if (userType == UserTier.FREE) {
        		userTypeStr = "FREE";
        } else if (userType == UserTier.HOBBYIST) {
        		userTypeStr = "HOBBYIST";
        } else if (userType == UserTier.PROFESSIONAL) {
        		userTypeStr = "PROFESSIONAL";
        } else {
        		userTypeStr = "ADMIN";
        }
        
        getPhotosForUser("src/imagesInfo.txt");
       
        topPanel = new JPanel(new BorderLayout());
        // MARGIN SETTINGS: // IF WE WANT WE CAN ADD THIS BUTTON TO A CLASS::::
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        logoutButton = new JButton("Logout");
        
        logoutButton.setMargin(new Insets(10, 10, 10, 10));
        
        // Font settings:
        Font boldFont = new Font(logoutButton.getFont().getName(), Font.BOLD, logoutButton.getFont().getSize());
        logoutButton.setFont(boldFont);
        
        
        logoutButton.addActionListener(new ActionListener() {
        		@Override
        		public void actionPerformed(ActionEvent e) {
        			JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ProfilePage.this);
        			
        			LoginPage loginPage = new LoginPage();
        			
                    Logger.LogInfo(user.getNickname()+ ": user has logged out.");
        			
        			loginPage.setBackground(Color.BLACK);
        			
        			frame.setContentPane(loginPage);
        			
        			frame.revalidate();
        			frame.repaint();
        		};
        });
        

        
        discoverPageButton = new JButton("Discover Page");
        discoverPageButton.setFont(boldFont);
        discoverPageButton.addActionListener(new ActionListener() {
        	
        		@Override
        		public void actionPerformed(ActionEvent e) {
        			JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ProfilePage.this);
        			
        			DiscoverPage discoverPage = new DiscoverPage(user);
        			
        			frame.setContentPane(discoverPage);
        			
        			frame.revalidate();
        			frame.repaint();
        		};
        });
        
        
        // Logout and Discover Page buttons added to topPanel
        topPanel.add(logoutButton, BorderLayout.WEST);
        topPanel.add(discoverPageButton, BorderLayout.EAST);
        
        // Add the topPanel to its parent panel
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH); // Add to top
        
        // Now: create a Center Panel to rest of it:
        centerPanel = new JPanel(new GridBagLayout());
        
        // General Layout Settings:        
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.insets = new Insets(5,5,5,5);
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        
        
        // Reset anchor for other components
        gbc.anchor = GridBagConstraints.CENTER;
        
        // Default görseli yükle
        defaultImageIcon = new ImageIcon("src/profilePics/zyro-image.png");
        

        // Profil fotoğrafı için label'ı oluştur
        profilePhotoLabel = new JLabel(defaultImageIcon);

        profilePhotoLabel.setHorizontalAlignment(SwingConstants.CENTER);

//         Add the new profilePhotoLabel to the centerPanel
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        centerPanel.add(profilePhotoLabel, gbc);

        
        
        // Position  : 
     // construct text field with label
    		nameLabel = new JLabel("Your Name: ");
    		nameLabel.setToolTipText("İlker");
    		nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
    		gbc.gridx = 0;
     	gbc.gridy = 3;
        gbc.gridwidth = 1; // to span 2 columns
        centerPanel.add(nameLabel, gbc);
     		
     	nameField = new JTextField(name ,20);
     	nameField.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridwidth = 1; // to span 2 columns
    		gbc.gridx = 1;
    		gbc.gridy = 3;
    		centerPanel.add(nameField, gbc);
     		
     		
     	surnameLabel = new JLabel("Your Surname: ");
    		surnameLabel.setToolTipText("Yörü");
    		surnameLabel.setHorizontalAlignment(SwingConstants.LEFT);
    		surnameLabel.setVerticalTextPosition(SwingConstants.CENTER);
    		gbc.gridx = 0;
     	gbc.gridy = 4;
     	centerPanel.add(surnameLabel, gbc);

     	surnameField = new JTextField(surname, 20);
     	surnameField.setHorizontalAlignment(SwingConstants.CENTER);
     	gbc.gridx = 1;
    		gbc.gridy = 4;
    		centerPanel.add(surnameField, gbc);
     		
     		
     	nicknameLabel = new JLabel("Your unique nickname: ");
     	nicknameLabel.setToolTipText("1lker");
     	nicknameLabel.setHorizontalAlignment(SwingConstants.LEFT);
    		nicknameLabel.setVerticalTextPosition(SwingConstants.CENTER);
    		gbc.gridx = 0;
    		gbc.gridy = 5;
    		centerPanel.add(nicknameLabel, gbc);
     		
     	nicknameField = new JTextField(nickname, 20);
     	nicknameField.setEditable(false);
     	nicknameField.setHorizontalAlignment(SwingConstants.CENTER);
    		gbc.gridx = 1;
    		gbc.gridy = 5;
    		centerPanel.add(nicknameField, gbc);
     		
     		
     	ageLabel = new JLabel("Your age: ");
     	ageLabel.setToolTipText("20");
    		ageLabel.setHorizontalAlignment(SwingConstants.LEFT);
    		ageLabel.setVerticalTextPosition(SwingConstants.CENTER);
    		gbc.gridx = 0;
    		gbc.gridy = 6;
    		centerPanel.add(ageLabel, gbc);
     	
     	ageField = new JTextField(age, 10);
    		ageField.setHorizontalAlignment(SwingConstants.CENTER);
    		gbc.gridx = 1;
    		gbc.gridy = 6;
    		centerPanel.add(ageField, gbc);
     	
     	emailLabel = new JLabel("Your email: ");
     	emailLabel.setToolTipText("ilker@gmail.com");
     	emailLabel.setHorizontalAlignment(SwingConstants.LEFT);
     	emailLabel.setVerticalTextPosition(SwingConstants.CENTER);
    		gbc.gridx = 0;
    		gbc.gridy = 7;
    		centerPanel.add(emailLabel, gbc);
     		
     	emailField = new JTextField(email, 20);
     	emailField.setHorizontalAlignment(SwingConstants.CENTER);
    		gbc.gridx = 1;
    		gbc.gridy = 7;
    		centerPanel.add(emailField, gbc);
     	
     	userTypeLabel = new JLabel("Your user type: ");
     	userTypeLabel.setToolTipText("ADMIN");
     	userTypeLabel.setHorizontalAlignment(SwingConstants.LEFT);
     	userTypeLabel.setVerticalTextPosition(SwingConstants.CENTER);
    		gbc.gridx = 0;
    		gbc.gridy = 8;
    		centerPanel.add(userTypeLabel, gbc);
     	
     	userTypeField = new JTextField(userTypeStr, 20);
     	userTypeField.setHorizontalAlignment(SwingConstants.CENTER);
     	userTypeField.setEditable(false);
    		gbc.gridx = 1;
    		gbc.gridy = 8;
    		centerPanel.add(userTypeField, gbc);
     	
     	passwordLabel = new JLabel("Your password:         ");
     	passwordLabel.setToolTipText("*******");
    		passwordLabel.setHorizontalAlignment(SwingConstants.LEFT);
    		passwordLabel.setVerticalTextPosition(SwingConstants.CENTER);
    		gbc.gridx = 0;
    		gbc.gridy = 9;
    		centerPanel.add(passwordLabel, gbc);
     	
     	passwordField = new JPasswordField(password, 20);
     	passwordField.setHorizontalAlignment(SwingConstants.CENTER);
     	gbc.gridx = 1;	
     	gbc.gridy = 9;
     	centerPanel.add(passwordField, gbc);
        
     	
     	showPass =  new JButton("Show");
     	showPass.addActionListener(new ActionListener() {
     		@Override
     		public void actionPerformed(ActionEvent e) {

     			JOptionPane.showMessageDialog(null, "Your password is: " + password, "Show Password", JOptionPane.INFORMATION_MESSAGE);
     		};
     	});
     	
     	editButton = new JButton("Edit and Save");
        editButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {     			
					updateUserInfo();
					JOptionPane.showMessageDialog(profilePhotoLabel, "Your changes have been saved.", "Change Successful.", JOptionPane.INFORMATION_MESSAGE);					
				}
        });
        
        gbc.gridx = 2;
        gbc.gridy = 9;
        
        
        centerPanel.add(showPass, gbc);
     	
        
		
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        
        centerPanel.add(editButton, gbc);
        
		uploadButton = new JButton("Upload Profile Picture");
		uploadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleUpload();
			}
		});
		
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        
        centerPanel.add(uploadButton, gbc);
		
     // Initialize user photos panel and add it to centerPanel
     // Kullanıcının fotoğraflarının 2 sıra ve dinamik sayıda kolon içerecek şekilde düzenlenmesi
        userPhotosPanel = new JPanel(new GridLayout(0, 2, 10, 10)); // 2 sıra, sınırsız sayıda kolon, 10 birimlik aralıklar
        userPhotosPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        displayUserPhotos();
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        centerPanel.add(userPhotosPanel, gbc);
        
        
     // Remove the existing centerPanel
        remove(centerPanel);

        // Create a new JScrollPane and add the centerPanel to it
        JScrollPane scrollPane = new JScrollPane(centerPanel);

        // Set the vertical scroll bar policy to always show
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Add the scrollPane to the main panel
        add(scrollPane, BorderLayout.CENTER);
        


        loadProfilePhoto();
        
        
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println(elapsedTime);
        Logger.LogInfo("ProfilePage loaded in " + elapsedTime + " milliseconds.");

    }
    
    
    /**
     * Displays the user's photos in the userPhotosPanel.
     */
    private void displayUserPhotos() {
        // Clear the user photos panel
        userPhotosPanel.removeAll();
        userPhotosPanel.revalidate();
        userPhotosPanel.repaint();

        // Display the user's shared photos in the user photos panel
        for (Photo photo : userPhotos) {
            JButton photoLabel = createPhotoLabel(photo);
            userPhotosPanel.add(photoLabel);
            photoLabels.add(photoLabel);
        }

        // Update the layout of the user photos panel
        userPhotosPanel.revalidate();
        userPhotosPanel.repaint();
    }

    /**
     * Retrieves the user's photos from the given file path and populates the userPhotos list.
     *
     * @param filePath the file path of the file containing photo information.
     */
    
    public void getPhotosForUser(String fileName) {
        String line;
        
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0 && parts[0].equals(user.getNickname())) {
                	System.out.println(parts[1]);
                	File imageFile = new File(parts[1]); // Okunacak görseli gir
                	Image originalImage = ImageIO.read(imageFile); // Görseli okuyorum
                	Image resizedImage = originalImage.getScaledInstance(400, 200, Image.SCALE_FAST);  // 400x200 pixel boyutuna yeniden boyutlandırılıyor
                	Photo photo = new Photo(resizedImage, user, parts[1]);
                	userPhotos.add(photo);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Photo> getUserPhotos() {
        return userPhotos;
    }
    
    /**
     * Creates a JButton with the specified photo image and returns it.
     *
     * @param photoPath the file path of the photo image.
     * @return the created JButton.
     */
    private JButton createPhotoLabel(Photo photo) {
        JButton photoLabel = new JButton(new ImageIcon(photo.getImage()));
        photoLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        photoLabel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				 PhotoPanel photoPanel = new PhotoPanel(new Photo(photo.getImage(), user, photo.getImagePath()), user);
                 // Remove the current JPanel from its parent JFrame:
                 JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ProfilePage.this);

                 // Now: set your current page this signup page:,
                 frame.setContentPane(photoPanel);

                 // Now we need to recalculate the page values:
                 frame.revalidate();
                 frame.repaint();
			}
        	
        });
        return photoLabel;
    }
    
    /**
     * Handles the upload of a new profile picture by allowing the user to choose a file from the file system.
     */
    private void handleUpload() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                removeOldProfilePicInfo();

                // Copy the selected file to the profilePics folder or appropriate location
                Path destination = Paths.get("src/profilePics", selectedFile.getName());
                Files.copy(selectedFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

                // Read the image from the selected file
                BufferedImage originalImage = ImageIO.read(selectedFile);

                // Resize the image to 300x300 pixels
                Image resizedImage = originalImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH);

                // Create an ImageIcon object from the resized image
                ImageIcon profilePhotoIcon = new ImageIcon(resizedImage);

                // Set the profile photo for the user
                user.setProfilePhoto(profilePhotoIcon);

                // Update the profile photo label
                profilePhotoLabel.setIcon(profilePhotoIcon);

                // Write to profilePics.txt
                try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("src/profilePics/profilePics.txt"), StandardOpenOption.APPEND)) {
                    writer.write(user.getNickname() + "\t" + destination.toString() + "\n");
                    Logger.LogInfo("Profile picture added or changed successfully.");
					JOptionPane.showMessageDialog(profilePhotoLabel, "Profile picture added or changed successfully.", "Change Successful.", JOptionPane.INFORMATION_MESSAGE);					

                } catch (IOException e) {
                    Logger.LogError("Error occurred while writing to profilePics.txt: " + e.getMessage());
                }

            } catch (IOException e) {
                Logger.LogError("Error occurred while uploading photo: " + e.getMessage());
            }
        }
    }
    /**
     * Loads the user's profile photo from the profilePics.txt file and displays it on the profile page.
     * The profile photo is resized to 300x300 pixels before displaying.
     */
    private void loadProfilePhoto() {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("src/profilePics/profilePics.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length >= 2 && parts[0].equals(user.getNickname())) {
                    // User's profile picture found
                    File file = new File(parts[1]);
                    BufferedImage originalImage = ImageIO.read(file);
                    Image resizedImage = originalImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                    ImageIcon profilePhotoIcon = new ImageIcon(resizedImage);
                    user.setProfilePhoto(profilePhotoIcon);
                    profilePhotoLabel.setIcon(profilePhotoIcon);
                    break;   
                }
            }
        } catch (IOException e) {
            Logger.LogError("Error occurred while reading from profilePics.txt: " + e.getMessage());
        }
    }

    /**
     * Removes the old profile photo information from the profilePics.txt file for the current user.
     * This is done before uploading a new profile photo.
     */

    private void removeOldProfilePicInfo() {
        try {
            Path path = Paths.get("src/profilePics/profilePics.txt");
            List<String> lines = Files.readAllLines(path);
            lines.removeIf(line -> line.startsWith(user.getNickname() + "\t"));
            Files.write(path, lines);
        } catch (IOException e) {
            Logger.LogError("Error occurred while removing old profile photo info: " + e.getMessage());
        }
    }

    /**
     * Updates the user's information with the values entered in the text fields.
     */
    private void updateUserInfo() {
        try {
            Path usersFile = Paths.get("src/users.txt");
            List<String> lines = Files.readAllLines(usersFile);

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] parts = line.split(",");
                if (parts.length >= 7 && parts[2].equals(user.getNickname())) {
                    // Update the user information
                    System.out.println("Before Update:");
                    System.out.println("Name: " + parts[0]);
                    System.out.println("Surname: " + parts[1]);
                    System.out.println("Nickname: " + parts[2]);
                    System.out.println("Age: " + parts[3]);
                    System.out.println("Password: " + parts[4]);
                    System.out.println("Email: " + parts[6]);
                    System.out.println("UserType: " + parts[5]);

                    parts[0] = nameField.getText();
                    parts[1] = surnameField.getText();
                    parts[2] = nicknameField.getText();
                    parts[3] = ageField.getText();
                    parts[4] = new String(passwordField.getPassword()); // Convert char array to String
                    parts[6] = emailField.getText();
                    parts[5] = userTypeField.getText();

                    // Join the parts with comma separator
                    String updatedLine = String.join(",", parts);
                    // Replace the line in the list
                    lines.set(i, updatedLine);
                    break;
                }
            }

            // Write the updated lines back to the file
            Files.write(usersFile, lines);
            
         // Update the user object with the new information
            user.setRealName(nameField.getText());
            user.setSurname(surnameField.getText());
            user.setNickname(nicknameField.getText());
            int age = Integer.parseInt(ageField.getText());
            user.setAge(age);
            user.setPassword(new String(passwordField.getPassword()));
            user.setEmail(emailField.getText());
            user.setUserType(UserTier.valueOf(userTypeField.getText().toUpperCase()));

            // Check if the update was successful
            System.out.println("After Update:");
            for (String line : lines) {
                String[] parts = line.split(",");
                System.out.println("Name: " + parts[0]);
                System.out.println("Surname: " + parts[1]);
                System.out.println("Nickname: " + parts[2]);
                System.out.println("Age: " + parts[3]);
                System.out.println("Password: " + parts[4]);
                System.out.println("Email: " + parts[6]);
                System.out.println("UserType: " + parts[5]);
            }

            Logger.LogInfo(user.getNickname() + ": your changes updated.");
        } catch (IOException e) {
            Logger.LogError("Error occurred while updating user information: " + e.getMessage());
        }
        
        
    }


}



    