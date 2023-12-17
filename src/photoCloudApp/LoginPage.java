/**
 * The LoginPage class represents the login page of the application.
 * It allows users to enter their nickname and password to log into the system.
 * If the login is successful, the user is redirected to the Discover page.
 * This class extends JPanel and serves as a graphical user interface component for the login page.
 * It contains various Swing components such as labels, text fields, and buttons to create the login form.
 *
 * Usage:
 * 1. Create an instance of LoginPage and add it to a parent container such as a JFrame.
 * 2. The user can enter their nickname and password in the respective fields.
 * 3. Clicking the "Log_in" button will attempt to log in the user by checking the provided credentials.
 * 4. If the login is successful, the user is redirected to the Discover page.
 * 5. If the login fails due to an incorrect nickname or password, an error message is displayed.
 * 6. Clicking the "Sign_up" button will navigate to the SignupPage for creating a new user account.
 *
 * Example:
 * LoginPage loginPage = new LoginPage();
 * frame.getContentPane().add(loginPage);
 *
 * Note: The LoginPage class relies on other classes such as User, UserTier, and exceptions for its functionality.
 * Make sure to have these classes available in the same package or import them as needed.
 */

package photoCloudApp;

import user.*;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import exception.InvalidPasswordException;
import exception.UnauthorizedAccessException;
import exception.UserNotFoundException;
import logging.Logger;

/**
 * The LoginPage class represents the login page of the application.
 */
public class LoginPage extends JPanel {

    private final JTextField nicknameField;
    private final JLabel nicknameLabel;

    private final JPasswordField passwordField;
    private final JLabel passwordLabel;

    private JButton submitButton;
    private JButton signupButton;

    // users file path:
    private static final String USERS_FILE_PATH = "src/users.txt";

    /**
     * Constructor for the LoginPage class.
     */
    public LoginPage() {
        super();

        setLayout(new GridBagLayout());

        // Create a GridBagConstraints:
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // For nickname field Label:
        gbc.gridx = 0;
        gbc.gridy = 0;
        nicknameLabel = new JLabel("NICKNAME: ");
        Font generalFont = new Font(nicknameLabel.getFont().getName(), Font.BOLD, 16);
        nicknameLabel.setToolTipText("1lker");
        nicknameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nicknameLabel.setVerticalTextPosition(SwingConstants.CENTER);
        nicknameLabel.setFont(generalFont);
        nicknameLabel.setForeground(Color.WHITE);
        add(nicknameLabel, gbc);

        // Construct text field:
        gbc.gridx = 1;
        gbc.gridy = 0;
        nicknameField = new JTextField(20);
        nicknameField.setHorizontalAlignment(SwingConstants.CENTER);
        nicknameField.setFont(generalFont);
        add(nicknameField, gbc);

        // Password Label:
        gbc.gridx = 0;
        gbc.gridy = 1;
        passwordLabel = new JLabel("PASSWORD: ");
        passwordLabel.setToolTipText("*******");
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        passwordLabel.setVerticalTextPosition(SwingConstants.CENTER);
        passwordLabel.setFont(generalFont);
        passwordLabel.setForeground(Color.WHITE);
        add(passwordLabel, gbc);

        // Password field:
        gbc.gridx = 1;
        gbc.gridy = 1;
        passwordField = new JPasswordField(20);
        passwordField.setHorizontalAlignment(SwingConstants.CENTER);
        passwordField.setFont(generalFont);
        add(passwordField, gbc);

        // Submit button creation and assignment of ActionListener
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        submitButton = new JButton("Log_in");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Actions to perform when the submit button is clicked
                String nickname = nicknameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                boolean isUser = false;
                // User Validity Check:
                try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE_PATH))) {
                    String line;

                    // Check the next line is empty or not
                    while ((line = reader.readLine()) != null) {
                        // Split the line with DELIMITER (that I choose in SignupPage):
                        String[] userData = line.split(",");
                        // Access the user's information:
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
                        } else {
                        	userTypeData = UserTier.ADMIN;
                        }
                        // Check if the given nickname exists or not, and then check the password:
                        if (nickname.equals(nicknameData)) {
                            isUser = true;
                            if (password.equals(passwordData)) {
                                System.out.println("Login is successful");
                                Logger.LogInfo("Login is successful. " + nickname + ": user has been logged in.");

                                // If the user is logged in, redirect to the discover page:
                                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(LoginPage.this);
                                DiscoverPage discoverPage = null;
                                if (userTypeData == UserTier.FREE) {
                                    FreeUser user = new FreeUser(nickname, password, nameData, surnameData, ageData, emailData, UserTier.FREE);
                                    discoverPage = new DiscoverPage(user);
                                } else if (userTypeData == UserTier.HOBBYIST) {
                                    HobbyistUser user = new HobbyistUser(nickname, password, nameData, surnameData, ageData, emailData, UserTier.HOBBYIST);
                                    discoverPage = new DiscoverPage(user);
                                } else if (userTypeData == UserTier.PROFESSIONAL) {
                                    ProfessionalUser user = new ProfessionalUser(nickname, password, nameData, surnameData, ageData, emailData, UserTier.PROFESSIONAL);
                                    discoverPage = new DiscoverPage(user);
                                } else {
                                	Administrator user = new Administrator(nickname, password, nameData, surnameData, ageData, emailData, UserTier.ADMIN);
                                    discoverPage = new DiscoverPage(user);
                                }

                                // Set the content pane to the discover page:
                                frame.setContentPane(discoverPage);
                                frame.revalidate();
                                frame.repaint();
                            } else {
                                throw new InvalidPasswordException();
                            }
                        }
                    }

                    if (!isUser) {
                        throw new UserNotFoundException(nickname);
                    }
                } catch (UserNotFoundException ex) {
                    JOptionPane.showMessageDialog(nicknameField, ex.getMessage(), "User Not Found Error", JOptionPane.ERROR_MESSAGE);
                } catch (InvalidPasswordException ex) {
                    JOptionPane.showMessageDialog(passwordField, ex.getMessage(), "Invalid Password", JOptionPane.ERROR_MESSAGE);
                } catch (FileNotFoundException ex) {
                    Logger.LogError("FileNotFoundException: " + ex.getMessage());
                } catch (Exception ex) {
                    Logger.LogError(ex.getClass().getName() + ": " + ex.getMessage());
                    JOptionPane.showMessageDialog(nicknameField, ex.getMessage(), "Unspecified Error Types", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        submitButton.setFont(new Font(submitButton.getFont().getName(), Font.BOLD, 16));
        submitButton.setMargin(new Insets(10, 6, 10, 6));

        add(submitButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;

        signupButton = new JButton("Sign_up");
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Remove the current JPanel from its parent JFrame:
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(LoginPage.this);

                // Create an instance of the Sign Up Page:
                SignupPage signupPage = new SignupPage();
                signupPage.setBackground(Color.BLACK);

                // Set the content pane to the sign up page:
                frame.setContentPane(signupPage);

                // Recalculate the page values:
                frame.revalidate();
                frame.repaint();
            }
        });

        signupButton.setFont(new Font(signupButton.getFont().getName(), Font.BOLD, 16));
        signupButton.setMargin(new Insets(10, 0, 10, 0));

        add(signupButton, gbc);
    }
}
