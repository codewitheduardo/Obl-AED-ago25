package sistemaAutogestion;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test2_06RepararBicicleta {

    private Retorno retorno;
    private final IObligatorio s = new Sistema();

    @Before
    public void setUp() {
        s.crearSistemaDeGestion();
        s.registrarBicicleta("BICI01", "URBANA");
        s.registrarBicicleta("BICI02", "MOUNTAIN");
        s.marcarEnMantenimiento("BICI02", "Rueda rota");
    }

    @Test
    public void repararBicicletaOk() {
        retorno = s.repararBicicleta("BICI02");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
    }

    @Test
    public void repararBicicletaError01() {
        retorno = s.repararBicicleta(null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.repararBicicleta("  ");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void repararBicicletaError02() {
        retorno = s.repararBicicleta("BICI03");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    public void repararBicicletaError03() {
        retorno = s.repararBicicleta("BICI01");
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }
}
