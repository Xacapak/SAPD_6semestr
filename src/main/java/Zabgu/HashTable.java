package Zabgu;

import java.util.Iterator;
import java.util.function.Function;

/**
 * Реализация хеш-таблицы с методом открытой адресации (линейное пробирование).
 * @param <T> тип хранимых данных
 */
public class HashTable<T> implements Iterable<DataItem<T>> {
    private DataItem<T>[] hashArray;                // Массив для хранения элементов
    private int arraySize;                          // Размер массива
    private DataItem<T> nonItem;                    // Специальный маркер для удаленных элементов
    private Function<T, Integer> hashFunction;      // Функция для вычисления хеша

    /**
     * Создает новую хеш-таблицу.
     * @param size размер таблицы
     * @param hashFunction функция для вычисления хеш-кода
     */
    @SuppressWarnings("unchecked")
    public HashTable(int size, Function<T, Integer> hashFunction){
        this.arraySize = size;
        this.hashArray = new DataItem[this.arraySize];
        this.nonItem = new DataItem<>(null);
        this.hashFunction = hashFunction;
    }

    /**
     * Выводит содержимое таблицы в консоль.
     * Элементы отображаются как [индекс]: значение,
     * удаленные элементы - как [индекс]: **
     */
    public void displayTable(){
        System.out.println("Таблица:");
        for (int i = 0; i < arraySize; i++) {
            if (hashArray[i] != null && hashArray[i] != nonItem){
                System.out.println("[" + i + "]: " + hashArray[i].getKey());
            }else {
                System.out.println("[" + i + "]: **");
            }
        }
        System.out.println();
    }

    /**
     * Поиск элемента по ключу.
     * @param key искомый ключ
     * @return найденный элемент или null
     */
    public DataItem<T> find(T key){
        int hashVal = hashFunc(key);
        int startIndex = hashVal;

        while (hashArray[hashVal] != null){
            if (hashArray[hashVal] != nonItem && hashArray[hashVal].getKey().equals(key)){
                return hashArray[hashVal];
            }
            hashVal = (hashVal + 1) % arraySize;

            if (hashVal == startIndex){
                break;
            }
        }
        return null;
    }

    /**
     * Вставка нового элемента в таблицу.
     * @param item элемент для вставки
     */
    public void insert(DataItem<T> item){
        T key = item.getKey();
        int hashVal = hashFunc(key);

        while (hashArray[hashVal] != null && hashArray[hashVal] != nonItem){
            hashVal = (hashVal + 1) % arraySize;
        }
        hashArray[hashVal] = item;
    }

    /**
     * Удаление элемента по ключу.
     * @param key ключ элемента для удаления
     * @return удаленный элемент или null
     */
    public DataItem<T> delete(T key){
        int hashVal = hashFunc(key);
        int startIndex = hashVal;

        while (hashArray[hashVal] != null){
            if (hashArray[hashVal] != nonItem && hashArray[hashVal].getKey().equals(key)){
                DataItem<T> temp = hashArray[hashVal];
                hashArray[hashVal] = nonItem;
                return temp;
            }
            hashVal = (hashVal + 1) % arraySize;

            if (hashVal == startIndex){
                break;
            }
        }
        return null;
    }

    /**
     * Очистка таблицы (все элементы становятся null).
     */
    public void clear(){
        for (int i = 0; i < arraySize; i++) {
            hashArray[i] = null;
        }
    }

    /**
     * Возвращает количество элементов в таблице.
     * @return количество действительных элементов
     */
    public int tableSize(){
        int count = 0;
        for (int i = 0; i < arraySize; i++) {
            if (hashArray[i] != null && hashArray[i] != nonItem) {
                count++;
            }
        }
        return count;
    }

    /**
     * Вычисляет хеш для ключа.
     * @param key ключ для хеширования
     * @return индекс в массиве
     */
    private int hashFunc(T key){
        return hashFunction.apply(key) % arraySize;
    }

    /**
     * Возвращает итератор для таблицы.
     * @return итератор элементов таблицы
     */
    @Override
    public Iterator<DataItem<T>> iterator() {
        return new HashTableIterator<>(this);
    }

    /**
     * Возвращает массив элементов таблицы.
     * @return массив элементов
     */
    public DataItem<T>[] getHashArray() {
        return hashArray;
    }

    /**
     * Возвращает специальный маркер для удаленных элементов.
     * @return маркер удаленных элементов
     */
    public DataItem<T> getNonItem() {
        return nonItem;
    }

    /**
     * Возвращает размер таблицы.
     * @return размер внутреннего массива
     */
    public int getArraySize() {
        return arraySize;
    }

}