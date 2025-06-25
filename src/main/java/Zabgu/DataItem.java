package Zabgu;

public class DataItem<T> {
    public T data;

    public DataItem(T data){
        this.data = data;
    }

    public T getKey(){
        return data;
    }
}
