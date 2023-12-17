package photoCloudApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import logging.Logger;
import photo.Photo;
import user.User;
import user.UserTier;

public class PublicProfilePage extends JPanel {
    private User searchedUser;
    private List<Photo> userPhotos;

    // Components
    private JLabel profilePhotoLabel;
    private JLabel nameLabel;
    private JLabel nicknameLabel;
    private JLabel ageLabel;
    private JPanel userPhotosPanel;
    /**
     * Creates a new instance of PublicProfilePage.
     * 
     * @param searchedUser The user whose public profile is being displayed.
     * @param user         The currently logged-in user.
     */
    public PublicProfilePage(User searchedUser, User user) {
        this.searchedUser = searchedUser;
        this.userPhotos = new ArrayList<>();

        setLayout(new BorderLayout());

        // Top Panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton logoutButton = new JButton("Logout");
        logoutButton.setMargin(new Insets(10, 10, 10, 10));
        Font boldFont = new Font(logoutButton.getFont().getName(), Font.BOLD, logoutButton.getFont().getSize());
        logoutButton.setFont(boldFont);
        logoutButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PublicProfilePage.this);
            LoginPage loginPage = new LoginPage();
            
            Logger.LogInfo(user.getNickname()+ ": user has logged out.");
            
            loginPage.setBackground(Color.BLACK);
            frame.setContentPane(loginPage);
            frame.revalidate();
            frame.repaint();
        });

        JButton discoverPageButton = new JButton("Discover Page");
        discoverPageButton.setFont(boldFont);
        discoverPageButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PublicProfilePage.this);
            DiscoverPage discoverPage = new DiscoverPage(user);
            frame.setContentPane(discoverPage);
            frame.revalidate();
            frame.repaint();
        });

        topPanel.add(logoutButton, BorderLayout.WEST);
        topPanel.add(discoverPageButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Profile Photo
//        profilePhotoLabel = new JLabel();
//        profilePhotoLabel.setHorizontalAlignment(SwingConstants.CENTER);
//        profilePhotoLabel.setVerticalAlignment(SwingConstants.CENTER);
//        loadProfilePhoto();
//        topPanel.add(profilePhotoLabel, BorderLayout.CENTER);

        // User Information
        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcInfo = new GridBagConstraints();
        gbcInfo.gridx = 0;
        gbcInfo.gridy = 0;
        gbcInfo.insets = new Insets(5, 5, 5, 5);
        gbcInfo.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font(Font.SANS_SERIF, Font.BOLD, 14);
        Font valueFont = new Font(Font.SANS_SERIF, Font.PLAIN, 14);

        // Name
        JLabel nameLabel = new JLabel("Name and Surname:");
        nameLabel.setFont(labelFont);
        infoPanel.add(nameLabel, gbcInfo);

        gbcInfo.gridx++;
        JLabel nameValueLabel = new JLabel(searchedUser.getRealName() + " " + searchedUser.getSurname());
        nameValueLabel.setFont(valueFont);
        infoPanel.add(nameValueLabel, gbcInfo);

        gbcInfo.gridy++;
        gbcInfo.gridx = 0;

        // Nickname
        JLabel nicknameLabel = new JLabel("Nickname:");
        nicknameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nicknameLabel.setVerticalAlignment(SwingConstants.CENTER);
        nicknameLabel.setFont(labelFont);
        infoPanel.add(nicknameLabel, gbcInfo);

        gbcInfo.gridx++;
        JLabel nicknameValueLabel = new JLabel(searchedUser.getNickname());
        nicknameValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nicknameValueLabel.setVerticalAlignment(SwingConstants.CENTER);
        nicknameValueLabel.setFont(valueFont);
        infoPanel.add(nicknameValueLabel, gbcInfo);

        gbcInfo.gridy++;
        gbcInfo.gridx = 0;

        // Age
        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ageLabel.setVerticalAlignment(SwingConstants.CENTER);
        ageLabel.setFont(labelFont);
        infoPanel.add(ageLabel, gbcInfo);

        gbcInfo.gridx++;
        JLabel ageValueLabel = new JLabel(String.valueOf(searchedUser.getAge()));
        ageValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ageValueLabel.setVerticalAlignment(SwingConstants.CENTER);
        ageValueLabel.setFont(valueFont);
        infoPanel.add(ageValueLabel, gbcInfo);

        gbcInfo.gridx = 0;
        gbcInfo.gridy++;
        gbcInfo.gridwidth = 2;
     // Profile Photo
        profilePhotoLabel = new JLabel();
        profilePhotoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        profilePhotoLabel.setVerticalAlignment(SwingConstants.CENTER);
        loadProfilePhoto();

        infoPanel.add(profilePhotoLabel, gbcInfo);
        add(infoPanel, BorderLayout.SOUTH);

        // User Photos
        userPhotosPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        displayUserPhotos(gbc);

        JScrollPane scrollPane = new JScrollPane(userPhotosPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Displays the user's photos in the user photos panel.
     * 
     * @param gbc The constraints for grid bag layout.
     */
    private void displayUserPhotos(GridBagConstraints gbc) {
        // Clear the user photos panel
        userPhotosPanel.removeAll();
        userPhotosPanel.revalidate();
        userPhotosPanel.repaint();

        // Get the uploaded photos of the searched user
        getPhotosForUser();

        // Display the user's photos in the user photos panel
        for (Photo photo : userPhotos) {
            JButton photoLabel = createPhotoLabel(photo);
            userPhotosPanel.add(photoLabel, gbc);
            gbc.gridy++;
        }

        // Update the layout of the user photos panel
        userPhotosPanel.revalidate();
        userPhotosPanel.repaint();
    }

    /**
     * Loads the profile photo of the searched user and displays it.
     */
    private void loadProfilePhoto() {
        String line;

        try (BufferedReader reader = new BufferedReader(new FileReader("src/profilePics/profilePics.txt"))) {
            boolean profilePhotoFound = false;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length >= 2 && parts[0].equals(searchedUser.getNickname())) {
                    // User's profile picture found
                    File file = new File(parts[1]);
                    Image originalImage = ImageIO.read(file);
                    Image resizedImage = originalImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                    ImageIcon profilePhotoIcon = new ImageIcon(resizedImage);
                    profilePhotoLabel.setIcon(profilePhotoIcon);
                    profilePhotoFound = true;
                    break;
                }
            }
            
            if (!profilePhotoFound) {
                // Default profile photo
                Image defaultImage = ImageIO.read(new File("src/profilePics/zyro-image.png"));
                Image resizedDefaultImage = defaultImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                ImageIcon defaultProfilePhotoIcon = new ImageIcon(resizedDefaultImage);
                profilePhotoLabel.setIcon(defaultProfilePhotoIcon);
            }
        } catch (IOException e) {
            Logger.LogError(e.getMessage());
        }
    }

    /**
     * Retrieves the uploaded photos of the searched user.
     */
    private void getPhotosForUser() {
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader("src/imagesInfo.txt"))) {
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0 && parts[0].equals(searchedUser.getNickname())) {
                    String imagePath = parts[1];
                    File imageFile = new File(imagePath);
                    Image originalImage = ImageIO.read(imageFile);
                    Image resizedImage = originalImage.getScaledInstance(400, 200, Image.SCALE_FAST);
                    Photo photo = new Photo(resizedImage, searchedUser, imagePath);
                    userPhotos.add(photo);
                }
            }
        } catch (IOException e) {
            Logger.LogError(e.getMessage());
        }
    }
    
    /**
     * Creates a button with the specified photo and sets its action listener.
     * 
     * @param photo The photo for the button.
     * @return The created JButton object.
     */
    private JButton createPhotoLabel(Photo photo) {
        JButton photoLabel = new JButton(new ImageIcon(photo.getImage()));
        photoLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        photoLabel.addActionListener(e -> {
            PhotoPanel photoPanel = new PhotoPanel(new Photo(photo.getImage(), searchedUser, photo.getImagePath()), searchedUser);
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PublicProfilePage.this);
            frame.setContentPane(photoPanel);
            frame.revalidate();
            frame.repaint();
        });
        return photoLabel;
    }
}
