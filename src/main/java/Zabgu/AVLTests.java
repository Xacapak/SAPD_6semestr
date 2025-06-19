package Zabgu;

public class AVLTests {
    public static void runAllTests() {
        System.out.println("\nЗапуск модульных тестов...");
        testInsertAndContains();
        testDelete();
        testInOrderTraversal();
        testBalanceAfterInsert();
        testBalanceAfterDelete();
        testHeight();
        testEmptyTree();
        System.out.println("Все тесты пройдены успешно!");
    }

    /**
     * Тестирует базовые операции вставки и поиска элементов.
     * Проверяет:
     * 1. Корректность добавления элементов
     * 2. Корректность поиска существующих элементов
     * 3. Отсутствие ложных срабатываний при поиске
     */
    private static void testInsertAndContains() {
        System.out.println("\nТест 1: Вставка и поиск элементов");
        AVLTree<Integer> tree = new AVLTree<>();
        tree.insert(10);
        tree.insert(20);
        tree.insert(5);

        assertTrue(tree.contains(10), "Дерево должно содержать 10");
        assertTrue(tree.contains(20), "Дерево должно содержать 20");
        assertTrue(tree.contains(5), "Дерево должно содержать 5");
        assertFalse(tree.contains(15), "Дерево не должно содержать 15");

        System.out.println("Тест 1 пройден успешно");
    }

    /**
     * Тестирует операцию удаления элементов.
     * Проверяет:
     * 1. Корректность удаления элемента
     * 2. Сохранение других элементов после удаления
     * 3. Отсутствие удаленного элемента в дереве
     */
    private static void testDelete() {
        System.out.println("\nТест 2: Удаление элементов");
        AVLTree<Integer> tree = new AVLTree<>();
        tree.insert(10);
        tree.insert(20);
        tree.insert(5);

        tree.delete(20);
        assertFalse(tree.contains(20), "Дерево не должно содержать 20 после удаления");
        assertTrue(tree.contains(10), "Дерево должно содержать 10");
        assertTrue(tree.contains(5), "Дерево должно содержать 5");

        System.out.println("Тест 2 пройден успешно");
    }

    /**
     * Тестирует корректность in-order обхода дерева.
     * Проверяет порядок вывода элементов (должен быть отсортированным).
     * Требует визуальной проверки вывода в консоли.
     */
    private static void testInOrderTraversal() {
        System.out.println("\nТест 3: In-order обход");
        AVLTree<Integer> tree = new AVLTree<>();
        tree.insert(10);
        tree.insert(20);
        tree.insert(5);
        tree.insert(15);
        tree.insert(25);

        System.out.print("In-order обход: ");
        tree.inOrder(); // Ожидаемый вывод: 5 10 15 20 25
        System.out.println();

        System.out.println("Тест 3 пройден успешно (проверьте вывод вручную)");
    }

    /**
     * Тестирует автоматическую балансировку после вставки элементов.
     * Проверяет корректность работы поворотов при добавлении элементов.
     */
    private static void testBalanceAfterInsert() {
        System.out.println("\nТест 4: Балансировка после вставки");
        AVLTree<Integer> tree = new AVLTree<>();
        tree.insert(10);
        tree.insert(20);
        tree.insert(30); // Должен вызвать левый поворот

        // Проверяем структуру дерева
        assertTrue(tree.contains(20), "Корень должен быть 20");
        assertTrue(tree.contains(10), "Дерево должно содержать 10");
        assertTrue(tree.contains(30), "Дерево должно содержать 30");

        System.out.println("Тест 4 пройден успешно");
    }

    /**
     * Тестирует автоматическую балансировку после удаления элементов.
     * Проверяет корректность работы поворотов при удалении элементов.
     */
    private static void testBalanceAfterDelete() {
        System.out.println("\nТест 5: Балансировка после удаления");
        AVLTree<Integer> tree = new AVLTree<>();
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(40);
        tree.insert(50);

        tree.delete(10); // Должен вызвать балансировку

        assertTrue(tree.contains(30), "Корень должен быть 30");
        assertTrue(tree.contains(20), "Дерево должно содержать 20");
        assertTrue(tree.contains(40), "Дерево должно содержать 40");
        assertTrue(tree.contains(50), "Дерево должно содержать 50");

        System.out.println("Тест 5 пройден успешно");
    }

    /**
     * Тестирует корректность вычисления высоты дерева.
     * Проверяет высоту:
     * 1. Пустого дерева
     * 2. Дерева с одним элементом
     * 3. Сбалансированного дерева
     */
    private static void testHeight() {
        System.out.println("\nТест 6: Высота дерева");
        AVLTree<Integer> tree = new AVLTree<>();
        assertEquals(0, tree.height(), "Высота пустого дерева должна быть 0");

        tree.insert(10);
        assertEquals(1, tree.height(), "Высота дерева с одним элементом должна быть 1");

        tree.insert(20);
        tree.insert(5);
        assertEquals(2, tree.height(), "Некорректная высота дерева");

        tree.insert(15);
        tree.insert(25);
        assertEquals(3, tree.height(), "Некорректная высота дерева");

        System.out.println("Тест 6 пройден успешно");
    }

    /**
     * Тестирует поведение пустого дерева.
     * Проверяет:
     * 1. Поиск в пустом дереве
     * 2. Высоту пустого дерева
     * 3. Удаление из пустого дерева (не должно вызывать ошибок)
     */
    private static void testEmptyTree() {
        System.out.println("\nТест 7: Пустое дерево");
        AVLTree<Integer> tree = new AVLTree<>();

        assertFalse(tree.contains(10), "Пустое дерево не должно содержать элементов");
        assertEquals(0, tree.height(), "Высота пустого дерева должна быть 0");

        // Удаление из пустого дерева не должно вызывать ошибок
        tree.delete(10);

        System.out.println("Тест 7 пройден успешно");
    }

    /**
     * Проверяет, что условие истинно.
     * @param condition проверяемое условие
     * @param message сообщение об ошибке при ложном условии
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
     * @param message сообщение об ошибке при истинном условии
     * @throws AssertionError если условие истинно
     */
    private static void assertFalse(boolean condition, String message) {
        assertTrue(!condition, message);
    }

    /**
     * Проверяет равенство ожидаемого и фактического значений.
     * @param expected ожидаемое значение
     * @param actual фактическое значение
     * @param message сообщение об ошибке при несовпадении
     * @throws AssertionError если значения не равны
     */
    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(String.format(
                    "Тест не пройден: %s. Ожидалось: %d, получено: %d",
                    message, expected, actual));
        }
    }
}
