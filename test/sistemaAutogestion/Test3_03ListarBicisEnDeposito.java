package sistemaAutogestion;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test3_03ListarBicisEnDeposito {

    private Retorno retorno;
    private final IObligatorio s = new Sistema();

    @Before
    public void setUp() {
        s.crearSistemaDeGestion();
    }

    @Test
    public void listarBicisEnDepositoVacio() {
        retorno = s.listarBicisEnDeposito();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("", retorno.getValorString());
    }

    @Test
    public void listarBicisEnDepositoSoloUna() {
        s.registrarBicicleta("BICI01", "URBANA");

        retorno = s.listarBicisEnDeposito();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("BICI01#URBANA#Disponible", retorno.getValorString());
    }

    @Test
    public void listarBicisEnDepositoIngresoOrdenado() {
        s.registrarBicicleta("BICI01", "URBANA");
        s.registrarBicicleta("BICI02", "MOUNTAIN");
        s.registrarBicicleta("BICI03", "ELECTRICA");

        retorno = s.listarBicisEnDeposito();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("BICI01#URBANA#Disponible|BICI02#MOUNTAIN#Disponible|BICI03#ELECTRICA#Disponible", retorno.getValorString());
    }

    @Test
    public void listarBicisEnDepositoIngresoDesordenado() {
        s.registrarBicicleta("BICI03", "ELECTRICA");
        s.registrarBicicleta("BICI01", "URBANA");
        s.registrarBicicleta("BICI02", "MOUNTAIN");

        retorno = s.listarBicisEnDeposito();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("BICI03#ELECTRICA#Disponible|BICI01#URBANA#Disponible|BICI02#MOUNTAIN#Disponible", retorno.getValorString());
    }

    @Test
    public void listarBicisEnDepositoConMantenimiento() {
        s.registrarBicicleta("BICI01", "URBANA");
        s.registrarBicicleta("BICI02", "ELECTRICA");

        s.marcarEnMantenimiento("BICI02", "Revisión de batería");

        retorno = s.listarBicisEnDeposito();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("BICI01#URBANA#Disponible|BICI02#ELECTRICA#Mantenimiento", retorno.getValorString());
    }
}

