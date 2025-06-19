package Zabgu;

public class DictionaryTests {
    public static void runAllTests() {
        System.out.println("\nЗапуск модульных тестов для словаря...");
        testPutAndGet();
        testContainsKey();
        testRemove();
        testSize();
        testClear();
        System.out.println("Все тесты словаря пройдены успешно!");
    }

    /**
     * Тестирует базовые операции добавления и получения элементов.
     * Проверяет:
     * 1. Корректность добавления элементов
     * 2. Корректность получения значений
     * 3. Поведение при запросе несуществующего ключа
     */
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

    /**
     * Тестирует работу метода containsKey().
     * Проверяет:
     * 1. Наличие добавленных ключей
     * 2. Отсутствие недобавленных ключей
     */
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

    /**
     * Тестирует операцию удаления элементов.
     * Проверяет:
     * 1. Корректность возвращаемого значения
     * 2. Фактическое удаление элемента
     * 3. Изменение размера словаря
     */
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

    /**
     * Тестирует корректность работы с размером словаря.
     * Проверяет:
     * 1. Размер пустого словаря
     * 2. Размер после добавления элементов
     * 3. Размер после удаления элементов
     */
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

    /**
     * Тестирует операцию очистки словаря.
     * Проверяет:
     * 1. Обнуление размера
     * 2. Отсутствие ранее добавленных ключей
     */
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

    // Вспомогательные методы для утверждений

    /**
     * Проверяет, что условие истинно.
     * @param condition проверяемое условие
     * @param message сообщение об ошибке
     * @throws AssertionError если условие ложно
     */
    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Тест не пройден: " + message);
        }
    }

    /**
     * Проверяет, что условие ложно.
     * @param condition проверяемое условие
     * @param message сообщение об ошибке
     * @throws AssertionError если условие истинно
     */
    private static void assertFalse(boolean condition, String message) {
        assertTrue(!condition, message);
    }

    /**
     * Проверяет равенство ожидаемого и фактического значений.
     * @param expected ожидаемое значение
     * @param actual фактическое значение
     * @param message сообщение об ошибке
     * @throws AssertionError если значения не равны
     */
    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError(String.format(
                    "Тест не пройден: %s. Ожидалось: %s, получено: %s",
                    message, expected, actual));
        }
    }

    /**
     * Проверяет, что объект равен null.
     * @param object проверяемый объект
     * @param message сообщение об ошибке
     * @throws AssertionError если объект не null
     */
    private static void assertNull(Object object, String message) {
        if (object != null) {
            throw new AssertionError("Тест не пройден: " + message);
        }
    }

    /**
     * Проверяет, что объект не равен null.
     * @param object проверяемый объект
     * @param message сообщение об ошибке
     * @throws AssertionError если объект null
     */
    private static void assertNotNull(Object object, String message) {
        if (object == null) {
            throw new AssertionError("Тест не пройден: " + message);
        }
    }
}