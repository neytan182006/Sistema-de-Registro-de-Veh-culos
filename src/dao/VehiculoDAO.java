package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VehiculoDAO {

    public int registrar(String placa, String marca, String modelo, int anio, int idPropietario) throws SQLException {
        String sql = "INSERT INTO VEHICULOS (Placa, Marca, Modelo, Anio, IdPropietario) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, placa);
            ps.setString(2, marca);
            ps.setString(3, modelo);
            ps.setInt(4, anio);
            ps.setInt(5, idPropietario);
            ps.executeUpdate();

            try (ResultSet generadas = ps.getGeneratedKeys()) {
                generadas.next();
                return generadas.getInt(1);
            }
        }
    }

    public void listar() throws SQLException {
        String sql = "SELECT v.IdVehiculo, v.Placa, v.Marca, v.Modelo, v.Anio, p.Nombre AS Propietario "
                + "FROM VEHICULOS v INNER JOIN PROPIETARIOS p ON v.IdPropietario = p.IdPropietario ORDER BY v.Placa";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.printf("[%d] %-10s %-12s %-15s %d | Propietario: %s%n",
                        rs.getInt("IdVehiculo"), rs.getString("Placa"), rs.getString("Marca"),
                        rs.getString("Modelo"), rs.getInt("Anio"), rs.getString("Propietario"));
            }
        }
    }

    public void buscarPorPlaca(String placa) throws SQLException {
        String sql = "SELECT v.Placa, v.Marca, v.Modelo, v.Anio, p.Nombre AS Propietario, p.Cedula "
                + "FROM VEHICULOS v INNER JOIN PROPIETARIOS p ON v.IdPropietario = p.IdPropietario WHERE v.Placa = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, placa);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("No se encontro un vehiculo con esa placa.");
                    return;
                }
                System.out.printf("Placa: %s | %s %s (%d)%n",
                        rs.getString("Placa"), rs.getString("Marca"), rs.getString("Modelo"), rs.getInt("Anio"));
                System.out.printf("Propietario: %s (cedula %s)%n", rs.getString("Propietario"), rs.getString("Cedula"));
            }
        }
    }

    public boolean eliminar(int idVehiculo) throws SQLException {
        String sql = "DELETE FROM VEHICULOS WHERE IdVehiculo = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idVehiculo);
            return ps.executeUpdate() > 0;
        }
    }
}
