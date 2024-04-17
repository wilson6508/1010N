package reflection;

public class Person {

    private final String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void publicMethodWithParam(String str) {
        System.out.println(str);
    }

    public void publicMethod() {
        System.out.println("publicMethod");
    }

    public static void publicStaticMethod() {
        System.out.println("publicStaticMethod");
    }

    private void privateMethod() {
        System.out.println("privateMethod");
    }

    private static void privateStaticMethod() {
        System.out.println("privateStaticMethod");
    }

}
