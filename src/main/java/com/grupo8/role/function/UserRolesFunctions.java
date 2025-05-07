package com.grupo8.role.function;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo8.role.dao.RolesDao;
import com.grupo8.role.dao.UserRoleDao;
import com.grupo8.role.model.Roles;
import com.grupo8.role.model.UserRoles;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.BindingName;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

public class UserRolesFunctions {

    private final UserRoleDao userRoleDao = new UserRoleDao();
    private final RolesDao rolesDao = new RolesDao();

    @FunctionName("createUserRole")
    public HttpResponseMessage crearUserRole(
            @HttpTrigger(name = "req", methods = {
                    HttpMethod.POST }, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<String> request,
            final ExecutionContext context) {

        try {
            context.getLogger().info(request.getBody());
            
            ObjectMapper mapper = new ObjectMapper();
            UserRoles userRol = mapper.readValue(request.getBody(), UserRoles.class);

            if (userRol == null) {
                return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Rol de usuario vacío").build();
            }
            Roles rolSearch = rolesDao.buscarPorId(userRol.getRole_id());
            if (rolSearch == null) {
                return request.createResponseBuilder(HttpStatus.NOT_FOUND).body("Rol no encontrado").build();
            }
            userRoleDao.insertar(userRol);
            return request.createResponseBuilder(HttpStatus.CREATED).body("Rol de usuario creado").build();
        } catch (Exception e) {
            context.getLogger().severe(e.getMessage());
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al insertar Rol")
                    .build();
        }
    }

    @FunctionName("getUserRole")
    public HttpResponseMessage obtenerUserRolesPorId(
            @HttpTrigger(name = "req", methods = {
                    HttpMethod.GET }, route = "getUserRole/{id}", authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            @BindingName("id") String id,
            final ExecutionContext context) {

        try {
            UserRoles userRol = userRoleDao.buscarPorId(Long.parseLong(id));
            if (userRol == null) {
                return request.createResponseBuilder(HttpStatus.NOT_FOUND).body("Rol de usuario no encontrado").build();
            }
            return request.createResponseBuilder(HttpStatus.OK).body(userRol).build();
        } catch (Exception e) {
            context.getLogger().severe(e.getMessage());
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al consultar Rol de usuario").build();
        }
    }

    @FunctionName("getUserRoleByUser")
    public HttpResponseMessage obtenerUserRolesPorUser(
            @HttpTrigger(name = "req", methods = {
                    HttpMethod.GET }, route = "getUserRoleByUser/{id}", authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            @BindingName("id") String id,
            final ExecutionContext context) {

        try {
            List<UserRoles> userRoleList = userRoleDao.buscarPorUserId(Long.parseLong(id));
            return request.createResponseBuilder(HttpStatus.OK).body(userRoleList).build();
        } catch (Exception e) {
            context.getLogger().severe(e.getMessage());
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al consultar Rol de usuario").build();
        }
    }

    @FunctionName("getUserRoleByRoleId")
    public HttpResponseMessage obtenerRolesPorRolId(
            @HttpTrigger(name = "req", methods = {
                    HttpMethod.GET }, route = "getUserRoleByRoleId/{id}", authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            @BindingName("id") String id,
            final ExecutionContext context) {

        try {
            Roles rolSearch = rolesDao.buscarPorId(Long.parseLong(id));
            if (rolSearch == null) {
                return request.createResponseBuilder(HttpStatus.NOT_FOUND).body("Rol no encontrado").build();
            }
            List<UserRoles> userRoleList = userRoleDao.buscarPorRolId(Long.parseLong(id));
            return request.createResponseBuilder(HttpStatus.OK).body(userRoleList).build();
        } catch (Exception e) {
            context.getLogger().severe(e.getMessage());
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al consultar Rol de usuario").build();
        }
    }

    @FunctionName("getAllUserRoles")
    public HttpResponseMessage obtenerTodosLosRoless(
            @HttpTrigger(name = "req", methods = {
                    HttpMethod.GET }, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {

        try {
            List<UserRoles> userRoleList = userRoleDao.obtenerTodos();
            return request.createResponseBuilder(HttpStatus.OK).body(userRoleList).build();
        } catch (Exception e) {
            context.getLogger().severe(e.getMessage());
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener la lista")
                    .build();
        }
    }

    @FunctionName("deleteUserRole")
    public HttpResponseMessage eliminarUserRoles(
            @HttpTrigger(name = "req", methods = {
                    HttpMethod.DELETE }, route = "deleteUserRole/{id}", authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            @BindingName("id") String id,
            final ExecutionContext context) {

        try {
            UserRoles userRol = userRoleDao.buscarPorId(Long.parseLong(id));
            if (userRol == null) {
                return request.createResponseBuilder(HttpStatus.NOT_FOUND).body("Rol de usuario no encontrado").build();
            }
            userRoleDao.eliminar(Long.parseLong(id));
            return request.createResponseBuilder(HttpStatus.OK).body("Rol eliminado").build();
        } catch (Exception e) {
            context.getLogger().severe(e.getMessage());
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar Rol")
                    .build();
        }
    }

}
