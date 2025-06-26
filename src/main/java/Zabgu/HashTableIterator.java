package Zabgu;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Итератор для хеш-таблицы, реализующий обход только действительных элементов.
 * Пропускает пустые ячейки (null) и удаленные элементы (nonItem).
 *
 * @param <T> тип данных в хеш-таблице
 */
public class HashTableIterator<T> implements Iterator<DataItem<T>> {
    private final HashTable<T> hashTable;           // Ссылка на хеш-таблицу для итерации
    private int currentIndex = 0;                   // Текущий индекс в массиве хеш-таблицы

    /**
     * Создает новый итератор для указанной хеш-таблицы
     * @param hashTable хеш-таблица для итерации
     */
    public HashTableIterator(HashTable<T> hashTable) {
        this.hashTable = hashTable;
    }

    /**
     * Проверяет наличие следующего действительного элемента
     * @return true если есть следующий элемент, false если достигнут конец таблицы
     */
    @Override
    public boolean hasNext() {
        // Пропускаем пустые ячейки и deleted-элементы
        while (currentIndex < hashTable.getArraySize() &&
                (hashTable.getHashArray()[currentIndex] == null ||
                        hashTable.getHashArray()[currentIndex] == hashTable.getNonItem())) {
            currentIndex++;
        }
        return currentIndex < hashTable.getArraySize();
    }

    /**
     * Возвращает следующий действительный элемент таблицы
     * @return следующий элемент DataItem<T>
     * @throws NoSuchElementException если достигнут конец таблицы
     */
    @Override
    public DataItem<T> next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Нет больше элементов в хеш-таблице");
        }
        return hashTable.getHashArray()[currentIndex++];
    }
}