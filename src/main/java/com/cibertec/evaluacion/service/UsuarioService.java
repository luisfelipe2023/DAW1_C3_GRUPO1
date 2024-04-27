package com.cibertec.evaluacion.service;

import com.cibertec.evaluacion.model.bd.Rol;
import com.cibertec.evaluacion.model.bd.Usuario;
import com.cibertec.evaluacion.repository.RolRepository;
import com.cibertec.evaluacion.repository.UsuarioRepository;
import lombok.AllArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@AllArgsConstructor
@Service
public class UsuarioService implements IUsuarioService  {
    private UsuarioRepository usuarioRepository;
    private RolRepository rolRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    // Método para buscar un usuario por su nombre de usuario
    @Override
    public Usuario findUserByNomUsuario(String nomusuario){
        return usuarioRepository.findByNomusuario(nomusuario);
    }

    // Método para guardar un nuevo usuario en la base de datos
    @Override
    public Usuario guardarUsuario(Usuario usuario){
        // Se encripta la contraseña antes de guardarla en la base de datos
        usuario.setPassword(bCryptPasswordEncoder.encode("123456"));
        usuario.setActivo(true);
        // Se busca el rol "ADMIN" que se asignará al usuario
        Rol usuarioRol = rolRepository.findByNomrol("ADMIN");
        usuario.setRoles(new HashSet<>(Arrays.asList(usuarioRol)));
        // Se guarda el usuario en la base de datos y se retorna el usuario guardado
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        // Este método devuelve una lista de todos los usuarios en la base de datos
        return usuarioRepository.findAll(); // Utiliza el método findAll() de la interfaz JpaRepository para recuperar todos los usuarios
    }

    @Override
    public Usuario obtenerUsuarioxId(int id) {
        // Este método devuelve un objeto de usuario por su ID
        Usuario usuario = usuarioRepository.findById(id).orElse(null); // Utiliza el método findById() de la interfaz JpaRepository para recuperar un usuario por su ID
        usuario.setPassword(""); // Establece la contraseña de usuario en una cadena vacía para evitar exponerla
        return usuario;
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        // Este método actualiza un objeto de usuario en la base de datos
        usuarioRepository.actualizarUsuario( // Utiliza un método de actualización personalizado de la interfaz UsuarioRepository
                usuario.getNombres(), usuario.getApellidos(), // Obtiene los nombres y apellidos del usuario
                usuario.getActivo(), usuario.getIdusuario() // Obtiene el estado de activación y el ID del usuario
        );
    }


}