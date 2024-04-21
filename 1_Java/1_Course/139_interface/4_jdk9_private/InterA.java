package jdk9_private;

public interface InterA {

    public default void show1() {
        System.out.println("enter show1");
        show3();
    }

    // 給default方法使用
    private void show3() {
        System.out.println("Interface Private Method");
    }

    public static void show2() {
        System.out.println("enter show2");
        show4();
    }

    // 給static方法使用
    private static void show4() {
        System.out.println("Interface Private Static Method");
    }

}
