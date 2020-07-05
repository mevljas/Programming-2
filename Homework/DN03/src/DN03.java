public class DN03 {
    public static void main(String[] args) {
        int min_index = 1;
        if (args[0].equals("min")) {
            System.out.print("Minimum stevil ");
            for (int i = 1; i < args.length; i++) {
                System.out.print(args[i]+" ");
                if ( Double.parseDouble(args[i]) < Double.parseDouble(args[min_index]) )
                    min_index = i;
            }
            System.out.printf("je %.2f", Double.parseDouble(args[min_index]));
        }
        else {
            char ukaz = args[1].charAt(0);
            double prvi = Double.parseDouble(args[0]);
            double drugi = Double.parseDouble(args[2]);
            double rezultat = 0.0;
            switch (ukaz) {
                case '+':
                    rezultat = prvi + drugi;
                    break;
                case '-':
                    rezultat = prvi - drugi;
                    break;
                case '#':
                    rezultat = prvi * drugi;
                    args[1] = "*";
                    break;
                case '/':
                    rezultat = prvi / drugi;
                    break;
                case '^':
                    rezultat = Math.pow(prvi, drugi);
                    break;

            }
            System.out.printf(args[0]+" "+args[1]+" "+args[2]+" = %.2f", rezultat);
        }

    }
}
