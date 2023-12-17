package photo;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * The ImagePreviewGenerator class provides functionality to generate image previews.
 */
public class ImagePreviewGenerator {
    private static final int PREVIEW_WIDTH = 200;  // Önizleme genişliği
    private static final int PREVIEW_HEIGHT = 150; // Önizleme yüksekliği
    
    /**
     * Generates image previews for the images in the specified folder.
     *
     * @param imagesFolderPath  the path of the folder containing the images
     * @param previewsFolderPath  the path of the folder to save the generated previews
     */
    public static void generateImagePreviews(String imagesFolderPath, String previewsFolderPath) {
        File imagesFolder = new File(imagesFolderPath);
        File[] imageFiles = imagesFolder.listFiles();
        
        if (imageFiles != null) {
            for (File imageFile : imageFiles) {
                try {
                    // Orijinal görüntüyü yükle
                    BufferedImage originalImage = ImageIO.read(imageFile);
                    
                    // Önizleme boyutlarına göre yeniden boyutlandır
                    Image previewImage = originalImage.getScaledInstance(PREVIEW_WIDTH, PREVIEW_HEIGHT, Image.SCALE_SMOOTH);
                    
                    // Önizleme dosyasını oluştur
                    String previewFileName = imageFile.getName().replace(".", "_preview.");
                    File previewFile = new File(previewsFolderPath + File.separator + previewFileName);
                    
                    // Önizleme dosyasına kaydet
                    ImageIO.write(toBufferedImage(previewImage), "JPEG", previewFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * Converts an Image to a BufferedImage.
     *
     * @param image  the Image to convert
     * @return the converted BufferedImage
     */    private static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(image, 0, 0, null);
        return bufferedImage;
    }
}
