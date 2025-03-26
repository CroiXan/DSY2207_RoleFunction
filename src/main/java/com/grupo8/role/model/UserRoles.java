package com.grupo8.role.model;

import java.time.LocalDateTime;

public class UserRoles {

    private Long id;
    private Long user_id;
    private Long role_id;
    private LocalDateTime fecha_asignacion;
    
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
    public LocalDateTime getFecha_asignacion() {
        return fecha_asignacion;
    }
    public void setFecha_asignacion(LocalDateTime fecha_asignacion) {
        this.fecha_asignacion = fecha_asignacion;
    }
    
}
