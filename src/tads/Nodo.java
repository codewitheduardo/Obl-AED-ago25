package tads;

public class Nodo<T> {
    private T dato;
    private Nodo siguiente;
    
    public Nodo(T dato) {
        this.dato = dato;
        siguiente = null;
    }
    
    public void setDato(T dato) {
        this.dato = dato;
    }
    
    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }
    
    public T getDato() {
        return this.dato;
    }
    
    public Nodo getSiguiente() {
        return this.siguiente;
    }
}
