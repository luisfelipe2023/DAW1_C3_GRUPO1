package com.cibertec.evaluacion.controller;

import com.cibertec.evaluacion.model.bd.Usuario;
import com.cibertec.evaluacion.model.dto.UsuarioSecurity;
import com.cibertec.evaluacion.service.IUsuarioService;
import com.cibertec.evaluacion.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@AllArgsConstructor
@Controller
@RequestMapping("/auth")
public class LoginController {
    private final UsuarioService usuarioService;
    private IUsuarioService iUsuarioService;
    @GetMapping("/login")
    public String login(){
        return "backoffice/auth/frmlogin";
    } //Ruta del HTML
    @GetMapping("/login-success")
    public String loginSuccess(){
        return "redirect:/auth/dashboard";
    }
    @GetMapping("/dashboard")
    public String dashboard(HttpServletRequest request){
        HttpSession session = request.getSession();
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        UsuarioSecurity usuarioSecurity = (UsuarioSecurity) userDetails;
        session.setAttribute("nomusuario", usuarioSecurity.getNombre());
        return "backoffice/auth/home";
    }



    @ModelAttribute("usuario")
    public Usuario retornarNuevoUsuarioRegistroDTO() {
        return new Usuario();
    }

    @GetMapping("/registrar")
    public String mostrarFormularioDeRegistro() {
        return "backoffice/auth/formregistrar";
    }


    @PostMapping("/registrar")
    public String registrarCuentaDeUsuario(@ModelAttribute("usuario") Usuario registroDTO) {
        usuarioService.guardarUsuario(registroDTO);
        return "redirect:/auth/login";
    }


    @GetMapping("/cambiar-contrasena")
    public String mostrarFormularioCambiarContrasena() {
        return "backoffice/auth/form-cambiar-contrasena";
    }


    @PostMapping("/cambiar-contrasena")
    public String cambiarContrasena(@RequestParam("contrasenaActual") String contrasenaActual,
                                    @RequestParam("nuevaContrasena") String nuevaContrasena,
                                    Principal principal) {

        String username = principal.getName();

        Usuario usuario = usuarioService.findUserByNomUsuario(username);

        if (!usuario.getPassword().equals(contrasenaActual)) {
            return "redirect:/auth/cambiar-contrasena?error=wrongCurrentPassword";
        }

        if (nuevaContrasena.equals(contrasenaActual)) {
            return "redirect:/auth/cambiar-contrasena?error=newPasswordSameAsCurrent";
        }

        usuario.setPassword(nuevaContrasena);
        usuarioService.cambiarContrasena(usuario);


        return "redirect:/auth/dashboard";
    }
}