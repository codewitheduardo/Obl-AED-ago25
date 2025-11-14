package sistemaAutogestion;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test3_09UsuariosEnEspera {

    private Retorno retorno;
    private final IObligatorio s = new Sistema();

    @Before
    public void setUp() {
        s.crearSistemaDeGestion();

        s.registrarEstacion("Estacion01", "Centro", 1);
        s.registrarEstacion("Estacion02", "Pocitos", 1);

        s.registrarUsuario("11111111", "Ana");
        s.registrarUsuario("22222222", "Luis");
        s.registrarUsuario("33333333", "Mia");

        s.registrarBicicleta("BICI01", "URBANA");
        s.registrarBicicleta("BICI02", "MOUNTAIN");

        s.asignarBicicletaAEstacion("BICI01", "Estacion01");
        s.asignarBicicletaAEstacion("BICI02", "Estacion02");
    }

    @Test
    public void usuariosEnEsperaVacio() {
        retorno = s.usuariosEnEspera("Estacion01");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("", retorno.getValorString());
    }

    @Test
    public void unUsuarioEnEspera() {
        s.alquilarBicicleta("11111111", "Estacion01");

        s.alquilarBicicleta("22222222", "Estacion01");

        retorno = s.usuariosEnEspera("Estacion01");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("22222222", retorno.getValorString());
    }

    @Test
    public void variosUsuariosEnEspera() {
        s.alquilarBicicleta("11111111", "Estacion01");

        s.alquilarBicicleta("22222222", "Estacion01");
        s.alquilarBicicleta("33333333", "Estacion01");

        retorno = s.usuariosEnEspera("Estacion01");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("22222222|33333333", retorno.getValorString());
    }

    @Test
    public void esperaConAsignacionAutomatica() {
        s.alquilarBicicleta("11111111", "Estacion01");

        s.alquilarBicicleta("22222222", "Estacion01");
        s.alquilarBicicleta("33333333", "Estacion01");

        s.devolverBicicleta("11111111", "Estacion01");

        retorno = s.usuariosEnEspera("Estacion01");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        assertEquals("33333333", retorno.getValorString());
    }

    @Test
    public void usuariosEnEsperaCuandoNuncaHuboBicicletas() {
        s.alquilarBicicleta("11111111", "Estacion02");

        s.alquilarBicicleta("22222222", "Estacion02");
        s.alquilarBicicleta("33333333", "Estacion02");

        retorno = s.usuariosEnEspera("Estacion02");
        assertEquals("22222222|33333333", retorno.getValorString());
    }
}
