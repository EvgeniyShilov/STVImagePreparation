import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ImageArea {

    private Image image;
    private List<Point> pixels;

    public ImageArea(Image image) {
        this.image = image;
        pixels = new ArrayList<>();
    }

    public void add(int x, int y) {
        pixels.add(new Point(x, y));
    }

    public boolean contains(int x, int y) {
        return pixels.contains(new Point(x, y));
    }

    public Image toImage() {
        BufferedImage rawImage = image.getRaw();
        int minX = rawImage.getWidth() - 1;
        int minY = rawImage.getHeight() - 1;
        int maxX = 0;
        int maxY = 0;
        for (Point pixel : pixels) {
            if (pixel.x < minX) minX = pixel.x;
            if (pixel.x > maxX) maxX = pixel.x;
            if (pixel.y < minY) minY = pixel.y;
            if (pixel.y > maxY) maxY = pixel.y;
        }
        final int width = maxX - minX + 1;
        final int height = maxY - minY + 1;
        Image result = new Image(width, height);
        BufferedImage rawResult = result.getRaw();
        for (int w = 0; w < width; w++)
            for (int h = 0; h < height; h++)
                rawResult.setRGB(w, h, rawImage.getRGB(minX + w, minY + h));
        return result;
    }

    public static List<ImageArea> getAreas(Image image, int minPixelsCount) {
        System.out.println("Searching for areas...");
        ArrayList<ImageArea> result = new ArrayList<>();
        BufferedImage rawImage = image.getRaw();
        final int width = rawImage.getWidth();
        final int height = rawImage.getHeight();
        for (int w = 0; w < width; w++)
            loop: for (int h = 0; h < height; h++)
                if (IsPixelPartOfArea(rawImage.getRGB(w, h))) {
                    for (ImageArea area : result)
                        if (area.contains(w, h))
                            continue loop;
                    System.out.print("New area found. ");
                    ImageArea area = new ImageArea(image);
                    addPixelToArea(rawImage, w, h, area);
                    System.out.println("Size = " + area.pixels.size());
                    if (area.pixels.size() >= minPixelsCount) result.add(area);
                }
        System.out.println("Total areas count = " + result.size());
        return result;
    }

    private static boolean IsPixelPartOfArea(int pixel) {
        return PixelFilter.getR(pixel) == 0xFF;
    }

    private static void addPixelToArea(BufferedImage rawImage, int x, int y, ImageArea area) {
        area.add(x, y);
        if (x + 1 < rawImage.getWidth() && IsPixelPartOfArea(rawImage.getRGB(x + 1, y)) && !area.contains(x + 1, y))
            addPixelToArea(rawImage, x + 1, y, area);
        if (x - 1 > -1 && IsPixelPartOfArea(rawImage.getRGB(x - 1, y)) && !area.contains(x - 1, y))
            addPixelToArea(rawImage, x - 1, y, area);
        if (y + 1 < rawImage.getHeight() && IsPixelPartOfArea(rawImage.getRGB(x, y + 1)) && !area.contains(x, y + 1))
            addPixelToArea(rawImage, x, y + 1, area);
        if (y - 1 > -1 && IsPixelPartOfArea(rawImage.getRGB(x, y - 1)) && !area.contains(x, y - 1))
            addPixelToArea(rawImage, x, y - 1, area);
    }
}
