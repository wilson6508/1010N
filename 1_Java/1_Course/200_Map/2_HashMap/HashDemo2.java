package demo2;

import java.util.*;

public class HashDemo2 {

    public static void main(String[] args) {
        // 投票數據
        String[] arr = {"A", "B", "C", "D"};
        ArrayList<String> arrayList = new ArrayList<>();
        Random random = new Random();
        for (int i = 1; i <= 80; i++) {
            int randIdx = random.nextInt(arr.length);
            arrayList.add(arr[randIdx]);
        }
        // 用HashMap計數
        HashMap<String, Integer> hashMap = new HashMap<>();
        for (String name : arrayList) {
            hashMap.putIfAbsent(name, 0);
            hashMap.computeIfPresent(name, (key, val) -> val + 1);
        }
        System.out.println(hashMap);
        // 找最高得票數
        List<Integer> collect = hashMap.values().stream().toList();
        Integer max = Collections.max(collect);
        // 找最高得票數的地點
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            if (entry.getValue().equals(max)) {
                System.out.println(entry);
            }
        }
    }

}
