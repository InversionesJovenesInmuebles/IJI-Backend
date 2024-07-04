package pe.edu.upao.PropiedadService.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.upao.PropiedadService.Entity.Propiedad;
import pe.edu.upao.PropiedadService.Repository.PropiedadRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PropiedadService {

    private final PropiedadRepository propiedadRepository;

    //Listar todas las propiedades
    public List<Propiedad> listarPropiedades(){
        return propiedadRepository.findAll();
    }

    //Listar propiedad en base a su id
    public Propiedad obtenerPropiedadPorId(Long id){
        return propiedadRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No se encontró la propiedad con el id: " + id));
    }

    //Listar propiedades en base al agente que lo ha agregado (id del agente)
    public List<Propiedad> listarPropiedadesPorAgente(Long idAgente) {
        return propiedadRepository.findByIdAgente(idAgente);
    }
}

