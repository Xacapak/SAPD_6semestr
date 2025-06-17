package Zabgu;

public class BinSTree<T extends Comparable<T>> {
    private Node<T> root;
    private int size;

    private static class Node<T>{
        T data;
        Node<T> left;
        Node<T> right;

        Node(T data){
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    public BinSTree(){
        root = null;
        size = 0;
    }

    // Вставка
    public void insert(T value){
        root = insertRec(root, value);
        size++;
    }

    private Node<T> insertRec(Node<T> node, T value){
        if(node == null){
            return new Node<>(value);
        }
        if(value.compareTo(node.data) < 0){
            node.left = insertRec(node.left, value);
        } else if (value.compareTo(node.data) > 0) {
            node.right = insertRec(node.right, value);
        }
        return node;
    }
}
