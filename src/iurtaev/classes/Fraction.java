package iurtaev.classes;

import iurtaev.annotations.*;
import iurtaev.validators.FractionValidator;
import java.util.Scanner;

@Cache({"numerator", "denominator"})
@Default(Fraction.class)
@ToString
@Two(first = "Fraction", second = 2)
@Validate({FractionValidator.class})
public class Fraction implements Comparable<Fraction> {
    @ToString
    private int numerator;

    @ToString
    private int denominator;

    // Конструктор с проверкой знака знаменателя
    public Fraction(int numerator, int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Знаменатель не может быть равен нулю.");
        }
        if (denominator < 0) {
            numerator = -numerator;
            denominator = -denominator;
        }
        int gcd = gcd(Math.abs(numerator), Math.abs(denominator));
        this.numerator = numerator / gcd;
        this.denominator = denominator / gcd;
    }

    private static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    @Invoke // Аннотируем метод
    public String getFractionString() {
        return numerator + "/" + denominator;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Fraction other = (Fraction) obj;
        return numerator == other.numerator && denominator == other.denominator;
    }

    @Override
    public int hashCode() {
        return 31 * numerator + denominator;
    }

    @Override
    public int compareTo(Fraction other) {
        return Integer.compare(this.numerator * other.denominator, other.numerator * this.denominator);
    }

    public static void run() {
        // Создание Scanner для ввода данных с консоли
        Scanner scanner = new Scanner(System.in);

        // Ввод числителя и знаменателя для первой дроби
        System.out.println("Введите числитель первой дроби:");
        int numerator1 = scanner.nextInt();
        System.out.println("Введите знаменатель первой дроби:");
        int denominator1 = scanner.nextInt();

        // Ввод числителя и знаменателя для второй дроби
        System.out.println("Введите числитель второй дроби:");
        int numerator2 = scanner.nextInt();
        System.out.println("Введите знаменатель второй дроби:");
        int denominator2 = scanner.nextInt();

        // Создание дробей с введенными значениями
        Fraction fraction1 = new Fraction(numerator1, denominator1);
        Fraction fraction2 = new Fraction(numerator2, denominator2);

        // 1. Показать работу @ToString
        System.out.println("Строковое представление первой дроби с аннотацией @ToString: " + fraction1);
        System.out.println("Строковое представление второй дроби с аннотацией @ToString: " + fraction2);

        // 2. Показать работу @Invoke
        System.out.println("Строковое представление первой дроби (метод getFractionString): " + fraction1.getFractionString());

        // 3. Работа с @Cache (кэширование - для примера можем использовать вывод значений)
        Cache cacheAnnotation = Fraction.class.getAnnotation(Cache.class);
        if (cacheAnnotation != null) {
            System.out.println("Кэшируемые поля: " + String.join(", ", cacheAnnotation.value()));
        }

        // 4. Работа с @Default (по умолчанию используем класс Fraction)
        Default defaultAnnotation = Fraction.class.getAnnotation(Default.class);
        if (defaultAnnotation != null) {
            System.out.println("По умолчанию используется класс: " + defaultAnnotation.value().getSimpleName());
        }

        // 5. Работа с @Two
        Two twoAnnotation = Fraction.class.getAnnotation(Two.class);
        if (twoAnnotation != null) {
            System.out.println("Аннотация Two - first: " + twoAnnotation.first() + ", second: " + twoAnnotation.second());
        }

        // Закрываем scanner
        scanner.close();
    }
}

