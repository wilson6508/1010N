package jdk8_static;

public class InterImpl implements Inter {

    @Override
    public void method() {
        System.out.println("實現類重寫的抽象方法");
    }

    // 不能Override Interface的static方法

}
