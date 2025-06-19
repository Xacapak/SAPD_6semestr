package Zabgu;

import java.util.*;

/**
 * Реализация словаря (ассоциативного массива) с использованием хеш-таблицы.
 * Поддерживает основные операции: добавление, получение, удаление элементов.
 *
 * @param <K> тип ключей
 * @param <V> тип значений
 */
public class Dictionary<K, V> implements Iterable<Dictionary.Entry<K, V>> {
    // Начальная емкость таблицы по умолчанию
    private static final int DEFAULT_CAPACITY = 16;
    // Коэффициент заполнения для расширения таблицы
    private static final float LOAD_FACTOR = 0.75f;

    // Массив бакетов (корзин) для хранения данных
    private Entry<K, V>[] table;
    // Количество элементов в словаре
    private int size;

    /**
     * Конструктор по умолчанию. Инициализирует таблицу начальной емкостью.
     */
    @SuppressWarnings("unchecked")
    public Dictionary() {
        table = (Entry<K, V>[]) new Entry[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * Внутренний класс для хранения пар ключ-значение.
     */
    public static class Entry<K, V> {
        final K key;
        V value;
        Entry<K, V> next;

        /**
         * Конструктор записи.
         * @param key ключ
         * @param value значение
         * @param next следующий элемент в цепочке коллизий
         */
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

    /**
     * Добавляет или обновляет пару ключ-значение.
     * @param key ключ (не может быть null)
     * @param value значение
     * @throws IllegalArgumentException если ключ null
     */
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Ключ не может быть нулевым");
        }

        int hash = hash(key);
        int index = indexFor(hash, table.length);

        // Поиск существующего ключа
        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (e.key.equals(key)) {
                e.value = value;
                return;
            }
        }

        // Добавление новой записи
        addEntry(key, value, index);
    }

    /**
     * Получает значение по ключу.
     * @param key ключ для поиска
     * @return значение или null, если ключ не найден
     */
    public V get(K key) {
        if (key == null) return null;

        int hash = hash(key);
        int index = indexFor(hash, table.length);

        // Поиск в цепочке коллизий
        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (e.key.equals(key)) {
                return e.value;
            }
        }

        return null;
    }

    /**
     * Удаляет пару ключ-значение.
     * @param key ключ для удаления
     * @return удаленное значение или null, если ключ не найден
     */
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

    /**
     * Проверяет наличие ключа в словаре.
     * @param key ключ для проверки
     * @return true если ключ существует, иначе false
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Возвращает количество элементов в словаре.
     * @return размер словаря
     */
    public int size() {
        return size;
    }

    /**
     * Проверяет, пуст ли словарь.
     * @return true если словарь пуст, иначе false
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Очищает словарь, восстанавливая начальную емкость.
     */
    @SuppressWarnings("unchecked")
    public void clear() {
        table = (Entry<K, V>[]) new Entry[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * Возвращает множество всех ключей.
     * @return множество ключей
     */
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

    /**
     * Возвращает коллекцию всех значений.
     * @return коллекция значений
     */
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

    /**
     * Возвращает множество всех записей.
     * @return множество записей
     */
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entries = new HashSet<>();
        for (Entry<K, V> entry : this) {
            entries.add(entry);
        }
        return entries;
    }

    /**
     * Возвращает итератор для последовательного обхода записей.
     * @return итератор записей
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new DictionaryIterator();
    }

    /**
     * Итератор для обхода всех элементов словаря.
     */
    private class DictionaryIterator implements Iterator<Entry<K, V>> {
        private int currentIndex = 0;               // Текущий индекс в таблице
        private Entry<K, V> currentEntry = null;    // Текущая запись

        public DictionaryIterator() {
            // Находим первый непустой бакет
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

            // Переходим к следующему элементу
            currentEntry = currentEntry.next;
            if (currentEntry == null) {
                // Переходим к следующему бакету
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

    // Вспомогательные методы

    /**
     * Вычисляет хеш-код ключа.
     * @param key ключ
     * @return хеш-код
     */
    private int hash(K key) {
        return key.hashCode();
    }

    /**
     * Вычисляет индекс в таблице по хеш-коду.
     * @param hash хеш-код ключа
     * @param length размер таблицы
     * @return индекс в таблице
     */
    private int indexFor(int hash, int length) {
        return hash & (length - 1);
    }

    /**
     * Добавляет новую запись в таблицу.
     * @param key ключ
     * @param value значение
     * @param index индекс в таблице
     */
    private void addEntry(K key, V value, int index) {
        Entry<K, V> e = table[index];
        table[index] = new Entry<>(key, value, e);
        if (size++ >= table.length * LOAD_FACTOR) {
            resize(2 * table.length);
        }
    }

    /**
     * Увеличивает размер таблицы и перераспределяет элементы.
     * @param newCapacity новый размер таблицы
     */
    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        Entry<K, V>[] newTable = (Entry<K, V>[]) new Entry[newCapacity];
        transfer(newTable);
        table = newTable;
    }

    /**
     * Переносит элементы в новую таблицу.
     * @param newTable новая таблица
     */
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