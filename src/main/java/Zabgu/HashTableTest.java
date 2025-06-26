package Zabgu;

/**
 * Класс для модульного тестирования функциональности хеш-таблицы.
 * Содержит тесты основных операций и итератора.
 */
public class HashTableTest {
    /**
     * Запускает все тесты для хеш-таблицы
     */
    public static void startTest(){
        System.out.println("\nЗапуск тестов....");
        testTableOperations();
        testIterator();
    }

    /**
     * Тестирует основные операции хеш-таблицы:
     * - Вставка и поиск элементов
     * - Обработка коллизий
     * - Удаление элементов
     * - Очистка таблицы
     */
    private static void testTableOperations() {
        HashTable<Integer> table = new HashTable<>(10, key -> key % 10);

        // Тест вставки и поиска
        table.insert(new DataItem<>(5));
        assert table.find(5) != null : "Элемент 5 должен быть найден";

        // Тест коллизий
        table.insert(new DataItem<>(15)); // Коллизия с 5
        assert table.find(15) != null : "Элемент 15 должен быть найден, несмотря на коллизию";

        // Тест удаления
        table.delete(5);
        assert table.find(5) == null : "Элемент 5 должен быть удален";
        assert table.find(15) != null : "Элемент 15 должен остаться после удаления 5";

        // Тест очистки
        table.clear();
        assert table.find(15) == null : "Таблица должна быть пустой после clear()";
        assert table.tableSize() == 0 : "Размер таблицы должен быть 0 после очистки";

        System.out.println("Тестирование HashTable.....Завершено.");
    }

    /**
     * Тестирует итератор хеш-таблицы:
     * - Корректность обхода всех элементов
     * - Пропуск удаленных элементов
     */
    private static void testIterator() {
        HashTable<Integer> table = new HashTable<>(10, key -> key % 10);
        table.insert(new DataItem<>(3));
        table.insert(new DataItem<>(13));
        table.insert(new DataItem<>(23));

        // Тест базовой итерации
        int count = 0;
        for (DataItem<Integer> item : table) {
            assert item != null : "Итератор не должен возвращать null";
            count++;
        }
        assert count == 3 : "Итератор должен найти 3 элемента";

        // Тест с удаленными элементами
        table.delete(13);
        count = 0;
        for (DataItem<Integer> item : table) {
            count++;
        }
        assert count == 2 : "Итератор должен пропускать удаленные элементы";

        System.out.println("Тестирование HashTableIterator.....Завершено.");
    }
}
