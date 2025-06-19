package Zabgu;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Итератор для хеш-таблицы, позволяющий последовательно перебирать все элементы.
 * Реализует интерфейс Iterator для обхода элементов хеш-таблицы.
 *
 * @param <K> тип ключа
 * @param <V> тип значения
 */
public class HashTableIterator<K, V> implements Iterator<HashTable.Entry<K, V>> {
    /**
     * Массив связных списков, представляющий внутреннюю структуру хеш-таблицы.
     * Каждый элемент массива содержит список пар ключ-значение для соответствующей корзины.
     */
    private final LinkedList<HashTable.Entry<K, V>>[] table;
    /**
     * Индекс текущей корзины (ячейки массива), которую мы обрабатываем.
     */
    private int currentBucket = 0;
    /**
     * Итератор для текущего связного списка (корзины).
     * Позволяет последовательно перебирать элементы внутри одной корзины.
     */
    private Iterator<HashTable.Entry<K, V>> currentIterator;

    /**
     * Конструктор итератора.
     *
     * @param hashTable хеш-таблица, для которой создается итератор
     */
    public HashTableIterator(HashTable<K, V> hashTable) {
        this.table = hashTable.getTable();
        findNextNonEmptyBucket();
    }

    /**
     * Проверяет наличие следующего элемента для итерации.
     *
     * @return true, если есть следующий элемент, false в противном случае
     */
    @Override
    public boolean hasNext() {
        // Если в текущем итераторе есть элементы, возвращаем true
        if (currentIterator != null && currentIterator.hasNext()) {
            return true;
        }
        // Иначе пытаемся найти следующую непустую корзину
        return findNextNonEmptyBucket();
    }

    /**
     * Возвращает следующий элемент итерации.
     *
     * @return следующую пару ключ-значение в таблице
     * @throws NoSuchElementException если больше нет элементов для итерации
     */
    @Override
    public HashTable.Entry<K, V> next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return currentIterator.next();
    }

    /**
     * Находит следующую непустую корзину в хеш-таблице.
     * Устанавливает currentIterator на итератор этой корзины.
     *
     * @return true, если непустая корзина найдена, false если достигнут конец таблицы
     */
    private boolean findNextNonEmptyBucket() {
        // Перебираем корзины, начиная с currentBucket
        while (currentBucket < table.length) {
            // Если корзина не пуста
            if (table[currentBucket] != null && !table[currentBucket].isEmpty()) {
                // Создаем итератор для этой корзины
                currentIterator = table[currentBucket].iterator();
                // Увеличиваем счетчик корзин, чтобы в следующий раз начать со следующей
                currentBucket++;
                return true;
            }
            currentBucket++;
        }
        return false;
    }
}