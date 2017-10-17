public class BicolourFilter extends PixelFilter {

    private int threshold;
    private int low;
    private int high;

    public BicolourFilter(int threshold) {
        this(threshold, true);
    }

    public BicolourFilter(int threshold, boolean isInverse) {
        this.threshold = threshold;
        low = isInverse ? 0xFF : 0;
        high = isInverse ? 0 : 0xFF;
    }

    @Override
    int process(int pixel) {
        final int grayscale = GrayscaleFilter.getInstance().process(pixel);
        final double Y = getR(grayscale) < threshold ? low : high;
        return getPixel(Y, Y, Y);
    }
}
