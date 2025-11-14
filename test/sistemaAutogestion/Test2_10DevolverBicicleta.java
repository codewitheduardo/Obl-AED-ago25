package sistemaAutogestion;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test2_10DevolverBicicleta {

    private Retorno retorno;
    private final IObligatorio s = new Sistema();

    @Before
    public void setUp() {
        s.crearSistemaDeGestion();

        s.registrarUsuario("12345678", "Usuario01");

        s.registrarEstacion("Estacion01", "Barrio", 2);
        s.registrarEstacion("Estacion02", "Barrio", 2);

        s.registrarBicicleta("BICI01", "URBANA");
        s.asignarBicicletaAEstacion("BICI01", "Estacion01");

        s.alquilarBicicleta("12345678", "Estacion01");
    }

    @Test
    public void devolverBicicletaOk() {
        retorno = s.devolverBicicleta("12345678", "Estacion02");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
    }

    @Test
    public void devolverBicicletaError01() {
        retorno = s.devolverBicicleta(null, "Estacion01");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.devolverBicicleta("  ", "Estacion01");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.devolverBicicleta("12345678", null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.devolverBicicleta("12345678", "  ");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void devolverBicicletaError02() {
        retorno = s.devolverBicicleta("01234567", "Estacion01");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        s.registrarUsuario("87654321", "Usuario02");
        retorno = s.devolverBicicleta("87654321", "Estacion01");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    public void devolverBicicletaError03() {
        retorno = s.devolverBicicleta("12345678", "NoExiste");
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }

    @Test
    public void devolverSinLugarUsuarioQuedaEnEsperaDeAnclaje() {
        s.registrarBicicleta("BICI02", "URBANA");
        s.asignarBicicletaAEstacion("BICI02", "Estacion02");

        s.registrarBicicleta("BICI03", "URBANA");
        s.asignarBicicletaAEstacion("BICI03", "Estacion02");

        retorno = s.devolverBicicleta("12345678", "Estacion02");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        retorno = s.listarBicicletasDeEstacion("Estacion02");
        assertEquals("BICI02|BICI03", retorno.getValorString());
    }

    @Test
    public void devolverConUsuariosEnEsperaAsignaAutomatico() {
        s.registrarUsuario("87654321", "Usuario02");
        s.alquilarBicicleta("87654321", "Estacion01");

        retorno = s.devolverBicicleta("12345678", "Estacion01");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        retorno = s.usuariosEnEspera("Estacion01");
        assertEquals("", retorno.getValorString());
    }
}
