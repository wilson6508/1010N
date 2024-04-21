package demo4;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ListDemo1 {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("AAA");
        list.add("BBB");
        list.add("CCC");

        // list.add(1, "QQQ");

        // String result = list.get(0);

        // 返回刪除的元素
        // String remove = list.remove(0);

        // 返回修改前的元素
        // String result = list.set(0, "QQQ");

        ListIterator<String> listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            String str = listIterator.next();
            if ("BBB".equals(str)) {
                listIterator.add("QQQ");
            }
        }
        System.out.println(list);
    }

}
