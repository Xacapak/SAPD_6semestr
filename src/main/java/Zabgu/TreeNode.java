package Zabgu;

/**
 * Класс, представляющий узел бинарного дерева.
 * Каждый узел содержит значение и ссылки на левого и правого потомков.
 *
 * @param <T> тип данных значения, хранящегося в узле.
 *
 */
public class TreeNode<T> {
    T value;
    TreeNode<T> left;
    TreeNode <T> right;

    /**
     * Конструктор, создающий новый узел с заданным значением.
     * Левый и правый потомки инициализируются как null.
     *
     * @param value значение, которое будет храниться в узле.
     */
    public TreeNode(T value){
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public void printTree() {
        printTreeRec(this, 0, new StringBuilder());
    }

    private void printTreeRec(TreeNode<T> node, int level, StringBuilder prefix) {
        if (node == null) {
            return;
        }

        // Сначала правый потомок (верхняя часть вывода)
        printTreeRec(node.right, level + 1, new StringBuilder(prefix).append("    "));

        // Вывод текущего узла
        if (level == 0) {
            System.out.println(node.value);
        } else {
            System.out.println(prefix + "|---" + node.value);
        }

        // Затем левый потомок (нижняя часть вывода)
        printTreeRec(node.left, level + 1, new StringBuilder(prefix).append("    "));
    }

}