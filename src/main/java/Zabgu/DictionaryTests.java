package Zabgu;

public class DictionaryTests {
    public static void runAllTests() {
        System.out.println("\nЗапуск модульных тестов для словаря...");
        testPutAndGet();
        testContainsKey();
        testRemove();
        testSize();
        testClear();
//        testIterator();
//        testEdgeCases();
        System.out.println("Все тесты словаря пройдены успешно!");
    }

    private static void testPutAndGet() {
        System.out.println("\nТест 1: Добавление и получение элементов");
        Dictionary<String, Integer> dict = new Dictionary<>();
        dict.put("apple", 10);
        dict.put("banana", 20);
        dict.put("orange", 30);

        assertEquals(10, dict.get("apple"), "Неверное значение для ключа 'apple'");
        assertEquals(20, dict.get("banana"), "Неверное значение для ключа 'banana'");
        assertEquals(30, dict.get("orange"), "Неверное значение для ключа 'orange'");
        assertNull(dict.get("grape"), "Несуществующий ключ должен возвращать null");

        System.out.println("Тест 1 пройден успешно");
    }

    private static void testContainsKey() {
        System.out.println("\nТест 2: Проверка наличия ключей");
        Dictionary<Integer, String> dict = new Dictionary<>();
        dict.put(1, "one");
        dict.put(2, "two");

        assertTrue(dict.containsKey(1), "Словарь должен содержать ключ 1");
        assertTrue(dict.containsKey(2), "Словарь должен содержать ключ 2");
        assertFalse(dict.containsKey(3), "Словарь не должен содержать ключ 3");

        System.out.println("Тест 2 пройден успешно");
    }

    private static void testRemove() {
        System.out.println("\nТест 3: Удаление элементов");
        Dictionary<String, Double> dict = new Dictionary<>();
        dict.put("pi", 3.14);
        dict.put("e", 2.71);
        dict.put("phi", 1.61);

        assertEquals(3.14, dict.remove("pi"), "Неверное удаленное значение для 'pi'");
        assertNull(dict.get("pi"), "Ключ 'pi' должен быть удален");
        assertEquals(2, dict.size(), "Неверный размер словаря после удаления");

        System.out.println("Тест 3 пройден успешно");
    }

    private static void testSize() {
        System.out.println("\nТест 4: Размер словаря");
        Dictionary<Character, Boolean> dict = new Dictionary<>();
        assertEquals(0, dict.size(), "Размер пустого словаря должен быть 0");

        dict.put('a', true);
        dict.put('b', false);
        assertEquals(2, dict.size(), "Неверный размер словаря после добавления");

        dict.remove('a');
        assertEquals(1, dict.size(), "Неверный размер словаря после удаления");

        System.out.println("Тест 4 пройден успешно");
    }

    private static void testClear() {
        System.out.println("\nТест 5: Очистка словаря");
        Dictionary<String, Integer> dict = new Dictionary<>();
        dict.put("one", 1);
        dict.put("two", 2);

        dict.clear();
        assertEquals(0, dict.size(), "Размер должен быть 0 после очистки");
        assertFalse(dict.containsKey("one"), "Словарь должен быть пустым");
        assertFalse(dict.containsKey("two"), "Словарь должен быть пустым");

        System.out.println("Тест 5 пройден успешно");
    }

//    private static void testIterator() {
//        System.out.println("\nТест 6: Итератор словаря");
//        Dictionary<Integer, String> dict = new Dictionary<>();
//        dict.put(1, "one");
//        dict.put(2, "two");
//        dict.put(3, "three");
//
//        int count = 0;
//        for (Dictionary.Entry<Integer, String> entry : dict) {
//            count++;
//            assertNotNull(entry.getKey(), "Ключ не должен быть null");
//            assertNotNull(entry.getValue(), "Значение не должно быть null");
//        }
//        assertEquals(3, count, "Итератор должен пройти по всем элементам");
//
//        System.out.println("Тест 6 пройден успешно");
//    }

//    private static void testEdgeCases() {
//        System.out.println("\nТест 7: Граничные случаи");
//        Dictionary<String, String> dict = new Dictionary<>();
//
//        // Проверка с null значениями
//        dict.put("key1", null);
//        assertTrue(dict.containsKey("key1"), "Словарь должен содержать ключ с null значением");
//        assertNull(dict.get("key1"), "Должно возвращаться null значение");
//
//        // Проверка перезаписи значения
//        dict.put("key1", "new value");
//        assertEquals("new value", dict.get("key1"), "Значение должно быть перезаписано");
//
//        // Удаление несуществующего ключа
//        assertNull(dict.remove("nonexistent"), "Удаление несуществующего ключа должно возвращать null");
//
//        System.out.println("Тест 7 пройден успешно");
//    }

    // Вспомогательные методы для утверждений
    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Тест не пройден: " + message);
        }
    }

    private static void assertFalse(boolean condition, String message) {
        assertTrue(!condition, message);
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError(String.format(
                    "Тест не пройден: %s. Ожидалось: %s, получено: %s",
                    message, expected, actual));
        }
    }

    private static void assertNull(Object object, String message) {
        if (object != null) {
            throw new AssertionError("Тест не пройден: " + message);
        }
    }

    private static void assertNotNull(Object object, String message) {
        if (object == null) {
            throw new AssertionError("Тест не пройден: " + message);
        }
    }
}