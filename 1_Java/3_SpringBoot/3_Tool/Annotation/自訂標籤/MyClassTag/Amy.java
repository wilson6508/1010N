package com.service;

import com.annotation.MyClassTag;

@MyClassTag(name = "Amy", age = 3)
public class Amy {

    public static void main(String[] args) {
        Amy amy = new Amy();
        amy.getNameAge(John.class);
        // Class<?> currentClass = this.getClass();
        amy.getNameAge();
        // myObject.getClass();
        amy.getNameAge(amy.getClass());
    }

    public void getNameAge() {
        if (this.getClass().isAnnotationPresent(MyClassTag.class)) {
            MyClassTag classTag = this.getClass().getAnnotation(MyClassTag.class);
            String name = classTag.name();
            int age = classTag.age();
            System.out.println("Name: " + name);
            System.out.println("Age: " + age);
        }
    }

    public void getNameAge(Class<?> clazz) {
        if (clazz.isAnnotationPresent(MyClassTag.class)) {
            MyClassTag classTag = clazz.getAnnotation(MyClassTag.class);
            String name = classTag.name();
            int age = classTag.age();
            System.out.println("Name: " + name);
            System.out.println("Age: " + age);
        }
    }

}
