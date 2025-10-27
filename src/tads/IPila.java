package tads;

public interface IPila<T> {
    boolean esVacia();
    void push(T n);
    T pop();
    T top();
    void vaciar();
    void mostrar();
    int size();
}
