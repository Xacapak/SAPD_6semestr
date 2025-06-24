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

    public void printTree() {
        printTree(this.root);
    }

    public static <T> void printTree(TreeNode<T> node) {
        printTreeRec(node, 0, new StringBuilder());
    }

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

    public List<T> traverseLNR() {
        return traverseLNR(root);
    }

    public static <T> List<T> traverseLNR(TreeNode<T> node) {
        List<T> result = new ArrayList<>();
        traverseLNR(node, result);
        return result;
    }

    private static <T> void traverseLNR(TreeNode<T> node, List<T> result) {
        if (node != null) {
            traverseLNR(node.left, result);
            result.add(node.value);
            traverseLNR(node.right, result);
        }
    }

/////////////////////////////////////////////////---Обход RNL---/////////////////////////////////////////////////////

    public List<T> traverseRNL() {
        return traverseRNL(root);
    }

    public static <T> List<T> traverseRNL(TreeNode<T> node) {
        List<T> result = new ArrayList<>();
        traverseRNL(node, result);
        return result;
    }

    private static <T> void traverseRNL(TreeNode<T> node, List<T> result) {
        if (node != null) {
            traverseLNR(node.right, result);
            result.add(node.value);
            traverseLNR(node.left, result);
        }
    }

/////////////////////////////////////////////////---Метод вставки---/////////////////////////////////////////////////////

    public void insert(T value) {
        root = insert(root, value);
    }

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

    public TreeNode<T> find(T value) {
        return find(root, value);
    }

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

    public void clear() {
        root = null;
    }

////////////////////////////////////////////---Метод глубины дерева---////////////////////////////////////////////////

    public int depth() {
        return depth(root);
    }

    public static <T> int depth(TreeNode<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(depth(node.left), depth(node.right));
    }

///////////////////////////////////////---Метод количество узлов в дереве---///////////////////////////////////////////

    public int size() {
        return size(root);
    }

    public static <T> int size(TreeNode<T> node) {
        if (node == null) return 0;
        return 1 + size(node.left) + size(node.right);
    }

///////////////////////////////////////////---Дополнительные методы---///////////////////////////////////////////
///////////////////////////////////////////---Метод удаления узла---/////////////////////////////////////////////

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
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            node.value = minValue(node.right).value;
            node.right = removeRec(node.right, node.value);
        }

        return node;
    }

    private TreeNode<T> minValue(TreeNode<T> node) {
        while (node.left != null){
            node = node.left;
        }
        return node;
    }
///////////////////////////////////////---Метод глубокого копирования---///////////////////////////////////////////

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

/////////////////////////////////////////////////---Метод для Итератора---/////////////////////////////////////////////////////

    @Override
    public Iterator<T> iterator() {
        return new BinSTreeIterator<>(root);
    }

}