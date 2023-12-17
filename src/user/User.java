package user;

import java.util.List;
import java.util.Objects;

import javax.swing.ImageIcon;

import java.util.ArrayList;

import photo.ImageMatrix;
import photo.Photo;
/**
 * Sets the user type of the user.
 *
 * @param userType the user type to set
 */
public class User {
	protected String nickname;
	protected String password;
	protected String realName;
	protected String surname;
	protected int age;
	protected String email;
	protected UserTier userType;
	protected ImageIcon profilePhoto; // We will use this info to take the name of pic. (source)
	private List<Photo> uploadedPhotos;
	
	
	/**
	 * @return the userType
	 */
	public UserTier getUserType() {
		return userType;
	}

	/**
	 * @param userType the userType to set
	 */
	public void setUserType(UserTier userType) {
		this.userType = userType;
	}

	/**
	 * Constructs a new User object without a profile photo.
	 *
	 * @param nickname  the nickname of the user
	 * @param password  the password of the user
	 * @param realName  the real name of the user
	 * @param surname  the surname of the user
	 * @param age  the age of the user
	 * @param email  the email of the user
	 * @param userType  the user type of the user
	 */
	public User(String nickname, String password, String realName, String surname, int age, String email , UserTier userType) {
		this.nickname = nickname;
		this.password = password;
		this.realName = realName;
		this.surname = surname;
		this.age = age;
		this.email = email;
		this.profilePhoto = null;
		this.userType = userType;
		this.uploadedPhotos = new ArrayList<>();
		
	}
	
	/**
	 * Constructs a new User object with a profile photo.
	 *
	 * @param nickname  the nickname of the user
	 * @param password  the password of the user
	 * @param realName  the real name of the user
	 * @param surname  the surname of the user
	 * @param age  the age of the user
	 * @param email  the email of the user
	 * @param userType  the user type of the user
	 * @param profilePhoto  the profile photo of the user
	 */
	public User(String nickname, String password, String realName, String surname, int age, String email, UserTier userType, ImageIcon profilePhoto ) {
		this.nickname = nickname;
		this.password = password;
		this.realName = realName;
		this.surname = surname;
		this.age = age;
		this.email = email;
		this.userType = userType;
		this.profilePhoto = profilePhoto;
		this.uploadedPhotos = new ArrayList<>();
	}

	// Upload foto to array:
	//public void uploadPhoto(ImageMatrix image, String description) {
	//	Photo photo = new Photo(image, this);
	//	photo.setDescription(description);
	//	uploadedPhotos.add(photo);
	// }
	
	/**
	 * Removes a photo from the user's uploaded photos.
	 *
	 * @param photo  the photo to remove
	 */
	public void removePhoto(Photo photo) {
		uploadedPhotos.remove(photo);
	}
	
	/////////////// GETTERS AND SETTERS 
	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the realName
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * @param realName the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the profilePhoto
	 */
	public ImageIcon getProfilePhoto() {
		return profilePhoto;
	}

	/**
	 * @param profilePhoto the profilePhoto to set
	 */
	public void setProfilePhoto(ImageIcon profilePhoto) {
		this.profilePhoto = profilePhoto;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
	        return true;
	    }

	    if (obj == null || getClass() != obj.getClass()) {
	        return false;
	    }

	    User otherUser = (User) obj;
	    return Objects.equals(nickname, otherUser.nickname); // Burada id alanını karşılaştırabilirsiniz veya başka bir alanı karşılaştırabilirsiniz
	}
	
	
}
