package PokeApi.Programacion.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import PokeApi.Programacion.JPA.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByCorreo(String correo);
}
