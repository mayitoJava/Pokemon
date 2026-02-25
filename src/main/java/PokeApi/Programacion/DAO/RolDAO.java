package PokeApi.Programacion.DAO;

import PokeApi.Programacion.ML.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolDAO extends JpaRepository<Rol, Long> {

    Rol findByNombre(String nombre);
}