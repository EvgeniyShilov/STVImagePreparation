import java.util.Arrays;

public class MedianFilter implements ConvolutionFilter {

    private static MedianFilter instance;

    @Override
    public int process(int px1, int px2, int px3,
                       int px4, int px5, int px6,
                       int px7, int px8, int px9) {
        int[] Ys = new int[]{
                px1, px2, px3,
                px4, px5, px6,
                px7, px8, px9
        };
        Arrays.sort(Ys);
        return Ys[4];
    }

    public static MedianFilter getInstance() {
        if (instance == null) instance = new MedianFilter();
        return instance;
    }
}
