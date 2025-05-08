package com.grupo8.role.model;

import java.time.format.DateTimeFormatter;

public class UserRolesDTO {
    private Long id;
    private Long user_id;
    private Long role_id;
    private String fecha_asignacion;

    public UserRolesDTO(UserRoles ur) {
        this.id = ur.getId();
        this.user_id = ur.getUser_id();
        this.role_id = ur.getRole_id();
        this.fecha_asignacion = ur.getFecha_asignacion() != null
            ? ur.getFecha_asignacion().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getRole_id() {
        return role_id;
    }

    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }

    public String getFecha_asignacion() {
        return fecha_asignacion;
    }

    public void setFecha_asignacion(String fecha_asignacion) {
        this.fecha_asignacion = fecha_asignacion;
    }

   
}
