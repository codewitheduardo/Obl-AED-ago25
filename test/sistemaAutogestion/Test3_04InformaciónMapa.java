package sistemaAutogestion;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test3_04InformaciónMapa {

    private Retorno retorno;
    private final IObligatorio s = new Sistema();

    @Before
    public void setUp() {
        s.crearSistemaDeGestion();
    }

    @Test
    public void ejemplo1_columna_existe() {
        String[][] mapa = {
            {"0", "0", "0", "0", "0", "0"},
            {"0", "0", "0", "E3", "0", "0"},
            {"0", "0", "0", "0", "0", "0"},
            {"E1", "0", "0", "0", "E5", "0"},
            {"0", "0", "0", "0", "0", "0"},
            {"0", "0", "E2", "0", "E6", "0"},
            {"0", "0", "0", "0", "E7", "0"},
            {"0", "0", "0", "E4", "0", "0"}
        };

        retorno = s.informaciónMapa(mapa);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("3#columna|existe", retorno.getValorString());
    }

    @Test
    public void ejemplo2_ambas_existe() {
        String[][] mapa = {
            {"0", "0", "0", "0", "0", "0"},
            {"0", "0", "0", "E3", "0", "0"},
            {"0", "0", "0", "0", "0", "0"},
            {"E1", "0", "0", "0", "E5", "0"},
            {"0", "0", "0", "0", "0", "0"},
            {"0", "0", "E2", "0", "E6", "0"},
            {"0", "0", "0", "0", "0", "0"},
            {"0", "0", "0", "E4", "0", "0"}
        };

        retorno = s.informaciónMapa(mapa);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("2#ambas|existe", retorno.getValorString());
    }

    @Test
    public void ejemplo3_ambas_no_existe() {
        String[][] mapa = {
            {"0", "0", "0", "0", "0", "0"},
            {"0", "0", "0", "E3", "0", "0"},
            {"0", "0", "0", "0", "0", "0"},
            {"E1", "0", "0", "0", "E5", "0"},
            {"0", "0", "0", "0", "0", "0"},
            {"0", "0", "E2", "0", "E6", "0"},
            {"0", "E7", "0", "0", "0", "0"},
            {"0", "0", "0", "E4", "0", "0"}
        };

        retorno = s.informaciónMapa(mapa);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("2#ambas|no existe", retorno.getValorString());
    }

    @Test
    public void fila_maxima_no_existe() {
        String[][] mapa = {
            {"E1", "E2", "E3", "0", "0", "0"},
            {"0", "0", "0", "0", "0", "0"},
            {"0", "0", "0", "0", "0", "0"},
            {"0", "0", "0", "0", "0", "0"}
        };
        retorno = s.informaciónMapa(mapa);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("3#fila|no existe", retorno.getValorString());
    }

    @Test
    public void columna_maxima_no_existe() {
        String[][] mapa = {
            {"E1", "0", "0"},
            {"E2", "E3", "0"},
            {"E4", "E5", "0"}
        };
        retorno = s.informaciónMapa(mapa);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("3#columna|no existe", retorno.getValorString());
    }

    @Test
    public void ambas_maxima_existe() {
        String[][] mapa = {
            {"E1", "E2", "E7"},
            {"0", "E4", "E6"},
            {"0", "0", "E5"}
        };
        retorno = s.informaciónMapa(mapa);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("3#ambas|existe", retorno.getValorString());
    }

    @Test
    public void mapa_vacio() {
        String[][] mapa = {
            {"0", "0", "0"},
            {"0", "0", "0"},
            {"0", "0", "0"}
        };
        retorno = s.informaciónMapa(mapa);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("0#ambas|no existe", retorno.getValorString());
    }

    @Test
    public void matriz_uno_por_uno() {
        String[][] mapa = {
            {"0"}
        };
        retorno = s.informaciónMapa(mapa);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("0#ambas|no existe", retorno.getValorString());
    }

    @Test
    public void mapa_primeraFilaVacia() {
        String[][] mapa = {
            {"0", "0", "0", "0", "0", "0"},
            {"0", "0", "E7", "E9", "0", "0"},
            {"0", "0", "0", "0", "0", "0"},
            {"0", "0", "0", "0", "E5", "0"},
            {"0", "0", "0", "0", "0", "0"},
            {"0", "0", "E2", "0", "E6", "0"},
            {"0", "E7", "0", "0", "0", "0"},
            {"0", "0", "0", "E4", "0", "0"}
        };

        retorno = s.informaciónMapa(mapa);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("2#ambas|existe", retorno.getValorString());
    }

    @Test
    public void mapa_lleno() {
        String[][] mapa = {
            {"E00", "E01", "E02", "E03", "E04", "E05"},
            {"E10", "E11", "E12", "E13", "E14", "E15"},
            {"E20", "E21", "E22", "E23", "E24", "E25"},
            {"E30", "E31", "E32", "E33", "E34", "E35"},
            {"E40", "E41", "E42", "E43", "E44", "E45"},
            {"E50", "E51", "E52", "E53", "E54", "E55"},
            {"E60", "E61", "E62", "E63", "E64", "E65"},
            {"E70", "E71", "E72", "E73", "E74", "E75"}
        };

        retorno = s.informaciónMapa(mapa);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("8#columna|no existe", retorno.getValorString());
    }

    @Test
    public void mapa_columnasDescendentes() {
        String[][] mapa = {
            {"E1", "E2", "E3", "E4", "E5", "E6"},
            {"E7", "E8", "E9", "0", "0", "0"},
            {"E10", "E11", "0", "0", "0", "0"},
            {"E12", "0", "0", "0", "0", "0"}
        };

        retorno = s.informaciónMapa(mapa);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("6#fila|no existe", retorno.getValorString());
    }
}
