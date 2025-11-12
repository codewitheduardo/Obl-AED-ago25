package sistemaAutogestion;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test3_07OcupacionPromedioXBarrio {

    private Retorno retorno;
    private final IObligatorio s = new Sistema();

    @Before
    public void setUp() {
        s.crearSistemaDeGestion();

        // Estaciones necesarias
        s.registrarEstacion("Est1", "Centro", 4);   // Capacidad total Centro = 4 + 2 = 6
        s.registrarEstacion("Est2", "Centro", 2);
        s.registrarEstacion("Est3", "Pocitos", 5);  // Capacidad total Pocitos = 5

        // Bicicletas
        s.registrarBicicleta("BICI01", "URBANA");
        s.registrarBicicleta("BICI02", "URBANA");
        s.registrarBicicleta("BICI03", "URBANA");
        s.registrarBicicleta("BICI04", "URBANA");
        s.registrarBicicleta("BICI05", "URBANA");
        s.registrarBicicleta("BICI06", "URBANA");
    }

    @Test
    public void ocupacionSinBicicletas() {
        retorno = s.ocupacionPromedioXBarrio();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        // Centro: 0/6 = 0%      Pocitos: 0/5 = 0%
        assertEquals("Centro#0|Pocitos#0", retorno.getValorString());
    }

    @Test
    public void ocupacionConAlgunas() {
        // Centro: agregar 3 bicis → 3/6 = 50% → redondea 50
        s.asignarBicicletaAEstacion("BICI01", "Est1");
        s.asignarBicicletaAEstacion("BICI02", "Est1");
        s.asignarBicicletaAEstacion("BICI03", "Est2");

        // Pocitos: agregar 2 bicis → 2/5 = 40% → redondea 40
        s.asignarBicicletaAEstacion("BICI04", "Est3");
        s.asignarBicicletaAEstacion("BICI05", "Est3");

        retorno = s.ocupacionPromedioXBarrio();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("Centro#50|Pocitos#40", retorno.getValorString());
    }

    @Test
    public void ocupacionBarrioLleno() {
        // Llenamos Pocitos (5 de 5) → 100%
        s.asignarBicicletaAEstacion("BICI01", "Est3");
        s.asignarBicicletaAEstacion("BICI02", "Est3");
        s.asignarBicicletaAEstacion("BICI03", "Est3");
        s.asignarBicicletaAEstacion("BICI04", "Est3");
        s.asignarBicicletaAEstacion("BICI05", "Est3");

        retorno = s.ocupacionPromedioXBarrio();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        // Centro sigue 0%, Pocitos = 100%
        assertEquals("Centro#0|Pocitos#100", retorno.getValorString());
    }

    @Test
    public void ocupacionConRedondeo() {
        // Centro capacidad total = 4 + 2 = 6
        // Ponemos 1 bici → 1/6 = 16.66 → redondea a 17
        s.asignarBicicletaAEstacion("BICI01", "Est1");

        retorno = s.ocupacionPromedioXBarrio();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("Centro#17|Pocitos#0", retorno.getValorString());
    }
}

