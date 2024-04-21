package demo3;

public class Student implements Comparable<Student> {
    private String name;
    private int age;

    public Student() {
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public int compareTo(Student o) {
        // 依照學生年齡升序 年齡相同 名字升序
        // this: 當前要添加的元素
        // o: 表示已經在紅黑樹中存在的元素

        // 返回
        // 負數: 表示當前要添加的元素是小的 存左邊
        // 正數: 表示當前要添加的元素是大的 存又邊
        // 0:    表示當前要添加的元素已存在 捨棄

        int i = this.getAge() - o.getAge();
        i = i == 0 ? this.getName().compareTo(o.getName()) : i;
        return i;
    }

}
