package sistemaAutogestion;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test2_05MarcarEnMantenimiento {

    private Retorno retorno;
    private final IObligatorio s = new Sistema();

    @Before
    public void setUp() {
        s.crearSistemaDeGestion();
        s.registrarBicicleta("BICI01", "URBANA");
        s.registrarEstacion("Estacion01", "Barrio01" , 5);
        s.registrarUsuario("12345678", "Usuario01");
    }

    @Test
    public void marcarEnMantenimientoOk() {
        retorno = s.marcarEnMantenimiento("BICI01", "Rueda pinchada");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
    }

    @Test
    public void marcarEnMantenimientoError01() {
        retorno = s.marcarEnMantenimiento(null, "Motivo");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.marcarEnMantenimiento("BICI01", null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.marcarEnMantenimiento("  ", "Motivo");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.marcarEnMantenimiento("BICI01", "  ");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }
    
    @Test
    public void marcarEnMantenimientoError02() {
        retorno = s.marcarEnMantenimiento("BICI00", "Motivo");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }
    
    @Test
    public void marcarEnMantenimientoError03() {
        s.asignarBicicletaAEstacion("BICI01", "Estacion01");
        s.alquilarBicicleta("12345678", "Estacion01");
        retorno = s.marcarEnMantenimiento("BICI01", "Revisión frenos");
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }
    
    @Test
    public void marcarEnMantenimientoError04() {
        s.marcarEnMantenimiento("BICI01", "Cadena rota");
        retorno = s.marcarEnMantenimiento("BICI01", "Otra falla");
        assertEquals(Retorno.Resultado.ERROR_4, retorno.getResultado());
    }
}
