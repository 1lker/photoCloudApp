/**
 * The PhotoPanel class represents a panel that displays a photo and its details using Swing components.
 * It provides functionalities for displaying the photo's thumbnail, owner's information, description, interaction buttons (like, dislike),
 * comments, and user interactions such as adding comments and deleting photos (available to owners and admins).
 * This class extends JPanel and serves as a graphical user interface component for displaying a photo and its details.
 *
 * Usage:
 * 1. Create an instance of PhotoPanel with the specified photo and user.
 * 2. Add the PhotoPanel instance to a parent container such as a JFrame.
 * 3. The photo's thumbnail, owner's information, description, like and dislike buttons, and comments will be displayed.
 * 4. The user can interact with the photo by liking or disliking it, adding comments, and deleting photos (if the user is the owner or an admin).
 *
 * Example:
 * Photo photo = // retrieve the photo to be displayed
 * User user = // retrieve the current user
 * PhotoPanel photoPanel = new PhotoPanel(photo, user);
 * frame.getContentPane().add(photoPanel);
 *
 * Note: The PhotoPanel class relies on other classes such as Photo, User, and CommentPanel for its functionality.
 * Make sure to have these classes available in the same package or import them as needed.
 */

package photoCloudApp;

import java.util.List;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import logging.Logger;
import photo.Comment;
import photo.ImageMatrix;
import photo.ImageSecretary;
import photo.Photo;
import user.User;
import user.UserTier;
/**
 * The PhotoPanel class represents a panel that displays a photo and its details using Swing components.
 */
public class PhotoPanel extends JPanel {
    private Photo photo;
    private JLabel fullImagePanel;
    private JLabel ownerLabel;
    private JTextArea descriptionTextArea;
    private JButton likeButton;
    private JButton dislikeButton;
    private JLabel likeCountLabel;
    private JLabel dislikeCountLabel;
    private List<CommentPanel> commentPanels;
    
    // Panels
    private JPanel topPanel;
    private JPanel centerPanel;
    private JPanel bottomPanel;
    
    // Buttons:
    private JButton profilePageButton;
    private JButton logoutButton;
    
    // image database:
    private static final String IMAGES_FILE_PATH = "src/imagesInfo.txt";
    
    // Delimiter to separate:
    private static final String DELIMITER = ",";
    
    /**
     * Constructs a PhotoPanel object with the specified photo and user.
     *
     * @param photo the photo to be displayed
     * @param user the user viewing the photo
     */
    public PhotoPanel(Photo photo, User user) {
        this.photo = photo;
        commentPanels = new ArrayList<>();
        
        // Implement the GUI for displaying a photo using Swing components
        
        // Display the photo's thumbnail, owner's nickname, description, interaction buttons (like, dislike), and comments
        // Handle user interactions like clicking on the like or dislike button, adding a comment, and replying to a comment
        
        topPanel = new JPanel(new BorderLayout());
        // Margin settings: with 10px
        topPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        logoutButton = new JButton("Logout");
        
        // Padding settings: with 10px
        logoutButton.setMargin(new Insets(10, 10, 10, 10));
        
        // Bold Font settings:
        Font boldFont = new Font(logoutButton.getFont().getName(), Font.BOLD, logoutButton.getFont().getSize());
        logoutButton.setFont(boldFont);
        
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PhotoPanel.this);
                
                LoginPage loginPage = new LoginPage();
                
                loginPage.setBackground(Color.BLACK);
                
                Logger.LogInfo(user.getNickname()+ ": user has logged out.");
                
                frame.setContentPane(loginPage);
                
                frame.revalidate();
                frame.repaint();
            }
        });
        
        topPanel.add(logoutButton, BorderLayout.WEST);
        
        profilePageButton = new JButton("Discover Page");
        
        // Bold Font Settings:
        profilePageButton.setFont(boldFont);
        
        profilePageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Remove the current JPanel from its parent JFrame:
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PhotoPanel.this);
                
                // Create an instance of Sign Up Page:
                DiscoverPage discoverPage = new DiscoverPage(user);
                
                // Now: set your current page this signup page:,
                frame.setContentPane(discoverPage);
                
                // Now we need to recalculate the page values:
                frame.revalidate();
                frame.repaint();            
            }
        });
        
        topPanel.add(profilePageButton, BorderLayout.EAST);
        
        
        setLayout(new BorderLayout());
        
        add(topPanel, BorderLayout.NORTH);
        


        
        // Now: create a Center Panel to rest of it:
        centerPanel = new JPanel(new GridBagLayout());
        
        // Basic Layout Settings:
        
        GridBagConstraints gbc = new GridBagConstraints();
        // It gives the grid 5px for every direction:
        gbc.insets = new Insets(5,5,5,5);
        // It makes the grid horizontally filled:
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int targetWidth = 1080; // Hedef genişlik
        int targetHeight = 720; // Hedef yükseklik

        // Görseli yeniden boyutlandır
        
        Image resizedImage = photo.getImage().getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        
        // Full Image:
        fullImagePanel = new JLabel(new ImageIcon(resizedImage));
        fullImagePanel.setPreferredSize(new Dimension(1080, 720));
        fullImagePanel.setMaximumSize(new Dimension(1080, 720));   // Maximum size

        fullImagePanel.setHorizontalAlignment(SwingConstants.CENTER);
        fullImagePanel.setVerticalAlignment(SwingConstants.CENTER);
        // Panel position:
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(fullImagePanel, gbc);
        // Change the Panel's sizes to Image size:
        // Panel position:


        // Owner Label
        ownerLabel = new JLabel("Owner Of Picture Real Name: " + photo.getOwner().getRealName() + " Nickname: " + photo.getOwner().getNickname());
        ownerLabel.setFont(boldFont);
        ownerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // Label position:
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(ownerLabel, gbc);

        
        // Description Text Area
        if (photo.getDescription() != "") {
            descriptionTextArea = new JTextArea(photo.getDescription());
            descriptionTextArea.setEditable(false);
            descriptionTextArea.setLineWrap(true); // we can wrap the lines
            descriptionTextArea.setWrapStyleWord(true);
            // Make it scrollable, it can be too long:
            JScrollPane descScrollPane = new JScrollPane(descriptionTextArea);
            descScrollPane.setPreferredSize(new Dimension(300, 100)); // change the preferred size to Width : 300 / Height : 100
            // descScrollPane position:
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            centerPanel.add(descScrollPane, gbc);
        }
        
        
        // Like Count Label
        likeCountLabel = new JLabel(Integer.toString(photo.getLikes()));
        likeCountLabel.setFont(new Font(likeCountLabel.getFont().getName(), Font.ITALIC, 16));
        likeCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // likeCountLabel position:
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(likeCountLabel, gbc);
        
        // Dislike Count Label
        dislikeCountLabel = new JLabel(Integer.toString(photo.getDislikes()));
        dislikeCountLabel.setFont(new Font(dislikeCountLabel.getFont().getName(), Font.ITALIC, 16));
        dislikeCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // likeCountLabel position:
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(dislikeCountLabel, gbc);

        // Like Button
        likeButton = new JButton("Like");
        likeButton.setIcon(new ImageIcon("src/images/zyro-image-like.png"));
        likeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent w) {
                photo.addLike();
                likeCountLabel.setText(Integer.toString(photo.getLikes()));
                
                // imagesInfo.txt dosyasını güncelleyin
            }
        });
        // likeButton position:
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(likeButton, gbc);
        

        // Dislike Button
        dislikeButton = new JButton("Dislike");
        dislikeButton.setIcon(new ImageIcon("src/images/zyro-image.png"));
        dislikeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent w) {
                photo.addDislike();
                dislikeCountLabel.setText(Integer.toString(photo.getDislikes()));
            }
            
        });
        
        // dislikeButton position:
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(dislikeButton, gbc);
        
        
        
        
        // Comment Panels
        // Comment position:
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        
        JButton commentButton = new JButton("ADD COMMENT");
        commentButton.setFont(boldFont);
        commentButton.setMargin(new Insets(10, 6, 10, 6));
        commentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent w) {
                String commentText = JOptionPane.showInputDialog(null, "Comment:");
                if (commentText != null && !commentText.isEmpty()) {
                    // Gönderilen yorumu işleyin
                    Comment comment = new Comment(user, commentText);
                    
                    // imagesInfo.txt dosyasına yorumu ekleyin
                    updateImagesInfoFile(comment);
                }
            }
            
        });
        
        centerPanel.add(commentButton, gbc);
        
        // Comment TextField
        JTextField commentTextField = new JTextField();
        commentTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String commentText = commentTextField.getText();
                if (commentText != null && !commentText.isEmpty()) {
                    // Gönderilen yorumu işleyin
                    Comment comment = new Comment(user, commentText);
                    
                    // imagesInfo.txt dosyasına yorumu ekleyin
                    updateImagesInfoFile(comment);
                    
                    // Clear the comment text field
                    commentTextField.setText("");
                }
            }
        });
        
        // Comment text field position:
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(commentTextField, gbc);

        
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane, BorderLayout.CENTER);
        
        bottomPanel = new JPanel(new BorderLayout());

        // Description Text Area

        descriptionTextArea = new JTextArea(photo.getDescription());
        descriptionTextArea.setEditable(false);
        descriptionTextArea.setLineWrap(true); // we can wrap the lines
        descriptionTextArea.setWrapStyleWord(true);
            // Make it scrollable, it can be too long:
        JScrollPane descScrollPane = new JScrollPane(descriptionTextArea);
        descScrollPane.setPreferredSize(new Dimension(300, 50)); // change the preferred size to Width : 300 / Height : 1

        bottomPanel.add(descScrollPane);
     // Set the initial description text

        
        add(bottomPanel, BorderLayout.SOUTH);
        updateTopPanel(user);
        
    }
    

    /**
     * Updates the imagesInfo.txt file with the given comment.
     * Adds the comment to the corresponding image entry in the file.
     *
     * @param comment The comment to be added.
     */
    private void updateImagesInfoFile(Comment comment) {
        try {
            String filePath = IMAGES_FILE_PATH;

            // Okunacak ve güncellenecek dosyayı açın
            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            List<String> lines = new ArrayList<>();
            
            // Dosyanın tüm satırlarını okuyun ve güncellemeleri yapın
            String line;
            while ((line = reader.readLine()) != null) {
                String[] imageInfo = line.split(DELIMITER);
                
                // Check if the current image info matches the current panel's image
                if (imageInfo.length >= 2 && imageInfo[1].equals(photo.getImagePath())) {
                    line += ",[" + comment.getUser().getNickname() + ": " + comment.getCommentText() + "]";
                }
                
                lines.add(line);
            }

            // Dosyayı kapatın
            reader.close();

            // Dosyayı yeniden yazın ve güncellenmiş satırları yazın
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (String updatedLine : lines) {
                writer.write(updatedLine);
                writer.newLine();
            }
            // Dosyayı kapatın
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Updates the top panel of the photo panel based on the current user.
     * If the user is the owner or an admin, adds a delete button to the top panel.
     *
     * @param user The current user.
     */
    private void updateTopPanel(User user) {
        boolean isOwner = photo.getOwner().getNickname().equals(user.getNickname());
        boolean isAdmin = user.getUserType() == UserTier.ADMIN;
        // Silme butonunu sadece sahibi veya admin görebilir
        if (isOwner || isAdmin) {
            JButton deleteButton = new JButton("Delete");
            deleteButton.setFont(new Font(deleteButton.getFont().getName(), Font.BOLD, deleteButton.getFont().getSize()));
            
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Görseli imagesInfo.txt'den sil
                    deleteImageInfo();
                    
                    // Ana pencereyi kapat
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PhotoPanel.this);
                    DiscoverPage discoverPage = new DiscoverPage(user);
                    
                    
                    // Now: set your current page this signup page:,
                    frame.setContentPane(discoverPage);
                    
                    // Now we need to recalculate the page values:
                    frame.revalidate();
                    frame.repaint();   
                }
            });
            
            topPanel.add(deleteButton, BorderLayout.CENTER);
        }
    }
    
    
    /**
     * Deletes the image entry from the imagesInfo.txt file.
     * Removes the line that corresponds to the current panel's image.
     */
    private void deleteImageInfo() {
        try {
            String filePath = IMAGES_FILE_PATH;

            // Okunacak ve güncellenecek dosyayı açın
            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            List<String> lines = new ArrayList<>();

            // Dosyanın tüm satırlarını okuyun ve silinecek satırı atlayın
            String line;
            while ((line = reader.readLine()) != null) {
                String[] imageInfo = line.split(DELIMITER);

                // Check if the current image info matches the current panel's image
                if (!(imageInfo.length >= 2 && imageInfo[1].equals(photo.getImagePath()))) {
                    lines.add(line);
                }
            }

            // Dosyayı kapatın
            reader.close();

            // Dosyayı yeniden yazın ve güncellenmiş satırları yazın
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (String updatedLine : lines) {
                writer.write(updatedLine);
                writer.newLine();
            }
            // Dosyayı kapatın
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

    

}
