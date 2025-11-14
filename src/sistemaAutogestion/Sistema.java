package sistemaAutogestion;

//Agregar aquí nombres y números de estudiante de los integrantes del equipo
// Eduardo Monzón - #3249307
// Ignacio Marichal - #353739
import dominio.Alquiler;
import dominio.Bicicleta;
import dominio.Estacion;
import dominio.Usuario;
import tads.ColaN;
import tads.ListaN;
import tads.ListaOrdenadaN;
import tads.PilaN;

public class Sistema implements IObligatorio {

    private ListaOrdenadaN<Usuario> listaUsuarios;
    private ListaN<Estacion> listaEstaciones;
    private ListaN<Bicicleta> listaDeposito;
    private PilaN<Alquiler> historialAlquileres;
    private int[] contRanking;

    public Sistema() {
        this.listaUsuarios = new ListaOrdenadaN();
        this.listaEstaciones = new ListaN();
        this.listaDeposito = new ListaN();
        this.historialAlquileres = new PilaN();
        this.contRanking = new int[]{0, 0, 0};
    }

    @Override
    public Retorno crearSistemaDeGestion() {
        this.listaUsuarios = new ListaOrdenadaN();
        this.listaEstaciones = new ListaN();
        this.listaDeposito = new ListaN();
        this.historialAlquileres = new PilaN();
        this.contRanking = new int[]{0, 0, 0};

        return new Retorno(Retorno.Resultado.OK);
    }

    @Override
    public Retorno registrarEstacion(String nombre, String barrio, int capacidad) {
        if (nombre == null || barrio == null || nombre.trim().isEmpty() || barrio.trim().isEmpty()) {
            return new Retorno(Retorno.Resultado.ERROR_1);
        }
        if (capacidad <= 0) {
            return new Retorno(Retorno.Resultado.ERROR_2);
        }

        Estacion nueva = new Estacion(nombre, barrio, capacidad);
        if (listaEstaciones.existeElemento(nueva)) {
            return new Retorno(Retorno.Resultado.ERROR_3);
        }

        listaEstaciones.agregarFinal(nueva);
        return new Retorno(Retorno.Resultado.OK);
    }

    @Override
    public Retorno registrarUsuario(String cedula, String nombre) {
        if (cedula == null || nombre == null || cedula.trim().isEmpty() || nombre.trim().isEmpty()) {
            return new Retorno(Retorno.Resultado.ERROR_1);
        }
        if (cedula.length() != 8) {
            return new Retorno(Retorno.Resultado.ERROR_2);
        }
        for (int i = 0; i < cedula.length() / 2; i++) {
            if (!Character.isDigit(cedula.charAt(i)) || !Character.isDigit(cedula.charAt((cedula.length() - 1 - i)))) {
                return new Retorno(Retorno.Resultado.ERROR_2);
            }
        }

        Usuario nuevo = new Usuario(cedula, nombre);
        if (listaUsuarios.existeElemento(nuevo)) {
            return new Retorno(Retorno.Resultado.ERROR_3);
        }

        listaUsuarios.agregarOrdenado(nuevo);
        return new Retorno(Retorno.Resultado.OK);
    }

    @Override
    public Retorno registrarBicicleta(String codigo, String tipo) {
        if (codigo == null || tipo == null || codigo.trim().isEmpty() || tipo.trim().isEmpty()) {
            return new Retorno(Retorno.Resultado.ERROR_1);
        }
        if (codigo.length() != 6) {
            return new Retorno(Retorno.Resultado.ERROR_2);
        }
        if (!(tipo.toUpperCase().equals("URBANA") || tipo.toUpperCase().equals("MOUNTAIN") || tipo.toUpperCase().equals("ELECTRICA"))) {
            return new Retorno(Retorno.Resultado.ERROR_3);
        }

        Bicicleta nueva = new Bicicleta(codigo.toUpperCase(), tipo.toUpperCase());
        if (listaDeposito.existeElemento(nueva)) {
            return new Retorno(Retorno.Resultado.ERROR_4);
        }
        for (int i = 0; i < listaEstaciones.cantElementos(); i++) {
            Estacion e = listaEstaciones.obtenerElementoDePos(i);
            if (e.getBicicletasAncladas().existeElemento(nueva)) {
                return new Retorno(Retorno.Resultado.ERROR_4);
            }
        }

        listaDeposito.agregarFinal(nueva);
        return new Retorno(Retorno.Resultado.OK);
    }

    @Override
    public Retorno marcarEnMantenimiento(String codigo, String motivo) {
        if (codigo == null || motivo == null || codigo.trim().isEmpty() || motivo.trim().isEmpty()) {
            return new Retorno(Retorno.Resultado.ERROR_1);
        }

        Bicicleta obj = new Bicicleta(codigo);
        Bicicleta encontrada = listaDeposito.obtenerElemento(obj);

        if (encontrada != null) {
            if (encontrada.getEstado().equals("Mantenimiento")) {
                return new Retorno(Retorno.Resultado.ERROR_4);
            }
            encontrada.setEstado("Mantenimiento");
            return new Retorno(Retorno.Resultado.OK);
        }

        for (int i = 0; i < listaUsuarios.cantElementos(); i++) {
            Usuario u = listaUsuarios.obtenerElementoDePos(i);
            if (u.tieneBicicleta()) {
                if (u.getBicicletaAlquilada().equals(obj)) {
                    return new Retorno(Retorno.Resultado.ERROR_3);
                }
            }
        }

        for (int i = 0; i < listaEstaciones.cantElementos(); i++) {
            Estacion e = listaEstaciones.obtenerElementoDePos(i);
            if (e.getBicicletasAncladas().existeElemento(obj)) {
                encontrada = e.getBicicletasAncladas().obtenerElemento(obj);
                if (encontrada.getEstado().equals("Mantenimiento")) {
                    return new Retorno(Retorno.Resultado.ERROR_4);
                }
                e.getBicicletasAncladas().borrarElemento(obj);
                encontrada.setEstado("Mantenimiento");
                listaDeposito.agregarFinal(encontrada);

                procesarUsuariosEnEsperaDeAnclaje(e);

                return new Retorno(Retorno.Resultado.OK);
            }
        }
        return new Retorno(Retorno.Resultado.ERROR_2);
    }

    @Override
    public Retorno repararBicicleta(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return new Retorno(Retorno.Resultado.ERROR_1);
        }

        Bicicleta encontrada = listaDeposito.obtenerElemento(new Bicicleta(codigo));
        if (encontrada == null) {
            return new Retorno(Retorno.Resultado.ERROR_2);
        }
        if (!encontrada.getEstado().equals("Mantenimiento")) {
            return new Retorno(Retorno.Resultado.ERROR_3);
        }

        encontrada.setEstado("Disponible");
        return new Retorno(Retorno.Resultado.OK);
    }

    @Override
    public Retorno eliminarEstacion(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return new Retorno(Retorno.Resultado.ERROR_1);
        }

        Estacion encontrada = listaEstaciones.obtenerElemento(new Estacion(nombre));
        if (encontrada == null) {
            return new Retorno(Retorno.Resultado.ERROR_2);
        }
        if (!encontrada.getBicicletasAncladas().esVacia()
                || !encontrada.getUsuariosEnEsperaAlquiler().esVacia()
                || !encontrada.getUsuariosEnEsperaAnclaje().esVacia()) {
            return new Retorno(Retorno.Resultado.ERROR_3);
        }

        listaEstaciones.borrarElemento(encontrada);
        return new Retorno(Retorno.Resultado.OK);
    }

    @Override
    public Retorno asignarBicicletaAEstacion(String codigo, String nombreEstacion) {
        if (codigo == null || nombreEstacion == null || codigo.trim().isEmpty() || nombreEstacion.trim().isEmpty()) {
            return new Retorno(Retorno.Resultado.ERROR_1);
        }

        Bicicleta biciBuscada = new Bicicleta(codigo);
        Bicicleta biciEncontrada = listaDeposito.obtenerElemento(biciBuscada);
        boolean enEstacion = false;
        Estacion estacionOrigen = null;

        if (biciEncontrada == null) {
            int i = 0;
            while (i < listaEstaciones.cantElementos() && !enEstacion) {
                Estacion e = listaEstaciones.obtenerElementoDePos(i);
                if (e.getBicicletasAncladas().existeElemento(biciBuscada)) {
                    biciEncontrada = e.getBicicletasAncladas().obtenerElemento(biciBuscada);
                    enEstacion = true;
                    estacionOrigen = e;
                }
                i++;
            }
        }

        if (biciEncontrada == null || !biciEncontrada.getEstado().equals("Disponible")) {
            return new Retorno(Retorno.Resultado.ERROR_2);
        }

        Estacion estacionDestino = listaEstaciones.obtenerElemento(new Estacion(nombreEstacion));
        if (estacionDestino == null) {
            return new Retorno(Retorno.Resultado.ERROR_3);
        }
        if (!estacionDestino.hayLugar()) {
            return new Retorno(Retorno.Resultado.ERROR_4);
        }

        if (enEstacion && estacionOrigen != null) {
            estacionOrigen.getBicicletasAncladas().borrarElemento(biciEncontrada);
        } else {
            listaDeposito.borrarElemento(biciEncontrada);
        }
        estacionDestino.getBicicletasAncladas().agregarOrdenado(biciEncontrada);
        return new Retorno(Retorno.Resultado.OK);
    }

    @Override
    public Retorno alquilarBicicleta(String cedula, String nombreEstacion) {
        if (cedula == null || nombreEstacion == null || cedula.trim().isEmpty() || nombreEstacion.trim().isEmpty()) {
            return new Retorno(Retorno.Resultado.ERROR_1);
        }

        Usuario encontrado = listaUsuarios.obtenerElemento(new Usuario(cedula));
        if (encontrado == null) {
            return new Retorno(Retorno.Resultado.ERROR_2);
        }

        Estacion estacion = listaEstaciones.obtenerElemento(new Estacion(nombreEstacion));
        if (estacion == null) {
            return new Retorno(Retorno.Resultado.ERROR_3);
        }

        if (!estacion.getBicicletasAncladas().esVacia()) {
            Bicicleta bici = estacion.getBicicletasAncladas().obtenerElementoDePos(0);
            estacion.getBicicletasAncladas().borrarElemento(bici);
            bici.setEstado("Alquilada");
            encontrado.setBicicletaAlquilada(bici);

            historialAlquileres.push(new Alquiler(encontrado, bici, estacion));

            incrementarContadorPorTipo(bici);

            procesarUsuariosEnEsperaDeAnclaje(estacion);
        } else {
            estacion.getUsuariosEnEsperaAlquiler().enqueue(encontrado);
        }
        return new Retorno(Retorno.Resultado.OK);
    }

    @Override
    public Retorno devolverBicicleta(String cedula, String nombreEstacionDestino) {
        if (cedula == null || nombreEstacionDestino == null || cedula.trim().isEmpty() || nombreEstacionDestino.trim().isEmpty()) {
            return new Retorno(Retorno.Resultado.ERROR_1);
        }

        Usuario encontrado = listaUsuarios.obtenerElemento(new Usuario(cedula));
        if (encontrado == null || !encontrado.tieneBicicleta()) {
            return new Retorno(Retorno.Resultado.ERROR_2);
        }

        Estacion estacionDestino = listaEstaciones.obtenerElemento(new Estacion(nombreEstacionDestino));
        if (estacionDestino == null) {
            return new Retorno(Retorno.Resultado.ERROR_3);
        }

        asignarEstacionDestinoEnAlquiler(encontrado, estacionDestino);

        if (estacionDestino.hayLugar()) {
            Bicicleta biciDevuelta = encontrado.getBicicletaAlquilada();

            if (!estacionDestino.getUsuariosEnEsperaAlquiler().esVacia()) {
                Usuario enEspera = estacionDestino.getUsuariosEnEsperaAlquiler().dequeue();
                enEspera.setBicicletaAlquilada(biciDevuelta);
                biciDevuelta.setEstado("Alquilada");

                Alquiler nuevo = new Alquiler(enEspera, biciDevuelta, estacionDestino);
                historialAlquileres.push(nuevo);

                incrementarContadorPorTipo(biciDevuelta);
            } else {
                biciDevuelta.setEstado("Disponible");
                estacionDestino.getBicicletasAncladas().agregarOrdenado(biciDevuelta);
            }
            encontrado.setBicicletaAlquilada(null);
        } else {
            estacionDestino.getUsuariosEnEsperaAnclaje().enqueue(encontrado);
        }
        return new Retorno(Retorno.Resultado.OK);
    }

    @Override
    public Retorno deshacerUltimosRetiros(int n) {
        if (n <= 0) {
            return new Retorno(Retorno.Resultado.ERROR_1);
        }
        if (n > historialAlquileres.size()) {
            n = historialAlquileres.size();
        }

        PilaN<Alquiler> aux = new PilaN<>();
        String textoRetorno = "";
        int deshechos = 0;

        while (deshechos < n && !historialAlquileres.esVacia()) {
            Alquiler alquiler = historialAlquileres.pop();

            if (!alquiler.tieneEstacionDestino()) {
                Usuario usuario = alquiler.getUsuario();
                Bicicleta bici = alquiler.getBicicleta();
                Estacion estacionOrigen = alquiler.getEstacionOrigen();

                if (estacionOrigen.hayLugar()) {
                    bici.setEstado("Disponible");
                    estacionOrigen.getBicicletasAncladas().agregarOrdenado(bici);
                    usuario.setBicicletaAlquilada(null);
                } else {
                    estacionOrigen.getUsuariosEnEsperaAnclaje().enqueue(usuario);
                }

                decrementarContadorPorTipo(bici);

                if (textoRetorno.isEmpty()) {
                    textoRetorno = alquiler.toString();
                } else {
                    textoRetorno += "|" + alquiler.toString();
                }

                deshechos++;
            } else {
                aux.push(alquiler);
            }
        }
        while (!aux.esVacia()) {
            historialAlquileres.push(aux.pop());
        }
        return new Retorno(Retorno.Resultado.OK, textoRetorno);
    }

    @Override
    public Retorno obtenerUsuario(String cedula) {
        if (cedula == null || cedula.trim().isEmpty()) {
            return new Retorno(Retorno.Resultado.ERROR_1);
        }
        if (cedula.length() != 8) {
            return new Retorno(Retorno.Resultado.ERROR_2);
        }
        for (int i = 0; i < cedula.length() / 2; i++) {
            if (!Character.isDigit(cedula.charAt(i)) || !Character.isDigit(cedula.charAt((cedula.length() - 1 - i)))) {
                return new Retorno(Retorno.Resultado.ERROR_2);
            }
        }

        Usuario u = listaUsuarios.obtenerElemento(new Usuario(cedula));
        if (u == null) {
            return new Retorno(Retorno.Resultado.ERROR_3);
        }
        return new Retorno(Retorno.Resultado.OK, u.getNombre() + "#" + u.getCedula());
    }

    @Override
    public Retorno listarUsuarios() {
        if (listaUsuarios == null || listaUsuarios.cantElementos() == 0) {
            return new Retorno(Retorno.Resultado.OK, "");
        }

        String textoRetorno = "";
        for (int i = 0; i < listaUsuarios.cantElementos(); i++) {
            Usuario u = listaUsuarios.obtenerElementoDePos(i);
            if (!textoRetorno.isEmpty()) {
                textoRetorno += "|";
            }
            textoRetorno += u.toString();
        }
        return new Retorno(Retorno.Resultado.OK, textoRetorno);
    }

    @Override
    public Retorno listarBicisEnDeposito() {
        if (listaDeposito == null || listaDeposito.cantElementos() == 0) {
            return new Retorno(Retorno.Resultado.OK, "");
        }

        return new Retorno(Retorno.Resultado.OK, listarBicisEnDeposito(listaDeposito.cantElementos() - 1));
    }

    private String listarBicisEnDeposito(int pos) {
        if (pos == 0) {
            Bicicleta b = (Bicicleta) listaDeposito.getInicio().getDato();
            return b.getCodigo() + "#" + b.getTipo() + "#" + b.getEstado();
        } else {
            Bicicleta b = (Bicicleta) listaDeposito.obtenerElementoDePos(pos);
            return listarBicisEnDeposito(pos - 1) + "|" + b.getCodigo() + "#" + b.getTipo() + "#" + b.getEstado();
        }
    }

    @Override
    public Retorno informaciónMapa(String[][] mapa) {
        if (mapa == null || mapa.length == 0 || mapa[0].length == 0) {
            return new Retorno(Retorno.Resultado.OK, "0#ambas|no existe");
        }

        String donde = "";
        boolean creciente = false;
        int cantMaxima = 0;

        for (int i = 0; i < mapa.length; i++) {
            int contPorFila = 0;
            for (int j = 0; j < mapa[i].length; j++) {
                if (!mapa[i][j].equals("") && !mapa[i][j].equals("0")) {
                    contPorFila++;
                }
            }
            if (contPorFila > cantMaxima) {
                cantMaxima = contPorFila;
                donde = "fila";
            } else if (contPorFila == cantMaxima && !donde.equals("fila")) {
                donde = "ambas";
            }
        }

        int[] estacionesPorColumna = new int[mapa[0].length];
        for (int j = 0; j < mapa[0].length; j++) {
            int contPorColumna = 0;
            for (int i = 0; i < mapa.length; i++) {
                if (!mapa[i][j].equals("") && !mapa[i][j].equals("0")) {
                    contPorColumna++;
                }
            }
            estacionesPorColumna[j] = contPorColumna;
            if (contPorColumna > cantMaxima) {
                cantMaxima = contPorColumna;
                donde = "columna";
            } else if (contPorColumna == cantMaxima && !donde.equals("columna")) {
                donde = "ambas";
            }
        }

        for (int col = 0; col < estacionesPorColumna.length - 2 && !creciente; col++) {
            int c1 = estacionesPorColumna[col];
            int c2 = estacionesPorColumna[col + 1];
            int c3 = estacionesPorColumna[col + 2];
            if (c1 < c2 && c2 < c3) {
                creciente = true;
            }
        }

        if (cantMaxima == 0) {
            donde = "ambas";
        }
        return new Retorno(Retorno.Resultado.OK, cantMaxima + "#" + donde + "|" + (creciente ? "existe" : "no existe"));
    }

    @Override
    public Retorno listarBicicletasDeEstacion(String nombreEstacion) {
        Estacion e = listaEstaciones.obtenerElemento(new Estacion(nombreEstacion));

        if (e.getBicicletasAncladas() == null || e.getBicicletasAncladas().esVacia()) {
            return new Retorno(Retorno.Resultado.OK, "");
        }

        String textoRetorno = "";
        for (int i = 0; i < e.getBicicletasAncladas().cantElementos(); i++) {
            Bicicleta b = e.getBicicletasAncladas().obtenerElementoDePos(i);

            if (!textoRetorno.isEmpty()) {
                textoRetorno += "|";
            }
            textoRetorno += b.getCodigo();
        }
        return new Retorno(Retorno.Resultado.OK, textoRetorno);
    }

    @Override
    public Retorno estacionesConDisponibilidad(int n) {
        if (n <= 1) {
            return new Retorno(Retorno.Resultado.ERROR_1);
        }

        int cantEstaciones = 0;
        for (int i = 0; i < listaEstaciones.cantElementos(); i++) {
            Estacion e = listaEstaciones.obtenerElementoDePos(i);
            int cantBicicletas = e.getBicicletasAncladas().cantElementos();

            if (cantBicicletas > n) {
                cantEstaciones++;
            }
        }
        return Retorno.ok(cantEstaciones);
    }

    @Override
    public Retorno ocupacionPromedioXBarrio() {
        ListaOrdenadaN<String> barrios = new ListaOrdenadaN();

        for (int i = 0; i < listaEstaciones.cantElementos(); i++) {
            String barrio = listaEstaciones.obtenerElementoDePos(i).getBarrio();

            if (!barrios.existeElemento(barrio)) {
                barrios.agregarOrdenado(barrio);
            }
        }

        String textoRetorno = "";
        for (int i = 0; i < barrios.cantElementos(); i++) {
            String barrio = barrios.obtenerElementoDePos(i);
            int cantAnclajes = 0;
            int capacidadTotal = 0;

            for (int j = 0; j < listaEstaciones.cantElementos(); j++) {
                Estacion e = listaEstaciones.obtenerElementoDePos(j);
                if (barrio.equals(e.getBarrio())) {
                    cantAnclajes += e.getBicicletasAncladas().cantElementos();
                    capacidadTotal += e.getCapacidad();
                }
            }
            double promedio = (cantAnclajes * 100.0) / capacidadTotal;
            int porcentaje = (int) Math.round(promedio);

            if (!textoRetorno.isEmpty()) {
                textoRetorno += "|";
            }
            textoRetorno += barrio + "#" + porcentaje;
        }
        return new Retorno(Retorno.Resultado.OK, textoRetorno);
    }

    @Override
    public Retorno rankingTiposPorUso() {
        int cantUrbana = contRanking[2];
        int cantMountain = contRanking[1];
        int cantElectrica = contRanking[0];

        String tipo1 = "URBANA";
        int c1 = cantUrbana;
        String tipo2 = "MOUNTAIN";
        int c2 = cantMountain;
        String tipo3 = "ELECTRICA";
        int c3 = cantElectrica;

        if (c2 > c1 || (c2 == c1 && tipo2.compareTo(tipo1) < 0)) {
            int tempCant = c1;
            c1 = c2;
            c2 = tempCant;
            String tempTipo = tipo1;
            tipo1 = tipo2;
            tipo2 = tempTipo;
        }
        if (c3 > c1 || (c3 == c1 && tipo3.compareTo(tipo1) < 0)) {
            int tempCant = c1;
            c1 = c3;
            c3 = tempCant;
            String tempTipo = tipo1;
            tipo1 = tipo3;
            tipo3 = tempTipo;
        }
        if (c3 > c2 || (c3 == c2 && tipo3.compareTo(tipo2) < 0)) {
            int tempCant = c2;
            c2 = c3;
            c3 = tempCant;
            String tempTipo = tipo2;
            tipo2 = tipo3;
            tipo3 = tempTipo;
        }

        String resultado = tipo1 + "#" + c1 + "|" + tipo2 + "#" + c2 + "|" + tipo3 + "#" + c3;

        return new Retorno(Retorno.Resultado.OK, resultado);
    }

    @Override
    public Retorno usuariosEnEspera(String nombreEstacion) {
        Estacion e = listaEstaciones.obtenerElemento(new Estacion(nombreEstacion));

        if (e.getUsuariosEnEsperaAlquiler() == null || e.getUsuariosEnEsperaAlquiler().esVacia()) {
            return new Retorno(Retorno.Resultado.OK, "");
        }

        String textoRetorno = "";
        ColaN<Usuario> aux = new ColaN();
        while (!e.getUsuariosEnEsperaAlquiler().esVacia()) {
            Usuario u = e.getUsuariosEnEsperaAlquiler().dequeue();

            if (!textoRetorno.isEmpty()) {
                textoRetorno += "|";
            }
            textoRetorno += u.getCedula();

            aux.enqueue(u);
        }
        while (!aux.esVacia()) {
            e.getUsuariosEnEsperaAlquiler().enqueue(aux.dequeue());
        }
        return new Retorno(Retorno.Resultado.OK, textoRetorno);
    }

    @Override
    public Retorno usuarioMayor() {
        String textoRetorno = "";
        int cantUsuarioMayor = 0;

        PilaN<Alquiler> aux = new PilaN<>();
        for (int i = 0; i < listaUsuarios.cantElementos(); i++) {
            Usuario u = listaUsuarios.obtenerElementoDePos(i);
            int cantUsuario = 0;

            while (!historialAlquileres.esVacia()) {
                Alquiler a = historialAlquileres.pop();

                if (u.equals(a.getUsuario())) {
                    cantUsuario++;
                }
                aux.push(a);
            }
            while (!aux.esVacia()) {
                historialAlquileres.push(aux.pop());
            }

            if (cantUsuario > cantUsuarioMayor) {
                cantUsuarioMayor = cantUsuario;
                textoRetorno = u.getCedula();
            } else if (cantUsuario == cantUsuarioMayor) {
                if (textoRetorno.equals("") || u.getCedula().compareTo(textoRetorno) < 0) {
                    textoRetorno = u.getCedula();
                }
            }
        }
        return new Retorno(Retorno.Resultado.OK, textoRetorno);
    }

    private void procesarUsuariosEnEsperaDeAnclaje(Estacion e) {
        while (e.hayLugar() && !e.getUsuariosEnEsperaAnclaje().esVacia()) {
            Usuario u = e.getUsuariosEnEsperaAnclaje().dequeue();
            Bicicleta biciPendiente = u.getBicicletaAlquilada();

            biciPendiente.setEstado("Disponible");
            e.getBicicletasAncladas().agregarOrdenado(biciPendiente);
            u.setBicicletaAlquilada(null);
        }
    }

    private void incrementarContadorPorTipo(Bicicleta b) {
        switch (b.getTipo()) {
            case "URBANA":
                contRanking[2]++;
                break;
            case "MOUNTAIN":
                contRanking[1]++;
                break;
            case "ELECTRICA":
                contRanking[0]++;
                break;
        }
    }

    private void decrementarContadorPorTipo(Bicicleta b) {
        switch (b.getTipo()) {
            case "URBANA":
                contRanking[2]--;
                break;
            case "MOUNTAIN":
                contRanking[1]--;
                break;
            case "ELECTRICA":
                contRanking[0]--;
                break;
        }
    }

    private void asignarEstacionDestinoEnAlquiler(Usuario u, Estacion estacionDestino) {
        PilaN<Alquiler> aux = new PilaN();
        boolean hayAlquiler = false;

        while (!historialAlquileres.esVacia() && !hayAlquiler) {
            Alquiler a = historialAlquileres.pop();
            if (a.getBicicleta().equals(u.getBicicletaAlquilada()) && !a.tieneEstacionDestino()) {
                a.setEstacionDestino(estacionDestino);
                hayAlquiler = true;
            }
            aux.push(a);
        }

        while (!aux.esVacia()) {
            historialAlquileres.push(aux.pop());
        }
    }
}
