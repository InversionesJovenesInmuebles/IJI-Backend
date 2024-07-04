package pe.edu.upao.InversionesJI.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pe.edu.upao.InversionesJI.Dto.PropiedadDto;

import java.util.List;

@FeignClient(name = "PropiedadService", url = "http://localhost:8083")
public interface PropiedadFeignClient {

    @GetMapping(value = "/propiedad/listarPropiedades")
    List<PropiedadDto> listarPropiedades();

    @GetMapping(value = "/propiedad/listarPropiedad/{id}")
    PropiedadDto obtenerPropiedadPorId(@PathVariable("id") Long id);

    @GetMapping("/propiedad/listarPropiedadesAgente/{idAgente}")
    List<PropiedadDto> listarPropiedadesPorAgente(@PathVariable("idAgente") Long idAgente);
}

