package pe.edu.upao.InversionesJI.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upao.InversionesJI.Entity.Inmobiliaria;

import java.util.Optional;

@Repository
public interface InmobiliariaRepository extends JpaRepository<Inmobiliaria, Long> {
    Optional<Inmobiliaria> findByUsername(String correo);
    Optional<Inmobiliaria> findByNombreInmobiliaria(String nombreInmobiliaria);

}
