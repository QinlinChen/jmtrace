package cn.edu.nju.cs;

class Dog {
    public static String category = "dog";

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
        // System.out.println(dog.name + ": " + dog.age);
        // dog.age = 2;
        // System.out.println(dog.name + ": " + dog.age);

        Dog.category = dog.name;
        System.out.println(Dog.category);

        int[] ai = new int[4];
        for (int i = 0 ; i < ai.length; ++i) {
            ai[i] = i;
        }
        for (int i : ai) {
            System.out.println(i);
        }

    }

}