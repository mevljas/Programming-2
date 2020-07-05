import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class DN13 {
    public static void main(String[] args) {
        JFrame okno = new JFrame("HTML prikazovalnik");
        okno.setSize(650, 520);
        okno.setLocation(500, 300);

        JPanel p = new JPanel();
        p.setLayout(null);

        JTextField urlVnos = new JTextField();
        urlVnos.setBounds(10, 10, 300, 20);
        p.add(urlVnos);

        JButton naloziGumb = new JButton("Naloži");
        naloziGumb.setBounds(330, 10, 100, 20);
        p.add(naloziGumb);

        JTextArea htmlIzpis = new JTextArea();

        JScrollPane scrollPane = new JScrollPane(htmlIzpis);
        scrollPane.setBounds(0, 40, 633, 440);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        p.add(scrollPane);


        naloziGumb.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                URL url = null;
                try {
                    url = new URL(urlVnos.getText());
                    URLConnection spoof = url.openConnection();

                    spoof.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0; H010818)");
                    BufferedReader in = new BufferedReader(new InputStreamReader(spoof.getInputStream()));
                    String strLine = "";

                    while ((strLine = in.readLine()) != null) {
                        htmlIzpis.append(strLine);
                        htmlIzpis.append("\n");
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });

        okno.add(p);
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        okno.setVisible(true);
    }
}
