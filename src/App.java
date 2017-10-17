import java.io.IOException;
import java.util.List;

public class App {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) throw new IllegalArgumentException("No path in the args");
        final String path = args[0];
        Image input = new Image(path + "input.png");
        List<ImageArea> areas = ImageArea.getAreas(input.apply(new BicolourFilter(172)).save(path + "output.png"));
        int i = 0;
        for (ImageArea area : areas)
            area.toImage().save(path + "areas\\" + i++ + ".jpg");
    }
}
