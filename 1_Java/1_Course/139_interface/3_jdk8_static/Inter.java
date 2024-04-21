package jdk8_static;

public interface Inter {

    public abstract void method();

    public static void show() {
        System.out.println("interface default method");
    }

}
