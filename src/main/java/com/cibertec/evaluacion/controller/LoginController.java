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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/auth")
public class LoginController {
    private final UsuarioService usuarioService;
    private IUsuarioService iUsuarioService;
    @GetMapping("/login")
    public String login(){
        return "backoffice/auth/frmlogin";
    }
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
}