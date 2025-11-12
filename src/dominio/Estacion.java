package dominio;

import java.util.Objects;
import tads.ColaN;
import tads.ListaOrdenadaN;

public class Estacion {

    private String nombre;
    private String barrio;
    private int capacidad;
    private ListaOrdenadaN<Bicicleta> bicicletasAncladas;
    private ColaN<Usuario> usuariosEnEsperaAnclaje;
    private ColaN<Usuario> usuariosEnEsperaAlquiler;

    public Estacion(String nombre, String barrio, int capacidad) {
        this.nombre = nombre;
        this.barrio = barrio;
        this.capacidad = capacidad;
        this.bicicletasAncladas = new ListaOrdenadaN();
        this.usuariosEnEsperaAnclaje = new ColaN();
        this.usuariosEnEsperaAlquiler = new ColaN();
    }

    public Estacion(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public String getBarrio() {
        return barrio;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public ListaOrdenadaN<Bicicleta> getBicicletasAncladas() {
        return bicicletasAncladas;
    }

    public ColaN<Usuario> getUsuariosEnEsperaAlquiler() {
        return usuariosEnEsperaAlquiler;
    }

    public ColaN<Usuario> getUsuariosEnEsperaAnclaje() {
        return usuariosEnEsperaAnclaje;
    }

    public boolean hayLugar() {
        return bicicletasAncladas.cantElementos() < capacidad;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Estacion other = (Estacion) obj;
        return Objects.equals(this.nombre, other.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.nombre);
    }
}
