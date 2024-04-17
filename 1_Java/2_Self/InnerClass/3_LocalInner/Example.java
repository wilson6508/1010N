package inner;

public class Example {

    public static void main(String[] args) {

        Outer outer = new Outer();
        outer.outerFunc();

        Outer.Inner inner = new Outer.Inner();
        inner.innerFunc();

    }

}
