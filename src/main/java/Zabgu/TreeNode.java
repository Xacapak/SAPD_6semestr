package Zabgu;

import java.util.Comparator;
import java.util.List;

public class TreeNode<T> {
    T value;
    TreeNode<T> left;
    TreeNode <T> right;

    public TreeNode(T value){
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public void insertNode(T newNodeValue, Comparator<T> comparator){
        if(comparator.compare(newNodeValue, value) < 0) {
            if(left == null) left = new TreeNode<>(newNodeValue);
            else left.insertNode(newNodeValue, comparator);
        } else {
            if(right == null) right = new TreeNode<>(newNodeValue);
            else right.insertNode(newNodeValue, comparator);
        }
    }

    public void printTreeLNR(){
        if(left != null){
            left.printTreeLNR();
        }
        System.out.print(value + " ");
        if(right != null){
            right.printTreeLNR();
        }
    }

    public void printTreeRNL(){
        if(right != null){
            right.printTreeRNL();
        }
        System.out.print(value + " ");
        if(left != null){
            left.printTreeRNL();
        }
    }

    public void fillingListLNR(List<T> list){
        if (left != null){
            left.fillingListLNR(list);
        }
        list.add(value);
        if (right != null){
            right.fillingListLNR(list);
        }
    }

    public void fillingListRNL(List<T> list){
        if(right != null){
            right.fillingListRNL(list);
        }
        list.add(value);
        if (left != null){
            left.fillingListRNL(list);
        }
    }

    public static <T> void printTree(TreeNode<T> root){
        printTree(root, 0, new StringBuilder());
    }

    public static <T> void printTree(TreeNode <T> node,int level, StringBuilder prefix){
        if (node == null) return;

        printTree(node.right, level + 1, new StringBuilder(prefix).append("    "));

        if(level == 0){
            System.out.println(" " + node.value);
        } else {
            System.out.println(prefix + " |---" + node.value);
        }
        printTree(node.left, level + 1, new StringBuilder(prefix).append("    "));
    }
}