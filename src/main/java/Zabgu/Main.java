package Zabgu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException{

        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите задание:\n 1) Деревья. Бинарные деревья поиска. \n 2) Хеш-таблица.");
        int TaskNumber = scanner.nextInt();

        switch (TaskNumber){
            case 1:{
                System.out.println("1) Деревья. Бинарные деревья поиска.");

                TreeNode root = new TreeNode(50);
                root.insertNode(30);
                root.insertNode(70);
                root.insertNode(20);
                root.insertNode(40);
                root.insertNode(60);
                root.insertNode(80);
                root.insertNode(10);
                root.insertNode(5);
                root.insertNode(85);
                root.insertNode(65);
                root.insertNode(55);
                TreeNode.printTree(root);

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