package Zabgu;

import java.util.List;

public class TreeNode {
    int nodeValue;
    TreeNode left;
    TreeNode right;

    public TreeNode(int nodeValue){
        this.nodeValue = nodeValue;
        this.left = null;
        this.right = null;
    }

    public void insertNode(int newNodeValue){
        if(newNodeValue < nodeValue){
            if(left == null) left = new TreeNode(newNodeValue);
            else left.insertNode(newNodeValue);
        } else {
            if(right == null) right = new TreeNode(newNodeValue);
            else right.insertNode(newNodeValue);
        }
    }

    public void printTreeLNR(){
        if(left != null){
            left.printTreeLNR();
        }
        System.out.print(nodeValue + " ");
        if(right != null){
            right.printTreeLNR();
        }
    }

    public void printTreeRNL(){
        if(right != null){
            right.printTreeRNL();
        }
        System.out.print(nodeValue + " ");
        if(left != null){
            left.printTreeRNL();
        }
    }

    public void fillingListLNR(List<Integer> list){
        if (left != null){
            left.fillingListLNR(list);
        }
        list.add(nodeValue);
        if (right != null){
            right.fillingListLNR(list);
        }
    }

    public void fillingListRNL(List<Integer> list){
        if(right != null){
            right.fillingListRNL(list);
        }
        list.add(nodeValue);
        if (left != null){
            left.fillingListRNL(list);
        }
    }

    public static void printTree(TreeNode root){
        printTree(root, 0, new StringBuilder());
    }

    public static void printTree(TreeNode node,int level, StringBuilder prefix){
        if (node == null) return;

        printTree(node.right, level + 1, new StringBuilder(prefix).append("    "));

        if(level == 0){
            System.out.println(" " + node.nodeValue);
        } else {
            System.out.println(prefix + " |---" + node.nodeValue);
        }
        printTree(node.left, level + 1, new StringBuilder(prefix).append("    "));
    }

}
