import java.io.File;
import java.util.Scanner;

public class DN08 {

    static class Planeti {
        String ime;
        int radij;

        Planeti(String ime, int radij) {
            this.ime = ime;
            this.radij = radij;
        }

        double povrsina() {
            return 4d * Math.PI * Math.pow(radij, 2);
        }
    }
    public static void main(String[] args) {
        Planeti[] planeti = new Planeti[9];
        int i = 0;
        String vrstica;
        try {
            Scanner in = new Scanner(new File(args[0]));
            while (in.hasNextLine()) {
                vrstica = in.nextLine();
                if ( vrstica.contains(":")) {
                    String[] tab = vrstica.split(":");
                    planeti[i++] = new Planeti(tab[0], Integer.parseInt(tab[1]));
                }
            }
            in.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        String [] argumenti = args[1].split("\\+");

        if (argumenti.length > 0) {
            double vsota = 0;
            for (String arg: argumenti) {
                for (Planeti p: planeti) {
                    if ( p.ime.toUpperCase().equals(arg.toUpperCase())) {
                        vsota += p.povrsina();
                    }
                }

            }
            String izpis = "Povrsina planetov \"" + argumenti[0];
            for (int j = 1; j < argumenti.length; j++) {
                izpis += "+"+argumenti[j];
            }
            izpis += "\" je "+ Math.round(vsota / 1000000) + " milijonov km2";
            System.out.println(izpis);
        }


    }
}
