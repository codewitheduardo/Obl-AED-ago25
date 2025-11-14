package sistemaAutogestion;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test3_10UsuarioMayor {

    private Retorno retorno;
    private final IObligatorio s = new Sistema();

    @Before
    public void setUp() {
        s.crearSistemaDeGestion();

        s.registrarEstacion("Est1", "Centro", 5);

        s.registrarUsuario("11111111", "Ana");
        s.registrarUsuario("22222222", "Luis");
        s.registrarUsuario("33333333", "Mia");

        s.registrarBicicleta("BICI01", "URBANA");
        s.registrarBicicleta("BICI02", "MOUNTAIN");
        s.registrarBicicleta("BICI03", "ELECTRICA");

        s.asignarBicicletaAEstacion("BICI01", "Est1");
        s.asignarBicicletaAEstacion("BICI02", "Est1");
        s.asignarBicicletaAEstacion("BICI03", "Est1");
    }

    @Test
    public void sinAlquileres() {
        retorno = s.usuarioMayor();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("11111111", retorno.getValorString());
    }

    @Test
    public void unUsuarioMayor() {
        s.alquilarBicicleta("11111111", "Est1");
        s.devolverBicicleta("11111111", "Est1");

        retorno = s.usuarioMayor();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("11111111", retorno.getValorString());
    }

    @Test
    public void variosUsuariosDiferentesCantidades() {

        // Ana: 1
        s.alquilarBicicleta("11111111", "Est1");
        s.devolverBicicleta("11111111", "Est1");

        // Luis: 3
        s.alquilarBicicleta("22222222", "Est1");
        s.devolverBicicleta("22222222", "Est1");

        s.alquilarBicicleta("22222222", "Est1");
        s.devolverBicicleta("22222222", "Est1");

        s.alquilarBicicleta("22222222", "Est1");
        s.devolverBicicleta("22222222", "Est1");

        // Mia: 2
        s.alquilarBicicleta("33333333", "Est1");
        s.devolverBicicleta("33333333", "Est1");

        s.alquilarBicicleta("33333333", "Est1");
        s.devolverBicicleta("33333333", "Est1");

        retorno = s.usuarioMayor();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("22222222", retorno.getValorString());
    }

    @Test
    public void empateCedulaMenor() {

        // Ana: 2
        s.alquilarBicicleta("11111111", "Est1");
        s.devolverBicicleta("11111111", "Est1");

        s.alquilarBicicleta("11111111", "Est1");
        s.devolverBicicleta("11111111", "Est1");

        // Luis: 2
        s.alquilarBicicleta("22222222", "Est1");
        s.devolverBicicleta("22222222", "Est1");

        s.alquilarBicicleta("22222222", "Est1");
        s.devolverBicicleta("22222222", "Est1");

        retorno = s.usuarioMayor();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("11111111", retorno.getValorString());
    }

    @Test
    public void devolverNoResta() {

        s.alquilarBicicleta("22222222", "Est1");
        s.devolverBicicleta("22222222", "Est1");

        retorno = s.usuarioMayor();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("22222222", retorno.getValorString());
    }
}

