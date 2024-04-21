// 每一個 Thread 都有它自己的 ThreadLocalMap
// ThreadLocalMap 由 ThreadLocal類 來存取
// ThreadLocal類 set:    1.獲取當前Thread 2.用當前Thread取ThreadLocalMap 3.放key value(key是ThreadLocal的弱引用)
// ThreadLocal類 get:    1.獲取當前Thread 2.用當前Thread取ThreadLocalMap 3.取值
// ThreadLocal類 remove: 1.獲取當前Thread 2.用當前Thread取ThreadLocalMap 3.刪鍵值對