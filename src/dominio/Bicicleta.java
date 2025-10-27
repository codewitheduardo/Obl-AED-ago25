package dominio;

import java.util.Objects;

public class Bicicleta implements Comparable<Bicicleta> {

    private String codigo;
    private String tipo;
    private String estado;

    public Bicicleta(String codigo, String tipo) {
        this.codigo = codigo;
        this.tipo = tipo;
        this.estado = "Disponible";
    }
    
    public Bicicleta(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return this.codigo + "#" + this.tipo + "#" + this.estado;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Bicicleta other = (Bicicleta) obj;
        return Objects.equals(this.codigo, other.codigo);
    }

    @Override
    public int compareTo(Bicicleta o) {
        return this.getCodigo().compareToIgnoreCase(o.getCodigo());
    }
}
