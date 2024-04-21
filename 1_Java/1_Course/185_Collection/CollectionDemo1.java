import java.util.ArrayList;
import java.util.Collection;

Collection<String> coll = new ArrayList<>();

// 往List添加元素 返回值必為true
// 往Set添加元素 如果元素已存在 返回值為false
boolean boo01 = coll.add("aaa");

// Collection裡面定義的是共性方法 所以不能通過索引刪除 只能通過元素對象刪除
// 刪除成功返回true 刪除失敗(要刪的元素不存在)返回false
boolean boo02 = coll.remove("aaa");

// contains底層是依賴equals方法
// 如果集合存儲自定義對象 要重寫equals方法 不重寫則使用Object類中equals(比較地址)
boolean boo03 = coll.contains("aaa");

// coll.clear();
// boolean empty = coll.isEmpty();
// int size = coll.size();