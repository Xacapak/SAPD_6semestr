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
}