package iurtaev.classes;
import iurtaev.annotations.ToString;

@ToString(ToString.ValueOption.NO) // Если помечен @ToString(NO), поля по умолчанию не включаются
public class A extends Entity {
    @ToString(ToString.ValueOption.YES)
    String s = "hello";

    @ToString(ToString.ValueOption.NO) // Поле исключено из строкового представления
    int x = 42;
}

