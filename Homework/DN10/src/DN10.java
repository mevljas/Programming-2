import java.io.File;
import java.util.Scanner;

interface Lik {
    public double povrsina();
}
class Kvadrat implements Lik {

    int a;

    public Kvadrat(int a) {
        this.a = a;
    }

    public double povrsina() {
        return this.a * this.a;
    }
}
class Pravokotnik implements Lik {

    int a;
    int b;

    public Pravokotnik(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public double povrsina() {
        return this.a * this.b;
    }
}
class Krog implements Lik {

    int r;

    public Krog(int r) {
        this.r = r;
    }

    public double povrsina() {
        return Math.PI * this.r * this.r;
    }
}

public class DN10 {

    static Lik[] Liki = new Lik[100];
    static int i = 0;

    static void preberi( String datoteka) {

        try {
            Scanner in = new Scanner( new File(datoteka));
            while (in.hasNextLine()) {
                String vrstica = in.nextLine();
                String[] podatki = vrstica.split(":");
                switch (podatki[0]) {
                    case "kvadrat":
                        Liki[i++ ] = new Kvadrat(Integer.parseInt(podatki[1]));
                        break;
                    case "krog":
                        Liki[i++ ] = new Krog(Integer.parseInt(podatki[1]));
                        break;
                    case "pravokotnik":
                        Liki[i++ ] = new Pravokotnik(Integer.parseInt(podatki[1]), Integer.parseInt(podatki[2]));
                        break;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void izracunaj() {
        double vsota = 0.0;
        for (int j = 0; j < i; j++) {
            vsota += Liki[j].povrsina();
        }
        System.out.printf("%.2f", vsota);
    }

    public static void main(String[] args) {
        preberi(args[0]);
        izracunaj();
    }


}