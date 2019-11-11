package cn.edu.nju.cs;

class Dog {
    public static String category = "dog";

    public String name;
    public int age;
    public double happy;

    Dog(String name, int age, double happy) {
        this.name = name;
        this.age = age;
        this.happy = happy;
    }
}


public class Demo {

    public static void main(String[] args) {
        Dog dog = new Dog("Danny", 1, 100.0);
        System.out.println(dog.happy);

        int[] ai = {1, 2, 3, 4};
        double[] di = {100.0, -0.01};

        ai[1] = 100;
        di[0] = 3.14;
        System.out.println(ai[1]);
        System.out.println(di[0]);

        // Dog[] dogs = new Dog[] { new Dog("Danny", 1) };
        // Dog.category = dogs[0].name;
        // System.out.println(Dog.category);

    }

}
