package user;

import photo.ImageMatrix;

public class FreeUser extends User {

	public FreeUser(String nickname, String password, String realName, String surname, int age, String email, UserTier userType) {
		super(nickname, password, realName, surname, age, email, userType);
		// TODO Auto-generated constructor stub
	}
	
	public FreeUser(String nickname, String password, String realName, String surname, int age, String email, UserTier userType , ImageMatrix profilePhoto) {
		super(nickname, password, realName, surname, age, email, userType);
		// TODO Auto-generated constructor stub
	}
	
	
	// methods for blurring and sharpening an image


}
