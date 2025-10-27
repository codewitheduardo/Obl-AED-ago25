package tads;

public interface ICola<T> {
    boolean esVacia();
    void enqueue(T n);
    T dequeue();
    T front();
    void vaciar();
    void mostrar();
    int size();
}
