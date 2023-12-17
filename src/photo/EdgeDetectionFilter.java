package photo;

/**
 * The EdgeDetectionFilter class represents a filter that detects edges in an image using the Sobel operator.
 * It applies the Sobel operator to calculate the gradients of the image in the x and y directions,
 * and then calculates the magnitude of the gradient to identify and highlight the edges in the image.
 */
public class EdgeDetectionFilter implements Filter{
    private static final int[][] SOBEL_X = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
    private static final int[][] SOBEL_Y = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};

    /**
     * Applies the edge detection filter to the given image matrix using the Sobel operator.
     *
     * @param imageMatrix the image matrix to apply the filter to
     * @return the resulting image matrix after applying the edge detection filter
     */
	public ImageMatrix apply(ImageMatrix imageMatrix) {
        int width = imageMatrix.getWidth();
        int height = imageMatrix.getHeight();

        ImageMatrix result = new ImageMatrix(width, height);

        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height - 1; y++) {
                int pixelX = (
                    SOBEL_X[0][0] * imageMatrix.getRed(x-1, y-1) + SOBEL_X[0][1] * imageMatrix.getRed(x, y-1) + SOBEL_X[0][2] * imageMatrix.getRed(x+1, y-1) +
                    SOBEL_X[1][0] * imageMatrix.getRed(x-1, y)   + SOBEL_X[1][1] * imageMatrix.getRed(x, y)   + SOBEL_X[1][2] * imageMatrix.getRed(x+1, y) +
                    SOBEL_X[2][0] * imageMatrix.getRed(x-1, y+1) + SOBEL_X[2][1] * imageMatrix.getRed(x, y+1) + SOBEL_X[2][2] * imageMatrix.getRed(x+1, y+1)
                );

                int pixelY = (
                    SOBEL_Y[0][0] * imageMatrix.getRed(x-1, y-1) + SOBEL_Y[0][1] * imageMatrix.getRed(x, y-1) + SOBEL_Y[0][2] * imageMatrix.getRed(x+1, y-1) +
                    SOBEL_Y[1][0] * imageMatrix.getRed(x-1, y)   + SOBEL_Y[1][1] * imageMatrix.getRed(x, y)   + SOBEL_Y[1][2] * imageMatrix.getRed(x+1, y) +
                    SOBEL_Y[2][0] * imageMatrix.getRed(x-1, y+1) + SOBEL_Y[2][1] * imageMatrix.getRed(x, y+1) + SOBEL_Y[2][2] * imageMatrix.getRed(x+1, y+1)
                );

                int magnitude = (int) Math.sqrt(pixelX * pixelX + pixelY * pixelY);

                magnitude = Math.min(Math.max(magnitude, 0), 255);  // Clamp between 0 and 255

                result.setRGB(x, y, ImageMatrix.convertRGB(magnitude, magnitude, magnitude));  // The edges are displayed in grayscale
            }
        }

        return result;
    }
}
