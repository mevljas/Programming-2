import java.awt.*;
import java.util.Random;


public class DN07 {
    public static void main(String[] args) {
        int x;
        int y;
        int b;
        int v;
        Random rand = new Random();
        StdDraw.setCanvasSize(800, 800);
        StdDraw.setScale(0, 800);

        for(int i = 0; i < 100; i++) {
            v = rand.nextInt(40);
            y = rand.nextInt( 550) +200;
            x = rand.nextInt(750);
            float red = rand.nextFloat();
            float green = rand.nextFloat();
            float blue = rand.nextFloat();
            Color randomColor = new Color(red, green, blue);
            narisi(x, y, randomColor, v);
        }

    }
    static void narisi(int x, int y, Color b, int v) {


        StdDraw.setPenColor(Color.green);
        StdDraw.setPenRadius(0.01);
        StdDraw.line(x, y - 2*v, x, y - 5*v);
        StdDraw.setPenRadius();
        StdDraw.setPenColor(b);
        StdDraw.filledCircle(x + v*1.3, y, v);
        StdDraw.filledCircle(x - v*1.3, y, v);
        StdDraw.filledCircle(x, y + v*1.3, v);
        StdDraw.filledCircle(x, y - v*1.3, v);
        StdDraw.setPenColor(Color.green);
        StdDraw.filledCircle(x, y, v * 0.6);

    }
}
