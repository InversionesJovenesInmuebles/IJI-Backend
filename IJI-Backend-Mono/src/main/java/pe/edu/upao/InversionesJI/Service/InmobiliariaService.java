package pe.edu.upao.InversionesJI.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upao.InversionesJI.Entity.Agente;
import pe.edu.upao.InversionesJI.Entity.Inmobiliaria;
import pe.edu.upao.InversionesJI.Jwt.JwtService;
import pe.edu.upao.InversionesJI.Repository.AgenteRepository;
import pe.edu.upao.InversionesJI.Repository.InmobiliariaRepository;
import pe.edu.upao.InversionesJI.Request.RegisterAgenteRequest;
import pe.edu.upao.InversionesJI.Request.RegisterInmobiliariaRequest;
import pe.edu.upao.InversionesJI.Response.AuthResponse;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InmobiliariaService {

    private final InmobiliariaRepository inmobiliariaRepository;
    private final AgenteRepository agenteRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    //Agregar Inmobiliaria
    @Transactional("monolitoTransactionManager")
    public AuthResponse agregarInmobiliaria(RegisterInmobiliariaRequest request) {
        System.out.println("Solicitud para agregar inmobiliaria recibida: " + request);
        Inmobiliaria inmobiliaria = new Inmobiliaria();
        inmobiliaria.setNombreInmobiliaria(request.getNombreInmobiliaria());
        inmobiliaria.setRole("Inmobiliaria");
        inmobiliaria.setUsername(request.getCorreo());
        inmobiliaria.setPassword(passwordEncoder.encode(request.getContrasena()));
        inmobiliaria.setRuc(request.getRuc());
        inmobiliaria.setTelefonoContacto(request.getTelefonoContacto());
        inmobiliaria.setDireccion(request.getDireccion());
        inmobiliariaRepository.save(inmobiliaria);

        String token = jwtService.getToken(inmobiliaria);
        return AuthResponse.builder()
                .token(token)
                .role("Inmobiliaria")
                .build();
    }

    // Listar datos de la inmobiliaria por token
    public Inmobiliaria obtenerInmobiliariaPorToken(String token) {
        String correo = jwtService.getUsernameFromToken(token);
        return inmobiliariaRepository.findByUsername(correo)
                .orElseThrow(() -> new RuntimeException("Inmobiliaria no encontrada para el token proporcionado"));
    }

    //AGENTE

    //Listar agentes por inmobiliaria
    public List<Agente> listarAgentesInmobiliaria(String nombreInmobiliaria) {
        return agenteRepository.findByNombreInmobiliaria(nombreInmobiliaria);
    }

    //Listar a todos los agentes
    public List<Agente> listarAgentes() {
        return agenteRepository.findAll();
    }

    // Obtener agente por ID
    public Agente obtenerAgentePorId(Long id) {
        return agenteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agente no encontrado con el ID: "+ id));
    }

    //Agregar a un agente
    @Transactional("monolitoTransactionManager")
    public AuthResponse agregarAgente(RegisterAgenteRequest request) {
        System.out.println("Solicitud para agregar agentes recibida: " + request);

        // Buscar la inmobiliaria por nombre
        Inmobiliaria inmobiliaria;
        inmobiliaria = inmobiliariaRepository.findByNombreInmobiliaria(request.getNombreInmobiliaria())
                .orElseThrow(() -> new UsernameNotFoundException("Inmobiliaria no encontrada"));

        Agente agente = new Agente();
        agente.setNombre(request.getNombre());
        agente.setApellido(request.getApellido());
        agente.setRole("Agente");
        agente.setInmobiliaria(inmobiliaria);
        inmobiliaria.addAgente(agente);
        agente.setUsername(request.getCorreo());
        agente.setPassword(passwordEncoder.encode(request.getContrasena()));
        agente.setDni(request.getDni());
        agente.setTelefono(request.getTelefono());
        agente.setNombreInmobiliaria(request.getNombreInmobiliaria());
        agenteRepository.save(agente);

        String token = jwtService.getToken(agente);
        return AuthResponse.builder()
                .token(token)
                .role("Agente")
                .build();
    }

    //Modificar datos del agente
    @Transactional("monolitoTransactionManager")
    public Agente modificarAgente(Long id, RegisterAgenteRequest request) {
        Agente agente = agenteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agente no encontrado con el ID: " + id));

        agente.setNombre(request.getNombre());
        agente.setApellido(request.getApellido());
        agente.setUsername(request.getCorreo());
        agente.setDni(request.getDni());
        agente.setTelefono(request.getTelefono());
        agente.setNombreInmobiliaria(request.getNombreInmobiliaria());

        if (request.getContrasena() != null && !request.getContrasena().isEmpty()) {
            agente.setPassword(passwordEncoder.encode(request.getContrasena()));
        }

        agenteRepository.save(agente);
        return agente;
    }

    //Eliminar al agente
    @Transactional("monolitoTransactionManager")
    public ResponseEntity<?> eliminarAgente(Long id) {
        Optional<Agente> agenteOptional = agenteRepository.findById(id);
        if (agenteOptional.isPresent()) {
            Agente agente = agenteOptional.get();
            agenteRepository.delete(agente);
            return ResponseEntity.status(HttpStatus.OK).body("Agente eliminado");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El agente no se ha encontrado");
        }
    }
}
