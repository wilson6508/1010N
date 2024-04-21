package demo3;

import java.util.Comparator;
import java.util.TreeMap;

public class TreeDemo1 {

    public static void main(String[] args) {
        // key實現Comparable介面 (Integer Double String(ASCII) 升序)
        TreeMap<Integer, String> tm = new TreeMap<>();

        // 創建TreeMap時 傳遞Comparator對象
        TreeMap<Integer, String> treeMap = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                // o1: 當前要添加的元素
                // o2: 已經在紅黑樹存在的元素
                return o2 - o1;
            }
        });
    }

}
