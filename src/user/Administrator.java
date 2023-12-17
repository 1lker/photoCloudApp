package user;

import photo.ImageMatrix;
import photo.Photo;

public class Administrator extends ProfessionalUser {

	public Administrator(String nickname, String password, String realName, String surname, int age, String email, UserTier userType) {
		super(nickname, password, realName, surname, age, email, userType);
		// TODO Auto-generated constructor stub
	}
	
	public Administrator(String nickname, String password, String realName, String surname, int age, String email, UserTier userType ,
			ImageMatrix profilePhoto) {
		super(nickname, password, realName, surname, age, email, userType , profilePhoto);
		// TODO Auto-generated constructor stub
	}
	
	
    // methods for managing the system
	
	public void removePhoto(Photo photo) {
        // Implement the logic to remove a photo from the Discover page and the owner's profile page
        // Also remove the photo from the owner's uploaded photos list
	}


}
