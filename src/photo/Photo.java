package photo;

import java.util.List;
import java.util.Map;
import java.util.*;
import java.util.UUID;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import user.User;
/**
 * The Photo class represents a photo with associated information and functionality.
 */
public class Photo {
    private String id;
    private User owner;
    private Image image;
    private ImageMatrix modifiedImage;
    private List<Comment> comments;
    private int likes;
    private int dislikes;
    private String description;
    private String imagePath;

    /**
     * Constructs a new Photo object with the specified image, owner, and image path.
     *
     * @param imagee  the image associated with the photo
     * @param owner  the owner of the photo
     * @param imagePath  the path of the image file
     */    public Photo(Image imagee,  User owner, String imagePath) {
        this.id = UUID.randomUUID().toString();
        this.image = imagee;
        this.owner = owner;
        this.comments = new ArrayList<>();
        this.likes = 0;
        this.dislikes = 0;
        this.description = "";
        this.imagePath = imagePath;
        readLikesAndDislikes();
    }

     /**
      * Adds a comment to the comments list.
      *
      * @param comment  the comment to add
      */    
    public void addComment(Comment comment) {
        comments.add(comment);
    }

      /**
       * Removes a comment from the comments list.
       *
       * @param comment  the comment to remove
       */
    public void removeComment(Comment comment) {
        comments.remove(comment);
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the owner
     */
    public User getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

    /**
     * @return the image
     */
    public Image getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    //public void setImage(ImageMatrix image) {
    //  this.image = image;
    //}

    /**
     * @return the modifiedImage
     */
    public ImageMatrix getModifiedImage() {
        return modifiedImage;
    }

    /**
     * @param modifiedImage the modifiedImage to set
     */
    public void setModifiedImage(ImageMatrix modifiedImage) {
        this.modifiedImage = modifiedImage;
    }

    /**
     * @return the comments
     */
    public List<Comment> getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    /**
     * @return the likes
     */
    public int getLikes() {
        return likes;
    }

    /**
     * @param likes the likes to set
     */
    public void setLikes(int likes) {
        this.likes = likes;
    }

    /**
     * @return the dislikes
     */
    public int getDislikes() {
        return dislikes;
    }

    /**
     * @param dislikes the dislikes to set
     */
    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the image path.
     *
     * @return the image path
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Increases the number of likes by 1 and updates the images info file.
     */
    public void addLike() {
        likes++;
        updateImagesInfoFile(true, false);
    }

    /**
     * Increases the number of dislikes by 1 and updates the images info file.
     */    
    public void addDislike() {
        dislikes++;
        updateImagesInfoFile(false, true);
    }

    /**
     * Updates the images info file with the updated like and dislike counts.
     *
     * @param updateLike  true if the like count should be updated, false otherwise
     * @param updateDislike  true if the dislike count should be updated, false otherwise
     */
    private void updateImagesInfoFile(boolean updateLike, boolean updateDislike) {
        try {
            String filePath = "src/imagesInfo.txt";

            // Okunacak ve güncellenecek dosyayı açın
            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            List<String> lines = new ArrayList<>();

            // Dosyanın tüm satırlarını okuyun ve güncellemeleri yapın
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[1].equals(getImagePath())) {
                	setDescription(parts[3]);
                    int newLikes = Integer.parseInt(parts[4]);
                    int newDislikes = Integer.parseInt(parts[5]);
                    setLikes(newLikes);

                    if (updateLike) {
                        newLikes++;
                        parts[4] = String.valueOf(newLikes);

                    }
                    if (updateDislike) {
                        newDislikes++;
                        parts[5] = String.valueOf(newDislikes);
                    }
                    setLikes(newLikes);
                    setDislikes(newDislikes);
                    // Satırı güncellenmiş bölümlerle birleştirin
                    line = String.join(",", parts);
                    
                    // Commentleri ekle
                    StringBuilder commentBuilder = new StringBuilder();

                    commentBuilder.append("[");
                    for (Comment comment : comments) {
                        commentBuilder.append(comment.getUser().getNickname());
                        commentBuilder.append(": ");
                        commentBuilder.append(comment.getCommentText());
                        commentBuilder.append(", ");
                    }
                    // Son karakter virgülü ve boşluğu kaldırın
                    if (commentBuilder.length() > 1) {
                        commentBuilder.delete(commentBuilder.length() - 2, commentBuilder.length());
                    }
                    commentBuilder.append("]");
                    line += commentBuilder.toString();
                }
                lines.add(line);
            }

            // Dosyayı kapatın
            reader.close();

            // Dosyayı yeniden yazın ve güncellenmiş satırları yazın
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (String updatedLine : lines) {
                writer.write(updatedLine);
                writer.newLine();
            }

            // Dosyayı kapatın
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Reads the number of likes and dislikes from the images info file.
     */
    private void readLikesAndDislikes() {
        try {
            String filePath = "src/imagesInfo.txt";

            // Okunacak dosyayı açın
            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));

            // Dosyanın tüm satırlarını okuyun
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[1].equals(getImagePath())) {
                    int newLikes = Integer.parseInt(parts[4]);
                    int newDislikes = Integer.parseInt(parts[5]);
                    setLikes(newLikes);
                    setDislikes(newDislikes);
                    break;
                }
            }

            // Dosyayı kapatın
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
//    
//    public Map<String, String> getCommentsAsMap() {
//        Map<String, String> commentsMap = new HashMap<>();
//        
//        try {
//            String filePath = "src/imagesInfo.txt";
//
//            // Okunacak dosyayı açın
//            File file = new File(filePath);
//            BufferedReader reader = new BufferedReader(new FileReader(file));
//
//            // Dosyanın tüm satırlarını okuyun
//            String line;
//            
//            while ((line = reader.readLine()) != null) {
//            	System.out.println(line);
//                String[] parts = line.split(",");
//                if (parts.length >= 2 && parts[1].equals(getImagePath())) {
//                    if (parts.length >= 7) {
//                        String[] commentsArray = parts[6].substring(1, parts[6].length() - 1).split("\\]\\[");
//                        for (String comment : commentsArray) {
//                            String[] commentParts = comment.split(": ");
//                            if (commentParts.length == 2) {
//                                String nickname = commentParts[0].trim();
//                                String commentText = commentParts[1].trim();
//                                System.out.println(commentText + "zhello");
//                                commentsMap.put(nickname, commentText);
//                            }
//                        }
//                    }
//                    break;
//                }
//            }
//
//            // Dosyayı kapatın
//            reader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        
//        return commentsMap;
//    }


    
}
