/**
 * The DiscoverPage class represents the panel for the Discover page in the Photo Cloud Application.
 * It displays shared photos and allows user interactions such as clicking on a photo or owner's nickname,
 * adding a comment, and liking/disliking.
 *
 * This class extends JPanel and serves as a graphical user interface component for the Discover page.
 * It contains various Swing components such as buttons, labels, and panels to create the page layout and handle user interactions.
 *
 * Usage:
 * 1. Create an instance of DiscoverPage by passing the associated User object to the constructor.
 *    This User object represents the currently logged-in user.
 * 2. Add the DiscoverPage instance to a parent container such as a JFrame.
 * 3. The Discover page will display shared photos as panels with thumbnail, owner's nickname, description,
 *    interaction buttons, and comments. Users can interact with the photos by clicking on them, adding comments,
 *    and liking/disliking.
 *
 * Example:
 * User user = new User("username", "password");
 * DiscoverPage discoverPage = new DiscoverPage(user);
 * frame.getContentPane().add(discoverPage);
 *
 * Note: The DiscoverPage class relies on other classes such as Photo, PhotoPanel, User, and UserTier for its functionality.
 * Make sure to have these classes available in the same package or import them as needed.
 */


package photoCloudApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import logging.Logger;
import photo.Photo;
import photo.*;
import user.User;
import user.UserTier;
/**
 * The DiscoverPage class represents the panel for the Discover page in the Photo Cloud Application.
 * It displays shared photos and allows user interactions such as clicking on a photo or owner's nickname,
 * adding a comment, and liking/disliking.
 */
public class DiscoverPage extends JPanel {
    private List<Photo> photos;
    private List<PhotoPanel> photoPanels;
    private User user;

    // Panels
    private JPanel topPanel;
    private JPanel centerPanel;

    // Buttons:
    private JButton profilePageButton;
    private JButton logoutButton;
    private JButton denemeButton;
    private JButton editPicButton;

    // users file path:
    private static final String IMAGES_FILE_PATH = "src/imagesInfo.txt";
    private static final String USERS_FILE_PATH = "src/users.txt";

    /**
     * Constructs a new instance of the DiscoverPage class with the specified user.
     *
     * @param user The user associated with the page.
     */
    public DiscoverPage(User user) {
        super();

        Instant start = Instant.now(); // Başlangıç zamanını al

        this.photos = new ArrayList<>();
        this.photoPanels = new ArrayList<>();
        this.user = user;

        // generateImagePreviews();

        // Implement the GUI for the Discover page using Swing components
        // Display the shared photos as panels with thumbnail, owner's nickname, description, interaction buttons, and comments
        // Handle user interactions like clicking on a photo or its owner's nickname, adding a comment, and liking/disliking

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
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(DiscoverPage.this);

                LoginPage loginPage = new LoginPage();

                Logger.LogInfo(user.getNickname()+ ": user has logged out.");

                loginPage.setBackground(Color.BLACK);

                frame.setContentPane(loginPage);

                frame.revalidate();
                frame.repaint();
            }
        });

        topPanel.add(logoutButton, BorderLayout.WEST);

        editPicButton = new JButton("Photo Editor");

        // Padding settings: with 10px
        editPicButton.setMargin(new Insets(10, 10, 10, 10));

        editPicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(DiscoverPage.this);

                PhotoEditPage editPage = null;
                try {
                    editPage = new PhotoEditPage(user);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                frame.setContentPane(editPage);

                frame.revalidate();
                frame.repaint();
            }
        });

        topPanel.add(editPicButton, BorderLayout.CENTER);

        profilePageButton = new JButton("Profile Page");

        // Bold Font Settings:
        profilePageButton.setFont(boldFont);

        profilePageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Remove the current JPanel from its parent JFrame:
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(DiscoverPage.this);

                // Create an instance of Sign Up Page:
                ProfilePage profilePage = new ProfilePage(user);

                // Now: set your current page this signup page:,
                frame.setContentPane(profilePage);

                // Now we need to recalculate the page values:
                frame.revalidate();
                frame.repaint();
            }
        });

        // Logout and Profile Page buttons added to topPanel
        topPanel.add(logoutButton, BorderLayout.WEST);
        topPanel.add(profilePageButton, BorderLayout.EAST);

        // Add the topPanel to its parent panel
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH); // Add to top

        // Now: create a Center Panel to rest of it:
        centerPanel = new JPanel(new GridBagLayout());

        // General Layout Settings:
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // to span 2 columns

        denemeButton = new JButton("Share Picture");
        denemeButton.setMargin(new Insets(10, 10, 10, 10));

        // Bold Font settings:
        Font denemeButtonFont = new Font(logoutButton.getFont().getName(), Font.BOLD, logoutButton.getFont().getSize());
        logoutButton.setFont(denemeButtonFont);
        denemeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create an instance of Share Image Page:
                ShareImage shareImage = new ShareImage(user);
                // Remove the current JPanel from its parent JFrame:
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(DiscoverPage.this);
                // Now: set your current page this signup page:,
                frame.setContentPane(shareImage);
                // Now we need to recalculate the page values:
                frame.revalidate();
                frame.repaint();
            }
        });
        centerPanel.add(denemeButton, gbc);

        // Search TextField
        JTextField searchTextField = new JTextField("TO SEARCH TYPE AND ENTER");
        searchTextField.setHorizontalAlignment(SwingConstants.CENTER);
        searchTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchNickname = searchTextField.getText();
                if (searchNickname != null && !searchNickname.isEmpty()) {
                    User searchedUser = searchUser(searchNickname);
                    if (searchedUser != null) {
                        // Create an instance of Public Profile Page:
                        PublicProfilePage publicProfilePage = new PublicProfilePage(searchedUser, user);
                        // Remove the current JPanel from its parent JFrame:
                        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(DiscoverPage.this);
                        // Now: set your current page this public profile page:
                        frame.setContentPane(publicProfilePage);
                        // Now we need to recalculate the page values:
                        frame.revalidate();
                        frame.repaint();
                    } else {
                        JOptionPane.showMessageDialog(DiscoverPage.this, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        centerPanel.add(searchTextField, gbc);

        // FOR BAŞLAR:::

        try (BufferedReader reader = new BufferedReader(new FileReader(IMAGES_FILE_PATH))) {
            String line;
            int lineCount = 0;
            int rowCount = 1;
            // check the next line is empty or not
            while ((line = reader.readLine()) != null) {
                // Split the line with DELIMITER (that I choose in SignupPage):
                String[] userData = line.split(",");
                // Now we can Access the user's information:
                String nicknameData = userData[0];
                String imagePath = userData[1];
                String imageDescription = userData[3];

                int likeData = Integer.parseInt(userData[4]);
                int dislikeData = Integer.parseInt(userData[5]);
                String userTypeStr = userData[2];

                System.out.println(nicknameData + imagePath + imageDescription);

                JLabel thumbnail = new JLabel("Nickname: " + nicknameData + " -  User Tier: " + userTypeStr);
                thumbnail.setHorizontalAlignment(SwingConstants.CENTER);
                thumbnail.setFont(denemeButtonFont);
                ImageIcon icon = new ImageIcon(imagePath);
                Image image = icon.getImage();
                Image scaledImage = image.getScaledInstance(400, 200, Image.SCALE_FAST);

                JButton thumbnailPanel = new JButton(new ImageIcon(scaledImage));

                thumbnailPanel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent w) {
                        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE_PATH))) {
                            String line;
                            // check the next line is empty or not
                            while ((line = reader.readLine()) != null) {
                                // Split the line with DELIMITER (that I choose in SignupPage):
                                String[] userData = line.split(",");
                                // Now we can Access the user's information:
                                String nameData = userData[0];
                                String surnameData = userData[1];
                                String nicknameData2 = userData[2];
                                int ageData = Integer.parseInt(userData[3]);
                                String passwordData = userData[4];
                                String emailData = userData[6];
                                UserTier userTypeData = null;
                                if (userData[5].equals("FREE")) {
                                    userTypeData = UserTier.FREE;
                                } else if (userData[5].equals("HOBBYIST")) {
                                    userTypeData = UserTier.HOBBYIST;
                                } else if (userData[5].equals("PROFESSIONAL")) {
                                    userTypeData = UserTier.PROFESSIONAL;
                                } else {
                                	userTypeData = UserTier.ADMIN;
                                }
                                if (nicknameData2.equals(nicknameData)) {

                                    User picOwner = new User(nicknameData2, passwordData, nameData, surnameData, ageData, emailData, userTypeData);
                                    File imageFile = new File(imagePath); // Okunacak görseli gir
                                    Image imagee = ImageIO.read(imageFile); // Görseli okuyorum

                                    PhotoPanel photoPanel = new PhotoPanel(new Photo(imagee, picOwner, imagePath), user);
                                    // Remove the current JPanel from its parent JFrame:
                                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(DiscoverPage.this);

                                    // Now: set your current page this signup page:,
                                    frame.setContentPane(photoPanel);

                                    // Now we need to recalculate the page values:
                                    frame.revalidate();
                                    frame.repaint();

                                    break;
                                }
                            }
                        } catch (IOException e) {
                            Logger.LogError(e.getMessage());
                        }
                    }
                });

                if (lineCount % 2 == 0) {
                    rowCount += 2;
                }

                gbc.gridx = lineCount % 2;
                gbc.gridy = rowCount;
                gbc.gridwidth = 1; // to span 2 columns

                centerPanel.add(thumbnail, gbc);

                gbc.gridx = lineCount % 2;
                gbc.gridy = rowCount + 1;
                gbc.gridwidth = 1; // to span 2 columns

                centerPanel.add(thumbnailPanel, gbc);

                lineCount++;
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane, BorderLayout.CENTER);

        // Sayfa oluşturma kodları burada yer alır

        Instant end = Instant.now(); // Bitiş zamanını al

        // Süreyi hesapla
        Duration duration = Duration.between(start, end);
        long milliseconds = duration.toMillis();
        System.out.println("Sayfa yükleme süresi: " + milliseconds + " milisaniye");
    }

    /**
     * Searches for a user with the given nickname in the users file.
     *
     * @param nickname The nickname to search for.
     * @return The User object if found, or null if not found.
     */
    private User searchUser(String nickname) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE_PATH))) {
            String line;
            // check the next line is empty or not
            while ((line = reader.readLine()) != null) {
                // Split the line with DELIMITER (that I choose in SignupPage):
                String[] userData = line.split(",");
                // Now we can Access the user's information:
                String nameData = userData[0];
                String surnameData = userData[1];
                String nicknameData = userData[2];
                int ageData = Integer.parseInt(userData[3]);
                String passwordData = userData[4];
                String emailData = userData[6];
                UserTier userTypeData = null;
                if (userData[5].equals("FREE")) {
                    userTypeData = UserTier.FREE;
                } else if (userData[5].equals("HOBBYIST")) {
                    userTypeData = UserTier.HOBBYIST;
                } else if (userData[5].equals("PROFESSIONAL")) {
                    userTypeData = UserTier.PROFESSIONAL;
                }
                if (nicknameData.equals(nickname)) {
                    return new User(nicknameData, passwordData, nameData, surnameData, ageData, emailData, userTypeData);
                }
            }
        } catch (IOException e) {
            Logger.LogError(e.getMessage());
        }
        return null;
    }
}