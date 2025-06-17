package Zabgu;

public class TreeNode {
    int nodeValue;
    TreeNode left;
    TreeNode right;

    public TreeNode(int nodeValue){
        this.nodeValue = nodeValue;
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
