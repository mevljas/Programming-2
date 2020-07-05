import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class DN11 {

    public static void main(String[] args) {
        TreeMap<String, Integer> map = new TreeMap<>();

        try {

            Scanner in = new Scanner(new File(args[0]));

            while (in.hasNext()) {
                String beseda = in.next().replace(".", "")
                        .replace(",", "").replace("(", "")
                        .replace(")", "").replace(";", "")
                        .replace("-", "");
                if (map.containsKey(beseda)) {
                    map.put(beseda, map.get(beseda) + 1);
                } else {
                    map.put(beseda, 1);
                }
            }
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Integer> sortedMap;
        if (Integer.parseInt(args[1]) == 1) {
            sortedMap = map.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByKey())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        } else {
            sortedMap = map.entrySet()
                    .stream()
                    .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        }

        for (HashMap.Entry<String, Integer> entry : sortedMap.entrySet()) {
            System.out.printf("%-6d%s\n", entry.getValue(), entry.getKey());
        }
    }
}
