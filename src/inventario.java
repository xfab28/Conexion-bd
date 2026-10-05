import java.sql.*;
import java.util.Scanner;

public class inventario {
    static Scanner s1 = new Scanner(System.in);
    static String url = "jdbc:sqlite:inventario.sqlite";
    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE INVENTARIO ===");

        boolean menu = false;
        int opcion = 0;

        do {
            do {
                System.out.println("\nMenú");
                System.out.println("1. Agregar datos");
                System.out.println("2. Actuailizar base de datos");
                System.out.println("3. Eliminar datos");
                System.out.println("4. Ver base de datos");
                System.out.println("5. Salir");
                System.out.print("\nTu opcion: ");
                opcion = s1.nextInt();
            } while (opcion < 0 || opcion > 5);

            switch (opcion) {
                case 1: agregar();
                    break;
                case 2: actualizarBaseDatos();
                    break;
                case 3: eliminar();
                    break;
                case 4: mostrarBaseDatos();
                    break;
                case 5: menu = true;
                    break;
                default: System.out.println("Error");
                    break;
            }
        } while (menu == false);
    }

    public static void agregar() {
        String nombre;
        int cantidad = 0;
        String estado;

        System.out.println("\n=== AGREGANDO DATOS ===");
        System.out.print("Nombre del producto: ");
        nombre = s1.next();

        System.out.print("Cantidad: ");
        cantidad = s1.nextInt();

        System.out.print("Estado: ");
        estado = s1.next();

        try(Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()) {

            stmt.execute("INSERT INTO articulos (nombre, cantidad, estado) VALUES" +
                "('" + nombre + "'" + "," + cantidad + "," + "'" + estado + "')"
            );

            System.out.println("Datos agregados con exito");

            mostrarBaseDatos();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        
    }

    public static void actualizarBaseDatos() {
        String sql;
        int fila;
        int columna = 0;

        String nuevoNombre;
        int nuevaCantidad = 0;
        String nuevoEstado;

        mostrarBaseDatos();

        System.out.print("\n¿Que fila deseas actualizar?: ");
        fila = s1.nextInt();

        do {
            System.out.println("\nColumnas");
            System.out.println("1. Nombre");
            System.out.println("2. Cantidad");
            System.out.println("3. Estado");
            System.out.println("4. Todas");
            System.out.print("Tu opcion: ");
            columna = s1.nextInt();
        } while (columna < 0 || columna > 4);

        switch (columna) {
            case 1:
                sql = "UPDATE articulos SET nombre = ? WHERE id = ?";

                try (Connection conn = DriverManager.getConnection(url);
                    PreparedStatement ps = conn.prepareStatement(sql)){

                    System.out.print("\nNombre nuevo: ");
                    nuevoNombre = s1.next();

                    ps.setString(1, nuevoNombre);
                    ps.setInt(2, fila);

                    ps.executeUpdate();

                    System.out.println("Actualizacion realizada con exito");

                    mostrarBaseDatos();
                } catch (SQLException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;

            case 2:
                sql = "UPDATE articulos SET cantidad = ? WHERE id = ?";

                try (Connection conn = DriverManager.getConnection(url);
                    PreparedStatement ps = conn.prepareStatement(sql)){

                    System.out.print("\nCantidad nueva: ");
                    nuevaCantidad = s1.nextInt();

                    ps.setInt(1, nuevaCantidad);
                    ps.setInt(2, fila);

                    ps.executeUpdate();

                    System.out.println("Actualizacion realizada con exito");

                    mostrarBaseDatos();
                } catch (SQLException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;

            case 3:
                sql = "UPDATE articulos SET estado = ? WHERE id = ?";

                try (Connection conn = DriverManager.getConnection(url);
                    PreparedStatement ps = conn.prepareStatement(sql)){
                    
                    System.out.print("\nEstado nuevo: ");
                    nuevoEstado = s1.next();

                    ps.setString(1, nuevoEstado);
                    ps.setInt(2, fila);

                    ps.executeUpdate();

                    System.out.println("Actualizacion realizada con exito");

                    mostrarBaseDatos();
                } catch (SQLException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;
                
            case 4:
                sql = "UPDATE articulos SET nombre = ?, cantidad = ?, estado = ? WHERE id = ?";

                try (Connection conn = DriverManager.getConnection(url);
                    PreparedStatement ps = conn.prepareStatement(sql)){
                    
                    System.out.print("\nNombre nuevo: ");
                    nuevoNombre = s1.next();
                    System.out.print("Cantidad nueva: ");
                    nuevaCantidad = s1.nextInt();
                    System.out.println("Estado nuevo: ");
                    nuevoEstado = s1.next();

                    ps.setString(1, nuevoNombre);
                    ps.setInt(2, nuevaCantidad);
                    ps.setString(3, nuevoEstado);
                    ps.setInt(4, fila);

                    ps.executeUpdate();

                    System.out.println("Actualizacion realizada con exito");

                    mostrarBaseDatos();
                } catch (SQLException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            default: System.out.println("Error intente de nuevo");
                break;
        }
    }

    public static void eliminar() {
        String sql = "DELETE FROM articulos WHERE id = ?";
        int lineaEliminar = 0;

        try (Connection conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(sql)){
            
            mostrarBaseDatos();

            System.out.print("\nIngrese la fila que desea eliminar: ");
            lineaEliminar = s1.nextInt();

            ps.setInt(1, lineaEliminar);

            ps.executeUpdate();

            System.out.println("Datos borrados con exito");

            mostrarBaseDatos();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void mostrarBaseDatos () {
        try(Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()) {
            
            ResultSet rs = stmt.executeQuery("SELECT * FROM articulos");
            System.out.println("\nNombre | Cantidad | Estado");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " +
                rs.getString("nombre") + " | " +
                rs.getInt("cantidad") + " | " +
                rs.getString("estado"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
