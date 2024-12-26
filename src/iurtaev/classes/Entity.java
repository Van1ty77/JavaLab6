package iurtaev.classes;

import iurtaev.annotations.ToString;

import java.lang.reflect.Field;
import java.util.TreeMap;

public abstract class Entity {
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        Class<?> ourClass = this.getClass();

        // Проверяем, есть ли аннотация @ToString(NO) на уровне класса
        ToString classAnnotation = ourClass.getAnnotation(ToString.class);
        boolean excludeAll = classAnnotation != null && classAnnotation.value() == ToString.ValueOption.NO;

        result.append(ourClass.getSimpleName()).append("(");

        // Используем TreeMap для сортировки полей по имени
        TreeMap<String, Object> fieldValues = new TreeMap<>();

        // Итерация по всем классам, включая суперклассы
        for (Class<?> currentClass = ourClass; currentClass != null; currentClass = currentClass.getSuperclass()) {
            Field[] fields = currentClass.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);

                ToString fieldAnnotation = field.getAnnotation(ToString.class);

                // Пропускаем поле, если:
                // 1. Класс помечен как @ToString(NO), но поле не помечено явно @ToString(YES)
                // 2. Поле помечено как @ToString(NO)
                if ((excludeAll && (fieldAnnotation == null || fieldAnnotation.value() != ToString.ValueOption.YES))
                        || (fieldAnnotation != null && fieldAnnotation.value() == ToString.ValueOption.NO)) {
                    continue;
                }

                try {
                    fieldValues.put(field.getName(), field.get(this));
                } catch (IllegalAccessException e) {
                    fieldValues.put(field.getName(), "<inaccessible>");
                }
            }
        }

        // Формируем строковое представление из отсортированных значений
        boolean firstField = true;
        for (var entry : fieldValues.entrySet()) {
            if (!firstField) {
                result.append(", ");
            }
            firstField = false;
            result.append(entry.getKey()).append("=").append(entry.getValue());
        }

        result.append(")");
        return result.toString();
    }
}
