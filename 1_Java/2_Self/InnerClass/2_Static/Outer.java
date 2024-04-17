package inner;

public class Outer {

    int num = 6;

    public void outerFunc() {
        System.out.println("outerFunc");
    }

    public static class Inner {

        int innerNum = 8;

        public void innerFunc() {
            System.out.println("innerFunc");
        }

    }

}
