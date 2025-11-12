package sistemaAutogestion;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test3_05ListarBicicletasDeEstacion {

    private Retorno retorno;
    private final IObligatorio s = new Sistema();

    @Before
    public void setUp() {
        s.crearSistemaDeGestion();
        s.registrarEstacion("Est1", "Centro", 5);
        s.registrarEstacion("Est2", "Pocitos", 4);
    }

    @Test
    public void listarBicisDeEstacionVacia() {
        retorno = s.listarBicicletasDeEstacion("Est1");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("", retorno.getValorString());
    }

    @Test
    public void listarBicisDeEstacionUna() {
        s.registrarBicicleta("BICI01", "URBANA");
        s.asignarBicicletaAEstacion("BICI01", "Est1");

        retorno = s.listarBicicletasDeEstacion("Est1");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("BICI01", retorno.getValorString());
    }

    @Test
    public void listarBicisDeEstacionIngresoOrdenado() {
        s.registrarBicicleta("BICI01", "URBANA");
        s.registrarBicicleta("BICI02", "MOUNTAIN");
        s.registrarBicicleta("BICI03", "ELECTRICA");

        s.asignarBicicletaAEstacion("BICI01", "Est1");
        s.asignarBicicletaAEstacion("BICI02", "Est1");
        s.asignarBicicletaAEstacion("BICI03", "Est1");

        retorno = s.listarBicicletasDeEstacion("Est1");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("BICI01|BICI02|BICI03", retorno.getValorString());
    }

    @Test
    public void listarBicisDeEstacionIngresoDesordenado() {
        s.registrarBicicleta("BICI03", "ELECTRICA");
        s.registrarBicicleta("BICI01", "URBANA");
        s.registrarBicicleta("BICI02", "MOUNTAIN");

        s.asignarBicicletaAEstacion("BICI03", "Est1");
        s.asignarBicicletaAEstacion("BICI01", "Est1");
        s.asignarBicicletaAEstacion("BICI02", "Est1");

        retorno = s.listarBicicletasDeEstacion("Est1");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("BICI01|BICI02|BICI03", retorno.getValorString());
    }

    @Test
    public void listarBicisDeEstacionNoAfectaOtra() {
        s.registrarBicicleta("BICI01", "URBANA");
        s.registrarBicicleta("BICI02", "URBANA");

        s.asignarBicicletaAEstacion("BICI01", "Est1");
        s.asignarBicicletaAEstacion("BICI02", "Est2");

        retorno = s.listarBicicletasDeEstacion("Est2");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("BICI02", retorno.getValorString());
    }

    @Test
    public void listarBicisDeEstacionConCodigosSimilares() {
        s.registrarBicicleta("A10000", "MOUNTAIN");
        s.registrarBicicleta("A2AAAA", "URBANA");
        s.registrarBicicleta("A11000", "URBANA");

        s.asignarBicicletaAEstacion("A10000", "Est1");
        s.asignarBicicletaAEstacion("A2AAAA", "Est1");
        s.asignarBicicletaAEstacion("A11000", "Est1");

        retorno = s.listarBicicletasDeEstacion("Est1");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("A10000|A11000|A2AAAA", retorno.getValorString());
    }
}
