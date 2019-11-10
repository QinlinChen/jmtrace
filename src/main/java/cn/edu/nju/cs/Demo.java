package cn.edu.nju.cs;

class Dog {
    public String name;
    public int age;

    Dog(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

public class Demo {
    
    public static void main(String[] args) {
        Dog dog = new Dog("Danny", 1);
        System.out.println(dog.name + ": " + dog.age);
    }

}