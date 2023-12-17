package photo;

/**
 * The ContrastFilter class represents a filter that adjusts the contrast of an image.
 * It applies a contrast adjustment to each pixel of the image based on the specified contrast value.
 */
public class ContrastFilter implements Filter{
    private double contrast;

    /**
     * Constructs a ContrastFilter object with the specified contrast value.
     * @param contrast the contrast value to apply
     */
    public ContrastFilter(double contrast) {
        this.contrast = contrast;
    }

    /**
     * Applies the contrast filter to the given image matrix.
     * @param imageMatrix the image matrix to apply the filter to
     * @return the resulting image matrix after applying the contrast filter
     */
    public ImageMatrix apply(ImageMatrix imageMatrix) {
        int width = imageMatrix.getWidth();
        int height = imageMatrix.getHeight();

        ImageMatrix result = new ImageMatrix(width, height);

        double f = (259 * (contrast + 255)) / (255 * (259 - contrast));

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int red = imageMatrix.getRed(x, y);
                int green = imageMatrix.getGreen(x, y);
                int blue = imageMatrix.getBlue(x, y);

                red = (int)(f * (red - 128) + 128);
                green = (int)(f * (green - 128) + 128);
                blue = (int)(f * (blue - 128) + 128);

                // Clamp values between 0 and 255
                red = Math.min(Math.max(red, 0), 255);
                green = Math.min(Math.max(green, 0), 255);
                blue = Math.min(Math.max(blue, 0), 255);

                result.setRGB(x, y, ImageMatrix.convertRGB(red, green, blue));
            }
        }

        return result;
    }
}
