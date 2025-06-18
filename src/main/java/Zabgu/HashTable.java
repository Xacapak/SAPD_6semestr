package Zabgu;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Function;

/**
 * Шаблонный класс хеш-таблицы с разрешением коллизий методом цепочек
 * @param <K> тип ключа
 * @param <V> тип значения
 */
class HashTable<K, V> {
    private LinkedList<Entry<K, V>>[] table;
    private Function<K, Integer> hashFunction;
    private int size;

    /**
     * Конструктор с заданием начальной емкости и хеш-функции
     * @param capacity начальная емкость таблицы
     * @param hashFunction хеш-функция
     */
    @SuppressWarnings("unchecked")
    public HashTable(int capacity, Function<K, Integer> hashFunction) {
        this.table = new LinkedList[capacity];
        this.hashFunction = hashFunction;
        this.size = 0;
    }

    /**
     * Вставка пары ключ-значение в таблицу
     * @param key ключ
     * @param value значение
     */
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Ключ не может быть null");
        }

        int index = getIndex(key);
        if (table[index] == null) {
            table[index] = new LinkedList<>();
        }

        // Проверяем, есть ли уже такой ключ в цепочке
        for (Entry<K, V> entry : table[index]) {
            if (entry.key.equals(key)) {
                entry.value = value; // Обновляем значение
                return;
            }
        }

        // Если ключа не было, добавляем новую запись
        table[index].add(new Entry<>(key, value));
        size++;
    }

    /**
     * Получение значения по ключу
     * @param key ключ
     * @return значение или null, если ключ не найден
     */
    public V get(K key) {
        if (key == null) {
            return null;
        }

        int index = getIndex(key);
        if (table[index] == null) {
            return null;
        }

        // Ищем ключ в цепочке
        for (Entry<K, V> entry : table[index]) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }

        return null;
    }

    /**
     * Удаление элемента по ключу
     * @param key ключ
     * @return удаленное значение или null, если ключ не найден
     */
    public V remove(K key) {
        if (key == null) {
            return null;
        }

        int index = getIndex(key);
        if (table[index] == null) {
            return null;
        }

        // Ищем ключ в цепочке для удаления
        Iterator<Entry<K, V>> iterator = table[index].iterator();
        while (iterator.hasNext()) {
            Entry<K, V> entry = iterator.next();
            if (entry.key.equals(key)) {
                V value = entry.value;
                iterator.remove();
                size--;
                return value;
            }
        }

        return null;
    }

    /**
     * Очистка таблицы
     */
    public void clear() {
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                table[i].clear();
            }
        }
        size = 0;
    }

    /**
     * Получение текущего размера таблицы
     * @return количество элементов в таблице
     */
    public int size() {
        return size;
    }

    /**
     * Проверка таблицы на пустоту
     * @return true, если таблица пуста
     */
    public boolean isEmpty() {
        return size == 0;
    }

    // Вспомогательный метод для вычисления индекса в массиве
    private int getIndex(K key) {
        int hash = hashFunction.apply(key);
        return (hash & 0x7FFFFFFF) % table.length; // Обеспечиваем неотрицательный индекс
    }

    // Внутренний класс для хранения пар ключ-значение
    static class Entry<K, V> {
        final K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public LinkedList<Entry<K, V>>[] getTable() {
        return table;
    }

}
