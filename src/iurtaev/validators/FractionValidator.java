package iurtaev.validators;

import iurtaev.classes.Fraction;

public class FractionValidator {
    public static void validate(Fraction fraction) {
        System.out.println("Проверка знаменателя: " + fraction.getDenominator());
        if (fraction.getDenominator() == 0) {
            throw new IllegalArgumentException("Знаменатель не может быть равен нулю.");
        }
    }
}
