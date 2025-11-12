package dominio;

public class Alquiler {

    private Usuario usuario;
    private Bicicleta bicicleta;
    private Estacion estacionOrigen;
    private Estacion estacionDestino;

    public Alquiler(Usuario usuario, Bicicleta bicicleta, Estacion estacionOrigen) {
        this.usuario = usuario;
        this.bicicleta = bicicleta;
        this.estacionOrigen = estacionOrigen;
        this.estacionDestino = null;
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
    
    public void setEstacionDestino(Estacion e) {
        this.estacionDestino = e;
    }
    
    public boolean tieneEstacionDestino() {
        return estacionDestino != null;
    }

    @Override
    public String toString() {
        return bicicleta.getCodigo() + "#" + usuario.getCedula() + "#" + estacionOrigen.getNombre();
    }
}
