package sistemaAutogestion;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test3_06EstacionesConDisponibilidad {

    private Retorno retorno;
    private final IObligatorio s = new Sistema();

    @Before
    public void setUp() {
        s.crearSistemaDeGestion();

        s.registrarEstacion("Est1", "Centro", 5);
        s.registrarEstacion("Est2", "Pocitos", 4);
        s.registrarEstacion("Est3", "Cordón", 10);

        s.registrarBicicleta("BICI01", "URBANA");
        s.registrarBicicleta("BICI02", "URBANA");
        s.registrarBicicleta("BICI03", "URBANA");
        s.registrarBicicleta("BICI04", "URBANA");
        s.registrarBicicleta("BICI05", "URBANA");
        s.registrarBicicleta("BICI06", "URBANA");
    }

    @Test
    public void estacionesConDisponibilidadErrorNMenorIgualUno() {
        retorno = s.estacionesConDisponibilidad(1);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.estacionesConDisponibilidad(0);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void estacionesTodasVacias() {
        retorno = s.estacionesConDisponibilidad(1);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.estacionesConDisponibilidad(2);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(0, retorno.getValorEntero());
    }

    @Test
    public void estacionesConDistintasCantidades() {
        s.asignarBicicletaAEstacion("BICI01", "Est1");
        s.asignarBicicletaAEstacion("BICI02", "Est1");

        s.asignarBicicletaAEstacion("BICI03", "Est2");
        s.asignarBicicletaAEstacion("BICI04", "Est2");
        s.asignarBicicletaAEstacion("BICI05", "Est2");
        s.asignarBicicletaAEstacion("BICI06", "Est2");

        retorno = s.estacionesConDisponibilidad(1);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.estacionesConDisponibilidad(2);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(1, retorno.getValorEntero());

        retorno = s.estacionesConDisponibilidad(0);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void estacionesConDisponibilidadGrande() {
        retorno = s.estacionesConDisponibilidad(3);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(0, retorno.getValorEntero());
    }
}

