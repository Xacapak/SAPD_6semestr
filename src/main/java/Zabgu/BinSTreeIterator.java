package Zabgu;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class BinSTreeIterator<T extends Comparable<T>> implements Iterator<T> {
    private final Stack<TreeNode<T>> stack = new Stack<>();

    public BinSTreeIterator(TreeNode<T> root) {
        pushLeft(root);
    }

    private void pushLeft(TreeNode<T> node) {
        while (node != null) {
            stack.push(node);
            node = node.left;
        }
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

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