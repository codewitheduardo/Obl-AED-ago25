package sistemaAutogestion;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test2_03RegistrarUsuario {
    
    private Retorno retorno;
    private final IObligatorio s = new Sistema();
    
    @Before
    public void setUp() {
        s.crearSistemaDeGestion();
    }
    
    @Test
    public void registrarUsuarioOk() {
        retorno = s.registrarUsuario("12345678", "Usuario01");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
    }
    
    @Test
    public void registrarUsuarioError01() {
        retorno = s.registrarUsuario(null, "Usuario01");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        
        retorno = s.registrarUsuario("12345678", null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        
        retorno = s.registrarUsuario("  ", "Usuario01");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        
        retorno = s.registrarUsuario("12345678", "  ");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }
    
    @Test
    public void registrarUsuarioError02() {
        retorno = s.registrarUsuario("1234567", "Usuario01");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
        
        retorno = s.registrarUsuario("123456789", "Usuario01");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
        
        retorno = s.registrarUsuario("1234a678", "Usuario01");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }
    
    @Test
    public void registrarUsuarioError03() {
        s.registrarUsuario("12345678", "Usuario01");
        retorno = s.registrarUsuario("12345678", "Usuario01");
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }
}
