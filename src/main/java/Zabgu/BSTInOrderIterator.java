package Zabgu;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Реализация итератора для симметричного обхода (in-order) бинарного дерева поиска.
 * Обеспечивает последовательный доступ к элементам дерева в отсортированном порядке.
 *
 * Особенности:
 *   Обход происходит в порядке LNR (Left-Node-Right)
 *   Использует стек для отслеживания узлов
 *   Амортизированная временная сложность next() - O(1)
 *   Пространственная сложность - O(h), где h - высота дерева
 *
 * @param <T> тип элементов дерева
 */
public class BSTInOrderIterator<T extends Comparable<T>> implements Iterator<T> {

    /**
     * Стек для хранения узлов, подлежащих обработке.
     * Содержит "открытые" узлы, чьи левые поддеревья уже обработаны,
     * но сами узлы и их правые поддеревья - ещё нет.
     */
    private Stack<TreeNode<T>> stack = new Stack<>();

    /**
     * Конструктор итератора. Инициализирует обход дерева.
     *
     * @param root корневой узел дерева. Если null, итератор будет считаться пустым.
     */
    public BSTInOrderIterator(TreeNode<T> root) {
        pushLeft(root);
    }

    /**
     * Вспомогательный метод для заполнения стека всеми левыми узлами,
     * начиная с заданного.
     *
     * @param node начальный узел для обработки (может быть null)
     */
    private void pushLeft(TreeNode<T> node) {
        while (node != null) {
            stack.push(node);
            node = node.left;
        }
    }

    /**
     * Проверяет наличие следующего элемента для обхода.
     *
     * @return true если есть следующие элементы, false если обход завершен
     */
    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    /**
     * Возвращает следующий элемент в порядке in-order обхода.
     *
     * Алгоритм работы:
     *   Извлекает узел из стека (текущий минимальный необработанный элемент)
     *   Перед возвратом значения обрабатывает правое поддерево
     *   Возвращает значение узла
     * @return следующее значение в порядке обхода
     * @throws NoSuchElementException если вызван при завершенном обходе
     */
    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Неь элементов в дереве");
        }

        TreeNode<T> current = stack.pop();
        pushLeft(current.right);

        return current.value;
    }

    /**
     * Удаление элементов не поддерживается данным итератором.
     *
     * @throws UnsupportedOperationException при любом вызове
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove() не поддерживается");
    }
}