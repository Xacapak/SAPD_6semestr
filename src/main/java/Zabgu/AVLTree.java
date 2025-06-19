package Zabgu;

import java.util.Iterator;

/**
 * Реализация самобалансирующегося AVL-дерева.
 * Поддерживает основные операции: вставка, удаление, поиск.
 * Гарантирует O(log n) время выполнения операций за счет автоматической балансировки.
 *
 * @param <T> тип элементов дерева, должен реализовывать Comparable
 */
class AVLTree<T extends Comparable<T>> implements Iterable<T> {
    /**
     * Внутренний класс для представления узла дерева.
     * Содержит ключ, высоту поддерева и ссылки на левого/правого потомков.
     */
    private class Node {
        T key;
        int height;
        Node left, right;

        /**
         * Конструктор узла.
         * @param key значение узла
         */
        Node(T key) {
            this.key = key;
            this.height = 1;
        }
    }

    private Node root;

    /**
     * Возвращает корень дерева в виде универсального TreeNode.
     * @return корень дерева или null, если дерево пустое
     */
    public TreeNode<T> getRoot() {
        return convertToTreeNode(root);
    }

    /**
     * Рекурсивно преобразует внутреннюю Node в публичный TreeNode.
     * @param node узел для преобразования
     * @return преобразованный узел или null, если node == null
     */
    private TreeNode<T> convertToTreeNode(Node node) {
        if (node == null) {
            return null;
        }
        TreeNode<T> treeNode = new TreeNode<>(node.key);
        treeNode.left = convertToTreeNode(node.left);
        treeNode.right = convertToTreeNode(node.right);
        return treeNode;
    }

    /**
     * Возвращает итератор для обхода дерева в порядке in-order.
     * @return итератор элементов дерева
     */
    @Override
    public Iterator<T> iterator() {
        return new BSTInOrderIterator<>(getRoot());
    }

    /**
     * Вставляет новый ключ в дерево.
     * @param key значение для вставки
     */
    public void insert(T key) {
        root = insert(root, key);
    }

    /**
     * Рекурсивная вставка ключа в поддерево.
     * @param node корень поддерева
     * @param key значение для вставки
     * @return новый корень поддерева после вставки и балансировки
     */
    private Node insert(Node node, T key) {
        if (node == null) {
            return new Node(key);
        }
        // Рекурсивная вставка в левое или правое поддерево
        if (key.compareTo(node.key) < 0) {
            node.left = insert(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            node.right = insert(node.right, key);
        } else {
            return node;        // Дубликаты не допускаются
        }
        // Обновление высоты и балансировка
        node.height = 1 + Math.max(height(node.left), height(node.right));
        return balance(node);
    }

    /**
     * Удаляет ключ из дерева.
     * @param key значение для удаления
     */
    public void delete(T key) {
        root = delete(root, key);
    }

    /**
     * Рекурсивное удаление ключа из поддерева.
     * @param node корень поддерева
     * @param key значение для удаления
     * @return новый корень поддерева после удаления и балансировки
     */
    private Node delete(Node node, T key) {
        if (node == null) {
            return null;
        }
        // Поиск удаляемого узла
        if (key.compareTo(node.key) < 0) {
            node.left = delete(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            node.right = delete(node.right, key);
        } else {
            // Узел с одним или без потомков
            if (node.left == null || node.right == null) {
                Node temp = (node.left != null) ? node.left : node.right;
                if (temp == null) {
                    temp = node;
                    node = null;
                } else {
                    node = temp;
                }
            } else {
                // Узел с двумя потомками: находим минимальный в правом поддереве
                Node temp = minValueNode(node.right);
                node.key = temp.key;                            // Копируем значение
                node.right = delete(node.right, temp.key);      // Удаляем дубликат
            }
        }
        if (node == null) {
            return null;
        }
        // Обновление высоты и балансировка
        node.height = 1 + Math.max(height(node.left), height(node.right));
        return balance(node);
    }

    /**
     * Проверяет наличие ключа в дереве.
     * @param key искомое значение
     * @return true если ключ найден, иначе false
     */
    public boolean contains(T key) {
        return contains(root, key);
    }

    /**
     * Рекурсивный поиск ключа в поддереве.
     * @param node корень поддерева
     * @param key искомое значение
     * @return true если ключ найден, иначе false
     */
    private boolean contains(Node node, T key) {
        if (node == null) {
            return false;
        }
        if (key.compareTo(node.key) < 0) {
            return contains(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            return contains(node.right, key);
        } else {
            return true;
        }
    }

    /**
     * Выводит элементы дерева в порядке in-order.
     */
    public void inOrder() {
        inOrder(root);
    }

    /**
     * Рекурсивный in-order обход поддерева.
     * @param node корень поддерева
     */
    private void inOrder(Node node) {
        if (node != null) {
            inOrder(node.left);
            System.out.print(node.key + " ");
            inOrder(node.right);
        }
    }

    /**
     * Возвращает высоту дерева.
     * @return высота дерева (0 для пустого)
     */
    public int height() {
        return height(root);
    }

    /**
     * Возвращает высоту узла.
     * @param node узел для проверки
     * @return высота поддерева (0 для null)
     */
    private int height(Node node) {
        return (node == null) ? 0 : node.height;
    }

    /**
     * Балансирует поддерево при необходимости.
     * @param node корень поддерева
     * @return новый корень после балансировки
     */
    private Node balance(Node node) {
        int balance = getBalance(node);
        // Left Left Case
        if (balance > 1 && getBalance(node.left) >= 0) {
            return rightRotate(node);
        }
        // Left Right Case
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        // Right Right Case
        if (balance < -1 && getBalance(node.right) <= 0) {
            return leftRotate(node);
        }
        // Right Left Case
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    /**
     * Вычисляет баланс-фактор узла.
     * @param node узел для проверки
     * @return разница высот левого и правого поддеревьев
     */
    private int getBalance(Node node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    /**
     * Правый поворот вокруг узла y.
     * @param y узел для поворота
     * @return новый корень поддерева
     */
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        // Выполняем поворот
        x.right = y;
        y.left = T2;
        // Обновляем высоты
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }

    /**
     * Левый поворот вокруг узла x.
     * @param x узел для поворота
     * @return новый корень поддерева
     */
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        // Выполняем поворот
        y.left = x;
        x.right = T2;
        // Обновляем высоты
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }

    /**
     * Находит узел с минимальным значением в поддереве.
     * @param node корень поддерева
     * @return узел с минимальным ключом
     */
    private Node minValueNode(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }
}