package Zabgu;

import java.util.Iterator;
import java.util.function.Function;

public class HashTable<T> implements Iterable<DataItem<T>> {
    private DataItem<T>[] hashArray;
    private int arraySize;
    private DataItem<T> nonItem;
    private Function<T, Integer> hashFunction;

    @SuppressWarnings("unchecked")
    public HashTable(int size, Function<T, Integer> hashFunction){
        this.arraySize = size;
        this.hashArray = new DataItem[this.arraySize];
        this.nonItem = new DataItem<>(null);
        this.hashFunction = hashFunction;
    }

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

    public void insert(DataItem<T> item){
        T key = item.getKey();
        int hashVal = hashFunc(key);

        while (hashArray[hashVal] != null && hashArray[hashVal] != nonItem){
            hashVal = (hashVal + 1) % arraySize;
        }
        hashArray[hashVal] = item;
    }

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

    public void clear(){
        for (int i = 0; i < arraySize; i++) {
            hashArray[i] = null;
        }
    }

    public int tableSize(){
        int count = 0;
        for (int i = 0; i < arraySize; i++) {
            if (hashArray[i] != null && hashArray[i] != nonItem) {
                count++;
            }
        }
        return count;
    }

    private int hashFunc(T key){
        return hashFunction.apply(key) % arraySize;
    }

    @Override
    public Iterator<DataItem<T>> iterator() {
        return new HashTableIterator<>(this);
    }

    public DataItem<T>[] getHashArray() {
        return hashArray;
    }

    public DataItem<T> getNonItem() {
        return nonItem;
    }

    public int getArraySize() {
        return arraySize;
    }

}