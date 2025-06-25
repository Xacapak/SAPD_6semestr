package Zabgu;

import java.util.List;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class BinSTreeTests {
    public static void BinSTreeShow(){
        System.out.println("\nЗапуск тестов....");
        testTreeNode();
        testBinSTree();
        testBinSTreeIterator();
    }

    public static void testTreeNode() {

        TreeNode<Integer> node = new TreeNode<>(10);
        assert node.value == 10 : "Значение узла должно сохраняться";
        assert node.left == null && node.right == null : "Новые узлы должны иметь null-потомков";

        node.left = new TreeNode<>(5);
        node.right = new TreeNode<>(15);
        assert node.left.value == 5 : "Левый потомок должен быть 5";
        assert node.right.value == 15 : "Правый потомок должен быть 15";

        System.out.println("Тестирование TreeNode.....Завершено.");
    }

    public static void testBinSTree() {

        BinSTree<Integer> tree = new BinSTree<>();
        assert tree.size() == 0 : "Размер пустого дерева должен быть 0";

        tree.insert(5);
        tree.insert(3);
        tree.insert(7);
        assert tree.size() == 3 : "Размер дерева после вставки 3 элементов";
        assert tree.find(5) != null : "Корневой элемент должен существовать";
        assert tree.find(3) != null : "Левый потомок должен существовать";
        assert tree.find(7) != null : "Правый потомок должен существовать";

        assert tree.traverseLNR().equals(List.of(3, 5, 7)) : "LNR обход должен возвращать отсортированные значения";
        assert tree.traverseRNL().equals(List.of(7, 5, 3)) : "RNL обход должен возвращать значения в обратном порядке";

        tree.remove(3);
        assert tree.find(3) == null : "Удаленный элемент не должен находиться";
        assert tree.size() == 2 : "Размер должен уменьшиться после удаления";

        tree.insert(6);
        assert tree.depth() == 3 : "Глубина дерева должна быть 3";

        System.out.println("Тестирование BinSTree.....Завершено.");
    }

    // 3. Тесты для BinSTreeIterator
    public static void testBinSTreeIterator() {

        BinSTree<Integer> tree = new BinSTree<>();
        tree.insert(4);
        tree.insert(2);
        tree.insert(6);

        StringBuilder sb = new StringBuilder();
        for (Integer val : tree) {
            sb.append(val).append(" ");
        }
        assert sb.toString().trim().equals("2 4 6") : "Итератор должен возвращать значения в порядке LNR";

        Iterator<Integer> it = tree.iterator();
        assert it.hasNext() : "Итератор должен иметь элементы";
        assert it.next() == 2 : "Первый элемент должен быть 2";
        assert it.next() == 4 : "Второй элемент должен быть 4";
        assert it.next() == 6 : "Третий элемент должен быть 6";

        boolean exceptionThrown = false;

        System.out.println("Тестирование BinSTreeIterator.....Завершено.");
        try {
            it.next();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Должно бросаться исключение при попытке next() после окончания элементов";
    }
}
