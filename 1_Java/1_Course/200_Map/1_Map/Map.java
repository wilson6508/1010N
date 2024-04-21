package demo1;

import java.util.HashMap;
import java.util.Map;

public class MapDemo1 {

    public static void main(String[] args) {
        // V put(K key, V value);
        Map<String, String> m = new HashMap<>();
        String value1 = m.put("郭靜", "黃榮");   // null
        m.put("韋小寶", "木劍平");
        String value2 = m.put("韋小寶", "雙而"); // 木劍平

        // V remove(Object key);
        String value3 = m.remove("郭靜");   // 黃榮

        // boolean containsKey(Object key);
        // boolean containsValue(Object value);

        // int size();
        // boolean isEmpty();
        // void clear();
    }

}
