package Zabgu;

import java.util.Iterator;

class AVLTree<T extends Comparable<T>> implements Iterable<T> {
    private class Node {
        T key;
        int height;
        Node left, right;

        Node(T key) {
            this.key = key;
            this.height = 1;
        }
    }

    private Node root;

    // Метод для получения корня в виде TreeNode<T>
    public TreeNode<T> getRoot() {
        return convertToTreeNode(root);
    }

    // Вспомогательный метод для преобразования Node в TreeNode
    private TreeNode<T> convertToTreeNode(Node node) {
        if (node == null) {
            return null;
        }
        TreeNode<T> treeNode = new TreeNode<>(node.key);
        treeNode.left = convertToTreeNode(node.left);
        treeNode.right = convertToTreeNode(node.right);
        return treeNode;
    }

    // Реализация Iterable
    @Override
    public Iterator<T> iterator() {
        return new BSTInOrderIterator<>(getRoot());
    }

    // Остальные методы AVLTree остаются без изменений
    public void insert(T key) {
        root = insert(root, key);
    }

    private Node insert(Node node, T key) {
        if (node == null) {
            return new Node(key);
        }
        if (key.compareTo(node.key) < 0) {
            node.left = insert(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            node.right = insert(node.right, key);
        } else {
            return node;
        }
        node.height = 1 + Math.max(height(node.left), height(node.right));
        return balance(node);
    }

    public void delete(T key) {
        root = delete(root, key);
    }

    private Node delete(Node node, T key) {
        if (node == null) {
            return null;
        }
        if (key.compareTo(node.key) < 0) {
            node.left = delete(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            node.right = delete(node.right, key);
        } else {
            if (node.left == null || node.right == null) {
                Node temp = (node.left != null) ? node.left : node.right;
                if (temp == null) {
                    temp = node;
                    node = null;
                } else {
                    node = temp;
                }
            } else {
                Node temp = minValueNode(node.right);
                node.key = temp.key;
                node.right = delete(node.right, temp.key);
            }
        }
        if (node == null) {
            return null;
        }
        node.height = 1 + Math.max(height(node.left), height(node.right));
        return balance(node);
    }

    public boolean contains(T key) {
        return contains(root, key);
    }

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

    public void inOrder() {
        inOrder(root);
    }

    private void inOrder(Node node) {
        if (node != null) {
            inOrder(node.left);
            System.out.print(node.key + " ");
            inOrder(node.right);
        }
    }

    public int height() {
        return height(root);
    }

    private int height(Node node) {
        return (node == null) ? 0 : node.height;
    }

    private Node balance(Node node) {
        int balance = getBalance(node);
        if (balance > 1 && getBalance(node.left) >= 0) {
            return rightRotate(node);
        }
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && getBalance(node.right) <= 0) {
            return leftRotate(node);
        }
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    private int getBalance(Node node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        x.right = y;
        y.left = T2;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }

    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        y.left = x;
        x.right = T2;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }

    private Node minValueNode(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }
}