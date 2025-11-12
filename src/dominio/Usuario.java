package dominio;

import java.util.Objects;

public class Usuario implements Comparable<Usuario> {

    private String cedula;
    private String nombre;
    private Bicicleta bicicletaAlquilada;

    public Usuario(String cedula, String nombre) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.bicicletaAlquilada = null;
    }

    public Usuario(String cedula) {
        this.cedula = cedula;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public Bicicleta getBicicletaAlquilada() {
        return bicicletaAlquilada;
    }

    public void setBicicletaAlquilada(Bicicleta bici) {
        this.bicicletaAlquilada = bici;
    }

    public boolean tieneBicicleta() {
        return this.bicicletaAlquilada != null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Usuario other = (Usuario) obj;
        return Objects.equals(this.cedula, other.cedula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.cedula);
    }

    @Override
    public String toString() {
        return this.getNombre() + "#" + this.getCedula();
    }

    @Override
    public int compareTo(Usuario o) {
        return this.getNombre().compareToIgnoreCase(o.getNombre());
    }
}
