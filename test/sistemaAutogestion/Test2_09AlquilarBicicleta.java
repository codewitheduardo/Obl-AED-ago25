package sistemaAutogestion;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test2_09AlquilarBicicleta {

    private Retorno retorno;
    private final IObligatorio s = new Sistema();

    @Before
    public void setUp() {
        s.crearSistemaDeGestion();

        s.registrarEstacion("Estacion01", "Barrio", 2);
        s.registrarUsuario("12345678", "Usuario01");
        s.registrarUsuario("87654321", "Usuario02");

        s.registrarBicicleta("BICI01", "URBANA");
        s.registrarBicicleta("BICI02", "MOUNTAIN");

        s.asignarBicicletaAEstacion("BICI01", "Estacion01");
    }

    @Test
    public void alquilarBicicletaOk() {
        retorno = s.alquilarBicicleta("12345678", "Estacion01");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
    }

    @Test
    public void alquilarBicicletaError01() {
        retorno = s.alquilarBicicleta(null, "Estacion01");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.alquilarBicicleta("  ", "Estacion01");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.alquilarBicicleta("12345678", null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.alquilarBicicleta("12345678", "  ");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void alquilarBicicletaError02() {
        retorno = s.alquilarBicicleta("01234567", "Estacion01");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    public void alquilarBicicletaError03() {
        retorno = s.alquilarBicicleta("12345678", "NoExiste");
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }
}
