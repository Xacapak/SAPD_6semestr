package Zabgu;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class HashTableIterator<K, V> implements Iterator<HashTable.Entry<K, V>> {
    private final LinkedList<HashTable.Entry<K, V>>[] table;
    private int currentBucket = 0;
    private Iterator<HashTable.Entry<K, V>> currentIterator;

    public HashTableIterator(HashTable<K, V> hashTable) {
        this.table = hashTable.getTable();
        findNextNonEmptyBucket();
    }

    @Override
    public boolean hasNext() {
        if (currentIterator != null && currentIterator.hasNext()) {
            return true;
        }
        return findNextNonEmptyBucket();
    }

    @Override
    public HashTable.Entry<K, V> next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return currentIterator.next();
    }

    private boolean findNextNonEmptyBucket() {
        while (currentBucket < table.length) {
            if (table[currentBucket] != null && !table[currentBucket].isEmpty()) {
                currentIterator = table[currentBucket].iterator();
                currentBucket++;
                return true;
            }
            currentBucket++;
        }
        return false;
    }
}