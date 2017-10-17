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

    public static Image loadFrom(String path) throws IOException {
        return new Image(path);
    }

    public static Image saveTo(Image image, String path) throws IOException {
        return image.save(path);
    }
}
