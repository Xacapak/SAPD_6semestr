package Zabgu;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashTableIterator<T> implements Iterator<DataItem<T>> {
    private final HashTable<T> hashTable;
    private int currentIndex = 0;

    public HashTableIterator(HashTable<T> hashTable) {
        this.hashTable = hashTable;
    }

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

    @Override
    public DataItem<T> next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return hashTable.getHashArray()[currentIndex++];
    }
}