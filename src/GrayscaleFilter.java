public class GrayscaleFilter extends PixelFilter {

    private static GrayscaleFilter instance;

    @Override
    public int process(int pixel) {
        final double R = getR(pixel);
        final double G = getG(pixel);
        final double B = getB(pixel);
        final double Y = R * 0.2126 + G * 0.7152 + B * 0.0722;
        return getPixel(Y, Y, Y);
    }

    public static GrayscaleFilter getInstance() {
        if (instance == null) instance = new GrayscaleFilter();
        return instance;
    }
}
