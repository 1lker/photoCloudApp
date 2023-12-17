package photo;

/**
 * The Filter interface represents a filter that can be applied to an ImageMatrix.
 * Implementing classes should provide an implementation for the apply() method.
 */
public interface Filter {
    /**
     * Applies the filter to the given ImageMatrix.
     *
     * @param imageMatrix the ImageMatrix to apply the filter to
     * @return the modified ImageMatrix after applying the filter
     */
    ImageMatrix apply(ImageMatrix imageMatrix);
}