import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image {

    private BufferedImage image;

    public Image(String path) throws IOException {
        image = ImageIO.read(new File(path));
    }

    public Image(int width, int height) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    public BufferedImage getRaw() {
        return image;
    }

    public Image save(String path) throws IOException {
        ImageIO.write(image, "png", new File(path));
        return this;
    }

    public Image apply(PixelFilter filter) {
        Image result = new Image(image.getWidth(), image.getHeight());
        for (int w = 0; w < image.getWidth(); w++)
            for (int h = 0; h < image.getHeight(); h++)
                result.image.setRGB(w, h, filter.process(image.getRGB(w, h)));
        return result;
    }

    public Image apply(ConvolutionFilter filter) {
        Image result = new Image(image.getWidth(), image.getHeight());
        for (int w = 0; w < image.getWidth(); w++)
            for (int h = 0; h < image.getHeight(); h++)
                result.image.setRGB(w, h, filter.process(
                        image.getRGB(Math.max(w - 1, 0), Math.max(h - 1, 0)),
                        image.getRGB(w, Math.max(h - 1, 0)),
                        image.getRGB(Math.min(w + 1, image.getWidth() - 1), Math.max(h - 1, 0)),
                        image.getRGB(Math.max(w - 1, 0), h),
                        image.getRGB(w, h),
                        image.getRGB(Math.min(w + 1, image.getWidth() - 1), h),
                        image.getRGB(Math.max(w - 1, 0), Math.min(h + 1, image.getHeight() - 1)),
                        image.getRGB(w, Math.min(h + 1, image.getHeight() - 1)),
                        image.getRGB(Math.min(w + 1, image.getWidth() - 1), Math.min(h + 1, image.getHeight() - 1))));
        return result;
    }

    public Image resize(int size) {
        final int width = image.getWidth();
        final int height = image.getHeight();
        final int edge = width > height ? width : height;
        final int xDelta = (edge - width) / 2;
        final int yDelta = (edge - height) / 2;
        Image square = new Image(edge, edge);
        for (int w = 0; w < edge; w++)
            for (int h = 0; h < edge; h++)
                square.image.setRGB(w, h, 0xFF000000);
        for (int w = xDelta; w < xDelta + width; w++)
            for (int h = yDelta; h < yDelta + height; h++)
                square.image.setRGB(w, h, image.getRGB(w - xDelta, h - yDelta));
        Image result = new Image(size, size);
        for (int w = 0; w < size; w++)
            for (int h = 0; h < size; h++) {
                final int x = (w * edge) / size;
                final int y = (h * edge) / size;
                result.image.setRGB(w, h, square.image.getRGB(x, y));
            }
        return result;
    }

    public static Image loadFrom(String path) throws IOException {
        return new Image(path);
    }

    public static Image saveTo(Image image, String path) throws IOException {
        return image.save(path);
    }
}
