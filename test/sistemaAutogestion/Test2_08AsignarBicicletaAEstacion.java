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

        // Crear estaciones
        s.registrarEstacion("Estacion01", "Barrio", 2); // con lugar libre
        s.registrarEstacion("Estacion02", "Barrio", 1); // se llenará
        s.registrarEstacion("Estacion03", "Barrio", 2); // estación destino para mover bici

        // Registrar bicicletas
        s.registrarBicicleta("BICI01", "URBANA");     // disponible en depósito
        s.registrarBicicleta("BICI02", "MOUNTAIN");   // disponible para mover
        s.registrarBicicleta("BICI03", "ELECTRICA");  // no disponible (en mantenimiento)

        // Llenar una estación (para probar ERROR_4)
        s.asignarBicicletaAEstacion("BICI01", "Estacion02");

        // Poner una bici en mantenimiento (para ERROR_2)
        s.marcarEnMantenimiento("BICI03", "Rueda pinchada");

        // Mover bici 222 a Est1 (para luego moverla a Est3)
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
    public void asignarBicicletaAEstacionErrorSinLugar() {
        retorno = s.asignarBicicletaAEstacion("BICI01", "Estacion02");
        assertEquals(Retorno.Resultado.ERROR_4, retorno.getResultado());
    }
}
