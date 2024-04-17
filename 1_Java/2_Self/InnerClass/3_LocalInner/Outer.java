package inner;

public class Outer {

    int num = 6;

    public void outerFunc() {
        System.out.println("outerFunc");

        class LocalInner {
            String local = "local";
            public void print() {
                System.out.println(local);
            }
        }

        LocalInner localInner = new LocalInner();
        localInner.print();
    }

    public static class Inner {

        int innerNum = 8;

        public void innerFunc() {
            System.out.println("innerFunc");
        }

    }

}
