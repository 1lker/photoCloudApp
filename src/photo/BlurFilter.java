/**
 * The BlurFilter class implements the Filter interface and represents a blurring filter for images.
 * It applies a blurring effect to an image by calculating the average color values of the surrounding pixels
 * within a specified kernel size and replacing the current pixel's color with the calculated average.
 *
 * Usage:
 * 1. Create an instance of BlurFilter with the specified kernel size.
 * 2. Apply the filter to an ImageMatrix by calling the apply() method.
 * 3. The apply() method calculates the average color values of the surrounding pixels within the kernel size
 *    and replaces each pixel's color with the calculated average.
 * 4. The resulting ImageMatrix with the applied blur filter can be used for further image processing or display.
 *
 * Example:
 * BlurFilter blurFilter = new BlurFilter(5);
 * ImageMatrix imageMatrix = // retrieve the image matrix to apply the filter
 * ImageMatrix result = blurFilter.apply(imageMatrix);
 *
 * Note: The BlurFilter class relies on the Filter and ImageMatrix classes for its functionality.
 * Make sure to have these classes available in the same package or import them as needed.
 */

package photo;
/**
 * The BlurFilter class represents a blurring filter for images. It applies a blurring effect to an image
 * by calculating the average color values of the surrounding pixels within a specified kernel size and
 * replacing the current pixel's color with the calculated average.
 */
public class BlurFilter implements Filter{
    private int kernelSize;  // the size of the blurring kernel

    /**
     * Constructs a BlurFilter object with the specified kernel size.
     * @param kernelSize the size of the blurring kernel
     */
    public BlurFilter(int kernelSize) {
        this.kernelSize = kernelSize / 5;
    }

    /**
     * Applies the blur filter to the given image matrix.
     * @param imageMatrix the image matrix to apply the blur filter to
     * @return the resulting image matrix with the applied blur filter
     */
    public ImageMatrix apply(ImageMatrix imageMatrix) {
        int width = imageMatrix.getWidth();
        int height = imageMatrix.getHeight();

        ImageMatrix result = new ImageMatrix(width, height);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int sumRed = 0, sumGreen = 0, sumBlue = 0;
                int count = 0;

                for (int i = -kernelSize; i <= kernelSize; i++) {
                    for (int j = -kernelSize; j <= kernelSize; j++) {
                        int nx = x + i;
                        int ny = y + j;

                        if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                            int rgb = imageMatrix.getRGB(nx, ny);
                            sumRed += imageMatrix.getRed(nx, ny);
                            sumGreen += imageMatrix.getGreen(nx, ny);
                            sumBlue += imageMatrix.getBlue(nx, ny);
                            count++;
                        }
                    }
                }

                int avgRed = sumRed / count;
                int avgGreen = sumGreen / count;
                int avgBlue = sumBlue / count;

                int avgRGB = ImageMatrix.convertRGB(avgRed, avgGreen, avgBlue);

                result.setRGB(x, y, avgRGB);
            }
        }

        return result;
    }
}
