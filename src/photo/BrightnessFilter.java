package photo;

/**
 * The BrightnessFilter class represents a filter for adjusting the brightness of an image. It applies
 * a specified brightness adjustment to each pixel in the image by adding the brightness value to the
 * red, green, and blue color components of the pixel. The resulting color values are clamped between
 * 0 and 255 to ensure they stay within the valid color range.
 */
public class BrightnessFilter implements Filter{
    private int brightness;

    /**
     * Constructs a BrightnessFilter object with the specified brightness adjustment value.
     * @param brightness the brightness adjustment value to apply to each pixel
     */
    public BrightnessFilter(int brightness) {
        this.brightness = brightness ;
    }

    /**
     * Applies the brightness filter to the given image matrix.
     * @param imageMatrix the image matrix to apply the brightness filter to
     * @return the resulting image matrix with the applied brightness filter
     */
    public ImageMatrix apply(ImageMatrix imageMatrix) {
        int width = imageMatrix.getWidth();
        int height = imageMatrix.getHeight();

        ImageMatrix result = new ImageMatrix(width, height);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = imageMatrix.getRGB(x, y);

                int red = imageMatrix.getRed(x, y) + brightness;
                int green = imageMatrix.getGreen(x, y) + brightness;
                int blue = imageMatrix.getBlue(x, y) + brightness;

                // Clamp values between 0 and 255
                red = Math.max(0, Math.min(255, red));
                green = Math.max(0, Math.min(255, green));
                blue = Math.max(0, Math.min(255, blue));

                int adjustedRGB = ImageMatrix.convertRGB(red, green, blue);
                result.setRGB(x, y, adjustedRGB);
            }
        }

        return result;
    }
}
