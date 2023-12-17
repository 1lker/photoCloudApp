package photoCloudApp;

import java.awt.Color;
import java.util.regex.Pattern;

import javax.swing.JFrame;
/**
 * The main class for the Photo Cloud Application.
 */
public class PhotoCloudApp {
    private JFrame frame;

    /**
     * Constructs a new instance of the PhotoCloudApp class.
     * Initializes the application.
     */
    public PhotoCloudApp() {
    	/************** Pledge of Honor ******************************************
    	I hereby certify that I have completed this programming project on my own without any help from anyone else. The effort in the project thus belongs completely to me. I did not search for a solution, or I did not consult any program written by others or did not copy any program from other sources. I read and followed the guidelines provided in the project description.
    	READ AND SIGN BY WRITING YOUR NAME SURNAME AND STUDENT ID
    	SIGNATURE: <İlker Yörü, 79816 > *************************************************************************/
    	
        initialize();
    }

    /**
     * Initializes the application by creating and configuring the main frame.
     */
    private void initialize() {
        frame = new JFrame("Photo Cloud Application");
        frame.setBounds(100, 100, 900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // LoginPage sınıfını başlatma ve görüntüleme
        LoginPage loginPage = new LoginPage();
        loginPage.setBackground(Color.BLACK);
        frame.getContentPane().add(loginPage);

        frame.setVisible(true);
    }

    /**
     * The main entry point of the application.
     *
     * @param args The command-line arguments.
     */

    public static void main(String[] args) {
        /************** Pledge of Honor ******************************************
    	I hereby certify that I have completed this programming project on my own without any help from anyone else. The effort in the project thus belongs completely to me. I did not search for a solution, or I did not consult any program written by others or did not copy any program from other sources. I read and followed the guidelines provided in the project description.
    	READ AND SIGN BY WRITING YOUR NAME SURNAME AND STUDENT ID
    	SIGNATURE: <İlker Yörü, 79816 > *************************************************************************/
        PhotoCloudApp window = new PhotoCloudApp();
    }
}
