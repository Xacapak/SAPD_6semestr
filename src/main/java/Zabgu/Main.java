package Zabgu;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException{

        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите задание:\n 1) Деревья. Бинарные деревья поиска. \n 2) Хеш-таблица.");
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
                break;
            }
            default:
                System.out.println("Выбрано некорректное задание.");
                break;
        }
    }
}