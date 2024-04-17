@FunctionalInterface // the compiler will enforce one abstract method (s-a-m)
public interface Printable {
    String print(String suffix);
}

public static void main(String[] args) {
    Printable printable = s -> "Meow" + s;
}
==================================================================================
public interface Printable {
    void print(String suffix);
}

public static void main(String[] args) {
    Printable printable = (s) -> System.out.println("Meow" + s);
    printThing(printable); // Meow!
}

static void printThing(Printable thing) {
    thing.print("!");
}
==================================================================================
public interface Printable {
    void print();
}

public class Cat implements Printable{
    public void print() {
        System.out.println("Meow");
    }
}

public static void main(String[] args) {
    printThing(new Cat());

    printThing(() -> {
        System.out.println(1);
        System.out.println(2);
    });
}

static void printThing(Printable thing) {
    thing.print();
}