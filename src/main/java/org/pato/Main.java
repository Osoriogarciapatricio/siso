package org.pato;
import java.time.Year;
import java.util.Scanner;
public class Main {

    // Declaración de Arreglos, variables y "Objetos" globales
    // Columnas:
    // 0 = Nombre
    // 1 = Correo
    // 2 = Rango
    // 3 = Contraseña
    static String[][] ListaUsuarios = new String[1000][4];
    static int contadorUsuarios = 0;
    // Lleva el control de cuántos usuarios se han registrado
    static Scanner scanner = new Scanner(System.in);
    // Escaner global
    static boolean EsAdmin = false;
    // Variable a usar en el futuro
    static int usuarioActual = -1; // índice del usuario con sesión iniciada

    // WISHLIST: discos de vinilo deseados por cada usuario
    // Columnas:
    // 0 = Correo del dueño de la wishlist
    // 1 = Álbum
    // 2 = Artista
    // 3 = Año
    // 4 = Precio
    static String[][] ListaDeseos = new String[1000][5];
    static int contadorDeseos = 0;

    // INICIO DEL CODIGO DEL SISTEMA
    // MENU PRINCIPAL
    public static void main(String[] args) {
        int opcion = 0;

        do {
            System.out.println("===========================");
            System.out.println("      DISCOS SALVAJES");
            System.out.println("===========================");
            System.out.println("0. Salir");
            System.out.println("1. Registrarse");
            System.out.println("2. Iniciar Sesión");
            System.out.print("Seleccione una opción: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();
            } else {
                scanner.next();
                System.out.println("Opción inválida. Ingrese un número.");
                continue;
            }

            switch (opcion) {
                case 1:
                    registrarUsuario();
                    break;
                case 2:
                    iniciarSesion();
                    break;
                case 0:
                    System.out.println("Fin del programa");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }

    // MODULO DE REGISTRO DE USUARIOS
    public static void registrarUsuario() {
        System.out.println("--- MÓDULO DE REGISTRO ---");
        System.out.println("Ingrese su nombre");
        String nombre = scanner.nextLine();

        System.out.println("Ingrese su correo");
        String correo = scanner.nextLine();

        // Valida si el correo ya está registrado previamente
        for (int i = 0; i < contadorUsuarios; i++) {
            if (ListaUsuarios[i][1].equals(correo)) {
                System.out.println("Este correo ya se encuentra registrado.");
                return;
            }
        }

        System.out.println("Ingrese su rango");
        System.out.println("1. Usuario");
        System.out.println("2. Administrador");
        String rango = scanner.nextLine();
        while(!rango.equals("1") && !rango.equals("2")){
            System.out.println("Rango incorrecto");
            System.out.println("1. Usuario");
            System.out.println("2. Administrador");
            rango = scanner.nextLine();
        }
        System.out.print("Ingrese su contraseña: ");
        String contraseña = scanner.nextLine();

        // Almacena datos en la matriz global
        ListaUsuarios[contadorUsuarios][0] = nombre;
        ListaUsuarios[contadorUsuarios][1] = correo;
        ListaUsuarios[contadorUsuarios][2] = rango;
        ListaUsuarios[contadorUsuarios][3] = contraseña;

        contadorUsuarios++;

        System.out.println("¡Usuario registrado con éxito!");
    }

    // MODULO DE INICIO DE SESION
    public static void iniciarSesion() {
        System.out.println("--- MÓDULO DE INICIO DE SESIÓN ---");
        System.out.print("Ingrese su correo: ");
        String correoIngresado = scanner.nextLine();

        System.out.print("Ingrese su contraseña: ");
        String contraseñaIngresada = scanner.nextLine();

        boolean usuarioEncontrado = false;
        for (int i = 0; i < contadorUsuarios; i++) {
            if (ListaUsuarios[i][1].equals(correoIngresado)) {
                usuarioEncontrado = true;

                // Verifica si la contraseña coincide
                if (ListaUsuarios[i][3].equals(contraseñaIngresada)) {
                    System.out.println("¡Inicio de sesión exitoso!");
                    if(ListaUsuarios[i][2].equals("1")){
                        EsAdmin = false;
                    }
                    else if(ListaUsuarios[i][2].equals("2")){
                        EsAdmin = true;
                    }
                    System.out.println("Bienvenido, " + ListaUsuarios[i][0] + " Admin: " + EsAdmin);

                    // Enlace con los módulos siguientes (wishlist y gestión de usuarios)
                    usuarioActual = i;
                    menuSesion();
                } else {
                    System.out.println("Contraseña incorrecta.");
                }
                break;
            }
        }

        if (!usuarioEncontrado) {
            System.out.println("El correo ingresado no existe en el sistema.");
        }
    }

    // MENU DESPUES DE INICIAR SESION
    public static void menuSesion() {
        String entrada;
        do {
            System.out.println("\n--- MENÚ DE SESIÓN ---");
            System.out.println("1. Mi wishlist de vinilos");
            if (EsAdmin) {
                System.out.println("2. Gestión de usuarios");
            }
            System.out.println("0. Cerrar sesión");
            System.out.print("Seleccione una opción: ");
            entrada = scanner.nextLine().trim();

            switch (entrada) {
                case "1":
                    menuWishlist();
                    break;
                case "2":
                    if (EsAdmin) menuUsuarios();
                    else System.out.println("Opción no válida.");
                    break;
                case "0":
                    System.out.println("Sesión cerrada.");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (!entrada.equals("0"));

        usuarioActual = -1;
        EsAdmin = false;
    }

    // ==========================================================
    // MODULO DE WISHLIST (discos de vinilo deseados)
    // ==========================================================
    public static void menuWishlist() {
        String entrada;
        do {
            System.out.println("\n--- MI WISHLIST DE VINILOS ---");
            System.out.println("1. Ver mi wishlist");
            System.out.println("2. Agregar disco");
            System.out.println("3. Editar disco");
            System.out.println("4. Eliminar disco");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");
            entrada = scanner.nextLine().trim();

            switch (entrada) {
                case "1":
                    mostrarWishlist();
                    break;
                case "2":
                    agregarDisco();
                    break;
                case "3":
                    editarDisco();
                    break;
                case "4":
                    eliminarDisco();
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (!entrada.equals("0"));
    }

    // Devuelve los índices (dentro de ListaDeseos) de los discos de un usuario
    public static int[] obtenerIndicesDeseos(String correo) {
        int cantidad = 0;
        for (int i = 0; i < contadorDeseos; i++) {
            if (ListaDeseos[i][0].equals(correo)) cantidad++;
        }
        int[] indices = new int[cantidad];
        int pos = 0;
        for (int i = 0; i < contadorDeseos; i++) {
            if (ListaDeseos[i][0].equals(correo)) indices[pos++] = i;
        }
        return indices;
    }

    // Muestra la wishlist del usuario con sesión y devuelve sus índices
    public static int[] mostrarWishlist() {
        String correo = ListaUsuarios[usuarioActual][1];
        int[] indices = obtenerIndicesDeseos(correo);

        System.out.println("\n--- WISHLIST DE " + ListaUsuarios[usuarioActual][0].toUpperCase() + " ---");
        if (indices.length == 0) {
            System.out.println("Tu wishlist está vacía.");
            return indices;
        }

        double total = 0;
        for (int k = 0; k < indices.length; k++) {
            String[] d = ListaDeseos[indices[k]];
            double precio = Double.parseDouble(d[4]);
            total += precio;
            System.out.println((k + 1) + ". " + d[1] + " - " + d[2]
                    + " (" + d[3] + ") | $" + String.format("%.2f", precio));
        }
        System.out.println("Discos: " + indices.length + " | Total estimado: $" + String.format("%.2f", total));
        return indices;
    }

    // "selecciona opción" -> agrega un disco a la wishlist
    public static void agregarDisco() {
        if (contadorDeseos >= ListaDeseos.length) {
            System.out.println("Mensaje error: la lista de deseos está llena.");
            return;
        }

        System.out.println("\n--- AGREGAR DISCO ---");
        System.out.print("Álbum: ");
        String album = scanner.nextLine().trim();
        System.out.print("Artista: ");
        String artista = scanner.nextLine().trim();
        System.out.print("Año de lanzamiento: ");
        String anio = scanner.nextLine().trim();
        System.out.print("Precio: ");
        String precio = scanner.nextLine().trim().replace(",", ".");

        String correo = ListaUsuarios[usuarioActual][1];

        // "validar datos" -> rama de ¿Éxito?
        String error = validarDisco(correo, -1, album, artista, anio, precio);
        if (error != null) {
            System.out.println("Mensaje error: " + error);
            return;
        }

        ListaDeseos[contadorDeseos][0] = correo;
        ListaDeseos[contadorDeseos][1] = album;
        ListaDeseos[contadorDeseos][2] = artista;
        ListaDeseos[contadorDeseos][3] = anio;
        ListaDeseos[contadorDeseos][4] = String.valueOf(Double.parseDouble(precio));
        contadorDeseos++;

        System.out.println("Mensaje éxito: disco agregado a tu wishlist.");
    }

    // "selecciona y edita" -> "envía acción y datos"
    public static void editarDisco() {
        int[] indices = mostrarWishlist();
        if (indices.length == 0) return;

        int k = seleccionarIndice(indices.length);
        if (k == -1) {
            System.out.println("Mensaje error: disco no válido.");
            return;
        }
        int g = indices[k]; // índice real dentro de ListaDeseos

        System.out.println("(Enter para mantener el valor actual)");
        System.out.print("Álbum [" + ListaDeseos[g][1] + "]: ");
        String album = scanner.nextLine().trim();
        System.out.print("Artista [" + ListaDeseos[g][2] + "]: ");
        String artista = scanner.nextLine().trim();
        System.out.print("Año [" + ListaDeseos[g][3] + "]: ");
        String anio = scanner.nextLine().trim();
        System.out.print("Precio [" + ListaDeseos[g][4] + "]: ");
        String precio = scanner.nextLine().trim().replace(",", ".");

        if (album.isEmpty())   album   = ListaDeseos[g][1];
        if (artista.isEmpty()) artista = ListaDeseos[g][2];
        if (anio.isEmpty())    anio    = ListaDeseos[g][3];
        if (precio.isEmpty())  precio  = ListaDeseos[g][4];

        // "validar datos"
        String error = validarDisco(ListaDeseos[g][0], g, album, artista, anio, precio);
        if (error != null) {
            System.out.println("Mensaje error: " + error);
            return;
        }

        // "modifica disco"
        ListaDeseos[g][1] = album;
        ListaDeseos[g][2] = artista;
        ListaDeseos[g][3] = anio;
        ListaDeseos[g][4] = String.valueOf(Double.parseDouble(precio));

        System.out.println("Mensaje éxito: disco modificado.");
    }

    public static void eliminarDisco() {
        int[] indices = mostrarWishlist();
        if (indices.length == 0) return;

        int k = seleccionarIndice(indices.length);
        if (k == -1) {
            System.out.println("Mensaje error: disco no válido.");
            return;
        }
        int g = indices[k];

        System.out.print("¿Quitar \"" + ListaDeseos[g][1] + "\" de tu wishlist? (s/n): ");
        if (!scanner.nextLine().trim().equalsIgnoreCase("s")) {
            System.out.println("Operación cancelada.");
            return;
        }

        quitarDeseoEn(g);
        System.out.println("Mensaje éxito: disco eliminado de tu wishlist.");
    }

    // Devuelve null si todo está bien, o el texto del error
    // ignorar = índice de ListaDeseos que se está editando (-1 si es un disco nuevo)
    public static String validarDisco(String correo, int ignorar, String album,
                                      String artista, String anio, String precio) {
        if (album.isEmpty()) return "El álbum no puede estar vacío.";
        if (artista.isEmpty()) return "El artista no puede estar vacío.";

        try {
            int a = Integer.parseInt(anio);
            if (a < 1900 || a > Year.now().getValue() + 1) return "El año no es válido.";
        } catch (NumberFormatException e) {
            return "El año debe ser un número.";
        }

        try {
            if (Double.parseDouble(precio) < 0) return "El precio no puede ser negativo.";
        } catch (NumberFormatException e) {
            return "El precio debe ser un número.";
        }

        // Evita repetir el mismo disco en la wishlist del mismo usuario
        for (int i = 0; i < contadorDeseos; i++) {
            if (i != ignorar
                    && ListaDeseos[i][0].equals(correo)
                    && ListaDeseos[i][1].equalsIgnoreCase(album)
                    && ListaDeseos[i][2].equalsIgnoreCase(artista)) {
                return "Ese disco ya está en tu wishlist.";
            }
        }
        return null;
    }

    // Borra un registro de ListaDeseos y tapa el hueco
    public static void quitarDeseoEn(int g) {
        for (int i = g; i < contadorDeseos - 1; i++) {
            ListaDeseos[i] = ListaDeseos[i + 1];
        }
        ListaDeseos[contadorDeseos - 1] = new String[5];
        contadorDeseos--;
    }

    // Borra toda la wishlist de un usuario (al borrar su cuenta)
    public static void quitarDeseosDe(String correo) {
        for (int i = contadorDeseos - 1; i >= 0; i--) {
            if (ListaDeseos[i][0].equals(correo)) quitarDeseoEn(i);
        }
    }

    // Pide un número de una lista de 'max' elementos y devuelve el índice, o -1 si es inválido
    public static int seleccionarIndice(int max) {
        System.out.print("Número: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx >= 0 && idx < max) return idx;
        } catch (NumberFormatException e) {
            // cae al return -1
        }
        return -1;
    }

    // ==========================================================
    // MODULO DE GESTION DE USUARIOS (solo administradores)
    // ==========================================================
    public static void menuUsuarios() {
        String entrada;
        do {
            System.out.println("\n--- GESTIÓN DE USUARIOS ---");
            System.out.println("1. Consultar usuarios");
            System.out.println("2. Editar usuario");
            System.out.println("3. Borrar usuario");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");
            entrada = scanner.nextLine().trim();

            switch (entrada) {
                case "1":
                    listarUsuarios();
                    break;
                case "2":
                    editarUsuario();
                    break;
                case "3":
                    borrarUsuario();
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
            // Si el admin se quita su propio rango, ya no puede seguir aquí
        } while (!entrada.equals("0") && EsAdmin);
    }

    // "obtiene usuarios" + "muestra lista" (no muestra contraseñas)
    public static void listarUsuarios() {
        System.out.println("\n--- LISTA DE USUARIOS ---");
        for (int i = 0; i < contadorUsuarios; i++) {
            String rango = ListaUsuarios[i][2].equals("2") ? "Administrador" : "Usuario";
            System.out.println((i + 1) + ". " + ListaUsuarios[i][0]
                    + " | " + ListaUsuarios[i][1] + " | " + rango);
        }
    }

    // "selecciona y edita usuario" -> "envía acción y datos"
    public static void editarUsuario() {
        listarUsuarios();
        int idx = seleccionarIndice(contadorUsuarios);
        if (idx == -1) {
            System.out.println("Mensaje error: usuario no válido.");
            return;
        }

        System.out.println("(Enter para mantener el valor actual)");
        System.out.print("Nombre [" + ListaUsuarios[idx][0] + "]: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Correo [" + ListaUsuarios[idx][1] + "]: ");
        String correo = scanner.nextLine().trim();
        System.out.print("Rango (1=Usuario, 2=Admin) [" + ListaUsuarios[idx][2] + "]: ");
        String rango = scanner.nextLine().trim();
        System.out.print("Contraseña nueva: ");
        String clave = scanner.nextLine();

        if (nombre.isEmpty()) nombre = ListaUsuarios[idx][0];
        if (correo.isEmpty()) correo = ListaUsuarios[idx][1];
        if (rango.isEmpty())  rango  = ListaUsuarios[idx][2];
        if (clave.isEmpty())  clave  = ListaUsuarios[idx][3];

        // "validar datos" -> rama de ¿Éxito?
        String error = validarDatos(idx, correo, rango);
        if (error != null) {
            System.out.println("Mensaje error: " + error);
            return;
        }

        // Si cambia el correo, su wishlist debe seguir a la cuenta
        String correoAnterior = ListaUsuarios[idx][1];
        if (!correoAnterior.equals(correo)) {
            for (int i = 0; i < contadorDeseos; i++) {
                if (ListaDeseos[i][0].equals(correoAnterior)) ListaDeseos[i][0] = correo;
            }
        }

        // "modifica usuario"
        ListaUsuarios[idx][0] = nombre;
        ListaUsuarios[idx][1] = correo;
        ListaUsuarios[idx][2] = rango;
        ListaUsuarios[idx][3] = clave;
        if (idx == usuarioActual) EsAdmin = rango.equals("2");

        System.out.println("Mensaje éxito: usuario modificado.");
    }

    // Devuelve null si todo está bien, o el texto del error
    public static String validarDatos(int idx, String correo, String rango) {
        if (!correo.contains("@")) return "El correo no es válido.";
        if (!rango.equals("1") && !rango.equals("2")) return "Rango incorrecto.";
        for (int i = 0; i < contadorUsuarios; i++) {
            if (i != idx && ListaUsuarios[i][1].equals(correo))
                return "Ese correo ya está registrado.";
        }
        return null;
    }

    public static void borrarUsuario() {
        listarUsuarios();
        int idx = seleccionarIndice(contadorUsuarios);
        if (idx == -1) {
            System.out.println("Mensaje error: usuario no válido.");
            return;
        }
        if (idx == usuarioActual) {
            System.out.println("Mensaje error: no puedes borrar tu propia cuenta.");
            return;
        }
        System.out.print("¿Seguro que desea borrar a " + ListaUsuarios[idx][0] + "? (s/n): ");
        if (!scanner.nextLine().trim().equalsIgnoreCase("s")) {
            System.out.println("Operación cancelada.");
            return;
        }

        // Se borra también su wishlist
        quitarDeseosDe(ListaUsuarios[idx][1]);

        // Recorre el arreglo para tapar el hueco
        for (int i = idx; i < contadorUsuarios - 1; i++) {
            ListaUsuarios[i] = ListaUsuarios[i + 1];
        }
        ListaUsuarios[contadorUsuarios - 1] = new String[4];
        contadorUsuarios--;
        if (idx < usuarioActual) usuarioActual--; // ajusta el índice de la sesión

        System.out.println("Mensaje éxito: usuario borrado.");
    }
}


// Tratar de hacer los siguientes modulos en este mismo archivo
// para asi verificar la compatibilidad entre todos los modulos