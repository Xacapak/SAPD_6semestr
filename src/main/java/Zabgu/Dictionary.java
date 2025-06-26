package Zabgu;

import java.util.*;

public class Dictionary<K, V> implements Iterable<Dictionary.Entry<K, V>> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;
    private Entry<K, V>[] table;
    private int size;

    @SuppressWarnings("unchecked")
    public Dictionary() {
        table = (Entry<K, V>[]) new Entry[DEFAULT_CAPACITY];
        size = 0;
    }

    public static class Entry<K, V> {
        final K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }

    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Ключ не может быть нулевым");
        }

        int hash = hash(key);
        int index = indexFor(hash, table.length);

        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (e.key.equals(key)) {
                e.value = value;
                return;
            }
        }

        addEntry(key, value, index);
    }

    public V get(K key) {
        if (key == null) return null;

        int hash = hash(key);
        int index = indexFor(hash, table.length);

        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (e.key.equals(key)) {
                return e.value;
            }
        }

        return null;
    }

    public V remove(K key) {
        if (key == null) return null;

        int hash = hash(key);
        int index = indexFor(hash, table.length);
        Entry<K, V> prev = table[index];
        Entry<K, V> e = prev;

        while (e != null) {
            Entry<K, V> next = e.next;
            if (e.key.equals(key)) {
                // Удаление из цепочки
                if (prev == e) {
                    table[index] = next;
                } else {
                    prev.next = next;
                }
                size--;
                return e.value;
            }
            prev = e;
            e = next;
        }

        return null;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

     @SuppressWarnings("unchecked")
    public void clear() {
        table = (Entry<K, V>[]) new Entry[DEFAULT_CAPACITY];
        size = 0;
    }

    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (int i = 0; i < table.length; i++) {
            Entry<K, V> entry = table[i];
            while (entry != null) {
                keys.add(entry.key);
                entry = entry.next;
            }
        }
        return keys;
    }

    public Collection<V> values() {
        List<V> values = new ArrayList<>();
        for (int i = 0; i < table.length; i++) {
            Entry<K, V> entry = table[i];
            while (entry != null) {
                values.add(entry.value);
                entry = entry.next;
            }
        }
        return values;
    }

    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entries = new HashSet<>();
        for (Entry<K, V> entry : this) {
            entries.add(entry);
        }
        return entries;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new DictionaryIterator();
    }

    private class DictionaryIterator implements Iterator<Entry<K, V>> {
        private int currentIndex = 0;
        private Entry<K, V> currentEntry = null;

        public DictionaryIterator() {
            while (currentIndex < table.length && table[currentIndex] == null) {
                currentIndex++;
            }
            if (currentIndex < table.length) {
                currentEntry = table[currentIndex];
            }
        }

        @Override
        public boolean hasNext() {
            return currentEntry != null;
        }

        @Override
        public Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Entry<K, V> result = currentEntry;

            currentEntry = currentEntry.next;
            if (currentEntry == null) {
                currentIndex++;
                while (currentIndex < table.length && table[currentIndex] == null) {
                    currentIndex++;
                }
                if (currentIndex < table.length) {
                    currentEntry = table[currentIndex];
                }
            }

            return result;
        }
    }

    private int hash(K key) {
        return key.hashCode();
    }

    private int indexFor(int hash, int length) {
        return hash & (length - 1);
    }

    private void addEntry(K key, V value, int index) {
        Entry<K, V> e = table[index];
        table[index] = new Entry<>(key, value, e);
        if (size++ >= table.length * LOAD_FACTOR) {
            resize(2 * table.length);
        }
    }

    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        Entry<K, V>[] newTable = (Entry<K, V>[]) new Entry[newCapacity];
        transfer(newTable);
        table = newTable;
    }

    private void transfer(Entry<K, V>[] newTable) {
        for (Entry<K, V> e : table) {
            while (e != null) {
                Entry<K, V> next = e.next;
                int index = indexFor(hash(e.key), newTable.length);
                e.next = newTable[index];
                newTable[index] = e;
                e = next;
            }
        }
    }
}