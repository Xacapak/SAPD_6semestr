package Zabgu;

public class DictionaryTests {
    public static void runAllTests() {
        System.out.println("\nЗапуск тестов словаря...");
        testPutAndGet();
        testContainsKey();
        testRemove();
        testSize();
        testClear();
        testIterator();
        System.out.println("Все тесты словаря пройдены успешно!");
    }

    public static void testPutAndGet() {
        Dictionary<String, Integer> dict = new Dictionary<>();
        dict.put("apple", 10);
        dict.put("banana", 20);

        assert dict.get("apple") == 10 : "Неверное значение для 'apple'";
        assert dict.get("banana") == 20 : "Неверное значение для 'banana'";
        assert dict.get("orange") == null : "Несуществующий ключ должен возвращать null";

        dict.put("apple", 30); // Перезапись
        assert dict.get("apple") == 30 : "Значение должно обновиться";

        System.out.println("Тест PutAndGet пройден");
    }

    public static void testContainsKey() {
        Dictionary<Integer, String> dict = new Dictionary<>();
        dict.put(1, "one");

        assert dict.containsKey(1) : "Должен содержать ключ 1";
        assert !dict.containsKey(2) : "Не должен содержать ключ 2";

        System.out.println("Тест ContainsKey пройден");
    }

    public static void testRemove() {
        Dictionary<String, Double> dict = new Dictionary<>();
        dict.put("pi", 3.14);

        assert dict.remove("pi") == 3.14 : "Должен вернуть удаленное значение";
        assert dict.get("pi") == null : "Ключ должен быть удален";
        assert dict.remove("pi") == null : "Повторное удаление должно возвращать null";

        System.out.println("Тест Remove пройден");
    }

    public static void testSize() {
        Dictionary<Character, Boolean> dict = new Dictionary<>();
        assert dict.size() == 0 : "Размер пустого словаря должен быть 0";

        dict.put('a', true);
        dict.put('b', false);
        assert dict.size() == 2 : "Размер после добавления должен быть 2";

        dict.remove('a');
        assert dict.size() == 1 : "Размер после удаления должен быть 1";

        dict.clear();
        assert dict.size() == 0 : "Размер после очистки должен быть 0";

        System.out.println("Тест Size пройден");
    }

    public static void testClear() {
        Dictionary<String, Integer> dict = new Dictionary<>();
        dict.put("one", 1);
        dict.put("two", 2);

        dict.clear();
        assert dict.size() == 0 : "Размер должен быть 0";
        assert dict.get("one") == null : "Данные должны быть очищены";

        System.out.println("Тест Clear пройден");
    }

    public static void testIterator() {
        Dictionary<Integer, String> dict = new Dictionary<>();
        dict.put(3, "three");
        dict.put(1, "one");
        dict.put(2, "two");

        StringBuilder sb = new StringBuilder();
        for (Dictionary.Entry<Integer, String> entry : dict) {
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append(" ");
        }

        String result = sb.toString().trim();
        assert result.contains("1:one") : "Должен содержать 1:one";
        assert result.contains("2:two") : "Должен содержать 2:two";
        assert result.contains("3:three") : "Должен содержать 3:three";

        System.out.println("Тест Iterator пройден");
    }
}