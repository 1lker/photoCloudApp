package user;

import photo.ImageMatrix;

public class HobbyistUser extends FreeUser {

	public HobbyistUser(String nickname, String password, String realName, String surname, int age, String email, UserTier userType) {
		super(nickname, password, realName, surname, age, email, userType);
		// TODO Auto-generated constructor stub
	}
	
	public HobbyistUser(String nickname, String password, String realName, String surname, int age, String email, UserTier userType ,
			ImageMatrix profilePhoto) {
		super(nickname, password, realName, surname, age, email, userType ,profilePhoto);
		// TODO Auto-generated constructor stub
	}
	
    // methods for changing brightness and contrast


}
