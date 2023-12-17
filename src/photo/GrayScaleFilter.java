package photo;

/**
 * The GrayScaleFilter class implements the Filter interface to convert an image to grayscale.
 */
public class GrayScaleFilter implements Filter{
    /**
     * Applies the grayscale filter to the given ImageMatrix.
     *
     * @param imageMatrix the ImageMatrix to apply the grayscale filter to
     * @return the modified ImageMatrix after applying the grayscale filter
     */
    public ImageMatrix apply(ImageMatrix imageMatrix) {
        int width = imageMatrix.getWidth();
        int height = imageMatrix.getHeight();

        ImageMatrix result = new ImageMatrix(width, height);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = imageMatrix.getRGB(x, y);

                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                // Calculate grayscale using luminosity method
                int gray = (int) (0.21 * red + 0.72 * green + 0.07 * blue);

                int adjustedRGB = ImageMatrix.convertRGB(gray, gray, gray);

                result.setRGB(x, y, adjustedRGB);
            }
        }

        return result;
    }
}

