package PokeApi.Programacion.Service;

import PokeApi.Programacion.DAO.UsuarioDAO;
import PokeApi.Programacion.ML.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Usuario usuario = usuarioDAO.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario no encontrado"));

        return new User(
                usuario.getEmail(),
                usuario.getPassword(),
                usuario.isEnabled(),
                true,
                true,
                true,
                usuario.getRoles().stream()
                        .map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
                        .collect(Collectors.toList())
        );
    }
}