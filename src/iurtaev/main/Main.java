package iurtaev.main;
import iurtaev.classes.*;


public class Main {
    public static void main(String[] args) {
        Fraction.run();

        A a = new A();
        System.out.println("Класс " + a);

        B b = new B();
        System.out.println("Класс " + b);
    }
}