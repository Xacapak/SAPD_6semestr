package Zabgu;

import java.util.Arrays;
import java.util.Iterator;
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

                // 1. Ручное создание узлов дерева, вывод дерева.
                System.out.println("\nБинарное дерево:");
                TreeNode<Integer> root = new TreeNode<>(1);
                root.left = new TreeNode<>(2);
                root.right = new TreeNode<>(3);
                root.left.left = new TreeNode<>(4);
                root.left.right = new TreeNode<>(5);

                BinSTree.printTree(root);

                // 2. Рекурсивный обход дерева методами LNR и RNL
                System.out.println("\nРекурсивный обход дерева методами LNR и RNL: ");
                System.out.println("LNR: " + BinSTree.traverseLNR(root));
                System.out.println("RNL: " + BinSTree.traverseRNL(root));

                // 3. Создание бинарного дерева поиска, через метод Insert и вывод дерева.
                System.out.println("\nБинарное дерево поиска, созданное через Insert: ");
                BinSTree<Integer> tree = new BinSTree<>();
                tree.insert(5);
                tree.insert(3);
                tree.insert(7);
                tree.insert(2);
                tree.insert(4);
                tree.insert(6);
                tree.insert(8);
                tree.insert(11);
                tree.insert(15);
                tree.insert(10);
                tree.printTree();

                // 4. Поиск узла в деревьях
                System.out.println("\nПоиск узла в бинарных деревьях: ");
                System.out.println("Бинарное дерево, искомый узел 10: " + BinSTree.find(root,10));
                System.out.println("Бинарное дерево поиска, искомый узел 8: " + tree.find(8).value);

                // 5. Глубина дерева
                System.out.println("\nГлубина дерева: ");
                System.out.println("Бинарное дерево: " + BinSTree.depth(root));
                System.out.println("Бинарное дерево поиска: " + tree.depth());

                // 6. Количество узлов в дереве
                System.out.println("\nКоличество узлов в дереве: ");
                System.out.println("Бинарное дерево: " + BinSTree.size(root));
                System.out.println("Бинарное дерево поиска: " + tree.size());

                // 7. Удаление узла в дереве
                System.out.println("\nУдаление узла в дереве 11.");
                tree.remove(11);
                tree.printTree();

                // 8. Глубокое копирование дерева
                System.out.println("\nГлубокое копирование дерева:");
                try {
                    BinSTree<Integer> copy = tree.deepCopy();
                    System.out.println("Скопированное дерево:");
                    copy.printTree();
                } catch (Exception e) {
                    System.err.println("Ошибка при копировании дерева: " + e.getMessage());
                    e.printStackTrace();
                }

                // 9. Использование Итератора
                System.out.println("\nИспользование Итератора, вывод методом LNR: ");
                for (Integer value : tree) {
                    System.out.print(value + " ");
                }

                // 10. Очистка всего дерева
                System.out.println("\n\nОчистка всего дерева");
                tree.clear();
                System.out.println("Бинарное дерево поиска, количество узлов: " + tree.size());

                // 11. Эффективность тестирования при поиске
                BinSTreePerformanceTest.testSearchPerformance();

                // 12. Создаем график (передаем только время)
                int[] testTimes = {5, 5, 9, 16, 35};
                BinSTreePerformanceChart.createChart(testTimes);

                // 13. Тесты
                BinSTreeTests.BinSTreeShow();

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

                // Оценка среднего времени поиска
                AVLPerformanceTest.testSearchPerformance();

                // Тесты
                AVLTests.runAllTests();

                // Использование итератора
                System.out.println("\nИтерация по элементам:");
                Iterator<Integer> iterator = new BinSTreeIterator<>(avlTree.getRoot());
                while (iterator.hasNext()) {
                    System.out.print(iterator.next() + " ");
                }

                break;
            }
            case 4: {
                System.out.println("4) Словарь.");

                // Создаем словарь
                Dictionary<String, Integer> dictionary = new Dictionary<>();

                // Добавление элементов
                System.out.println("Добавление элементов: {'apple': 10}, {'banana': 20}, {'orange': 30}, {'grape': 40}, {'melon': 50}");
                dictionary.put("apple", 10);
                dictionary.put("banana", 20);
                dictionary.put("orange", 30);
                dictionary.put("grape", 40);
                dictionary.put("melon", 50);

                // Вывод всех элементов
                System.out.println("\nВсе элементы словаря:");
                for (Dictionary.Entry<String, Integer> entry : dictionary) {
                    System.out.println(entry.getKey() + " => " + entry.getValue());
                }

                // Поиск элементов
                System.out.println("\nПоиск элементов:");
                System.out.println("Значение для 'banana': " + dictionary.get("banana"));
                System.out.println("Значение для 'pear': " + dictionary.get("pear"));
                System.out.println("Содержит ключ 'orange'? " + dictionary.containsKey("orange"));
                System.out.println("Содержит ключ 'kiwi'? " + dictionary.containsKey("kiwi"));

                // Удаление элемента
                System.out.println("\nУдаление элемента 'orange'");
                dictionary.remove("orange");
                System.out.println("Все элементы после удаления:");
                for (Dictionary.Entry<String, Integer> entry : dictionary) {
                    System.out.println(entry.getKey() + " => " + entry.getValue());
                }

                // Размер словаря
                System.out.println("\nКоличество элементов в словаре: " + dictionary.size());

                // Проверка на пустоту
                System.out.println("Словарь пустой? " + dictionary.isEmpty());

                // Получение всех ключей и значений
                System.out.println("\nВсе ключи: " + dictionary.keySet());
                System.out.println("Все значения: " + dictionary.values());

                // Очистка словаря
                System.out.println("\nОчистка словаря");
                dictionary.clear();
                System.out.println("Словарь пустой? " + dictionary.isEmpty());
                System.out.println("Количество элементов: " + dictionary.size());

                // Тестирование производительности поиска
                DictionaryPerformanceTest.testSearchPerformance();

                DictionaryTests.runAllTests();

                break;
            }
            case 5:{

                System.out.println("Задание для Экзамена.");

                // Создаем массив из 100 случайных чисел
                int[] array = new int[100];
                for (int i = 0; i < array.length; i++) {
                    array[i] = (int)(Math.random() * 1000);
                }

                System.out.println("Исходный массив:");
                System.out.println(Arrays.toString(array));

                // Сортируем массив
                SelectionSortAndBinarySearch.selectionSort(array);

                System.out.println("\nОтсортированный массив:");
                System.out.println(Arrays.toString(array));

                // Вводим число
                System.out.print("\nВведите элемент для поиска: ");
                int target = scanner.nextInt();
                scanner.close();

                // Выполняем бинарный поиск
                int result = SelectionSortAndBinarySearch.binarySearch(array, target);

                // Выводим результат
                if (result == -1) {
                    System.out.println("Элемент не найден в массиве.");
                } else {
                    System.out.println("Элемент найден по индексу: " + result);
                }

            }
            default:
                System.out.println("Выбрано некорректное задание.");
                break;
        }
    }
}