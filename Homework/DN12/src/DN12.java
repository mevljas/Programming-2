

import java.io.*;
import java.util.*;

public class DN12 {
    public static void main(String[] args) {

        int ukaz = Integer.parseInt(args[0]);
        Fotka slikca;

        try {
            switch (ukaz) {
                case 1:
                    slikca = Fotka.preberiZnakovnoSliko(args[1], false);
                    new Izris(slikca).prikazi();
                    break;
                case 2:
                    slikca = Fotka.preberiBinarnoSliko(args[1], false);
                    new Izris(slikca).prikazi();
                    break;
                case 3:
                    String koncnica = args[1].substring(args[1].indexOf("."));
                    if (koncnica.equals(".p2t")) {
                        Fotka.pretvoriVBinarno(args[1]);
                        slikca = Fotka.preberiBinarnoSliko(args[1].replace(".p2t", ".p2b"), false);
                    } else {
                        Fotka.pretvoriVTekstovno(args[1]);
                        slikca = Fotka.preberiZnakovnoSliko(args[1].replace(".p2b", ".p2t"), false);
                    }
                    new Izris(slikca).prikazi();
                    break;
                case 4:
                    slikca = Fotka.preberiDatoteko(args[1], true);
                    new Izris(slikca).prikazi();
                    break;
                case 5:
                    System.out.println("Mediana pikslov je: " + Fotka.mediana(args[1]));
                    break;
                case 6:
                    slikca = Fotka.preberiDatoteko(args[1], false);
                    slikca.najkrajsaPotDatoteke(args[2]);
                    break;
                case 7:
                    slikca = Fotka.preberiDatoteko(args[1], false);
                    String[] koordinata = args[2].split(",");
                    String[] resolucija = args[3].split("x");
                    slikca.zamenjajSlikovneTocke(Integer.parseInt(koordinata[0]), Integer.parseInt(koordinata[1]), Integer.parseInt(resolucija[0]), Integer.parseInt(resolucija[1]));
                    slikca.setIme(args[4] + ".p2b");
                    slikca.shraniBitnoDatoteko();
                    Fotka.preberiBinarnoSliko(args[4] + ".p2b", false);
                    new Izris(slikca).prikazi();
                    break;
                case 8:
                    HashSet<String> datoteke = new HashSet<>();
                    for (int i = 1; i < args.length; i++) {
                        File f = new File(args[i]);
                        if (f.isDirectory()) {
                            datoteke.addAll(poisciDatoteke(f, f.getName() + "/", datoteke));
                        } else {

                            if (args[i].endsWith(".p2b") || args[i].endsWith(".p2t")) {
                                datoteke.add(args[i]);
                            }
                        }
                    }
                    List<Fotka> fotke = new ArrayList<>();
                    for (String d : datoteke) {
                        fotke.add(Fotka.preberiDatoteko(d, false));
                    }
                    Collections.sort(fotke);
                    for (Fotka f : fotke) {
                        System.out.println(f.getIme() + ": Mediana: " + Fotka.mediana(f.getIme()) + "; Velikost[b]: " + f.getVelikostDatoteke());
                    }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    private static HashSet<String> poisciDatoteke(File dir, String pot, HashSet<String> datoteke) {
        try {
            File[] files = dir.listFiles();
            assert files != null;
            for (File file : files) {
                if (file.isDirectory()) {
                    datoteke.addAll(poisciDatoteke(file, pot.concat(file.getName() + "/"), datoteke));
                } else {
                    if (file.getName().endsWith(".p2b") || file.getName().endsWith(".p2t")) {
                        datoteke.add(pot + file.getName());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datoteke;
    }


}

class SlikovnaTocka {
    private final int rdeca;
    private final int zelena;
    private final int modra;

    SlikovnaTocka(int rdeca, int zelena, int modra) {
        this.rdeca = rdeca;
        this.zelena = zelena;
        this.modra = modra;
    }

    SlikovnaTocka(int siv) {
        this.rdeca = siv;
        this.zelena = siv;
        this.modra = siv;
    }

    int[] getBarve() {
        return new int[]{rdeca, zelena, modra};
    }

}

class Fotka implements Comparable<Fotka> {
    private final int sirina;
    private final int visina;
    private final String tipSlike;
    private final SlikovnaTocka[][] seznamTock;
    private final long velikost;
    private String ime;


    private Fotka(int sirina, int visina, String ime, String tipSlike, SlikovnaTocka[][] seznamTock) {
        this.sirina = sirina;
        this.visina = visina;
        this.ime = ime;
        this.tipSlike = tipSlike;
        this.seznamTock = seznamTock;
        File f = new File(ime);
        velikost = f.length();
    }

    static Fotka preberiZnakovnoSliko(String imeDatoteke, boolean test) throws Exception {
        SlikovnaTocka[][] seznamTock;
        String[] rdeca;
        String[] zelena;
        String[] modra;
        String tipSlike;
        int sirina;
        int visina;
        Scanner in = new Scanner(new File(imeDatoteke));
        tipSlike = in.next();


        String[] velikost = in.next().split("x");

        sirina = Integer.parseInt(velikost[0]);

        visina = Integer.parseInt(velikost[1]);


        in.nextLine();
        if (test) {
            preveriIme(tipSlike, imeDatoteke);
            preveriTIpTekstovne(tipSlike);
            preveriDimenzije(velikost);
            preveriVelikostSlike(sirina);
            preveriVelikostSlike(visina);
            preveriVelikostSlike(sirina * visina);
        }
        seznamTock = new SlikovnaTocka[sirina][visina];
        if (tipSlike.equals("fp2tC")) {
            rdeca = in.nextLine().split(" ");
            zelena = in.nextLine().split(" ");
            modra = in.nextLine().split(" ");
            if (test) {
                preveriSteviloPixlov(sirina * visina * 3, rdeca.length + zelena.length + modra.length, "P2T");

            }
            int st = 0;
            for (int i = 0; i < sirina; i++) {
                for (int j = 0; j < visina; j++) {
                    int r = Integer.parseInt(rdeca[st]);
                    int g = Integer.parseInt(zelena[st]);
                    int b = Integer.parseInt(modra[st++]);

                    if (test) {
                        preveriVrednostPixla(r, "P2T");
                        preveriVrednostPixla(g, "P2T");
                        preveriVrednostPixla(b, "P2T");
                    }

                    seznamTock[i][j] = new SlikovnaTocka(r, g, b);
                }
            }
        } else {

            int st = 0;
            for (int i = 0; i < sirina; i++) {
                for (int j = 0; j < visina; j++) {
                    seznamTock[i][j] = new SlikovnaTocka(in.nextInt());
                    if (test) {
                        preveriVrednostPixla(seznamTock[i][j].getBarve()[0], "P2T");
                        st++;
                    }

                }
            }
            if (test) {
                preveriSteviloPixlov(sirina * visina, st, "P2T");
            }

        }
        in.close();
        return new Fotka(sirina, visina, imeDatoteke, tipSlike, seznamTock);

    }


    static Fotka preberiBinarnoSliko(String imeDatoteke, boolean test) throws Exception {
        SlikovnaTocka[][] seznamTock;
        int[] rdeca;
        int[] zelena;
        int[] modra;
        StringBuilder temp = new StringBuilder();
        String tipSlike;
        int sirina;
        int visina;
        DataInputStream inputStream = new DataInputStream(new FileInputStream(imeDatoteke));
        for (int i = 0; i < 5; i++) {
            temp.append((char) inputStream.readByte());
        }
        tipSlike = temp.toString();

        sirina = inputStream.readInt();

        visina = inputStream.readInt();


        if (test) {
            preveriIme(tipSlike, imeDatoteke);
            preveriTIpBinarne(tipSlike);
            preveriVelikostSlike(sirina);
            preveriVelikostSlike(visina);
            preveriVelikostSlike(sirina * visina);
        }

        rdeca = new int[sirina * visina];
        zelena = new int[sirina * visina];
        modra = new int[sirina * visina];
        seznamTock = new SlikovnaTocka[sirina][visina];
        if (tipSlike.equals("fp2bC")) {

            if (test) {
                preveriSteviloPixlov(sirina * visina * 3, inputStream.available(), "P2B");
            }

            int st = 0;
            for (int i = 0; i < (sirina * visina); i++) {
                rdeca[st++] = inputStream.read();
            }
            st = 0;
            for (int i = 0; i < (sirina * visina); i++) {
                zelena[st++] = inputStream.read();
            }
            st = 0;
            for (int i = 0; i < (sirina * visina); i++) {
                modra[st++] = inputStream.read();
            }
            st = 0;
            for (int i = 0; i < sirina; i++) {
                for (int j = 0; j < visina; j++) {

                    if (test) {
                        preveriVrednostPixla(rdeca[st], "P2B");
                        preveriVrednostPixla(zelena[st], "P2B");
                        preveriVrednostPixla(modra[st], "P2B");
                    }

                    seznamTock[i][j] = new SlikovnaTocka(rdeca[st], zelena[st], modra[st++]);
                }
            }
        } else {

            if (test) {
                preveriSteviloPixlov(sirina * visina, inputStream.available(), "P2B");
            }

            for (int i = 0; i < sirina; i++) {
                for (int j = 0; j < visina; j++) {
                    seznamTock[i][j] = new SlikovnaTocka(inputStream.read());
                    if (test) {
                        preveriVrednostPixla(seznamTock[i][j].getBarve()[0], "P2B");
                    }

                }
            }
        }
        inputStream.close();
        return new Fotka(sirina, visina, imeDatoteke, tipSlike, seznamTock);
    }

    static void pretvoriVBinarno(String imeDatoteke) throws Exception {
        String[] rdeca;
        String[] zelena;
        String[] modra;
        int sirina;
        int visina;

        Scanner in = new Scanner(new File(imeDatoteke));
        String tipSlike = in.next().replace("t", "b");


        String[] velikost = in.next().split("x");
        sirina = Integer.parseInt(velikost[0]);
        visina = Integer.parseInt(velikost[1]);
        in.nextLine();

        DataOutputStream out = new DataOutputStream(new FileOutputStream(imeDatoteke.replace(".p2t", ".p2b")));
        for (int i = 0; i < tipSlike.length(); i++) {
            out.writeByte(tipSlike.charAt(i));
        }
        out.writeInt(sirina);
        out.writeInt(visina);
        if (tipSlike.equals("fp2bC")) {
            rdeca = in.nextLine().split(" ");
            zelena = in.nextLine().split(" ");
            modra = in.nextLine().split(" ");
            for (String r : rdeca) {
                out.write(Integer.parseInt(r));
            }
            for (String z : zelena) {
                out.write(Integer.parseInt(z));
            }
            for (String m : modra) {
                out.write(Integer.parseInt(m));
            }

        } else for (int i = 0; i < sirina; i++) {
            for (int j = 0; j < visina; j++) {
                out.write(in.nextInt());
            }
        }
        in.close();
        out.close();
    }

    static void pretvoriVTekstovno(String imeDatoteke) throws Exception {
        StringBuilder temp = new StringBuilder();
        int sirina;
        int visina;

        DataInputStream inputStream = new DataInputStream(new FileInputStream(imeDatoteke));
        PrintWriter out = new PrintWriter(new File(imeDatoteke.replace(".p2b", ".p2t")));
        for (int i = 0; i < 5; i++) {
            temp.append((char) inputStream.readByte());
        }
        String tipSlike = temp.toString().replace("b", "t");

        out.print(tipSlike);
        out.print(" ");
        sirina = inputStream.readInt();
        visina = inputStream.readInt();
        out.print(sirina);
        out.print("x");
        out.println(visina);
        if (tipSlike.equals("fp2tC")) {
            for (int i = 0; i < (sirina * visina); i++) {
                out.print(inputStream.read() + " ");
            }
            out.println();
            for (int i = (sirina * visina); i < (sirina * visina * 2); i++) {
                out.print(inputStream.read() + " ");
            }
            out.println();
            for (int i = (sirina * visina) * 2; i < (sirina * visina * 3); i++) {
                out.print(inputStream.read() + " ");
            }

        } else {
            for (int i = 0; i < (sirina * visina); i++) {
                out.print(inputStream.read() + " ");
            }
        }
        inputStream.close();
        out.close();
    }

    private static void preveriTIpTekstovne(String tipSlike) throws Exception {
        if (!(tipSlike.equals("fp2tG") || tipSlike.equals("fp2tC"))) {
            throw new MyException("Datoteka ni v formatu P2T: napaka v tipu slike.");
        }
    }

    private static void preveriTIpBinarne(String tipSlike) throws Exception {
        if (!(tipSlike.equals("fp2bG") || tipSlike.equals("fp2bC"))) {
            throw new MyException("Datoteka ni v formatu P2B: napaka v tipu slike.");
        }
    }

    private static void preveriVrednostPixla(int pixel, String tip) throws Exception {
        if (pixel > 255) {
            throw new MyException("Datoteka ni v formatu " + tip + ": napaka pri vrednosti pikslov.");
        }
    }

    private static void preveriVelikostSlike(long velikost) throws Exception {
        if (velikost > Integer.MAX_VALUE || velikost <= 0) {
            throw new MyException("Datoteka ni v formatu P2B/P2T: napaka v velikosti slike.");
        }
    }

    private static void preveriDimenzije(String[] tab) throws Exception {
        if (tab.length != 2) {
            throw new MyException("Datoteka ni v formatu P2B/P2T: napaka v podatkih o pixlih.");
        }
    }

    private static void preveriIme(String tip, String imeDatoteke) throws Exception {
        if (!tip.substring(1, 4).equals(imeDatoteke.substring(imeDatoteke.indexOf(".") + 1))) {
            throw new MyException("Datoteka ni v formatu P2T/P2B: napaka v imenu datoteke.");
        }
    }

    private static void preveriSteviloPixlov(int pricakovano, int zahtevano, String tip) throws MyException {
        if (pricakovano != zahtevano) {
            throw new MyException("Datoteka ni v formatu " + tip + ": napaka pri vrednosti pikslov.");
        }
    }

    public static float medianaTekstovneDatoteke(String imeDatoteke) {
        float[] seznamPixlov = new float[0];
        String[] rdeca;
        String[] zelena;
        String[] modra;
        String tipSlike;
        int sirina = 0;
        int visina = 0;
        try {
            Scanner in = new Scanner(new File(imeDatoteke));
            tipSlike = in.next();


            String[] velikost = in.next().split("x");

            sirina = Integer.parseInt(velikost[0]);

            visina = Integer.parseInt(velikost[1]);


            in.nextLine();
            seznamPixlov = new float[sirina * visina];
            if (tipSlike.equals("fp2tC")) {
                rdeca = in.nextLine().split(" ");
                zelena = in.nextLine().split(" ");
                modra = in.nextLine().split(" ");

                int st = 0;
                for (int i = 0; i < sirina * visina; i++) {
                    int r = Integer.parseInt(rdeca[st]);
                    int g = Integer.parseInt(zelena[st]);
                    int b = Integer.parseInt(modra[st++]);
                    seznamPixlov[i] = (r + g + b) / 3f;
                }
            } else {

                for (int i = 0; i < sirina * visina; i++) {
                    seznamPixlov[i] = in.nextInt();
                }

            }
            in.close();
            Arrays.sort(seznamPixlov);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return Float.parseFloat((String.format("%.1f", seznamPixlov[sirina * visina / 2])).replace(",", "."));
    }

    public static float medianaBitneSlike(String imeDatoteke) {
        float[] seznamPixlov = new float[0];
        int sirina = 0;
        int visina = 0;
        try {
            int[] rdeca;
            int[] zelena;
            int[] modra;
            StringBuilder temp = new StringBuilder();
            String tipSlike;

            DataInputStream inputStream = new DataInputStream(new FileInputStream(imeDatoteke));
            for (int i = 0; i < 5; i++) {
                temp.append((char) inputStream.readByte());
            }
            tipSlike = temp.toString();

            sirina = inputStream.readInt();

            visina = inputStream.readInt();


            rdeca = new int[sirina * visina];
            zelena = new int[sirina * visina];
            modra = new int[sirina * visina];
            seznamPixlov = new float[sirina * visina];
            if (tipSlike.equals("fp2bC")) {

                for (int i = 0; i < (sirina * visina); i++) {
                    rdeca[i] = inputStream.read();
                }
                for (int i = 0; i < (sirina * visina); i++) {
                    zelena[i] = inputStream.read();
                }
                for (int i = 0; i < (sirina * visina); i++) {
                    modra[i] = inputStream.read();
                }
                for (int i = 0; i < sirina * visina; i++) {
                    seznamPixlov[i] = (rdeca[i] + zelena[i] + modra[i]) / 3f;
                }
            } else {

                for (int i = 0; i < sirina * visina; i++) {
                    seznamPixlov[i] = inputStream.read();
                }
            }
            inputStream.close();
            Arrays.sort(seznamPixlov);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return Float.parseFloat((String.format("%.1f", seznamPixlov[sirina * visina / 2])).replace(",", "."));


    }

    static Fotka preberiDatoteko(String imeDatoteke, boolean test) throws Exception {
        Fotka slikca;
        if (imeDatoteke.endsWith(".p2t")) {
            Fotka.preberiZnakovnoSliko(imeDatoteke, test);
            slikca = Fotka.preberiZnakovnoSliko(imeDatoteke, test);
        } else {
            Fotka.preberiBinarnoSliko(imeDatoteke, test);
            slikca = Fotka.preberiBinarnoSliko(imeDatoteke, test);
        }
        return slikca;
    }

    static float mediana(String ime) {
        if (ime.endsWith(".p2t")) {
            return medianaTekstovneDatoteke(ime);
        } else {
            return medianaBitneSlike(ime);
        }
    }

    public String getSirinaVisina() {
        return sirina + "x" + visina;
    }

    public int getVelikost() {
        return sirina * visina;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public SlikovnaTocka getTocka(int x, int y) {
        return seznamTock[x][y];
    }

    public int getSirina() {
        return sirina;
    }

    public int getVisina() {
        return visina;
    }

    void najkrajsaPotDatoteke(String novoIme) throws Exception {
        int zacetek = 0;
        int konec = visina;
        int prejsniIndex = 0;
        float prejsnaSvtelost;
        if (this.tipSlike.endsWith("C")) {

            for (SlikovnaTocka[] stolpec : seznamTock) {
                prejsnaSvtelost = Integer.MAX_VALUE;
                for (int i = zacetek; i < konec; i++) {
                    int r = stolpec[i].getBarve()[0];
                    int g = stolpec[i].getBarve()[1];
                    int b = stolpec[i].getBarve()[2];
                    float vrednost = (r + g + b) / 3f;
                    if (vrednost < prejsnaSvtelost) {
                        prejsniIndex = i;
                        prejsnaSvtelost = vrednost;
                    }
                }
                stolpec[prejsniIndex] = new SlikovnaTocka(255, 255, 255);
                System.out.println(prejsniIndex);
                if (prejsniIndex > 0)
                    zacetek = prejsniIndex - 1;
                else
                    zacetek = prejsniIndex;
                if (prejsniIndex < visina - 1)
                    konec = prejsniIndex + 2;
                else if (prejsniIndex < visina)
                    konec = prejsniIndex + 1;
                else
                    konec = prejsniIndex;
            }
        } else {
            for (SlikovnaTocka[] stolpec : seznamTock) {
                prejsnaSvtelost = Integer.MAX_VALUE;
                for (int i = zacetek; i < konec; i++) {
                    int vrednost = stolpec[i].getBarve()[0];

                    if (vrednost < prejsnaSvtelost) {
                        prejsniIndex = i;
                        prejsnaSvtelost = vrednost;
                    }
                }

                stolpec[prejsniIndex] = new SlikovnaTocka(255, 255, 255);
                System.out.println(prejsniIndex);
                if (prejsniIndex > 0)
                    zacetek = prejsniIndex - 1;
                else
                    zacetek = prejsniIndex;
                if (prejsniIndex < visina - 1)
                    konec = prejsniIndex + 2;
                else if (prejsniIndex < visina)
                    konec = prejsniIndex + 1;
                else
                    konec = prejsniIndex;
            }
        }
        String novoImeDatoteke;
        if (this.tipSlike.charAt(3) == 'b') {
            novoImeDatoteke = novoIme + ".p2b";
        } else {
            novoImeDatoteke = novoIme + ".p2t";
        }
        Fotka fotka = new Fotka(this.sirina, this.visina, novoImeDatoteke, tipSlike, seznamTock);
        fotka.shraniDatoteko();
        Fotka.preberiDatoteko(novoImeDatoteke, false);
        new Izris(fotka).prikazi();

    }

    void shraniBitnoDatoteko() {
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(this.ime));
            for (int i = 0; i < tipSlike.length(); i++) {
                out.writeByte(tipSlike.charAt(i));
            }
            out.writeInt(sirina);
            out.writeInt(visina);
            if (tipSlike.equals("fp2bC")) {
                for (SlikovnaTocka[] t : seznamTock) {
                    for (SlikovnaTocka t2 : t) {
                        out.write(t2.getBarve()[0]);
                    }
                }
                for (SlikovnaTocka[] t : seznamTock) {
                    for (SlikovnaTocka t2 : t) {
                        out.write(t2.getBarve()[1]);
                    }
                }
                for (SlikovnaTocka[] t : seznamTock) {
                    for (SlikovnaTocka t2 : t) {
                        out.write(t2.getBarve()[2]);
                    }
                }

            } else {
                for (SlikovnaTocka[] t : seznamTock) {
                    for (SlikovnaTocka t2 : t) {
                        out.write(t2.getBarve()[0]);
                    }
                }
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void shraniTeksotvnoDatoteko() {
        try {
            PrintWriter out = new PrintWriter(new File(this.ime));
            out.print(tipSlike);
            out.print(" ");
            out.print(sirina);
            out.print("x");
            out.println(visina);
            if (tipSlike.equals("fp2tC")) {
                for (SlikovnaTocka[] t : seznamTock) {
                    for (SlikovnaTocka t2 : t) {
                        out.print(t2.getBarve()[0]);
                    }
                }
                for (SlikovnaTocka[] t : seznamTock) {
                    for (SlikovnaTocka t2 : t) {
                        out.print(t2.getBarve()[1] + " ");
                    }
                }
                for (SlikovnaTocka[] t : seznamTock) {
                    for (SlikovnaTocka t2 : t) {
                        out.print(t2.getBarve()[2] + " ");
                    }
                }

            } else {
                for (SlikovnaTocka[] t : seznamTock) {
                    for (SlikovnaTocka t2 : t) {
                        out.print(t2.getBarve()[2] + " ");
                    }
                }
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void shraniDatoteko() {
        if (this.ime.endsWith("p2t")) {
            shraniTeksotvnoDatoteko();
        } else {
            shraniBitnoDatoteko();
        }
    }

    void zamenjajSlikovneTocke(int x, int y, int sirina, int visina) {

        if (x < 0) {
            sirina = sirina + x;
            x = 0;
        }
        if (y < 0) {
            visina = visina + y;
            y = 0;
        }

        SlikovnaTocka temp;
        for (int ix = x; ix <= x + sirina; ix++) {
            for (int iy = y; iy <= (y + visina) / 2; iy++) {
                int odmikY = y + visina - (iy - y) - 1;
                temp = seznamTock[ix][odmikY];
                seznamTock[ix][odmikY] = seznamTock[ix][iy];
                seznamTock[ix][iy] = temp;
            }
        }
    }

    long getVelikostDatoteke() {
        return this.velikost;
    }

    @Override
    public int compareTo(Fotka o) {
        if (Fotka.mediana(this.ime) < Fotka.mediana(o.ime)) return 1;
        if (Fotka.mediana(this.ime) > Fotka.mediana(o.ime)) return -1;
        return Long.compare(o.getVelikostDatoteke(), this.getVelikostDatoteke());
    }
}

class MyException extends Exception {
    MyException(String errorMessage) {
        super(errorMessage);
    }
}


