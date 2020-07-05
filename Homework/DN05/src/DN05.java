import java.io.File;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;

public class DN05 {
    public static void main(String[] args) {
        int[][] tabela;
        int[][] tabela2;
        int[] vsote;
        Locale.setDefault(Locale.GERMANY);
        if (args.length == 1) {
            analizirajTemperature(args[0]);
        } else if (args.length == 4) {
            tabela = preberiResitevSudoku(args[0]);
            tabela2 = preberiResitevSudoku(args[0]);
            if (preveriSudoku(tabela)) {
                System.out.println("Resitev je pravilna.");
            } else {
                System.out.println("Resitev NI pravilna.");
                tabela = popraviResitev(tabela);

            }
            vsote = vsotaPoDiagonali(tabela2);
            System.out.printf("Vsota na diagonalah je %d, %d in %d.\n", vsote[1], vsote[0], vsote[2]);
            zapisiResitev(args[0].replace(".txt", "-popravljeno.txt"), tabela);
            zamenjajStolpca(tabela2, Integer.parseInt(args[2]), Integer.parseInt(args[3]));
            zapisiResitev(args[1], tabela2);
        }
    }

    static void analizirajTemperature(String datoteka) {
        String podatki = "";
        try {
            Scanner in = new Scanner(new File(datoteka));
            while (in.hasNextDouble()) {
                podatki += in.nextDouble() + ":";
            }
            in.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        String[] temeprature = podatki.split(":");
        double vsota = 0.0;
        for (String x : temeprature) {
            vsota += Double.parseDouble(x);
        }
        double povprecje = vsota / temeprature.length;
        System.out.printf("Povprecje zapisanih temperatur je %5.2f.\n", povprecje);
        double[] najPet = {-Double.MAX_VALUE, -Double.MAX_VALUE, -Double.MAX_VALUE, -Double.MAX_VALUE, -Double.MAX_VALUE};
        double temp;
        int zadnjiIndex = 0;
        for (int i = 0; i < najPet.length; i++) {
            for (int j = 0; j < temeprature.length; j++) {
                temp = Double.parseDouble(temeprature[j]);
                if (i == 0 && (temp > najPet[i])) {
                    najPet[i] = temp;
                    zadnjiIndex = j;
                } else if ((temp > najPet[i])) {
                    najPet[i] = temp;
                    zadnjiIndex = j;
                }
            }
            temeprature[zadnjiIndex] = -Double.MAX_VALUE + "";
        }
        System.out.println("Najvisjih 5 temperatur:");
        for (double x : najPet) {
            System.out.printf("%-5.2f\n", x);
        }
    }

    static int[][] preberiResitevSudoku(String datoteka) {
        Scanner in;
        int[][] resitev = new int[25][25];
        try {
            in = new Scanner(new File(datoteka));
            for (int i = 0; i < 25; i++) {
                for (int j = 0; j < 25; j++) {
                    resitev[i][j] = in.nextInt();
                }
            }
            in.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return resitev;
    }

    static boolean preveriSudoku(int[][] tab) {
        int trenutniElement = 0;
        int st = 0;
        int vrsticaMin = 0;
        int stolpecMin = 0;
        int vrsticaMax = 0;
        int stolpecMax = 0;
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                trenutniElement = tab[i][j];
                if (trenutniElement > 25 || trenutniElement == 0) {
                    return false;
                }
                st = 0;
                for (int z = 0; z < 25; z++) {
                    if (tab[i][z] == trenutniElement) {
                        st++;
                    }
                }
                if (st > 1) {
                    return false;
                }
            }
        }
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                trenutniElement = tab[i][j];
                st = 0;
                for (int z = 0; z < 25; z++) {
                    if (tab[z][j] == trenutniElement) {
                        st++;
                    }
                }
                if (st > 1) {
                    return false;
                }
            }
        }
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                trenutniElement = tab[i][j];

                stolpecMin = (i / 5) * 5;
                stolpecMax = stolpecMin + 5;
                vrsticaMin = (j / 5) * 5;
                vrsticaMax = vrsticaMin + 5;
                st = 0;
                for (int i1 = stolpecMin; i1 < stolpecMax; i1++) {
                    for (int j1 = vrsticaMin; j1 < vrsticaMax; j1++) {
                        if (tab[i1][j1] == trenutniElement) {
                            st++;
                        }
                    }
                }
                if (st > 1) {
                    return false;
                }
            }
        }
        return true;
    }

    static int[][] popraviResitev(int[][] tab) {
        int trenutniElement = 0;
        int st = 0;
        int vrsticaMin = 0;
        int stolpecMin = 0;
        int vrsticaMax = 0;
        int stolpecMax = 0;
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                trenutniElement = tab[i][j];
                if (trenutniElement > 25) {
                    trenutniElement = 0;
                    tab[i][j] = 0;
                }
                if (trenutniElement != 0) {
                    st = 0;
                    for (int z = 0; z < 25; z++) {
                        if (tab[i][z] == trenutniElement) {
                            st++;
                        }
                    }
                    if (st > 1) {
                        for (int z = 0; z < 25; z++) {
                            if (tab[i][z] == trenutniElement) {
                                tab[i][z] = 0;
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                trenutniElement = tab[i][j];
                if (trenutniElement != 0) {
                    st = 0;
                    for (int z = 0; z < 25; z++) {
                        if (tab[z][j] == trenutniElement) {
                            st++;
                        }
                    }
                    if (st > 1) {
                        for (int z = 0; z < 25; z++) {
                            if (tab[z][j] == trenutniElement) {
                                tab[z][j] = 0;
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                trenutniElement = tab[i][j];
                if (trenutniElement != 0) {
                    stolpecMin = (i / 5) * 5;
                    stolpecMax = stolpecMin + 5;
                    vrsticaMin = (j / 5) * 5;
                    vrsticaMax = vrsticaMin + 5;
                    st = 0;
                    for (int i1 = stolpecMin; i1 < stolpecMax; i1++) {
                        for (int j1 = vrsticaMin; j1 < vrsticaMax; j1++) {
                            if (tab[i1][j1] == trenutniElement) {
                                st++;
                            }
                        }
                    }
                    if (st > 1) {
                        for (int i1 = stolpecMin; i1 < stolpecMax; i1++) {
                            for (int j1 = vrsticaMin; j1 < vrsticaMax; j1++) {
                                if (tab[i1][j1] == trenutniElement) {
                                    tab[i1][j1] = 0;
                                }
                            }
                        }
                    }
                }
            }
        }
        return tab;
    }

    static int[] vsotaPoDiagonali(int[][] tab) {
        int[] vsote = new int[3];
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                if (i == j) {
                    vsote[1] += tab[i][j];
                }
                if ((i + j) == (tab.length - 1)) {
                    vsote[0] += tab[i][j];
                }
            }
        }
        for (int i = 0; i < tab.length - 4; i = i + 5) {
            for (int j = 0; j < tab[i].length - 4; j = j + 5) {
                for (int i1 = 0; i1 < 5; i1++) {
                    for (int j1 = 0; j1 < 5; j1++) {
                        if (i1 == j1) {
                            vsote[2] += tab[i + i1][j + j1];
                        }
                        if ((i1 + j1) == 4) {
                            vsote[2] += tab[i + i1][j + j1];
                        }
                    }
                }
            }
        }

        return vsote;
    }

    static void zamenjajStolpca(int[][] tab, int j, int k) {
        int temp;
        j--;
        k--;
        for (int i = 0; i < tab.length; i++) {
            temp = tab[i][j];
            tab[i][j] = tab[i][k];
            tab[i][k] = temp;
        }
    }

    static void zapisiResitev(String datoteka, int[][] tab) {
        try {
            PrintWriter out = new PrintWriter(new File(datoteka));
            for (int i = 0; i < tab.length; i++) {
                for (int j = 0; j < tab[i].length; j++) {
                    out.printf("%3d", tab[i][j]);
                }
                out.println();
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
