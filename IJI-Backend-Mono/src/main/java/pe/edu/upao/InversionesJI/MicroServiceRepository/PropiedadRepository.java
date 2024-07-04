package pe.edu.upao.InversionesJI.MicroServiceRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upao.InversionesJI.MicroServiceEntity.Propiedad;

import java.util.Optional;

@Repository
public interface PropiedadRepository extends JpaRepository<Propiedad, Long> {
    Optional<Propiedad> findById(Long id);
}
