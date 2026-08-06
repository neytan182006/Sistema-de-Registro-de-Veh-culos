package app;

import dao.VehiculoDAO;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    private static final Scanner TECLADO = new Scanner(System.in);
    private static final VehiculoDAO vehiculoDAO = new VehiculoDAO();

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opcion: ");

            try {
                switch (opcion) {
                    case 1 -> vehiculoDAO.listar();
                    case 2 -> registrarVehiculo();
                    case 3 -> buscarPorPlaca();
                    case 4 -> eliminarVehiculo();
                    case 0 -> System.out.println("Hasta luego.");
                    default -> System.out.println("Opcion invalida.");
                }
            } catch (SQLException e) {
                System.out.println("Error de base de datos: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println("\n=== SISTEMA DE REGISTRO DE VEHICULOS ===");
        System.out.println("1. Listar vehiculos");
        System.out.println("2. Registrar vehiculo");
        System.out.println("3. Buscar vehiculo por placa");
        System.out.println("4. Eliminar vehiculo");
        System.out.println("0. Salir");
    }

    private static void registrarVehiculo() throws SQLException {
        System.out.print("Placa: ");
        String placa = TECLADO.nextLine();
        System.out.print("Marca: ");
        String marca = TECLADO.nextLine();
        System.out.print("Modelo: ");
        String modelo = TECLADO.nextLine();
        int anio = leerEntero("Anio: ");
        int idPropietario = leerEntero("Id del propietario: ");

        int id = vehiculoDAO.registrar(placa, marca, modelo, anio, idPropietario);
        System.out.println("Vehiculo registrado con id " + id);
    }

    private static void buscarPorPlaca() throws SQLException {
        System.out.print("Placa a buscar: ");
        String placa = TECLADO.nextLine();
        vehiculoDAO.buscarPorPlaca(placa);
    }

    private static void eliminarVehiculo() throws SQLException {
        int id = leerEntero("Id del vehiculo a eliminar: ");
        boolean eliminado = vehiculoDAO.eliminar(id);
        System.out.println(eliminado ? "Vehiculo eliminado." : "No existe un vehiculo con ese id.");
    }

    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!TECLADO.hasNextInt()) {
            System.out.print("Ingrese un numero valido: ");
            TECLADO.next();
        }
        int valor = TECLADO.nextInt();
        TECLADO.nextLine();
        return valor;
    }
}
