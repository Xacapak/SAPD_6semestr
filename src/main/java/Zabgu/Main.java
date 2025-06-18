package Zabgu;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException{

        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите задание:\n 1) Деревья. Бинарные деревья поиска. " +
                                                "\n 2) Хеш-таблица." +
                                                "\n 3) АВЛ деревья." +
                                                "\n 4) Словарь.");
        int TaskNumber = scanner.nextInt();

        switch (TaskNumber){
            case 1:{
                System.out.println("1) Деревья. Бинарные деревья поиска.");

                // 1. Создаем дерево с 5 узлами
                BinSTree<Integer> tree = new BinSTree<>();
                tree.insert(5);
                tree.insert(3);
                tree.insert(7);
                tree.insert(2);
                tree.insert(4);

                // 2. Изображаем дерево
                System.out.println("Бинарное дерево поиска:");
                tree.printTree();

                // 3. Рекурсивные обходы дерева
                System.out.println("\nОбход LNR:");
                List<Integer> lnr = tree.traverseLNR();
                System.out.println(lnr);

                System.out.println("\nОбход RNL:");
                List<Integer> rnl = tree.traverseRNL();
                System.out.println(rnl);

                // 4. Вставка нового узла
                System.out.println("\nВставляем 6:");
                tree.insert(6);
                tree.printTree();

                // 5. Поиск узла в дереве
                System.out.println("Дерево содержит 4? " + tree.contains(4));
                System.out.println("Дерево содержит 10? " + tree.contains(10));

                // 6. Глубина дерева
                System.out.println("Глубина дерева: " + tree.depth());

                // 7. Количество узлов в дереве
                System.out.println("Количество узлов: " + tree.size());

                // 8. Удаление узла
                System.out.println("\nУдаляем 3:");
                tree.remove(3);
                tree.printTree();

                // 9. Глубокое копирование
                BinSTree<Integer> copy = tree.deepCopy();
                System.out.println("\nКопия дерева:");
                copy.printTree();

                // 10. Итератор для дерева
                System.out.println("Обход дерева с использованием итератора: ");
                Iterator<Integer> it = new BSTInOrderIterator<>(tree.getRoot());
                while (it.hasNext()){
                    System.out.print(it.next() + " ");
                }

                // 11. Очистка дерева
                tree.clear();
                System.out.println("\nПосле очистки, размер: " + tree.size());

                // 12. Время поиска для "бинарного дерева поиска"
                BSTPerformanceTest.testSearchPerformance();

                break;
            }
            case 2:{
                System.out.println("2) Хеш-таблица.");

                // Создаем хеш-таблицу с целочисленными ключами и строковыми значениями
                // Используем простую хеш-функцию (остаток от деления)
                HashTable<Integer, String> table = new HashTable<>(10, key -> key % 10);

                // Вставка элементов
                table.put(1, "Apple");
                table.put(2, "Banana");
                table.put(11, "Orange"); // Коллизия с ключом 1
                table.put(12, "Grape");  // Коллизия с ключом 2
                table.put(21, "Mango");  // Коллизия с ключом 1

                // Вывод размера таблицы
                System.out.println("Размер таблицы: " + table.size());

                // Поиск элементов
                System.out.println("Поиск ключа 1: " + table.get(1));
                System.out.println("Поиск ключа 11: " + table.get(11));
                System.out.println("Поиск ключа 21: " + table.get(21));
                System.out.println("Поиск несуществующего ключа 3: " + table.get(3));

                // Удаление элемента
                System.out.println("\nУдаление ключа 11...");
                table.remove(11);
                System.out.println("Поиск ключа 11 после удаления: " + table.get(11));
                System.out.println("Размер таблицы после удаления: " + table.size());

                // Использование итератора
                System.out.println("\nИтерация по элементам таблицы:");
                Iterator<HashTable.Entry<Integer, String>> iterator = new HashTableIterator<>(table);
                while (iterator.hasNext()) {
                    HashTable.Entry<Integer, String> entry = iterator.next();
                    System.out.println("Ключ: " + entry.key + ", Значение: " + entry.value);
                }

                // Очистка таблицы
                System.out.println("\nОчистка таблицы...");
                table.clear();
                System.out.println("Размер таблицы после очистки: " + table.size());

                // Оценка среднего времени поиска
                HashTablePerformanceTest.testSearchPerformance();

                // Тесты
                HashTableTests.runAllTests();

                break;
            }
            case 3: {
                System.out.println("3) АВЛ деревья.");

                AVLTree<Integer> avlTree = new AVLTree<>();

                // Вставка элементов
                System.out.println("Вставка элементов: 10, 20, 30, 40, 50, 25");
                avlTree.insert(10);
                avlTree.insert(20);
                avlTree.insert(30);
                avlTree.insert(40);
                avlTree.insert(50);
                avlTree.insert(25);

                // Вывод дерева
                System.out.println("\nОбход дерева в порядке in-order:");
                avlTree.inOrder();

                // Поиск элементов
                System.out.println("\n\nПоиск элементов:");
                System.out.println("Содержит 30? " + avlTree.contains(30));
                System.out.println("Содержит 35? " + avlTree.contains(35));

                // Удаление элемента
                System.out.println("\nУдаление элемента 30");
                avlTree.delete(30);
                System.out.println("Обход дерева после удаления:");
                avlTree.inOrder();

                // Вывод высоты дерева
                System.out.println("\n\nВысота дерева: " + avlTree.height());

                AVLPerformanceTest.testSearchPerformance();

                AVLTests.runAllTests();

                Iterator<Integer> iterator = new BSTInOrderIterator<>(avlTree.getRoot());
                while (iterator.hasNext()) {
                    System.out.print(iterator.next());
                }

                break;
            }
            case 4: {
                System.out.println("4) Словарь.");
                break;
            }
            default:
                System.out.println("Выбрано некорректное задание.");
                break;
        }
    }
}