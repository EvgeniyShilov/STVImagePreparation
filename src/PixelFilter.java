public abstract class PixelFilter {

    abstract int process(int pixel);

    static double getR(int pixel) {
        return (0x00FF0000 & pixel) >> 16;
    }

    static double getG(int pixel) {
        return (0x0000FF00 & pixel) >> 8;
    }

    static double getB(int pixel) {
        return (0x000000FF & pixel);
    }

    static int getPixel(double R, double G, double B) {
        return 0xFF000000 | ((int) R << 16) | ((int) G << 8) | (int) B;
    }
}