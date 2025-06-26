package Zabgu;

/**
 * Класс-контейнер для хранения данных в хеш-таблице.
 * @param <T> тип хранимых данных
 */
public class DataItem<T> {
    public T data;

    /**
     * Создает новый элемент данных.
     * @param data данные для хранения
     */
    public DataItem(T data){
        this.data = data;
    }

    /**
     * Возвращает ключ элемента (сами данные).
     * @return данные элемента
     */
    public T getKey(){
        return data;
    }
}
