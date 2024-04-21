package jdk8_default;

public class InterImpl implements Inter {

    @Override
    public void method() {
        System.out.println("實現類重寫的抽象方法");
    }

//    @Override
//    public void show() {
//        Inter.super.show();
//    }

}
