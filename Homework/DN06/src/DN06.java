import java.io.File;
import java.util.Scanner;

public class DN06 {
    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(new File(args[0]));
            String niz = "";
            String ascii;
            while (in.hasNext()) {
                niz += in.next();
            }
            for(int i = 0; i < niz.length(); i+=8) {
                ascii = niz.substring(i, i + 8);
                System.out.print((char)Integer.parseInt(ascii, 2));
            }

            in.close();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
