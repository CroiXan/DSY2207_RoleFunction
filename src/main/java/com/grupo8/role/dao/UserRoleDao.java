package com.grupo8.role.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.grupo8.role.config.OracleConnectionUtil;
import com.grupo8.role.model.UserRoles;

public class UserRoleDao {

    public void insertar(UserRoles userRol) throws Exception {
        String sql = "INSERT INTO USER_ROLES (USER_ID, ROLE_ID) VALUES (?, ?)";
        try (Connection conn = OracleConnectionUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userRol.getUser_id());
            ps.setLong(2, userRol.getRole_id());
            ps.executeUpdate();
        }
    }

    public UserRoles buscarPorId(Long id) throws Exception {
        String sql = "SELECT * FROM USER_ROLES WHERE ID = ?";
        try (Connection conn = OracleConnectionUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new UserRoles(
                        rs.getLong("ID"),
                        rs.getLong("USER_ID"),
                        rs.getLong("ROLE_ID"),
                        rs.getTimestamp("FECHA_ASIGNACION").toLocalDateTime());
            }
        }
        return null;
    }

    public List<UserRoles> buscarPorRolId(Long id) throws Exception {
        List<UserRoles> listaUserRol = new ArrayList<>();
        String sql = "SELECT * FROM USER_ROLES WHERE ROLE_ID = ?";
        try (Connection conn = OracleConnectionUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserRoles userRol = new UserRoles(
                        rs.getLong("ID"),
                        rs.getLong("USER_ID"),
                        rs.getLong("ROLE_ID"),
                        rs.getTimestamp("FECHA_ASIGNACION").toLocalDateTime());
                listaUserRol.add(userRol);
            }
        }
        return null;
    }

    public List<UserRoles> buscarPorUserId(Long id) throws Exception {
        List<UserRoles> listaUserRol = new ArrayList<>();
        String sql = "SELECT * FROM USER_ROLES WHERE USER_ID = ?";
        try (Connection conn = OracleConnectionUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserRoles userRol = new UserRoles(
                        rs.getLong("ID"),
                        rs.getLong("USER_ID"),
                        rs.getLong("ROLE_ID"),
                        rs.getTimestamp("FECHA_ASIGNACION").toLocalDateTime());
                listaUserRol.add(userRol);
            }
        }
        return null;
    }

    public List<UserRoles> obtenerTodos() throws Exception {
        List<UserRoles> listaUserRol = new ArrayList<>();
        String sql = "SELECT * FROM USER_ROLES";
        try (Connection conn = OracleConnectionUtil.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                UserRoles userRol = new UserRoles(
                        rs.getLong("ID"),
                        rs.getLong("USER_ID"),
                        rs.getLong("ROLE_ID"),
                        rs.getTimestamp("FECHA_ASIGNACION").toLocalDateTime());
                listaUserRol.add(userRol);
            }
        }
        return listaUserRol;
    }

    public void eliminar(Long id) throws Exception {
        String sql = "DELETE FROM USER_ROLES WHERE ID = ?";
        try (Connection conn = OracleConnectionUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    public void eliminarPorRol(Long role_id) throws Exception {
        String sql = "DELETE FROM USER_ROLES WHERE ROLE_ID = ?";
        try (Connection conn = OracleConnectionUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, role_id);
            ps.executeUpdate();
        }
    }

}
