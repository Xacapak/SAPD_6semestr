package Zabgu;

class HashTableTests {
    public static void runAllTests() {
        System.out.println("\nЗапуск модульных тестов...");
        testPutAndGet();
        testCollisions();
        testRemove();
        testClear();
        testSize();
        System.out.println("Все тесты пройдены успешно!");
    }

    /**
     * Тестирует базовые операции добавления и получения элементов.
     * Проверяет:
     * 1. Корректность добавления элементов
     * 2. Корректность получения существующих элементов
     * 3. Возврат null для несуществующих ключей
     */
    private static void testPutAndGet() {
        HashTable<Integer, String> table = new HashTable<>(10, key -> key % 10);
        table.put(1, "One");
        table.put(2, "Two");

        assertEqual(table.get(1), "One", "testPutAndGet 1");
        assertEqual(table.get(2), "Two", "testPutAndGet 2");
        assertEqual(table.get(3), null, "testPutAndGet 3");
    }

    /**
     * Тестирует обработку коллизий хеш-функции.
     * Проверяет корректность работы при:
     * 1. Наличии коллизий (разные ключи дают одинаковый хеш)
     * 2. Хранении нескольких элементов в одной корзине
     */
    private static void testCollisions() {
        HashTable<Integer, String> table = new HashTable<>(10, key -> key % 10);
        table.put(1, "Apple");
        table.put(11, "Orange");
        table.put(21, "Mango");

        assertEqual(table.get(1), "Apple", "testCollisions 1");
        assertEqual(table.get(11), "Orange", "testCollisions 2");
        assertEqual(table.get(21), "Mango", "testCollisions 3");
    }

    /**
     * Тестирует операцию удаления элементов.
     * Проверяет:
     * 1. Корректность возвращаемого значения при удалении
     * 2. Фактическое удаление элемента
     * 3. Изменение размера таблицы после удаления
     */
    private static void testRemove() {
        HashTable<Integer, String> table = new HashTable<>(10, key -> key % 10);
        table.put(1, "One");
        table.put(2, "Two");

        assertEqual(table.remove(1), "One", "testRemove 1");
        assertEqual(table.get(1), null, "testRemove 2");
        assertEqual(table.size(), 1, "testRemove 3");
    }

    /**
     * Тестирует операцию очистки таблицы.
     * Проверяет:
     * 1. Обнуление размера таблицы
     * 2. Удаление всех элементов
     */
    private static void testClear() {
        HashTable<Integer, String> table = new HashTable<>(10, key -> key % 10);
        table.put(1, "One");
        table.put(2, "Two");
        table.clear();

        assertEqual(table.size(), 0, "testClear 1");
        assertEqual(table.get(1), null, "testClear 2");
    }

    /**
     * Тестирует корректность работы метода size().
     * Проверяет:
     * 1. Начальный размер пустой таблицы
     * 2. Изменение размера после добавления элементов
     * 3. Изменение размера после удаления элементов
     */
    private static void testSize() {
        HashTable<Integer, String> table = new HashTable<>(10, key -> key % 10);
        assertEqual(table.size(), 0, "testSize 1");

        table.put(1, "One");
        assertEqual(table.size(), 1, "testSize 2");

        table.put(2, "Two");
        assertEqual(table.size(), 2, "testSize 3");

        table.remove(1);
        assertEqual(table.size(), 1, "testSize 4");
    }

    /**
     * Вспомогательный метод для сравнения ожидаемого и фактического результатов.
     * @param actual фактическое значение
     * @param expected ожидаемое значение
     * @param testName название теста для вывода в отчет
     * @throws AssertionError если значения не совпадают
     */
    private static void assertEqual(Object actual, Object expected, String testName) {
        if ((actual == null && expected == null) ||
                (actual != null && actual.equals(expected))) {
            System.out.println("[OK] " + testName);
        } else {
            System.out.println("[FAIL] " + testName +
                    ": ожидалось " + expected + ", получено " + actual);
            throw new AssertionError("Тест не пройден");
        }
    }
}
