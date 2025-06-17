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

                GeneratedBinaryTree.execute(scanner);

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