package sistemaAutogestion;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test2_08AsignarBicicletaAEstacion {

    private Retorno retorno;
    private final IObligatorio s = new Sistema();

    @Before
    public void setUp() {
        s.crearSistemaDeGestion();

        s.registrarEstacion("Estacion01", "Barrio", 2); 
        s.registrarEstacion("Estacion02", "Barrio", 1); 
        s.registrarEstacion("Estacion03", "Barrio", 2); 

        s.registrarBicicleta("BICI01", "URBANA");     
        s.registrarBicicleta("BICI02", "MOUNTAIN");   
        s.registrarBicicleta("BICI03", "ELECTRICA");

        s.asignarBicicletaAEstacion("BICI01", "Estacion02");

        s.marcarEnMantenimiento("BICI03", "Rueda pinchada");

        s.asignarBicicletaAEstacion("BICI02", "Estacion01");
    }

    @Test
    public void asignarBicicletaAEstacionOk() {
        retorno = s.asignarBicicletaAEstacion("BICI02", "Estacion03");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
    }

    @Test
    public void asignarBicicletaAEstacionError01() {
        retorno = s.asignarBicicletaAEstacion(null, "Estacion01");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.asignarBicicletaAEstacion("  ", "Estacion01");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.asignarBicicletaAEstacion("BICI01", null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.asignarBicicletaAEstacion("BICI01", "  ");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void asignarBicicletaAEstacionError02() {
        retorno = s.asignarBicicletaAEstacion("BICI04", "Estacion01");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        retorno = s.asignarBicicletaAEstacion("BICI03", "Estacion01");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    public void asignarBicicletaAEstacionError03() {
        retorno = s.asignarBicicletaAEstacion("BICI01", "NoExiste");
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }

    @Test
    public void asignarBicicletaAEstacionError04() {
        retorno = s.asignarBicicletaAEstacion("BICI01", "Estacion02");
        assertEquals(Retorno.Resultado.ERROR_4, retorno.getResultado());
    }
}
