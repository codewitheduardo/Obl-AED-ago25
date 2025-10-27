package sistemaAutogestion;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test2_07EliminarEstacion {

    private Retorno retorno;
    private final IObligatorio s = new Sistema();

    @Before
    public void setUp() {
        s.crearSistemaDeGestion();

        s.registrarEstacion("Estacion01", "Barrio", 5);
        s.registrarEstacion("Estacion02", "Barrio", 5);
        s.registrarBicicleta("BICI01", "URBANA");
        s.asignarBicicletaAEstacion("BICI01", "Estacion02");

        s.registrarEstacion("Estacion03", "Barrio", 5);
        s.registrarUsuario("12345678", "Usuario01");
        s.alquilarBicicleta("12345678", "Estacion03");
    }

    @Test
    public void eliminarEstacionOk() {
        retorno = s.eliminarEstacion("Estacion01");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
    }

    @Test
    public void eliminarEstacionError01() {
        retorno = s.eliminarEstacion(null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.eliminarEstacion("  ");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void eliminarEstacionError02() {
        retorno = s.eliminarEstacion("NoExiste");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    public void eliminarEstacionError03() {
        retorno = s.eliminarEstacion("Estacion02");
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());

        retorno = s.eliminarEstacion("Estacion03");
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }
}
