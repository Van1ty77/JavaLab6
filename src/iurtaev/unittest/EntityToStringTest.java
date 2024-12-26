package iurtaev.unittest;

import org.junit.jupiter.api.Test;
import iurtaev.classes.*;
import iurtaev.annotations.ToString;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityToStringTest {

    /**
     * Тестирует метод toString() для объекта класса A.
     * Поля должны отображаться в строке в зависимости от аннотации @ToString:
     * - Поле `y` включено (@ToString(YES)).
     * - Поле `s` исключено, так как класс помечен как @ToString(NO).
     * - Поле `x` исключено (@ToString(NO)).
     */
    @Test
    void testToStringWithAnnotations() {
        // Создаем объект тестируемого класса
        A a = new A();

        // Ожидаемое строковое представление
        String expected = "A(s=hello)"; // Учитываем только поле s, помеченное @ToString(YES)

        // Сравниваем реальный результат с ожидаемым
        assertEquals(expected, a.toString(), "Строковое представление объекта должно соответствовать аннотациям @ToString.");
    }

    /**
     * Проверяет, что метод toString() работает корректно для наследования.
     */
    @Test
    void testToStringWithInheritance() {
        // Класс-наследник
        class B extends A {
            String z = "newField";
        }

        // Создаем объект класса-наследника
        B b = new B();

        // Ожидаемое строковое представление (s включено из A, z включено по умолчанию)
        String expected = "B(s=hello, z=newField)";

        // Сравниваем реальный результат с ожидаемым
        assertEquals(expected, b.toString(), "Строковое представление должно учитывать поля суперклассов в соответствии с аннотациями.");
    }

    /**
     * Проверяет поведение, если класс не имеет аннотации @ToString(NO).
     */
    @Test
    void testToStringWithoutClassAnnotation() {
        class C extends Entity {
            String name = "testName";

            @ToString(ToString.ValueOption.NO)
            int age = 30;
        }

        // Создаем объект тестируемого класса
        C c = new C();

        // Ожидаемое строковое представление (name включено, age исключено)
        String expected = "C(name=testName)";

        // Сравниваем реальный результат с ожидаемым
        assertEquals(expected, c.toString(), "Строковое представление должно включать только поля без @ToString(NO).");
    }
}