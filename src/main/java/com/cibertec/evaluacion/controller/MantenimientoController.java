package com.cibertec.evaluacion.controller;

import com.cibertec.evaluacion.model.bd.Usuario;
import com.cibertec.evaluacion.model.dto.request.UsuarioRequest;
import com.cibertec.evaluacion.model.dto.response.ResultadoResponse;
import com.cibertec.evaluacion.service.IUsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para el mantenimiento de usuarios.
 */
@AllArgsConstructor
@Controller
@RequestMapping("/mantenimiento")
public class MantenimientoController {
    private IUsuarioService iUsuarioService;

    /**
     * Muestra el formulario de usuario.
     *
     * @param model Modelo para la vista
     * @return Nombre de la vista del formulario de usuario
     */
    @GetMapping("/usuario")
    public String frmUsuario(Model model){
        model.addAttribute("listausuarios",iUsuarioService.listarUsuarios());
        return "backoffice/auth/mantenimiento/formusuario";
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id ID del usuario
     * @return Usuario encontrado por su ID
     */
    @GetMapping("/usuario/{id}")
    @ResponseBody
    public Usuario obtenerUsuarioxId(@PathVariable("id") int id){
        return iUsuarioService.obtenerUsuarioxId(id);
    }

    /**
     * Registra un nuevo usuario o actualiza uno existente.
     *
     * @param usuarioRequest Datos del usuario a registrar o actualizar
     * @return Resultado de la operación de registro o actualización
     */
    @PostMapping("/usuario/registrar")
    @ResponseBody
    public ResultadoResponse registrarUsuario(@RequestBody UsuarioRequest usuarioRequest){
        String mensaje = "Usuario registrado correctamente";
        boolean respuesta = true;
        try{
            Usuario usuario = new Usuario();
            usuario.setNombres(usuarioRequest.getNombres());
            usuario.setApellidos(usuarioRequest.getApellidos());
            if(usuarioRequest.getIdusuario() > 0){
                usuario.setIdusuario(usuarioRequest.getIdusuario());
                usuario.setActivo(usuarioRequest.getActivo());
                iUsuarioService.actualizarUsuario(usuario);
            }else{
                usuario.setNomusuario(usuarioRequest.getNomusuario());
                usuario.setEmail(usuarioRequest.getEmail());
                iUsuarioService.guardarUsuario(usuario);
            }
        }catch (Exception ex){
            mensaje = "Usuario no registrado, error en la BD";
            respuesta = false;
        }
        return ResultadoResponse.builder().mensaje(mensaje)
                .respuesta(respuesta).build();
    }


    /**
     * Obtiene la lista de usuarios.
     *
     * @return Lista de usuarios
     */
    @GetMapping("/usuario/lista")
    @ResponseBody
    public List<Usuario> listarUsuarios() {
        return iUsuarioService.listarUsuarios();
    }
}
