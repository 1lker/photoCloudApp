/**
 * The SignupPage class represents a panel for user signup.
 * It allows users to enter their personal information, such as name, surname, nickname, age, email, and password,
 * and choose their user type. Users can submit the form to create an account.
 * This class extends JPanel and serves as a graphical user interface component for user signup.
 *
 * Usage:
 * 1. Create an instance of SignupPage.
 * 2. Add the SignupPage instance to a parent container such as a JFrame.
 * 3. The panel will display fields for entering personal information and choosing a user type.
 * 4. Users can enter their information, select a user type, and submit the form to create an account.
 *
 * Example:
 * SignupPage signupPage = new SignupPage();
 * frame.getContentPane().add(signupPage);
 *
 * Note: The SignupPage class relies on other classes such as InvalidPasswordException, InvalidEmailFormatException,
 * InvalidNickNameException, EmailValidator, UserTier, and Logger for its functionality.
 * Make sure to have these classes available in the same package or import them as needed.
 */

package photoCloudApp;

import exception.*;
import logging.Logger;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import user.UserTier;

/**
 * SignupPage class represents the panel for user signup.
 */
public class SignupPage extends JPanel {
    private final JTextField nameField; // Name of user
    private final JLabel nameLabel;
    
    private final JTextField surnameField; // Surname of user
    private final JLabel surnameLabel;
    
    private final JTextField nicknameField; // Nickname of user
    private final JLabel nicknameLabel;
    
    private final JTextField ageField; // Age of user
    private final JLabel ageLabel;
    
    private final JPasswordField passwordField; // Password of user
    private final JLabel passwordLabel;
    
    private final JRadioButton userFreeButton; // Choose type of user 
    private final JRadioButton userHobbyistButton; // Choose type of user 
    private final JRadioButton userProButton; // Choose type of user 
    
    private final JLabel emailLabel; 
    private final JTextField emailField; // Email of user
    
    private ButtonGroup radioGroup; // ButtonGroup to hold radio buttons
    
    private final JLabel userTypeLabel;
    private UserTier userType;
    
    private JButton submitButton;
    private JButton loginButton;
    
    // File paths
    private static final String NICKNAMES_FILE_PATH = "nicknames.txt";
    private static final String USERS_FILE_PATH = "src/users.txt";
    // Delimiter character to separate user details
    private static final String DELIMITER = ",";
    
    /**
     * Creates a new SignupPage panel.
     */
    public SignupPage() {
        super();
        setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        // It gives 5px margin for every direction:
        gbc.insets = new Insets(5, 5, 5, 5);
        // It makes the grid horizontally filled:
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Construct text field with label
        nameLabel = new JLabel("Your Name: ");
        Font generalFont = new Font(nameLabel.getFont().getName(), Font.BOLD, 16);
        nameLabel.setToolTipText("İlker");
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(generalFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(nameLabel, gbc);
        
        nameField = new JTextField(20);
        nameField.setHorizontalAlignment(SwingConstants.CENTER);
        nameField.setFont(generalFont);
        nameField.setBackground(Color.LIGHT_GRAY);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(nameField, gbc);
        
        surnameLabel = new JLabel("Your Surname: ");
        surnameLabel.setToolTipText("Yörü");
        surnameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        surnameLabel.setVerticalTextPosition(SwingConstants.CENTER);
        surnameLabel.setForeground(Color.WHITE);
        surnameLabel.setFont(generalFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(surnameLabel, gbc);
        
        surnameField = new JTextField(20);
        surnameField.setHorizontalAlignment(SwingConstants.CENTER);
        surnameField.setFont(generalFont);
        surnameField.setBackground(Color.LIGHT_GRAY);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(surnameField, gbc);
        
        nicknameLabel = new JLabel("Your unique nickname: ");
        nicknameLabel.setToolTipText("1lker");
        nicknameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nicknameLabel.setVerticalTextPosition(SwingConstants.CENTER);
        nicknameLabel.setForeground(Color.WHITE);
        nicknameLabel.setFont(generalFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(nicknameLabel, gbc);
        
        nicknameField = new JTextField(40);
        nicknameField.setHorizontalAlignment(SwingConstants.CENTER);
        nicknameField.setFont(generalFont);
        nicknameField.setBackground(Color.LIGHT_GRAY);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(nicknameField, gbc);
        
        emailLabel = new JLabel("Your email: ");
        emailLabel.setToolTipText("ilker@gmail.com");
        emailLabel.setHorizontalAlignment(SwingConstants.CENTER);
        emailLabel.setVerticalTextPosition(SwingConstants.CENTER);
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setFont(generalFont);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(emailLabel, gbc);
        
        emailField = new JTextField(40);
        emailField.setHorizontalAlignment(SwingConstants.CENTER);
        emailField.setFont(generalFont);
        emailField.setBackground(Color.LIGHT_GRAY);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(emailField, gbc);
        
        ageLabel = new JLabel("Your age: ");
        ageLabel.setToolTipText("20");
        ageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ageLabel.setVerticalTextPosition(SwingConstants.CENTER);
        ageLabel.setForeground(Color.WHITE);
        ageLabel.setFont(generalFont);
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(ageLabel, gbc);
        
        ageField = new JTextField(10);
        ageField.setHorizontalAlignment(SwingConstants.CENTER);
        ageField.setFont(generalFont);
        ageField.setBackground(Color.LIGHT_GRAY);
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(ageField, gbc);
        
        passwordLabel = new JLabel("Your password: ");
        passwordLabel.setToolTipText("*******");
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        passwordLabel.setVerticalTextPosition(SwingConstants.CENTER);
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(generalFont);
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(passwordLabel, gbc);
        
        passwordField = new JPasswordField(20);
        passwordField.setHorizontalAlignment(SwingConstants.CENTER);
        passwordField.setBackground(Color.LIGHT_GRAY);
        passwordField.setFont(generalFont);
        gbc.gridx = 1;
        gbc.gridy = 5;
        add(passwordField, gbc);
        
        userTypeLabel = new JLabel("Choose Your User Type: ");
        userTypeLabel.setToolTipText("Ex: FreeUser");
        userTypeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userTypeLabel.setVerticalTextPosition(SwingConstants.CENTER);
        userTypeLabel.setForeground(Color.WHITE);
        userTypeLabel.setFont(generalFont);
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(userTypeLabel, gbc);
        
        userFreeButton = new JRadioButton("Free User", false);
        userHobbyistButton = new JRadioButton("Hobbyist User", false);
        userProButton = new JRadioButton("Professional User", false);
        
        userFreeButton.addItemListener(new RadioButtonHandler(UserTier.FREE));
        userHobbyistButton.addItemListener(new RadioButtonHandler(UserTier.HOBBYIST));
        userProButton.addItemListener(new RadioButtonHandler(UserTier.PROFESSIONAL));
        userFreeButton.setForeground(Color.WHITE);
        userFreeButton.setFont(generalFont);
        userHobbyistButton.setForeground(Color.WHITE);
        userHobbyistButton.setFont(generalFont);
        userProButton.setForeground(Color.WHITE);
        userProButton.setFont(generalFont);
        
        // Create logical relationship between JRadioButtons
        radioGroup = new ButtonGroup(); // Create ButtonGroup
        radioGroup.add(userFreeButton); // Add plain to group
        radioGroup.add(userHobbyistButton); // Add bold to group
        radioGroup.add(userProButton); // Add italic to group
        
        // Add them to a JPanel
        JPanel radioPanel = new JPanel();
        radioPanel.add(userFreeButton);
        radioPanel.add(userHobbyistButton);
        radioPanel.add(userProButton);
        radioPanel.setBackground(Color.BLACK);
        radioPanel.setForeground(Color.WHITE);
        
        gbc.gridx = 1;
        gbc.gridy = 6;
        add(radioPanel, gbc);
        
        // Submit button creation and ActionListener assignment
        submitButton = new JButton("Submit");
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Actions to be performed when the Submit button is clicked
                boolean flag = true;
                
                String name = nameField.getText();
                String surname = surnameField.getText();
                String nickname = nicknameField.getText();
                String email = emailField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);
                String ageS = ageField.getText();
                
                // Password Validity Check and Email Format Check
                try {
                    if (password.length() < 8) {
                        throw new InvalidPasswordException();
                    }
                } catch (InvalidPasswordException exception) {
                    JOptionPane.showMessageDialog(passwordField, exception.getMessage(), "Invalid Password", JOptionPane.ERROR_MESSAGE);
                    flag = false;
                }
                
                try {
                    if (!new EmailValidator(email).validate()) {
                        throw new InvalidEmailFormatException(email);
                    }
                } catch (InvalidEmailFormatException exception) {
                    JOptionPane.showMessageDialog(emailField, exception.getMessage(), "Invalid Email Format", JOptionPane.ERROR_MESSAGE);
                    flag = false;
                }
                
                // Age Validity Check
                try {
                    int age = Integer.parseInt(ageField.getText());
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(ageField, "Invalid Age! Please enter a valid numeric age value.", "Invalid Age", JOptionPane.ERROR_MESSAGE);
                    Logger.LogError("Invalid age!");
                    flag = false;
                }
                
                // Check if the nickname already exists
                try (BufferedReader reader = new BufferedReader(new FileReader(NICKNAMES_FILE_PATH))) {
                    String line;
                    boolean nicknameExists = false;
                    Pattern pattern = Pattern.compile("\\b" + nickname + "\\b", Pattern.CASE_INSENSITIVE);
                    while ((line = reader.readLine()) != null) {
                        Matcher matcher = pattern.matcher(line);
                        if (matcher.find()) {
                            nicknameExists = true;
                            break;
                        }
                    }
                    
                    if (nicknameExists) {
                        throw new InvalidNickNameException(nickname);
                    } else {
                        writeNicknameToFile(nickname);
                    }
                } catch (InvalidNickNameException ex) {
                    JOptionPane.showMessageDialog(nicknameField, ex.getMessage(), "Nickname already exists", JOptionPane.ERROR_MESSAGE);
                    flag = false;
                } catch (IOException ex) {
                    Logger.LogError("An error occurred while reading the nicknames file: " + ex.getMessage());
                    flag = false;
                }
                
                // Perform the desired operations here
                // For example, save the user to the database or perform another action
                
                // Concatenate user details with the delimiter
                String userInfo = name + DELIMITER + surname + DELIMITER + nickname + DELIMITER + ageS + DELIMITER + password + DELIMITER + getUserTier() + DELIMITER + email;
                
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE_PATH, true))) {
                    if (flag) {
                        writer.write(userInfo);
                        writer.newLine();
                        writer.flush();
                        // User log for creating
                        Logger.LogInfo("User has been created.");
                        JFrame frame = new JFrame();
                        frame.setLocationRelativeTo(null);
                        JOptionPane.showMessageDialog(frame, "User has been created.", "User Creation Successful", JOptionPane.INFORMATION_MESSAGE);
                    }
                    
                } catch (IOException ex) {
                    System.out.println("An error occurred while writing user information: " + ex.getMessage());
                    Logger.LogError("An error occurred while writing user information: ");
                    flag = false;
                }
                
                System.out.println("Signup Submitted!");
                
                System.out.println("Name: " + name);
                System.out.println("Surname: " + surname);
                System.out.println("Nickname: " + nickname);
                System.out.println("Age: " + ageS);
                System.out.println("Password: " + password);
                System.out.println("Email: " + email);
                
                UserTier userTier = getUserTier();
                
                // Perform the desired operations based on the obtained UserTier
                // For example, apply a special behavior based on the user's type
                
                System.out.println("User Tier: " + userTier);
                
                nameField.setText("");
                surnameField.setText("");
                nicknameField.setText("");
                ageField.setText("");
                passwordField.setText("");
                emailField.setText("");
            }
        });
        add(submitButton, gbc);
        
        loginButton = new JButton("Log_in");
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Remove the current JPanel (SignupPage) from the PhotoCloudApp
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(SignupPage.this);
                
                // Create an instance of LoginPage
                LoginPage loginPage = new LoginPage();
                
                loginPage.setBackground(Color.BLACK);
                
                // Add the Login Page to the PhotoCloudApp
                frame.setContentPane(loginPage);
                
                // Revalidate and repaint the frame to reflect the changes
                frame.revalidate();
                frame.repaint();
            }
        });
        
        add(loginButton, gbc);
    }
    
    /**
     * Sets the user tier.
     * @param type the user tier
     */
    public void setUserTier(UserTier type) {
        userType = type;
    }
    
    /**
     * Returns the user tier.
     * @return the user tier
     */
    public UserTier getUserTier() {
        return userType;
    }
    
    /**
     * RadioButtonHandler class handles the item state change for radio buttons.
     */
    private class RadioButtonHandler implements ItemListener {
        private UserTier userType;
        
        /**
         * Creates a new RadioButtonHandler with the specified user tier.
         * @param user the user tier
         */
        public RadioButtonHandler(UserTier user) {
            userType = user;
        }
        
        @Override
        public void itemStateChanged(ItemEvent event) {
            setUserTier(userType);
        }
    }
    
    /**
     * Writes the nickname to the file.
     * @param nickname the nickname to write
     */
    public static void writeNicknameToFile(String nickname) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NICKNAMES_FILE_PATH, true))) {
            writer.write(nickname);
            writer.newLine();
            System.out.println("Nickname has been written to the file.");
            Logger.LogInfo("Nickname has been written to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing the nickname to the file.");
            Logger.LogError(e.getMessage());
        }
    }
}
