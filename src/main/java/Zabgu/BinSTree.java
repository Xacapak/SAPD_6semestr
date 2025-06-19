package Zabgu;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс реализующий бинарное дерево поиска (Binary Search Tree).
 * Поддерживает основные операции: вставка, удаление, поиск, обходы дерева.
 *
 * @param <T> тип элементов дерева.
 */
public class BinSTree<T extends Comparable<T>> {
    private TreeNode<T> root;

    /**
     * Конструктор по умолчанию. Создает пустое дерево.
     */
    public BinSTree() {
        this.root = null;
    }

    /**
     * Выполняет обход дерева (LNR).
     *
     * @return список элементов в порядке LNR
     */
    public List<T> traverseLNR() {
        List<T> result = new ArrayList<>();
        traverseLNRRec(root, result);
        return result;
    }

    private void traverseLNRRec(TreeNode<T> node, List<T> result) {
        if (node != null) {
            traverseLNRRec(node.left, result);
            result.add(node.value);
            traverseLNRRec(node.right, result);
        }
    }

    /**
     * Выполняет обратный симметричный обход дерева (RNL).
     *
     * @return список элементов в порядке RNL
     */
    public List<T> traverseRNL() {
        List<T> result = new ArrayList<>();
        traverseRNLRec(root, result);
        return result;
    }

    private void traverseRNLRec(TreeNode<T> node, List<T> result) {
        if (node != null) {
            traverseRNLRec(node.right, result);
            result.add(node.value);
            traverseRNLRec(node.left, result);
        }
    }

    /**
     * Вставляет новый элемент в дерево.
     *
     * @param value значение для вставки
     */
    public void insert(T value) {
        root = insertRec(root, value);
    }

    private TreeNode<T> insertRec(TreeNode<T> node, T value) {
        if (node == null) {
            return new TreeNode<>(value);
        }

        if (value.compareTo(node.value) < 0) {
            node.left = insertRec(node.left, value);
        } else if (value.compareTo(node.value) > 0) {
            node.right = insertRec(node.right, value);
        }

        return node;
    }

    /**
     * Поиск элемента в дереве.
     *
     * @param value искомое значение
     * @return true если элемент найден, false в противном случае
     */
    public boolean contains(T value) {
        return containsRec(root, value);
    }

    private boolean containsRec(TreeNode<T> node, T value) {
        if (node == null) return false;

        int cmp = value.compareTo(node.value);
        if (cmp < 0) return containsRec(node.left, value);
        if (cmp > 0) return containsRec(node.right, value);

        return true;
    }

    /**
     * Очищает дерево, удаляя все элементы.
     */
    public void clear() {
        root = null;
    }

    /**
     * Вычисляет глубину дерева.
     *
     * @return глубина дерева (0 для пустого дерева)
     */
    public int depth() {
        return depthRec(root);
    }

    private int depthRec(TreeNode<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(depthRec(node.left), depthRec(node.right));
    }

    /**
     * Возвращает количество узлов в дереве.
     * Вычисляется рекурсивно при каждом вызове.
     *
     * @return количество узлов в дереве
     */
    public int size() {
        return sizeRec(root);
    }

    /**
     * Вспомогательный рекурсивный метод для подсчета узлов.
     *
     * @param node текущий узел для подсчета
     * @return количество узлов в поддереве
     */
    private int sizeRec(TreeNode<T> node) {
        if (node == null) {
            return 0;
        }
        return 1 + sizeRec(node.left) + sizeRec(node.right);
    }

    /**
     * Выводит дерево в удобочитаемом виде в консоль. Строит визуальное дерево.
     */
    public void printTree() {
        printTreeRec(root, 0, new StringBuilder());
    }

    private void printTreeRec(TreeNode<T> node, int level, StringBuilder prefix) {
        if (node == null) return;

        printTreeRec(node.right, level + 1, new StringBuilder(prefix).append("   "));

        if(level == 0){
            System.out.println(" " + node.value);
        }else {
            System.out.println(prefix + " |---" + node.value);
        }
        printTreeRec(node.left, level + 1, new StringBuilder(prefix).append("   "));
    }

    /**
     *  Дополнительные методы
     */

    /**
     * Удаляет элемент из дерева.
     *
     * @param value значение для удаления
     */
    public void remove(T value) {
        root = removeRec(root, value);
    }

    private TreeNode<T> removeRec(TreeNode<T> node, T value) {
        if (node == null) return null;

        int cmp = value.compareTo(node.value);
        if (cmp < 0) {
            node.left = removeRec(node.left, value);
        } else if (cmp > 0) {
            node.right = removeRec(node.right, value);
        } else {

            // Узел с одним потомком или без потомков
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            // Узел с двумя потомками
            node.value = minValue(node.right);

            node.right = removeRec(node.right, node.value);
        }

        return node;
    }

    private T minValue(TreeNode<T> node) {
        T min = node.value;
        while (node.left != null) {
            min = node.left.value;
            node = node.left;
        }
        return min;
    }

    /**
     * Создает глубокую копию дерева.
     *
     * @return новая независимая копия дерева
     */
    public BinSTree<T> deepCopy() {
        BinSTree<T> copy = new BinSTree<>();
        copy.root = deepCopyRec(root);
        return copy;
    }

    private TreeNode<T> deepCopyRec(TreeNode<T> node) {
        if (node == null) return null;

        TreeNode<T> newNode = new TreeNode<>(node.value);
        newNode.left = deepCopyRec(node.left);
        newNode.right = deepCopyRec(node.right);

        return newNode;
    }

    // Возвращает корневой узел дерева (Итератор)
    public TreeNode<T> getRoot(){
        return this.root;
    }
}
