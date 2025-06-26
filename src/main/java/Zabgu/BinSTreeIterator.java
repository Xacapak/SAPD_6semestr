package Zabgu;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Итератор для симметричного обхода (LNR) бинарного дерева поиска.
 * Реализует интерфейс Iterator<T> для последовательного доступа к элементам дерева.
 * @param <T> тип элементов дерева, должен реализовывать Comparable<T>
 */
public class BinSTreeIterator<T extends Comparable<T>> implements Iterator<T> {
    /**
     * Стек для хранения узлов дерева при обходе.
     * Позволяет организовать нерекурсивный симметричный обход.
     */
    private final Stack<TreeNode<T>> stack = new Stack<>();

    /**
     * Конструктор итератора.
     * Инициализирует обход, начиная с корневого узла.
     *
     * @param root корневой узел дерева или поддерева для обхода
     */
    public BinSTreeIterator(TreeNode<T> root) {
        pushLeft(root);
    }

    /**
     * Вспомогательный метод для добавления всех левых потомков в стек.
     * Используется для инициализации и продолжения обхода.
     *
     * @param node текущий узел, с которого начинается добавление левых потомков
     */
    private void pushLeft(TreeNode<T> node) {
        while (node != null) {
            stack.push(node);
            node = node.left;
        }
    }

    /**
     * Проверяет наличие следующего элемента в обходе.
     *
     * @return true, если в стеке есть следующий элемент, false в противном случае
     */
    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    /**
     * Возвращает следующий элемент в симметричном обходе.
     *
     * @return следующее значение элемента дерева
     * @throws NoSuchElementException если в дереве больше нет элементов
     */
    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        TreeNode<T> node = stack.pop();
        pushLeft(node.right);
        return node.value;
    }
}