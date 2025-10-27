package tads;

public class PilaN<T> implements IPila<T> {

    private Nodo tope;
    private int cantElementos;

    public PilaN() {
        tope = null;
        cantElementos = 0;
    }

    @Override
    public boolean esVacia() {
        return tope == null;
    }

    @Override
    public void push(T n) {
        Nodo nuevo = new Nodo(n);
        nuevo.setSiguiente(tope);
        tope = nuevo;
        cantElementos++;
    }

    @Override
    public T pop() {
        T dato = top();
        if (dato != null) {
            tope = tope.getSiguiente();
            cantElementos--;
        }
        return dato;
    }

    @Override
    public T top() {
        if (!esVacia()) {
            Nodo<T> aux = tope;
            return aux.getDato();
        }
        return null;
    }

    @Override
    public void vaciar() {
        tope = null;
        cantElementos = 0;
    }

    @Override
    public void mostrar() {
        Nodo<T> aux = tope;
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
