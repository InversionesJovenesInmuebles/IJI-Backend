package pe.edu.upao.InversionesJI.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upao.InversionesJI.Dto.CasaDto;
import pe.edu.upao.InversionesJI.Dto.DepartamentoDto;
import pe.edu.upao.InversionesJI.Dto.PropiedadDto;
import pe.edu.upao.InversionesJI.Entity.Agente;
import pe.edu.upao.InversionesJI.Jwt.JwtService;
import pe.edu.upao.InversionesJI.MicroServiceEntity.Casa;
import pe.edu.upao.InversionesJI.MicroServiceEntity.Departamento;
import pe.edu.upao.InversionesJI.MicroServiceEntity.Foto;
import pe.edu.upao.InversionesJI.MicroServiceEntity.Propiedad;
import pe.edu.upao.InversionesJI.MicroServiceRepository.PropiedadRepository;
import pe.edu.upao.InversionesJI.Repository.AgenteRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AgenteService {

    private final PropiedadRepository propiedadRepository;
    private final AgenteRepository agenteRepository;
    private final JwtService jwtService;


    //Listar datos del agente por token
    public Agente obtenerAgentePorToken(String token) {
        String correo = jwtService.getUsernameFromToken(token);
        return agenteRepository.findByUsername(correo)
                .orElseThrow(() -> new RuntimeException("Agente no encontrado con el token proporcionado"));
    }

    //Convertir la propeidad en base al tipo de propiedad
    private <T extends Propiedad> T convertirPropiedad(PropiedadDto propiedadDto, T propiedad) {
        propiedad.setLatitud(propiedadDto.getLatitud());
        propiedad.setLongitud(propiedadDto.getLongitud());
        propiedad.setPais(propiedadDto.getPais());
        propiedad.setRegion(propiedadDto.getRegion());
        propiedad.setProvincia(propiedadDto.getProvincia());
        propiedad.setDistrito(propiedadDto.getDistrito());
        propiedad.setDireccion(propiedadDto.getDireccion());
        propiedad.setDescripcion(propiedadDto.getDescripcion());
        propiedad.setOtrasComodidades(propiedadDto.getOtrasComodidades());
        propiedad.setTipoPropiedad(propiedadDto.getTipoPropiedad());
        propiedad.setAreaTerreno(propiedadDto.getAreaTerreno());
        propiedad.setCostoTotal(propiedadDto.getCostoTotal());
        propiedad.setCostoInicial(propiedadDto.getCostoInicial());
        propiedad.setCochera(propiedadDto.isCochera());
        propiedad.setCantBanos(propiedadDto.getCantBanos());
        propiedad.setCantDormitorios(propiedadDto.getCantDormitorios());
        propiedad.setCantCochera(propiedadDto.getCantCochera());
        propiedad.setIdAgente(propiedadDto.getIdAgente());

        // Asignar fotos
        List<Foto> fotos = convertirFotos(propiedadDto.getFotosUrls(), propiedad);
        propiedad.setFotos(fotos);

        return propiedad;
    }

    // Lista de fotos para las propiedades
    private List<Foto> convertirFotos(List<String> fotosUrls, Propiedad propiedad) {
        return fotosUrls.stream()
                .map(fotoUrl -> {
                    Foto foto = new Foto();
                    foto.setNombreFoto(fotoUrl);
                    foto.setPropiedad(propiedad);
                    return foto;
                })
                .collect(Collectors.toList());
    }

    //CASA

    // Agregar Casa
    @Transactional("propiedadTransactionManager")
    public void agregarCasa(CasaDto casaDto, String token) {
        Long idAgente = jwtService.getIdAgenteFromToken(token);
        Casa casa = convertirPropiedad(casaDto, new Casa());

        //Atributos de la propiedad casa
        casa.setSotano(casaDto.isSotano());
        casa.setAreaJardin(casaDto.getAreaJardin());
        casa.setAtico(casaDto.isAtico());
        casa.setJardin(casaDto.isJardin());
        casa.setCantPisos(casaDto.getCantPisos());
        casa.setIdAgente(idAgente);
        propiedadRepository.save(casa);
    }

    //Modificar datos de la casa
    @Transactional("propiedadTransactionManager")
    public void modificarCasa(Long id, CasaDto casaDto) {
        Casa casa = (Casa) propiedadRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Casa no encontrada con el ID: " + id));

        //Convierte y actualiza los campos de Propiedad
        convertirPropiedad(casaDto, casa);

        // Atributos específicos de Casa
        casa.setSotano(casaDto.isSotano());
        casa.setAreaJardin(casaDto.getAreaJardin());
        casa.setAtico(casaDto.isAtico());
        casa.setJardin(casaDto.isJardin());
        casa.setCantPisos(casaDto.getCantPisos());
        casa.setIdAgente(casaDto.getIdAgente());
        propiedadRepository.save(casa);
    }

    //Eliminar la casa del repositorio
    @Transactional("propiedadTransactionManager")
    public void eliminarCasa(Long id) {
        Casa casa = (Casa) propiedadRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Casa no encontrada"));
        propiedadRepository.delete(casa);
    }

    //DEPARTAMENTO

    // Agregar Departamento
    @Transactional("propiedadTransactionManager")
    public void agregarDepartamento(DepartamentoDto departamentoDto) {
        Departamento departamento = convertirPropiedad(departamentoDto, new Departamento());

        //Atributos de la propiedad departamento
        departamento.setPisos(departamentoDto.getPisos());
        departamento.setInterior(departamentoDto.getInterior());
        departamento.setAscensor(departamentoDto.isAscensor());
        departamento.setAreasComunes(departamentoDto.isAreasComunes());
        departamento.setAreasComunesEspecificas(departamentoDto.getAreasComunesEspecificas());
        departamento.setIdAgente(departamento.getIdAgente());
        propiedadRepository.save(departamento);
    }

    //Modificar datos del departamento
    @Transactional("propiedadTransactionManager")
    public void modificarDepartamento(Long id, DepartamentoDto departamentoDto) {
        Departamento departamento = (Departamento) propiedadRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Departamento no encontrado con el ID: " + id));

        //Convierte y actualiza los campos de Propiedad
        convertirPropiedad(departamentoDto, departamento);

        // Atributos específicos de Departamento
        departamento.setPisos(departamentoDto.getPisos());
        departamento.setInterior(departamentoDto.getInterior());
        departamento.setAscensor(departamentoDto.isAscensor());
        departamento.setAreasComunes(departamentoDto.isAreasComunes());
        departamento.setAreasComunesEspecificas(departamentoDto.getAreasComunesEspecificas());
        departamento.setIdAgente(departamento.getIdAgente());
        propiedadRepository.save(departamento);
    }

    //Eliminar el departamento del repositorio
    @Transactional("propiedadTransactionManager")
    public void eliminarDepartamento(Long id) {
        Departamento departamento = (Departamento) propiedadRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Departamento no encontrado"));
        propiedadRepository.delete(departamento);
    }
}
