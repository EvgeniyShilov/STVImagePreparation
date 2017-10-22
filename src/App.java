import java.io.IOException;
import java.util.List;

public class App {

    public static void main(String[] args) {
        if (args.length == 0) throw new IllegalArgumentException("No path in the args");
        final String path = args[0];
        for (int i = 0; i < 40; i++) {
            try {
                System.out.println("Processing " + i + " image");
                Image input = new Image(path + "sources\\" + i + ".png");
                Image bicolour = input.apply(new BicolourFilter(136)).save(path + "output\\" + i + "\\bicolour.png");
                System.out.println("Bicolour image was saved");
                List<ImageArea> areas = ImageArea.getAreas(bicolour, 90);
                System.out.println("Possible symbols count = " + areas.size());
                int j = 0;
                for (ImageArea area : areas)
                    area.toImage().resize(256).save(path + "output\\" + i + "\\" + j++ + ".png");
                System.out.println("Symbols were saved to " + i + " directory");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
