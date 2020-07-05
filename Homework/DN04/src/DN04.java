public class DN04 {
    public static void main(String[] args) {
        String[] prva   = {"Miha", "Micka", "Tone", "Lojze", "Mojca", "Pepca", "Franci", "Francka"};
        String[] druga  = {"Vozi", "Seka", "Potrebuje", "Gleda", "Barva", "Voha", "Lomi", "Popravlja"};
        String[] tretja = {"Kolo", "Avto", "Likalnik", "Sonce", "Vrtnico", "Drevo", "Lopato", "Sekiro"};
        String niz = args[0];
        Boolean je_ustvaril = false;
        for ( String ime: prva) {
            if (niz.startsWith(ime) ) {
                je_ustvaril = true;
                niz = niz.substring(ime.length(), niz.length());
                break;
            }
        }
        if (je_ustvaril) {
            je_ustvaril = false;
            for ( String ime: druga) {
                if (niz.startsWith(ime))  {
                    je_ustvaril = true;
                    niz = niz.substring(ime.length(), niz.length());
                    break;
                }
            }
        }
        if (je_ustvaril) {
            je_ustvaril = false;
            for ( String ime: tretja) {
                if (niz.startsWith(ime))  {
                    je_ustvaril = true;
                    niz = niz.substring(ime.length(), niz.length());
                    break;
                }
            }
        }
        System.out.println(je_ustvaril);
    }
}
