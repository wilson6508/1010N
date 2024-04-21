package jdk8_default;

public interface Inter {

    public abstract void method();

    public default void show() {
        System.out.println("interface default method");
    }

}
