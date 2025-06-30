package Zabgu;

import java.util.*;

/**
 * Реализация хеш-таблицы (словаря) с методом цепочек для разрешения коллизий.
 * Поддерживает основные операции: добавление, получение, удаление элементов,
 * а также итерацию по элементам. Автоматически расширяется при достижении
 * предельного коэффициента загрузки.
 *
 * @param <K> тип ключей, должен правильно реализовывать hashCode() и equals()
 * @param <V> тип значений
 */
public class Dictionary<K, V> implements Iterable<Dictionary.Entry<K, V>> {
    private static final int DEFAULT_CAPACITY = 16;             // Начальная емкость таблицы по умолчанию
    private static final float LOAD_FACTOR = 0.75f;             // Коэффициент загрузки для определения момента расширения таблицы
    private Entry<K, V>[] table;                                // Основная хеш-таблица (массив цепочек)
    private int size;                                           // Текущее количество элементов в таблице

    /**
     * Конструктор создает пустой словарь с начальной емкостью по умолчанию.
     */
    @SuppressWarnings("unchecked")
    public Dictionary() {
        table = (Entry<K, V>[]) new Entry[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * Внутренний класс для хранения элементов словаря.
     * Реализует односвязный список для разрешения коллизий.
     */
    public static class Entry<K, V> {
        final K key;                            // Ключ элемента (не может изменяться)
        V value;                                // Значение элемента
        Entry<K, V> next;                       // Ссылка на следующий элемент в цепочке

        /**
         * Создание новой записи в словаре.
         * @param key ключ элемента
         * @param value значение элемента
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

        /**
         * Устанавливает новое значение для элемента.
         * @param value новое значение
         */
        public void setValue(V value) {
            this.value = value;
        }
    }

/////////////////////////////////////---Метод добавления---/////////////////////////////////////
    /**
     * Добавляет или обновляет пару ключ-значение в словаре.
     * @param key ключ для добавления/обновления
     * @param value соответствующее значение
     * @throws IllegalArgumentException если ключ равен null
     */
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Ключ не может быть нулевым");
        }

        // Вычисление хеша и индекса в таблице
        int hash = hash(key);
        int index = indexFor(hash, table.length);

        // Поиск существующего ключа в цепочке
        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (e.key.equals(key)) {
                e.value = value;
                return;
            }
        }

        // Добавление нового элемента в начало цепочки
        addEntry(key, value, index);
    }

    /////////////////////////////////////---Метод поиска---/////////////////////////////////////
    /**
     * Возвращает значение по ключу.
     * @param key ключ для поиска
     * @return найденное значение или null, если ключ отсутствует
     */
    public V get(K key) {
        if (key == null) return null;

        int hash = hash(key);
        int index = indexFor(hash, table.length);

        // Поиск ключа в соответствующей цепочке
        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (e.key.equals(key)) {
                return e.value;
            }
        }

        return null;
    }

    /////////////////////////////////////---Метод удаления---/////////////////////////////////////
    /**
     * Удаляет элемент по ключу.
     * @param key ключ элемента для удаления
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
                    table[index] = next;        // Удаление первого элемента
                } else {
                    prev.next = next;           // Удаление из середины/конца
                }
                size--;
                return e.value;
            }
            prev = e;
            e = next;
        }

        return null;
    }

    /////////////////////////////////////---Метод наличия ключа(поиск)---/////////////////////////////////////

    /**
     * Проверяет наличие ключа в словаре.
     * @param key ключ для проверки
     * @return true если ключ присутствует, иначе false
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /////////////////////////////////////---Метод количество элементов---/////////////////////////////////////
    /**
     * Возвращает текущее количество элементов в словаре.
     * @return количество элементов
     */
    public int size() {
        return size;
    }

    /////////////////////////////////////---Метод пустого словаря---/////////////////////////////////////
    /**
     * Проверяет, пуст ли словарь.
     * @return true если словарь пуст, иначе false
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /////////////////////////////////////---Метод очистки---/////////////////////////////////////
    /**
     * Очищает словарь, восстанавливая начальную емкость.
     */
    @SuppressWarnings("unchecked")
    public void clear() {
        table = (Entry<K, V>[]) new Entry[DEFAULT_CAPACITY];
        size = 0;
    }

    /////////////////////////////////////---Метод возврат ключей---/////////////////////////////////////
    /**
     * Возвращает множество всех ключей в словаре.
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

    /////////////////////////////////////---Метод возврат значений---/////////////////////////////////////
    /**
     * Возвращает коллекцию всех значений в словаре.
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
     * Возвращает множество всех элементов словаря.
     * @return множество элементов (пар ключ-значение)
     */
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entries = new HashSet<>();
        for (Entry<K, V> entry : this) {
            entries.add(entry);
        }
        return entries;
    }

    /**
     * Возвращает итератор по элементам словаря.
     * @return итератор элементов
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new DictionaryIterator();
    }

    /**
     * Внутренний класс итератора для обхода элементов словаря.
     * Реализует обход всех цепочек хеш-таблицы.
     */
    private class DictionaryIterator implements Iterator<Entry<K, V>> {
        private int currentIndex = 0;
        private Entry<K, V> currentEntry = null;

        /**
         * Конструктор инициализирует итератор на первый непустой элемент.
         */
        public DictionaryIterator() {
            while (currentIndex < table.length && table[currentIndex] == null) {
                currentIndex++;
            }
            // Переход к следующей цепочке при необходимости
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

    /**
     * Вычисляет хеш-код ключа.
     * @param key ключ для хеширования
     * @return хеш-код ключа
     */
    private int hash(K key) {
        return key.hashCode();
    }

    /**
     * Определяет индекс в таблице по хеш-коду.
     * @param hash хеш-код ключа
     * @param length размер таблицы
     * @return индекс в таблице
     */
    private int indexFor(int hash, int length) {
        return hash & (length - 1);
    }

    /**
     * Добавляет новую запись в таблицу.
     * @param key ключ элемента
     * @param value значение элемента
     * @param index индекс в таблице
     */
    private void addEntry(K key, V value, int index) {
        Entry<K, V> e = table[index];
        table[index] = new Entry<>(key, value, e);
        // Проверка необходимости расширения таблицы
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
     * Переносит все элементы в новую таблицу.
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