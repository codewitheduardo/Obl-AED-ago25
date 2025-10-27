package tads;

public class NodoOrdenado<T extends Comparable> {
    private T dato;
    private NodoOrdenado siguiente;

    public NodoOrdenado(T dato) {
        this.dato = dato;
        siguiente = null;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public void setSiguiente(NodoOrdenado siguiente) {
        this.siguiente = siguiente;
    }

    public T getDato() {
        return this.dato;
    }

    public NodoOrdenado getSiguiente() {
        return this.siguiente;
    }
}
