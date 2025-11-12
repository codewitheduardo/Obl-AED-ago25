package sistemaAutogestion;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test3_08RankingTiposPorUso {

    private Retorno retorno;
    private final IObligatorio s = new Sistema();

    // Setup para preparar datos comunes antes de cada prueba
    @Before
    public void setUp() {
        s.crearSistemaDeGestion();

        s.registrarEstacion("Est1", "Centro", 10);
        s.registrarEstacion("Est2", "Pocitos", 10);
        s.registrarEstacion("Est3", "Buceo", 10);

        s.registrarUsuario("11111111", "Ana");
        s.registrarUsuario("22222222", "Luis");
        s.registrarUsuario("33333333", "Mia");

        s.registrarBicicleta("BICI01", "URBANA");
        s.registrarBicicleta("BICI02", "MOUNTAIN");
        s.registrarBicicleta("BICI03", "ELECTRICA");
        s.registrarBicicleta("BICI04", "URBANA");
        s.registrarBicicleta("BICI05", "MOUNTAIN");
        s.registrarBicicleta("BICI06", "ELECTRICA");
        s.registrarBicicleta("BICI07", "URBANA");
        s.registrarBicicleta("BICI08", "MOUNTAIN");
        s.registrarBicicleta("BICI09", "ELECTRICA");

        s.asignarBicicletaAEstacion("BICI01", "Est1");
        s.asignarBicicletaAEstacion("BICI02", "Est2");
        s.asignarBicicletaAEstacion("BICI03", "Est3");
        s.asignarBicicletaAEstacion("BICI04", "Est1");
        s.asignarBicicletaAEstacion("BICI05", "Est2");
        s.asignarBicicletaAEstacion("BICI06", "Est3");
        s.asignarBicicletaAEstacion("BICI07", "Est1");
        s.asignarBicicletaAEstacion("BICI08", "Est2");
        s.asignarBicicletaAEstacion("BICI09", "Est3");
    }

    @Test
    public void rankingSinAlquileres() {
        retorno = s.rankingTiposPorUso();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        // No se han realizado alquileres, por lo que el ranking debe ser ordenado alfabéticamente
        assertEquals("ELECTRICA#0|MOUNTAIN#0|URBANA#0", retorno.getValorString());
    }

    @Test
    public void rankingUnAlquilerDeCada() {
        s.alquilarBicicleta("11111111", "Est1");  // Ana alquila URBANA
        s.alquilarBicicleta("22222222", "Est2");  // Luis alquila MOUNTAIN
        s.alquilarBicicleta("33333333", "Est3");  // Mia alquila ELECTRICA

        s.devolverBicicleta("11111111", "Est1");
        s.devolverBicicleta("22222222", "Est2");
        s.devolverBicicleta("33333333", "Est3");

        retorno = s.rankingTiposPorUso();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        // Los tres tipos de bicicleta tienen un alquiler, se ordenan alfabéticamente
        assertEquals("ELECTRICA#1|MOUNTAIN#1|URBANA#1", retorno.getValorString());
    }

    @Test
    public void rankingConConteosDiferentes() {
        // URBANA: 3 alquileres
        s.alquilarBicicleta("11111111", "Est1");  // Ana alquila URBANA
        s.devolverBicicleta("11111111", "Est1");
        s.alquilarBicicleta("11111111", "Est1");  // Ana alquila URBANA
        s.devolverBicicleta("11111111", "Est1");
        s.alquilarBicicleta("11111111", "Est1");  // Ana alquila URBANA
        s.devolverBicicleta("11111111", "Est1");

        // MOUNTAIN: 1 alquiler
        s.alquilarBicicleta("22222222", "Est2");  // Luis alquila MOUNTAIN
        s.devolverBicicleta("22222222", "Est2");

        // ELECTRICA: 2 alquileres
        s.alquilarBicicleta("33333333", "Est3");  // Mia alquila ELECTRICA
        s.devolverBicicleta("33333333", "Est3");
        s.alquilarBicicleta("33333333", "Est3");  // Mia alquila ELECTRICA
        s.devolverBicicleta("33333333", "Est3");

        retorno = s.rankingTiposPorUso();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("URBANA#3|ELECTRICA#2|MOUNTAIN#1", retorno.getValorString());
    }

    @Test
    public void rankingConEmpateParcial() {
        // URBANA: 2 alquileres
        s.alquilarBicicleta("11111111", "Est1");  // Ana alquila URBANA
        s.devolverBicicleta("11111111", "Est1");
        s.alquilarBicicleta("11111111", "Est1");  // Ana alquila URBANA
        s.devolverBicicleta("11111111", "Est1");

        // MOUNTAIN: 2 alquileres
        s.alquilarBicicleta("22222222", "Est2");  // Luis alquila MOUNTAIN
        s.devolverBicicleta("22222222", "Est2");
        s.alquilarBicicleta("22222222", "Est2");  // Luis alquila MOUNTAIN
        s.devolverBicicleta("22222222", "Est2");

        // ELECTRICA: 1 alquiler
        s.alquilarBicicleta("33333333", "Est3");
        s.devolverBicicleta("33333333", "Est3");

        retorno = s.rankingTiposPorUso();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("MOUNTAIN#2|URBANA#2|ELECTRICA#1", retorno.getValorString());
    }
}
