package tads;

public interface IListaOrdenada<T> {
    boolean esVacia();
    void vaciar();
    int cantElementos();
    boolean existeElemento(T n);
    T obtenerElementoDePos(int pos);
    void mostrar();
    void agregarOrdenado(T n);
    void borrarElemento(T n);
    T obtenerElemento(T n);
}
