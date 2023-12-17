//package photo;
//
//import java.io.IOException;
//
//import logging.Logger;
//
//public class PhotoEditor {
//	private ImageMatrix imageMatrix;
//	private ImageSecretary imageSecretary;
//	
//	
//	/**
//     * Adjusts the brightness of the given image.
//     * 
//     * @param image the image to adjust
//     * @param factor the factor by which to adjust the brightness. A factor greater than 1 will increase brightness, less than 1 will decrease it.
//     * @return the adjusted image
//     */
//    public static ImageMatrix adjustBrightness(ImageMatrix image, double factor) {
//        int width = image.getWidth();
//        int height = image.getHeight();
//        ImageMatrix adjustedImage = new ImageMatrix(width, height);
//        
//        for (int x = 0; x < width; x++) {
//            for (int y = 0; y < height; y++) {
//                int rgb = image.getRGB(x, y);
//                int red = (int) Math.min(255, ((rgb >> 16) & 0xFF) * factor);
//                int green = (int) Math.min(255, ((rgb >> 8) & 0xFF) * factor);
//                int blue = (int) Math.min(255, (rgb & 0xFF) * factor);
//                
//                adjustedImage.setRGB(x, y, ImageMatrix.convertRGB(red, green, blue));
//            }
//        }
//        
//        return adjustedImage;
//    }
//
//}
