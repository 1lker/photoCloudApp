/**
 * The ShareImage class represents a panel for sharing an image.
 * It allows the user to upload an image, add a description, and submit it to be saved in a database.
 * This class extends JPanel and serves as a graphical user interface component for sharing images.
 *
 * Usage:
 * 1. Create an instance of ShareImage with the specified user.
 * 2. Add the ShareImage instance to a parent container such as a JFrame.
 * 3. The panel will display options for uploading an image, adding a description, and submitting the image.
 * 4. When the image is submitted, it will be saved in a database along with the provided description.
 *
 * Example:
 * User user = // retrieve the current user
 * ShareImage shareImage = new ShareImage(user);
 * frame.getContentPane().add(shareImage);
 *
 * Note: The ShareImage class relies on other classes such as User and Logger for its functionality.
 * Make sure to have these classes available in the same package or import them as needed.
 */

package photoCloudApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JScrollPane;

import logging.Logger;
import user.User;

/**
 * This class represents a panel for sharing an image.
 */
public class ShareImage extends JPanel {

    // image database:
    private static final String IMAGES_FILE_PATH = "src/imagesInfo.txt";

    // Delimiter to separate:
    private static final String DELIMITER = ",";

    private static JButton sharePhotoButton;

    private static JLabel imageDescLabel;
    private static JTextArea imageDescription;

    // Panels
    private JPanel topPanel;
    private JPanel centerPanel;

    // Buttons:
    private JButton profilePageButton;
    private JButton logoutButton;
    private JButton submitImageButton;

    private User user;

    private String imageInformation = " ";
    
    /**
     * Constructs a ShareImage object.
     *
     * @param user the User object associated with the ShareImage panel
     */
    public ShareImage(User user) {
        super();
        this.user = user;

        topPanel = new JPanel(new BorderLayout());
        // Margin settings: with 10px
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        logoutButton = new JButton("Logout");

        // Padding settings: with 10px
        logoutButton.setMargin(new Insets(10, 10, 10, 10));

        // Bold Font settings:
        Font boldFont = new Font(logoutButton.getFont().getName(), Font.BOLD, logoutButton.getFont().getSize());
        logoutButton.setFont(boldFont);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ShareImage.this);

                LoginPage loginPage = new LoginPage();
                Logger.LogInfo(user.getNickname()+ ": user has logged out.");

                
                loginPage.setBackground(Color.BLACK);

                frame.setContentPane(loginPage);

                frame.revalidate();
                frame.repaint();
            }
        });

        topPanel.add(logoutButton, BorderLayout.WEST);

        profilePageButton = new JButton("Profile Page");

        // Bold Font Settings:
        profilePageButton.setFont(boldFont);

        profilePageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Remove the current JPanel from its parent JFrame:
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ShareImage.this);

                // Create an instance of Sign Up Page:
                ProfilePage profilePage = new ProfilePage(user);

                // Now: set your current page this signup page:,
                frame.setContentPane(profilePage);

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

        // General Layout Settings:
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Reset anchor for other components
        gbc.anchor = GridBagConstraints.CENTER;

        // Position:
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3; // to span 2 columns

        imageDescLabel = new JLabel("Image Description");
        imageDescLabel.setToolTipText("image description");
        imageDescLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageDescLabel.setFont(new Font(imageDescLabel.getFont().getName(), Font.BOLD, 16));

        centerPanel.add(imageDescLabel, gbc);

        // Position:
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3; // to span 2 columns

        imageDescription = new JTextArea(10, 40);
        imageDescription.setToolTipText("image description");

        // make image description field scrollable
        JScrollPane descScrollPane = new JScrollPane(imageDescription);

        centerPanel.add(descScrollPane, gbc);

        // Position:
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 5; // to span 2 columns

        // Upload the picture to anywhere on the computer to the allocated position in my image folder
        sharePhotoButton = new JButton("Upload Picture");
        sharePhotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent w) {
                imageInformation = uploadDatabaseSelectedPhoto(user);
            }
        });
        Font boldFont2 = new Font(sharePhotoButton.getFont().getName(), Font.BOLD, 16);

        sharePhotoButton.setFont(boldFont2);
        sharePhotoButton.setForeground(Color.DARK_GRAY);
        sharePhotoButton.setMargin(new Insets(10, 10, 10, 10));

        centerPanel.add(sharePhotoButton, gbc);

        // Position:
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 5; // to span 2 columns

        // Submit button to submit and save the content to the database (txt):
        submitImageButton = new JButton("Submit");
        submitImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent w) {
                String imageDescriptionText = imageDescription.getText();
                imageDescriptionText = imageDescriptionText.replace("\n", "\\n");
                imageInformation = imageInformation + DELIMITER + imageDescriptionText + DELIMITER + 0 + DELIMITER + 0 + DELIMITER + "[]";

                System.out.println(imageInformation);

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(IMAGES_FILE_PATH, true))) {
                    writer.write(imageInformation);
                    writer.newLine();
                    writer.flush();
                    // User log for sharing the image:
                    Logger.LogInfo("Post has been sent.");

                    JOptionPane.showMessageDialog(null, "Post successfully shared.", "Post Share Successful", JOptionPane.INFORMATION_MESSAGE);

                    // After sharing the post, send the user to their profile page
                    // Remove the current JPanel from its parent JFrame:
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ShareImage.this);

                    // Create an instance of the DiscoverPage:
                    DiscoverPage discoverPage = new DiscoverPage(user);

                    // Set the content pane to the DiscoverPage:
                    frame.setContentPane(discoverPage);

                    // Recalculate the page values:
                    frame.revalidate();
                    frame.repaint();
                } catch (IOException ex) {
                    System.out.println("An error occurred while writing user information: " + ex.getMessage());
                    Logger.LogError("An error occurred while writing user information: " + ex.getMessage());
                    JOptionPane.showMessageDialog(submitImageButton, "Post sharing process failed.", "Post Share Fail", JOptionPane.ERROR_MESSAGE);
                }

                imageDescription.setText("");
            }
        });
        submitImageButton.setFont(new Font(submitImageButton.getFont().getName(), Font.BOLD, 16));
        submitImageButton.setForeground(Color.DARK_GRAY);
        submitImageButton.setMargin(new Insets(10, 10, 10, 10));
        centerPanel.add(submitImageButton, gbc);

        add(centerPanel, BorderLayout.CENTER);
    }
    /**
     * Uploads the selected photo to the database.
     *
     * @param user the User object associated with the ShareImage panel
     * @return the image information
     */
    private String uploadDatabaseSelectedPhoto(User user) {
        String imageInfo = "";

        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try {
                BufferedImage originalImage = ImageIO.read(selectedFile);
                int width = 1000;
                int height = 500;
                BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = resizedImage.createGraphics();
                g2d.drawImage(originalImage, 0, 0, width, height, null);
                g2d.dispose();

                // Save the resized image
                File resizedImageFile = new File("src/images/resized_" + selectedFile.getName());
                ImageIO.write(resizedImage, "jpg", resizedImageFile);

                Path imagePath = Paths.get(selectedFile.getAbsolutePath());
                Path targetImagePath = Paths.get("src/images/" + selectedFile.getName());
                Files.move(imagePath, targetImagePath, StandardCopyOption.REPLACE_EXISTING);

                System.out.println("Image uploaded to images. Image: " + selectedFile.getName());
                JFrame frame = new JFrame();
                frame.setLocationRelativeTo(null);
                JOptionPane.showMessageDialog(frame, "Image uploading is done.", "Image Upload Successful", JOptionPane.INFORMATION_MESSAGE);
                Logger.LogInfo("Image uploaded to images. Image: " + selectedFile.getName());

                imageInfo = user.getNickname() + DELIMITER + targetImagePath.toString() + DELIMITER + user.getUserType();

            } catch (IOException ex) {
                Logger.LogError(ex.getMessage());
                JOptionPane.showMessageDialog(this, "An error occurred while choosing the image.", "Image Selection Failed", JOptionPane.ERROR_MESSAGE);
            }
        }

        return imageInfo;
    }
}
