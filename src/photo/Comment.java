package photo;

import user.User;

/**
 * The Comment class represents a comment made by a user on a photo. It stores the user who made the comment
 * and the text of the comment.
 */
public class Comment {
	private User user;
	private String commentText;
	
    /**
     * Constructs a Comment object with the specified user and comment text.
     * @param user the user who made the comment
     * @param commentText the text of the comment
     */
	public Comment(User user, String commentText) {
		this.user = user;
		this.commentText = commentText;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the commentText
	 */
	public String getCommentText() {
		return commentText;
	}

	/**
	 * @param commentText the commentText to set
	 */
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	
	
}
