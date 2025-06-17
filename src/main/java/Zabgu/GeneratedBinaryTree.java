package Zabgu;

import java.util.*;

public class GeneratedBinaryTree {
    public static void execute(Scanner scanner){

        System.out.print("Введите количество узлов в дереве: ");
        int nodeCount = scanner.nextInt();

        System.out.print("Введите максимальное число для генерации случайных чисел: ");
        int maxValue = scanner.nextInt();

        TreeNode root = generateRandomTree(nodeCount, maxValue);

        System.out.println("\nСгенерированное бинарное дерево поиска:");
        TreeNode.printTree(root);

        System.out.println("Выберите действие: \n 1) Рекурсивный обход деревьев (LNR и RNL)");
        int TaskNumber = scanner.nextInt();

        switch (TaskNumber){
            case 1:{

                System.out.println("\nОбход дерева LNR:");
                root.printTreeLNR();

                List<Integer> completionLNR = new ArrayList<>();
                root.fillingListLNR(completionLNR);
                System.out.println("\nСписок значений в порядке LNR: " + completionLNR);

                System.out.println("\nОбход дерева RNL:");
                root.printTreeRNL();

                List<Integer> completionRNL = new ArrayList<>();
                root.fillingListRNL(completionRNL);
                System.out.println("\nСписок значений в порядке RNL: " + completionRNL);

                break;
            }
            case 2:{

            }
            default:
                System.out.println("Выбрано некорректное действие.");
                break;
        }
    }

    public static TreeNode generateRandomTree(int nodeCount, int maxValue){
        if (nodeCount <= 0) return null;

        Random random = new Random();
        Set<Integer> values = new HashSet<>();
        TreeNode root = null;

        while (values.size() < nodeCount) {
            values.add(random.nextInt(maxValue) + 1);
        }

        for (int value : values) {
            if (root == null) {
                root = new TreeNode(value);
            } else {
                root.insertNode(value);
            }
        }

        return root;
    }

}