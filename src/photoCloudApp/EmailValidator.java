package photoCloudApp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import exception.InvalidEmailFormatException;
/**
 * The EmailValidator class is used to validate email addresses based on a regular expression pattern.
 */
public class EmailValidator {
	private Pattern pattern;
	private Matcher matcher;
	private String email;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-]+([_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
    /**
     * Constructs a new instance of the EmailValidator class with the specified email address.
     *
     * @param email The email address to validate.
     */
	public EmailValidator(final String email) {
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		this.email = email;
	}
	
    /**
     * Validates the email address.
     *
     * @return true if the email address is valid, false otherwise.
     */
	public boolean validate() {
		return matcher.matches();
	}
}
