package tads;

public class ListaOrdenadaN<T extends Comparable> implements IListaOrdenada<T> {

    private NodoOrdenado inicio;
    private NodoOrdenado fin;
    private int cantElementos;

    public ListaOrdenadaN() {
        inicio = null;
        fin = null;
        cantElementos = 0;
    }

    public NodoOrdenado getInicio() {
        return inicio;
    }

    public NodoOrdenado getFinal() {
        return inicio;
    }

    @Override
    public boolean esVacia() {
        return inicio == null;
    }

    @Override
    public void vaciar() {
        inicio = null;
        fin = null;
        cantElementos = 0;
    }

    @Override
    public int cantElementos() {
        return cantElementos;
    }

    @Override
    public boolean existeElemento(T n) {
        NodoOrdenado aux = inicio;
        while (aux != null) {
            if (aux.getDato().equals(n)) {
                return true;
            }
            aux = aux.getSiguiente();
        }
        return false;
    }

    /*
    pre: pos >= 0 y < largo de la lista
     */
    @Override
    public T obtenerElementoDePos(int pos) {
        NodoOrdenado<T> aux = inicio;
        int posActual = 0;
        while (pos != posActual) {
            aux = aux.getSiguiente();
            posActual++;
        }
        return aux.getDato();
    }

    @Override
    public void mostrar() {
        NodoOrdenado aux = inicio;
        while (aux != null) {
            System.out.println(aux.getDato() + " ");
            aux = aux.getSiguiente();
        }
    }

    @Override
    public void agregarOrdenado(T n) {
        NodoOrdenado nuevo = new NodoOrdenado(n);

        if (esVacia() || inicio.getDato().compareTo(n) >= 0) {
            nuevo.setSiguiente(inicio);
            inicio = nuevo;
            if (fin == null) {
                fin = nuevo;
            }
        } else if (fin.getDato().compareTo(n) <= 0) {
            fin.setSiguiente(nuevo);
            fin = nuevo;
        } else {
            NodoOrdenado aux = inicio;
            while (aux.getSiguiente() != null
                    && aux.getSiguiente().getDato().compareTo(n) < 0) {
                aux = aux.getSiguiente();
            }
            nuevo.setSiguiente(aux.getSiguiente());
            aux.setSiguiente(nuevo);
        }
        cantElementos++;
    }

    @Override
    public void borrarElemento(T n) {
        if (!esVacia()) {
            boolean borrado = false;

            if (inicio.getDato().equals(n)) {
                inicio = inicio.getSiguiente();
                borrado = true;

                if (inicio == null) {
                    fin = null;
                }
            } else {
                NodoOrdenado<T> aux = inicio;
                while (aux.getSiguiente() != null && !borrado) {
                    if (aux.getSiguiente().getDato().equals(n)) {
                        aux.setSiguiente(aux.getSiguiente().getSiguiente());
                        borrado = true;

                        if (aux.getSiguiente() == null) {
                            fin = aux;
                        }
                    } else {
                        aux = aux.getSiguiente();
                    }
                }
            }
            if (borrado) {
                cantElementos--;
            }
        }
    }

    @Override
    public T obtenerElemento(T n) {
        NodoOrdenado<T> aux = inicio;
        while (aux != null) {
            if (aux.getDato().equals(n)) {
                return aux.getDato();
            }
            aux = aux.getSiguiente();
        }
        return null;
    }
}
