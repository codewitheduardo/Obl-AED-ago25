package dominio;

public class Alquiler {

    private Usuario usuario;
    private Bicicleta bicicleta;
    private Estacion estacionOrigen;

    public Alquiler(Usuario usuario, Bicicleta bicicleta, Estacion estacionOrigen) {
        this.usuario = usuario;
        this.bicicleta = bicicleta;
        this.estacionOrigen = estacionOrigen;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Bicicleta getBicicleta() {
        return bicicleta;
    }

    public Estacion getEstacionOrigen() {
        return estacionOrigen;
    }

    @Override
    public String toString() {
        return bicicleta.getCodigo() + "#" + usuario.getCedula() + "#" + estacionOrigen.getNombre();
    }
}
