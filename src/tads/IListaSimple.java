package tads;

public interface IListaSimple<T> {

    boolean esVacia();
    void agregarInicio(T n);
    void agregarFinal(T n);
    void borrarInicio();
    void borrarFin();
    void vaciar();
    void mostrar();
    int cantElementos();
    boolean existeElemento(T n);
    T obtenerElementoDePos(int pos);
    T obtenerElemento(T n);
    void borrarElemento(T n);
}
