package Zabgu;

import java.util.List;

public class BinSTreeTest {
    public static void testSearchPerformance() {
        testInsertAndContains();
        testRemove();
        testTraversals();
        testDepthAndSize();
        testClear();
        testDeepCopy();
    }

    private static void testInsertAndContains() {
        System.out.println("--- Тест вставки и поиска ---");
        BinSTree<Integer> tree = new BinSTree<>();
        tree.insert(5);
        tree.insert(3);
        tree.insert(7);
        tree.insert(2);
        tree.insert(4);

        System.out.println("Содержит 3: " + tree.contains(3)); // true
        System.out.println("Содержит 7: " + tree.contains(7)); // true
        System.out.println("Содержит 10: " + tree.contains(10)); // false
        System.out.println("Дерево:");
        tree.printTree();
    }

    private static void testRemove() {
        System.out.println("\n--- Тест удаления ---");
        BinSTree<Integer> tree = new BinSTree<>();
        tree.insert(5);
        tree.insert(3);
        tree.insert(7);
        tree.insert(2);
        tree.insert(4);

        System.out.println("До удаления:");
        tree.printTree();

        tree.remove(3);
        System.out.println("После удаления 3:");
        tree.printTree();
        System.out.println("Содержит 3: " + tree.contains(3)); // false
    }

    private static void testTraversals() {
        System.out.println("\n--- Тест обходов ---");
        BinSTree<Integer> tree = new BinSTree<>();
        tree.insert(4);
        tree.insert(2);
        tree.insert(6);
        tree.insert(1);
        tree.insert(3);
        tree.insert(5);
        tree.insert(7);

        System.out.println("LNR (симметричный): " + tree.traverseLNR()); // [1, 2, 3, 4, 5, 6, 7]
        System.out.println("RNL (обратный симметричный): " + tree.traverseRNL()); // [7, 6, 5, 4, 3, 2, 1]
    }

    private static void testDepthAndSize() {
        System.out.println("\n--- Тест глубины и размера ---");
        BinSTree<Integer> tree = new BinSTree<>();
        tree.insert(5);
        tree.insert(3);
        tree.insert(7);
        tree.insert(2);
        tree.insert(4);

        System.out.println("Глубина: " + tree.depth()); // 3
        System.out.println("Размер: " + tree.size()); // 5
    }

    private static void testClear() {
        System.out.println("\n--- Тест очистки ---");
        BinSTree<Integer> tree = new BinSTree<>();
        tree.insert(5);
        tree.insert(3);
        tree.insert(7);

        System.out.println("До очистки, размер: " + tree.size()); // 3
        tree.clear();
        System.out.println("После очистки, размер: " + tree.size()); // 0
    }

    private static void testDeepCopy() {
        System.out.println("\n--- Тест глубокой копии ---");
        BinSTree<Integer> tree = new BinSTree<>();
        tree.insert(5);
        tree.insert(3);
        tree.insert(7);

        BinSTree<Integer> copy = tree.deepCopy();
        System.out.println("Оригинал:");
        tree.printTree();
        System.out.println("Копия:");
        copy.printTree();

        tree.remove(3);
        System.out.println("Оригинал после удаления 3:");
        tree.printTree();
        System.out.println("Копия осталась неизменной:");
        copy.printTree();
    }
}
