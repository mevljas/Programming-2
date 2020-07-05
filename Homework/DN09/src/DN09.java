import java.io.File;
import java.util.Objects;
import java.util.Scanner;

public class DN09 {
    public static void main(String[] args) {
        String prvi_argument = args[0];
        if (prvi_argument.equals("poisci")) {
            File file = new File(args[2] + "/" + args[1]);
            if (!file.isDirectory()) {
                Datoteka datoteka = new Datoteka(args[1], args[2]);
                datoteka.poisci(args[3].trim());
            } else {
                Mapa mapa = new Mapa(args[1], args[2]);
                mapa.poisci(args[3].trim());
            }

        } else if (prvi_argument.equals("drevo")) {
            Mapa mapa = new Mapa(args[1], args[2]);
            mapa.drevo(0);

        }
    }

}


abstract class Entiteta {
    String ime;
    String pot;
    Entiteta prednik;


    Entiteta(String ime, String pot) {
        this.ime = ime;
        this.pot = pot;
    }

    void poisci(String niz) {
    }

    void drevo(int nivo) {
    }

}

class Datoteka extends Entiteta {


    Datoteka(String ime, String pot) {
        super(ime, pot);

    }

    void poisci(String niz) {
        StringBuilder prebrano = new StringBuilder();
        boolean vsebuje = false;
        try {
            Scanner in = new Scanner(new File(pot + "/" + ime));

            while (in.hasNextLine()) {
                String vrstica = in.nextLine();
                prebrano.append(vrstica).append("<split>");
                if (vrstica.contains(niz)) {
                    vsebuje = true;
                }
            }


            if (vsebuje) {

                String[] vrstice = prebrano.toString().split("<split>");

                System.out.println(pot + "/" + ime);
                for (int i = 0; i < vrstice.length; i++) {
                    if (vrstice[i].contains(niz)) {
                        boolean[] oznaci = new boolean[vrstice[i].length()];
                        int j = 0;
                        while (true) {
                            int indeks = vrstice[i].indexOf(niz, j);
                            if (indeks != -1) {
                                for (int k = indeks; k < indeks + niz.length(); k++) {
                                    oznaci[k] = true;
                                }
                                j++;
                            } else {
                                break;
                            }
                        }
                        String odmik = i + 1 + ": ";
                        System.out.println(i + 1 + ": " + vrstice[i]);
                        for (int k = 0; k < odmik.length(); k++) {
                            System.out.print(" ");
                        }
                        for (j = 0; j < oznaci.length; j++) {
                            if (oznaci[j]) {
                                System.out.print("^");
                            } else {
                                System.out.print(" ");
                            }
                        }
                        System.out.println();
                    }

                }
                System.out.println();
            }

            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void drevo(int nivo) {
        System.out.print("+--");
        System.out.println(this.ime);
    }

}

class Mapa extends Entiteta {
    private Entiteta[] nasledniki;


    Mapa(String ime, String pot) {
        super(ime, pot);
        this.prednik = null;
    }


    void poisci(String niz) {
        try {
            File dir = new File(pot + "/" + ime);
            File[] files = dir.listFiles();
            int dolzina = Objects.requireNonNull(dir.list()).length;
            nasledniki = new Entiteta[dolzina];
            int i = 0;
            assert files != null;
            for (File f : files) {
                if (f.isDirectory()) {
                    Mapa mapa = new Mapa(f.getName(), this.pot + "/" + this.ime);
                    mapa.prednik = this;
                    this.nasledniki[i++] = mapa;

                } else {
                    Datoteka datoteka = new Datoteka(f.getName(), this.pot + "/" + this.ime);
                    datoteka.prednik = this;
                    this.nasledniki[i++] = datoteka;
                }
            }
            for (Entiteta naslednik : this.nasledniki) {
                naslednik.poisci(niz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void drevo(int nivo) {
        try {
            System.out.println(this.ime);

            File dir = new File(pot + "/" + ime);
            File[] files;
            files = dir.listFiles();
            int dolzina = Objects.requireNonNull(dir.list()).length;
            this.nasledniki = new Entiteta[dolzina];
            int j = 0;
            assert files != null;
            for (File file : files) {

                if (file.isDirectory()) {
                    Mapa mapa = new Mapa(file.getName(), this.pot + "/" + this.ime);
                    mapa.prednik = this;
                    nasledniki[j++] = mapa;
                } else {
                    Datoteka datoteka = new Datoteka(file.getName(), this.pot + "/" + this.ime);
                    datoteka.prednik = this;
                    nasledniki[j++] = datoteka;
                }

            }
            for (Entiteta naslednik : this.nasledniki) {
                System.out.print(" ");
                izrisVej();
                if (naslednik instanceof Mapa) {
                    System.out.print("+--");
                }
                naslednik.drevo(nivo + 1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void izrisVej() {
        if (this.prednik != null) {
            ((Mapa) this.prednik).izrisVej();
            if (((Mapa) this.prednik).nasledniki[((Mapa) this.prednik).nasledniki.length - 1].ime.equals(this.ime)) {
                System.out.print("    ");
            } else {
                System.out.print("|   ");
            }
        }
    }
}


