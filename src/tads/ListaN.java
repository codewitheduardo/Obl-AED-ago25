package tads;

public class ListaN<T> implements IListaSimple<T> {

    private Nodo inicio;
    private Nodo fin;
    private int cantElementos;

    public ListaN() {
        inicio = null;
        fin = null;
        cantElementos = 0;
    }

    public Nodo getInicio() {
        return inicio;
    }

    public Nodo getFin() {
        return fin;
    }

    @Override
    public boolean esVacia() {
        return inicio == null;
    }

    @Override
    public void agregarInicio(T n) {
        Nodo nuevo = new Nodo(n);

        if (esVacia()) {
            fin = nuevo;
        }
        nuevo.setSiguiente(inicio);
        inicio = nuevo;
        cantElementos++;
    }

    @Override
    public void agregarFinal(T n) {
        if (esVacia()) {
            agregarInicio(n);
        } else {
            Nodo nuevo = new Nodo(n);
            fin.setSiguiente(nuevo);
            fin = nuevo;
            cantElementos++;
        }
    }

    @Override
    public void borrarInicio() {
        if (!esVacia()) {
            if (inicio == fin) {
                vaciar();
            } else {
                Nodo aBorrar = inicio;
                inicio = inicio.getSiguiente();
                aBorrar.setSiguiente(null);
                cantElementos--;
            }
        }
    }

    @Override
    public void borrarFin() {
        if (!esVacia()) {
            if (inicio.getSiguiente() == null) {
                vaciar();
            } else {
                Nodo aux = inicio;
                while (aux.getSiguiente().getSiguiente() != null) {
                    aux = aux.getSiguiente();
                }
                aux.setSiguiente(null);
                fin = aux;
                cantElementos--;
            }
        }
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
            System.out.println(aux.getDato() + " ");
            aux = aux.getSiguiente();
        }
    }

    @Override
    public int cantElementos() {
        return cantElementos;
    }

    @Override
    public boolean existeElemento(T n) {
        boolean existe = false;
        Nodo aux = inicio;
        while (aux != null && !existe) {
            if (aux.getDato().equals(n)) {
                existe = true;
            }
            aux = aux.getSiguiente();
        }
        return existe;
    }

    /*
    pre: pos >= 0 y < largo de la lista
     */
    @Override
    public T obtenerElementoDePos(int pos) {
        int posActual = 0;
        Nodo<T> aux = inicio;
        while (pos != posActual) {
            aux = aux.getSiguiente();
            posActual++;
        }
        return aux.getDato();
    }

    @Override
    public T obtenerElemento(T n) {
        Nodo<T> aux = inicio;
        while (aux != null) {
            if (aux.getDato().equals(n)) {
                return aux.getDato();
            }
            aux = aux.getSiguiente();
        }
        return null;
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
                Nodo<T> aux = inicio;
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
}
