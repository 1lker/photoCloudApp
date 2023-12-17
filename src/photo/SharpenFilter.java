package photo;

/**
 * The SharpenFilter class represents a filter that sharpens an image by enhancing its edges.
 * It applies a sharpening effect to each pixel of the image based on the difference between the original pixel and its blurred counterpart.
 */
public class SharpenFilter implements Filter{
    private BlurFilter blurFilter;

    /**
     * Constructs a SharpenFilter object with the specified kernel size for blurring.
     * @param kernelSize the kernel size used for blurring the image before applying the sharpening effect
     */
    public SharpenFilter(int kernelSize) {
        this.blurFilter = new BlurFilter(kernelSize / 5);
    }

    /**
     * Applies the sharpening filter to the given image matrix.
     * @param imageMatrix the image matrix to apply the filter to
     * @return the resulting image matrix after applying the sharpening filter
     */
    public ImageMatrix apply(ImageMatrix imageMatrix) {
        ImageMatrix blurred = blurFilter.apply(imageMatrix);
        int width = imageMatrix.getWidth();
        int height = imageMatrix.getHeight();

        ImageMatrix result = new ImageMatrix(width, height);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int originalRGB = imageMatrix.getRGB(x, y);
                int blurredRGB = blurred.getRGB(x, y);

                int deltaRed = imageMatrix.getRed(x, y) - ImageMatrix.getRed(blurredRGB);
                int deltaGreen = imageMatrix.getGreen(x, y) - ImageMatrix.getGreen(blurredRGB);
                int deltaBlue = imageMatrix.getBlue(x, y) - ImageMatrix.getBlue(blurredRGB);

                int newRed = Math.min(Math.max(imageMatrix.getRed(x, y) + deltaRed, 0), 255);
                int newGreen = Math.min(Math.max(imageMatrix.getGreen(x, y) + deltaGreen, 0), 255);
                int newBlue = Math.min(Math.max(imageMatrix.getBlue(x, y) + deltaBlue, 0), 255);

                result.setRGB(x, y, ImageMatrix.convertRGB(newRed, newGreen, newBlue));
            }
        }

        return result;
    }
}
