package com.grupo8.role.function;

import java.util.List;
import java.util.Optional;

import com.grupo8.role.dao.RolesDao;
import com.grupo8.role.model.Roles;
import com.grupo8.role.util.FileSearchUtil;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.BindingName;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

public class RoleFunctions {

    private final RolesDao rolesDao = new RolesDao();

    @FunctionName("createRole")
    public HttpResponseMessage crearRoles(
            @HttpTrigger(name = "req", methods = {
                    HttpMethod.POST }, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<Roles>> request,
            final ExecutionContext context) {

        Roles rol = request.getBody().orElse(null);
        if (rol == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Rol vacío").build();
        }

        try {
            rolesDao.insertar(rol);
            return request.createResponseBuilder(HttpStatus.CREATED).body("Rol creado").build();
        } catch (Exception e) {
            context.getLogger().severe(e.getMessage());
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al insertar Rol")
                    .build();
        }
    }

    @FunctionName("getRole")
    public HttpResponseMessage obtenerRolesPorId(
            @HttpTrigger(name = "req", methods = {
                    HttpMethod.GET }, route = "getRole/{id}", authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            @BindingName("id") String id,
            final ExecutionContext context) {

        try {
            Roles rol = rolesDao.buscarPorId(Long.parseLong(id));
            if (rol == null) {
                return request.createResponseBuilder(HttpStatus.NOT_FOUND).body("Rol no encontrado").build();
            }
            return request.createResponseBuilder(HttpStatus.OK).body(rol).build();
        } catch (Exception e) {
            context.getLogger().severe(e.getMessage());
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al consultar Rol")
                    .build();
        }
    }

    @FunctionName("getAllRoles")
    public HttpResponseMessage obtenerTodosLosRoless(
            @HttpTrigger(name = "req", methods = {
                    HttpMethod.GET }, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {

        try {
            List<Roles> rols = rolesDao.obtenerTodos();
            return request.createResponseBuilder(HttpStatus.OK).body(rols).build();
        } catch (Exception e) {
            context.getLogger().severe(e.getMessage());
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener la lista")
                    .build();
        }
    }

    @FunctionName("updateRole")
    public HttpResponseMessage actualizarRoles(
            @HttpTrigger(name = "req", methods = {
                    HttpMethod.PUT }, route = "updateRole/{id}", authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<Roles>> request,
            @BindingName("id") String id,
            final ExecutionContext context) {

        Roles rol = request.getBody().orElse(null);
        if (rol == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Rol vacío").build();
        }

        try {
            Roles rolSearch = rolesDao.buscarPorId(Long.parseLong(id));
            if (rolSearch == null) {
                return request.createResponseBuilder(HttpStatus.NOT_FOUND).body("Rol no encontrado").build();
            }
            rol.setId(Long.parseLong(id));
            rolesDao.actualizar(rol);
            return request.createResponseBuilder(HttpStatus.OK).body("Rol actualizado").build();
        } catch (Exception e) {
            context.getLogger().severe(e.getMessage());
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar Rol")
                    .build();
        }
    }

    @FunctionName("deleteRole")
    public HttpResponseMessage eliminarRoles(
            @HttpTrigger(name = "req", methods = {
                    HttpMethod.DELETE }, route = "deleteRole/{id}", authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            @BindingName("id") String id,
            final ExecutionContext context) {

        try {
            Roles rolSearch = rolesDao.buscarPorId(Long.parseLong(id));
            if (rolSearch == null) {
                return request.createResponseBuilder(HttpStatus.NOT_FOUND).body("Rol no encontrado").build();
            }
            rolesDao.eliminar(Long.parseLong(id));
            return request.createResponseBuilder(HttpStatus.OK).body("Rol eliminado").build();
        } catch (Exception e) {
            context.getLogger().severe(e.getMessage());
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar Rol")
                    .build();
        }
    }

    @FunctionName("buscarArchivos")
    public HttpResponseMessage buscarArchivos(
            @HttpTrigger(name = "req", methods = {
                    HttpMethod.GET }, authLevel = AuthorizationLevel.FUNCTION, route = "buscarArchivos") HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {

        String patron = request.getQueryParameters().getOrDefault("patron", "");
        String directorio = "/home";

        try {
            List<String> archivos = FileSearchUtil.buscarArchivosEnDirectorio(directorio, patron);
            return request.createResponseBuilder(HttpStatus.OK).body(archivos).build();
        } catch (Exception e) {
            context.getLogger().severe("Error buscando archivos: " + e.getMessage());
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar archivos").build();
        }
    }

    @FunctionName("getRolesByUserId")
    public HttpResponseMessage obtenerRolesPorUsuario(
            @HttpTrigger(name = "req", methods = {
                    HttpMethod.GET }, route = "user/{userId}/roles", authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            @BindingName("userId") String userIdStr,
            final ExecutionContext context) {

        try {
            Long userId = Long.parseLong(userIdStr);
            List<String> roles = rolesDao.listarNombreRolesPorUsuario(userId);

            return request.createResponseBuilder(HttpStatus.OK).body(roles).build();

        } catch (NumberFormatException e) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("ID de usuario inválido").build();
        } catch (Exception e) {
            context.getLogger().severe(e.getMessage());
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener roles del usuario").build();
        }
    }
}
