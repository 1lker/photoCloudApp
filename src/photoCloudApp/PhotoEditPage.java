/**
 * The PhotoEditPage class represents a JPanel for editing photos.
 * It allows the user to select an image, apply various filters to the image, and save the edited image.
 * This class extends JPanel and serves as a graphical user interface component for the photo editing page.
 * It contains buttons for selecting an image, applying filters, and navigating to other pages.
 *
 * Usage:
 * 1. Create an instance of PhotoEditPage with the current user.
 * 2. Add the PhotoEditPage instance to a parent container such as a JFrame.
 * 3. The user can select an image using the "Select Image" button.
 * 4. Various filter buttons are available to apply different filters to the image.
 * 5. The user can save the edited image using the "Save" button.
 * 6. The user can navigate to the Discover page using the "Discover Page" button.
 * 7. The user can navigate to the Profile page using the "Profile Page" button.
 * 8. The user can logout using the "Logout" button.
 *
 * Example:
 * User user = // retrieve the current user
 * PhotoEditPage editPage = new PhotoEditPage(user);
 * frame.getContentPane().add(editPage);
 *
 * Note: The PhotoEditPage class relies on other classes such as ImageMatrix, Filter, and User for its functionality.
 * Make sure to have these classes available in the same package or import them as needed.
 */

package photoCloudApp;

import javax.imageio.ImageIO;
import javax.swing.*;

import logging.Logger;
import photo.*;
import user.User;
import user.UserTier;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
/**
 * The PhotoEditPage class represents a JPanel for editing photos.
 * It allows the user to select an image, apply various filters to the image, and save the edited image.
 */
public class PhotoEditPage extends JPanel {

    private ImageMatrix imageMatrix;
    private JLabel imageLabel;
    private User user;
    private JButton selectImageButton;
    private JButton saveButton;
    private JButton logoutButton;
    private JButton discoverPageButton;
    private JButton profilePageButton;

    /**
     * Constructs a PhotoEditPage object with the given user.
     *
     * @param user The current user.
     * @throws IOException If an I/O error occurs while reading the default image.
     */
    public PhotoEditPage(User user) throws IOException {
        this.imageMatrix = ImageSecretary.readResourceImage("default.png", ".jpg");
        this.user = user;
        
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topTopPanel = new JPanel(new BorderLayout());
        topTopPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        logoutButton = new JButton("Logout");
        
        // Padding settings: with 10px
//        logoutButton.setMargin(new Insets(10, 10, 10, 10));
        
        // Bold Font settings:
        Font boldFont = new Font(logoutButton.getFont().getName(), Font.BOLD, logoutButton.getFont().getSize());
        logoutButton.setFont(boldFont);
        
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PhotoEditPage.this);
                
                LoginPage loginPage = new LoginPage();
                
                Logger.LogInfo(user.getNickname()+ ": user has logged out.");
                
                loginPage.setBackground(Color.BLACK);
                
                frame.setContentPane(loginPage);
                
                frame.revalidate();
                frame.repaint();
            }
        });
        
        
        
        discoverPageButton = new JButton("Discover Page");
        
        // Bold Font Settings:
        discoverPageButton.setFont(boldFont);
        
        discoverPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Remove the current JPanel from its parent JFrame:
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PhotoEditPage.this);
                
                // Create an instance of Sign Up Page:
                DiscoverPage discoverPage = new DiscoverPage(user);
                
                // Now: set your current page this signup page:,
                frame.setContentPane(discoverPage);
                
                // Now we need to recalculate the page values:
                frame.revalidate();
                frame.repaint();            
            }
        });
        
        profilePageButton = new JButton("Profile Page");
        
        // Bold Font Settings:
        profilePageButton.setFont(boldFont);
        
        profilePageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Remove the current JPanel from its parent JFrame:
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PhotoEditPage.this);
                
                // Create an instance of Sign Up Page:
                ProfilePage profilePage = new ProfilePage(user);
                
                // Now: set your current page this signup page:,
                frame.setContentPane(profilePage);
                
                // Now we need to recalculate the page values:
                frame.revalidate();
                frame.repaint();            
            }
        });
        
        selectImageButton = new JButton("Görsel Seç");
        selectImageButton.setFont(new Font(selectImageButton.getFont().getName(), Font.BOLD, 16));
        selectImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(PhotoEditPage.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        BufferedImage bufferedImage = ImageIO.read(selectedFile);
                        ImageMatrix newImageMatrix = new ImageMatrix(bufferedImage);
                        setImage(newImageMatrix);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        topTopPanel.add(selectImageButton, BorderLayout.WEST);
        topTopPanel.add(discoverPageButton, BorderLayout.CENTER);

        saveButton = new JButton("KAYDET");
        saveButton.setFont(new Font(saveButton.getFont().getName(), Font.BOLD, 16));
        saveButton.setMargin(new Insets(10, 10, 10, 10));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showSaveDialog(PhotoEditPage.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        ImageIO.write(imageMatrix.getBufferedImage(), "jpg", selectedFile);
                        JOptionPane.showMessageDialog(PhotoEditPage.this, "Görsel kaydedildi.");
                        Logger.LogInfo("Image has been saved.");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(PhotoEditPage.this, "Görsel kaydedilirken bir hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
                        Logger.LogError(ex.getMessage());
                    }
                }
            }
        });
        topTopPanel.add(saveButton, BorderLayout.EAST);
        
        topPanel.add(logoutButton, BorderLayout.WEST);
        topPanel.add(profilePageButton, BorderLayout.EAST);
        
        topPanel.add(topTopPanel, BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());

        JButton blurButton = new JButton("Apply Blur Filter");
        blurButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (user.getUserType() == UserTier.FREE || user.getUserType() == UserTier.HOBBYIST) {
                    applyFilter("Blur Filter", new BlurFilter(getFilterIntensity()));
                } else {
                    JOptionPane.showMessageDialog(PhotoEditPage.this, "You don't have permission to apply this filter.", "Permission Denied", JOptionPane.WARNING_MESSAGE);
                    Logger.LogError("You don't have permission to apply this filter. :" + "Blur Filter" );
                }
            }
        });

        JButton sharpenButton = new JButton("Apply Sharpen Filter");
        sharpenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (user.getUserType() == UserTier.FREE || user.getUserType() == UserTier.HOBBYIST) {
                    applyFilter("Sharpen Filter", new SharpenFilter(getFilterIntensity()));
                } else {
                    JOptionPane.showMessageDialog(PhotoEditPage.this, "You don't have permission to apply this filter.", "Permission Denied", JOptionPane.WARNING_MESSAGE);
                    Logger.LogError("You don't have permission to apply this filter. :" + "Sharpen Filter" );

                }
            }
        });

        JButton contrastButton = new JButton("Apply Contrast Filter");
        contrastButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (user.getUserType() == UserTier.HOBBYIST || user.getUserType() == UserTier.PROFESSIONAL) {
                    applyFilter("Contrast Filter", new ContrastFilter(getFilterIntensity()));
                } else {
                    JOptionPane.showMessageDialog(PhotoEditPage.this, "You don't have permission to apply this filter.", "Permission Denied", JOptionPane.WARNING_MESSAGE);
                    Logger.LogError("You don't have permission to apply this filter. :" + "Contrast Filter" );

                }
            }
        });

        JButton edgeDetectionButton = new JButton("Apply Edge Detection Filter");
        edgeDetectionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (user.getUserType() == UserTier.PROFESSIONAL) {
                    applyFilter("Edge Detection Filter", new EdgeDetectionFilter());
                } else {
                    JOptionPane.showMessageDialog(PhotoEditPage.this, "You don't have permission to apply this filter.", "Permission Denied", JOptionPane.WARNING_MESSAGE);
                    Logger.LogError("You don't have permission to apply this filter. :" + "Edge Detection Filter" );

                }
            }
        });

        JButton grayScaleButton = new JButton("Apply Grayscale Filter");
        grayScaleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (user.getUserType() == UserTier.PROFESSIONAL) {
                    applyFilter("Grayscale Filter", new GrayScaleFilter());
                } else {
                    JOptionPane.showMessageDialog(PhotoEditPage.this, "You don't have permission to apply this filter.", "Permission Denied", JOptionPane.WARNING_MESSAGE);
                    Logger.LogError("You don't have permission to apply this filter. :" + "Grayscale Filter" );

                }
            }
        });

        JButton brightnessButton = new JButton("Apply Brightness Filter");
        brightnessButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (user.getUserType() == UserTier.HOBBYIST || user.getUserType() == UserTier.PROFESSIONAL) {
                    applyFilter("Brightness Filter", new BrightnessFilter(getFilterIntensity()));
                } else {
                    JOptionPane.showMessageDialog(PhotoEditPage.this, "You don't have permission to apply this filter.", "Permission Denied", JOptionPane.WARNING_MESSAGE);
                    Logger.LogError("You don't have permission to apply this filter. :" + "Brightness Filter" );

                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(blurButton);
        buttonPanel.add(sharpenButton);
        buttonPanel.add(contrastButton);
        buttonPanel.add(edgeDetectionButton);
        buttonPanel.add(grayScaleButton);
        buttonPanel.add(brightnessButton);

        centerPanel.add(buttonPanel, BorderLayout.PAGE_END);

        imageLabel = new JLabel(new ImageIcon(imageMatrix.getBufferedImage()));
        JScrollPane scrollPane = new JScrollPane(imageLabel);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * Applies the specified filter to the image.
     *
     * @param filterName The name of the filter.
     * @param filter     The filter to apply.
     */
    private void applyFilter(String filterName, Filter filter) {
        long startTime = System.currentTimeMillis();

        imageMatrix = filter.apply(imageMatrix);

        updateImage();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        Logger.LogInfo("Filter: " + filterName + ", Execution Time: " + executionTime + "ms");
    }
    /**
     * Updates the displayed image with the current imageMatrix.
     */
    private void updateImage() {
        BufferedImage resizedImage = resizeImage(imageMatrix.getBufferedImage(), 1000, (int) (imageMatrix.getHeight() * (1000.0 / imageMatrix.getWidth())));
        ImageIcon icon = new ImageIcon(resizedImage);
        imageLabel.setIcon(icon);
    }
    /**
     * Resizes the given image to the specified width and height.
     *
     * @param originalImage The original image to resize.
     * @param width         The desired width.
     * @param height        The desired height.
     * @return The resized image.
     */
    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, width, height, null);
        g2d.dispose();
        return resizedImage;
    }
    /**
     * Sets the image to the given ImageMatrix.
     *
     * @param newImageMatrix The new image matrix.
     */
    public void setImage(ImageMatrix newImageMatrix) {
        this.imageMatrix = newImageMatrix;
        updateImage();
    }
    /**
     * Prompts the user to enter the filter intensity (0-100) and returns the entered value.
     *
     * @return The filter intensity.
     */
    private int getFilterIntensity() {
        String input = JOptionPane.showInputDialog(this, "Enter filter intensity (0-100):");
        try {
            int intensity = Integer.parseInt(input);
            if (intensity < 0 || intensity > 100) {
                throw new NumberFormatException();
            }
            return intensity;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a number between 0 and 100.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return getFilterIntensity();
        }
    }
}

