package Zabgu;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
 * Класс бинарного дерева поиска.
 * @param <T> тип элементов дерева.
 */
public class BinSTree<T extends Comparable<T>> implements Iterable<T> {
    private TreeNode<T> root;

    /**
     * Конструктор по умолчанию, создает пустое дерево.
     */
    public BinSTree() {
        this.root = null;
    }

/////////////////////////////////////////////////---Вывод дерева---/////////////////////////////////////////////////////

    /**
     * Выводит структуру дерева в консоль в древовидной форме
     */
    public void printTree() {
        printTree(this.root);
    }

    /**
     * Выводит структуру поддерева с заданным корнем
     * @param <T> тип элементов дерева
     * @param node корневой узел поддерева для вывода
     */
    public static <T> void printTree(TreeNode<T> node) {
        printTreeRec(node, 0, new StringBuilder());
    }

    /**
     * Рекурсивный метод для форматированного вывода дерева
     * @param <T> тип элементов дерева
     * @param node текущий узел
     * @param level текущий уровень вложенности
     * @prefix префикс для отступов
     */
    private static <T> void printTreeRec(TreeNode<T> node, int level, StringBuilder prefix) {
        if (node == null) return;
        printTreeRec(node.right, level + 1, new StringBuilder(prefix).append("   "));
        if (level == 0) {
            System.out.println(" " + node.value);
        } else {
            System.out.println(prefix + " |---" + node.value);
        }
        printTreeRec(node.left, level + 1, new StringBuilder(prefix).append("   "));
    }

/////////////////////////////////////////////////---Обход LNR---/////////////////////////////////////////////////////

    /**
     * Выполняет симметричный обход дерева (LNR)
     * @return список элементов в порядке возрастания
     */
    public List<T> traverseLNR() {
        return traverseLNR(root);
    }

    /**
     * Выполняет симметричный обход поддерева
     * @param <T> тип элементов дерева
     * @param node корень поддерева
     * @return список элементов поддерева в порядке возрастания
     */
    public static <T> List<T> traverseLNR(TreeNode<T> node) {
        List<T> result = new ArrayList<>();
        traverseLNR(node, result);
        return result;
    }

    /**
     * Рекурсивный метод симметричного обхода
     * @param <T> тип элементов дерева
     * @param node текущий узел
     * @param result список для сохранения результатов
     */
    private static <T> void traverseLNR(TreeNode<T> node, List<T> result) {
        if (node != null) {
            traverseLNR(node.left, result);
            result.add(node.value);
            traverseLNR(node.right, result);
        }
    }

/////////////////////////////////////////////////---Обход RNL---/////////////////////////////////////////////////////

    /**
     * Выполняет обратный симметричный обход дерева (RNL)
     * @return список элементов в порядке убывания
     */
    public List<T> traverseRNL() {
        return traverseRNL(root);
    }

    /**
     * Выполняет обратный симметричный обход поддерева
     * @param <T> тип элементов дерева
     * @param node корень поддерева
     * @return список элементов поддерева в порядке убывания
     */
    public static <T> List<T> traverseRNL(TreeNode<T> node) {
        List<T> result = new ArrayList<>();
        traverseRNL(node, result);
        return result;
    }

    /**
     * Рекурсивный метод обратного симметричного обхода
     * @param <T> тип элементов дерева
     * @param node текущий узел
     * @param result список для сохранения результатов
     */
    private static <T> void traverseRNL(TreeNode<T> node, List<T> result) {
        if (node != null) {
            traverseLNR(node.right, result);
            result.add(node.value);
            traverseLNR(node.left, result);
        }
    }

/////////////////////////////////////////////////---Метод вставки---/////////////////////////////////////////////////////

    /**
     * Вставляет новый элемент в дерево
     * @param value значение для вставки
     */
    public void insert(T value) {
        root = insert(root, value);
    }

    /**
     * Вставляет элемент в поддерево
     * @param <T> тип элементов дерева
     * @param root корень поддерева
     * @param value значение для вставки
     * @return новый корень поддерева
     */
    public static <T extends Comparable<T>> TreeNode<T> insert(TreeNode<T> root, T value) {
        TreeNode<T> newNode = new TreeNode<>(value);
        if (root == null) return newNode;

        TreeNode<T> current = root;
        TreeNode<T> parent = null;

        while (current != null) {
            parent = current;
            int cmp = value.compareTo(current.value);
            if (cmp < 0) current = current.left;
            else if (cmp > 0) current = current.right;
            else return root;
        }

        int cmp = value.compareTo(parent.value);
        if (cmp < 0) parent.left = newNode;
        else parent.right = newNode;

        return root;
    }

/////////////////////////////////////////////////---Метод поиска---/////////////////////////////////////////////////////

    /**
     * Ищет узел с заданным значением
     * @param value искомое значение
     * @return найденный узел или null
     */
    public TreeNode<T> find(T value) {
        return find(root, value);
    }

    /**
     * Ищет узел в поддереве
     * @param <T> тип элементов дерева
     * @param node корень поддерева
     * @param value искомое значение
     * @return найденный узел или null
     */
    public static <T extends Comparable<T>> TreeNode<T> find(TreeNode<T> node, T value) {
        while (node != null) {
            int cmp = value.compareTo(node.value);
            if (cmp < 0) node = node.left;
            else if (cmp > 0) node = node.right;
            else return node;
        }
        return null;
    }

////////////////////////////////////////////---Метод очистки дерева---////////////////////////////////////////////////

    /**
     * Очищает дерево
     */
    public void clear() {
        root = null;
    }

////////////////////////////////////////////---Метод глубины дерева---////////////////////////////////////////////////

    /**
     * Вычисляет глубину дерева
     * @return максимальная глубина дерева
     */
    public int depth() {
        return depth(root);
    }

    /**
     * Вычисляет глубину поддерева
     * @param <T> тип элементов дерева
     * @param node корень поддерева
     * @return глубина поддерева
     */
    public static <T> int depth(TreeNode<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(depth(node.left), depth(node.right));
    }

///////////////////////////////////////---Метод количество узлов в дереве---///////////////////////////////////////////

    /**
     * Подсчитывает количество узлов в дереве
     * @return размер дерева
     */
    public int size() {
        return size(root);
    }

    /**
     * Подсчитывает количество узлов в поддереве
     * @param <T> тип элементов дерева
     * @param node корень поддерева
     * @return размер поддерева
     */
    public static <T> int size(TreeNode<T> node) {
        if (node == null) return 0;
        return 1 + size(node.left) + size(node.right);
    }

///////////////////////////////////////////---Дополнительные методы---///////////////////////////////////////////
///////////////////////////////////////////---Метод удаления узла---/////////////////////////////////////////////

    /**
     * Удаляет узел с заданным значением
     * @param value значение для удаления
     */
    public void remove(T value) {
        root = removeRec(root, value);
    }

    /**
     * Рекурсивный метод удаления узла
     * @param node текущий узел
     * @param value значение для удаления
     * @return новый корень поддерева
     */
    private TreeNode<T> removeRec(TreeNode<T> node, T value) {
        if (node == null) return null;

        int cmp = value.compareTo(node.value);
        if (cmp < 0) {
            node.left = removeRec(node.left, value);
        } else if (cmp > 0) {
            node.right = removeRec(node.right, value);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            node.value = minValue(node.right).value;
            node.right = removeRec(node.right, node.value);
        }

        return node;
    }

    /**
     * Находит узел с минимальным значением в поддереве
     * @param node корень поддерева
     * @return узел с минимальным значением
     */
    private TreeNode<T> minValue(TreeNode<T> node) {
        while (node.left != null){
            node = node.left;
        }
        return node;
    }
///////////////////////////////////////---Метод глубокого копирования---///////////////////////////////////////////

    /**
     * Создает глубокую копию дерева
     * @return новая независимая копия дерева
     */
    public BinSTree<T> deepCopy() {
        BinSTree<T> copy = new BinSTree<>();
        copy.root = deepCopyRec(root);
        return copy;
    }

    /**
     * Рекурсивный метод создания глубокой копии поддерева
     * @param node корень поддерева для копирования
     * @return корень скопированного поддерева
     */
    private TreeNode<T> deepCopyRec(TreeNode<T> node) {
        if (node == null) return null;

        TreeNode<T> newNode = new TreeNode<>(node.value);
        newNode.left = deepCopyRec(node.left);
        newNode.right = deepCopyRec(node.right);

        return newNode;
    }

/////////////////////////////////////////////////---Метод для Итератора---/////////////////////////////////////////////////////

    /**
     * Возвращает итератор для обхода дерева
     * @return итератор в порядке LNR (симметричный обход)
     */
    @Override
    public Iterator<T> iterator() {
        return new BinSTreeIterator<>(root);
    }

}