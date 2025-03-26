package com.grupo8.role.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.grupo8.role.config.OracleConnectionUtil;
import com.grupo8.role.model.Roles;

public class RolesDao {

    public void insertar(Roles rol) throws Exception {
        String sql = "INSERT INTO ROLES (NOMBRE, DESCRIPCION) VALUES (?, ?)";
        try (Connection conn = OracleConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, rol.getNombre());
            ps.setString(2, rol.getDescripcion());
            ps.executeUpdate();
        }
    }

    public Roles buscarPorId(Long id) throws Exception {
        String sql = "SELECT * FROM ROLES WHERE ID = ?";
        try (Connection conn = OracleConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Roles(
                    rs.getLong("ID"),
                    rs.getString("NOMBRE"),
                    rs.getString("DESCRIPCION")
                );
            }
        }
        return null;
    }

    public List<Roles> obtenerTodos() throws Exception {
        List<Roles> lista = new ArrayList<>();
        String sql = "SELECT * FROM ROLES";
        try (Connection conn = OracleConnectionUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Roles rol = new Roles(
                    rs.getLong("ID"),
                    rs.getString("NOMBRE"),
                    rs.getString("DESCRIPCION")
                );
                lista.add(rol);
            }
        }
        return lista;
    }

    public void actualizar(Roles rol) throws Exception {
        String sql = "UPDATE ROLES SET NOMBRE = ?, DESCRIPCION = ?, WHERE ID = ?";
        try (Connection conn = OracleConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, rol.getNombre());
            ps.setString(2, rol.getDescripcion());
            ps.setLong(3, rol.getId());
            ps.executeUpdate();
        }
    }

    public void eliminar(Long id) throws Exception {
        String sql = "DELETE FROM PACIENTE WHERE ID = ?";
        try (Connection conn = OracleConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

}
