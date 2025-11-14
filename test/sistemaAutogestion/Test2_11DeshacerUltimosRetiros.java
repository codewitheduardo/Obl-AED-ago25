package sistemaAutogestion;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test2_11DeshacerUltimosRetiros {

    private Retorno retorno;
    private final IObligatorio s = new Sistema();

    @Before
    public void setUp() {
        s.crearSistemaDeGestion();

        s.registrarUsuario("12345678", "Usuario01");
        s.registrarUsuario("87654321", "Usuario02");
        s.registrarUsuario("01234567", "Usuario03");

        s.registrarEstacion("Estacion01", "Barrio", 3);
        s.registrarEstacion("Estacion02", "Barrio", 2);

        s.registrarBicicleta("BICI01", "URBANA");
        s.registrarBicicleta("BICI02", "URBANA");
        s.registrarBicicleta("BICI03", "URBANA");

        s.asignarBicicletaAEstacion("BICI01", "Estacion01");
        s.asignarBicicletaAEstacion("BICI02", "Estacion01");
        s.asignarBicicletaAEstacion("BICI03", "Estacion01");

        s.alquilarBicicleta("12345678", "Estacion01");
        s.alquilarBicicleta("87654321", "Estacion01");
        s.alquilarBicicleta("01234567", "Estacion01");
    }

    @Test
    public void deshacerUltimosRetirosOk() {
        retorno = s.deshacerUltimosRetiros(1);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("BICI03#01234567#Estacion01", retorno.getValorString());

        retorno = s.deshacerUltimosRetiros(2);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("BICI02#87654321#Estacion01|BICI01#12345678#Estacion01", retorno.getValorString());

        s.alquilarBicicleta("12345678", "Estacion01");
        s.alquilarBicicleta("87654321", "Estacion01");
        retorno = s.deshacerUltimosRetiros(10);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("BICI02#87654321#Estacion01|BICI01#12345678#Estacion01", retorno.getValorString());
    }

    @Test
    public void deshacerUltimosRetirosError01() {
        retorno = s.deshacerUltimosRetiros(0);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.deshacerUltimosRetiros(-2);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void noDeshacerAlquileresConEstacionDestino() {
        s.devolverBicicleta("01234567", "Estacion02");

        retorno = s.deshacerUltimosRetiros(3);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("BICI02#87654321#Estacion01|BICI01#12345678#Estacion01", retorno.getValorString());
    }

    @Test
    public void usuarioQuedaEnColaEsperaAnclaje() {
        s.registrarBicicleta("BICI04", "URBANA");
        s.asignarBicicletaAEstacion("BICI04", "Estacion01");

        // Ahora Estacion01 tiene capacidad 3 y 1 bici anclada:
        // PERO necesitamos llenarla completamente.
        // Entonces devolvemos dos bicis para llenar los 3 espacios:
        s.devolverBicicleta("12345678", "Estacion01");
        s.devolverBicicleta("87654321", "Estacion01");

        retorno = s.deshacerUltimosRetiros(1);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("BICI03#01234567#Estacion01", retorno.getValorString());

        // Si 01234567 está en la cola de espera de anclaje,
        // cuando se libere un espacio debe anclarse automáticamente.
        // Liberamos 1 anclaje
        s.alquilarBicicleta("12345678", "Estacion01");

        // Ahora la bici de 01234567 debe haberse anclado automáticamente
        retorno = s.listarBicicletasDeEstacion("Estacion01");

        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertTrue(retorno.getValorString().contains("BICI03"));
    }

}
