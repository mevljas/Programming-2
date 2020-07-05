public class DN02 {

    public static void main(String[] args) {
        int max_word_length = 0;
        for (int i = 2; i < args.length; i++) {
            int word_lenght = args[i].length();
            if( word_lenght > max_word_length) {
                max_word_length = word_lenght;
            }
        }
        izpisZvezdic(max_word_length);
        for (int i = 0; i < args.length; i++) {
            String current_word = args[i];
            System.out.print("*");
            int left_spacing = (max_word_length - current_word.length()) / 2 + 1;
            for (int j = 1; j <= left_spacing; j++) {
                System.out.print(" ");
            }
            int right_spacing = (max_word_length - current_word.length() - left_spacing) + 2;
            System.out.print(current_word);
            for (int j = 1; j <= right_spacing; j++) {
                System.out.print(" ");
            }
            System.out.println("*");
                   
        }
        izpisZvezdic(max_word_length);
    }
    static void izpisZvezdic( int n) {
        for( int i = 1; i <= n + 4; i++){
            System.out.print("*");
        }
        System.out.println("");
    }
    
}
