package user;

import photo.ImageMatrix;

public class ProfessionalUser extends HobbyistUser {
	
	public ProfessionalUser(String nickname, String password, String realName, String surname, int age, String email, UserTier userType) {
		super(nickname, password, realName, surname, age, email, userType);
		// TODO Auto-generated constructor stub
	}

	public ProfessionalUser(String nickname, String password, String realName, String surname, int age, String email, UserTier userType ,
			ImageMatrix profilePhoto) {
		super(nickname, password, realName, surname, age, email, userType, profilePhoto);
		// TODO Auto-generated constructor stub
	}

	
    // methods for applying grayscale and edge detection filters

	
}
