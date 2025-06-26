package Zabgu;

public class SelectionSortAndBinarySearch {

    // Функция сортировки выбором
    public static void selectionSort(int[] arr) {
        // Внешний цикл проходит по всем элементам массива, кроме последнего
        for (int i = 0; i < arr.length - 1; i++) {
            // Предполагаем, что минимальный элемент находится на текущей позиции
            int minIndex = i;
            // Внутренний цикл ищет минимальный элемент в оставшейся части массива
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;                   // Нашли новый минимальный элемент
                }
            }
            // Обмен минимального элемента с текущим
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }

    // Функция бинарного поиска
    public static int binarySearch(int[] arr, int target) {
        // Границы поиска - весь массив
        int left = 0;
        int right = arr.length - 1;

        // Пока границы не сомкнутся
        while (left <= right) {
            // Вычисляем середину текущего диапазона
            int mid = left + (right - left) / 2;                // Защита от переполнения

            if (arr[mid] == target) {
                return mid;                                     // Элемент найден
            }

            // Сужаем диапазон поиска
            if (arr[mid] < target) {
                left = mid + 1;                                 // Ищем в правой половине
            } else {
                right = mid - 1;                                // Ищем в левой половине
            }
        }

        return -1; // Элемент не найден
    }
}
