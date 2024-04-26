package com.cibertec.evaluacion.service;
import com.cibertec.evaluacion.model.bd.Rol;
import com.cibertec.evaluacion.model.bd.Usuario;
import com.cibertec.evaluacion.model.dto.UsuarioSecurity;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@AllArgsConstructor
@Service
public class DetalleUsuarioService implements UserDetailsService {
    private IUsuarioService iUsuarioService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = iUsuarioService.findUserByNomUsuario(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }
        return autenticacionUsuario(usuario,
                obtenerListaRolesUsuario(usuario.getRoles()));
    }

    private List<GrantedAuthority> obtenerListaRolesUsuario(Set<Rol> listRoles){
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        for (Rol rol : listRoles){
            grantedAuthorityList.add(new SimpleGrantedAuthority(rol.getNomrol()));
        }
        return grantedAuthorityList;
    }

    private UserDetails autenticacionUsuario(
            Usuario usuario, List<GrantedAuthority> authorityList
    ){
        UsuarioSecurity usuarioSecurity = new UsuarioSecurity(
                usuario.getNomusuario(),
                usuario.getPassword(), usuario.getActivo(),
                true, true,
                true, authorityList
        );
        usuarioSecurity.setEmail(usuario.getEmail());
        usuarioSecurity.setNombre(usuario.getNombres());
        return usuarioSecurity;
    }
}