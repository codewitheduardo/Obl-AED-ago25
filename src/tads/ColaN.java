package tads;

public class ColaN<T> implements ICola<T> {

    private Nodo inicio;
    private Nodo fin;
    private int cantElementos;

    public ColaN() {
        inicio = null;
        fin = null;
        cantElementos = 0;
    }

    @Override
    public boolean esVacia() {
        return inicio == null;
    }

    @Override
    public void enqueue(T n) {
        Nodo nuevo = new Nodo(n);
        if (esVacia()) {
            inicio = nuevo;
            fin = nuevo;
        } else {
            fin.setSiguiente(nuevo);
            fin = nuevo;
        }
        cantElementos++;
    }

    @Override
    public T dequeue() {
        T dato = front();
        if (dato != null) {
            inicio = inicio.getSiguiente();
            if (inicio == null) {
                fin = null;
            }
            cantElementos--;
        }
        return dato;
    }

    @Override
    public T front() {
        if (!esVacia()) {
            Nodo<T> dato = inicio;
            return dato.getDato();
        }
        return null;
    }

    @Override
    public void vaciar() {
        inicio = null;
        fin = null;
        cantElementos = 0;
    }

    @Override
    public void mostrar() {
        Nodo aux = inicio;
        while (aux != null) {
            System.out.print(aux.getDato() + " ");
            aux = aux.getSiguiente();
        }
        System.out.println();
    }

    @Override
    public int size() {
        return cantElementos;
    }
}
