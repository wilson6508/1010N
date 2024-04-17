package reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Example {

    public static void main(String[] args) throws Exception {
        
        Person person = new Person("Tom", 18);
        Field[] fields = person.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals("name")) {
                field.setAccessible(true);
                field.set(person, "Amy");
            }
        }

        Method[] methods = person.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals("publicMethodWithParam")) {
                method.invoke(person, "ABC");
            }
            if (method.getName().equals("publicMethod")) {
                method.invoke(person);
            }
            if (method.getName().equals("publicStaticMethod")) {
                method.invoke(null);
            }
            if (method.getName().equals("privateMethod")) {
                method.setAccessible(true);
                method.invoke(person);
            }
            if (method.getName().equals("privateStaticMethod")) {
                method.setAccessible(true);
                method.invoke(person);
            }
        }
        
    }

}
