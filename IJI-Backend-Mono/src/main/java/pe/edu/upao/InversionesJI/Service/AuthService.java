package pe.edu.upao.InversionesJI.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upao.InversionesJI.Entity.Agente;
import pe.edu.upao.InversionesJI.Entity.Inmobiliaria;
import pe.edu.upao.InversionesJI.Jwt.JwtService;
import pe.edu.upao.InversionesJI.Repository.AgenteRepository;
import pe.edu.upao.InversionesJI.Repository.ClienteRepository;
import pe.edu.upao.InversionesJI.Repository.InmobiliariaRepository;
import pe.edu.upao.InversionesJI.Request.LoginRequest;
import pe.edu.upao.InversionesJI.Request.RegisterClienteRequest;
import pe.edu.upao.InversionesJI.Response.AuthResponse;
import pe.edu.upao.InversionesJI.Entity.Cliente;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ClienteRepository clienteRepository;
    private final AgenteRepository agenteRepository;
    private final InmobiliariaRepository inmobiliariaRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Transactional("monolitoTransactionManager")
    public AuthResponse login(LoginRequest request) {
        System.out.println("Intento de inicio de sesión para el usuario: " + request.getCorreo());
        UserDetails userDetails = loadUserByUsername(request.getCorreo());
        Long idAgente = null;

        if (userDetails != null && passwordEncoder.matches(request.getContrasena(), userDetails.getPassword())) {
            System.out.println("Autenticación exitosa para el usuario: " + request.getCorreo());

            // Verificar si el usuario es un agente y obtener el idAgente
            Optional<Agente> agenteOptional = agenteRepository.findByUsername(request.getCorreo());
            if (agenteOptional.isPresent()) {
                Agente agente = agenteOptional.get();
                idAgente = agente.getIdAgente();
            }

            // Generar token con o sin idAgente según el rol
            String token;
            if (idAgente != null) {
                token = jwtService.getTokenAgente(userDetails, idAgente);
            } else {
                token = jwtService.getToken(userDetails);
            }

            String role = userDetails.getAuthorities().stream().findFirst().get().getAuthority();

            return AuthResponse.builder()
                    .token(token)
                    .role(role)
                    .build();
        } else {
            throw new BadCredentialsException("Credenciales incorrectas para el usuario: " + request.getCorreo());
        }
    }

    @Transactional("monolitoTransactionManager")
    public UserDetails loadUserByUsername(String correo) {
        Optional<Cliente> clienteOptional = clienteRepository.findByUsername(correo);
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            return new User(cliente.getUsername(), cliente.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("Cliente")));
        }

        Optional<Agente> agenteOptional = agenteRepository.findByUsername(correo);
        if (agenteOptional.isPresent()) {
            Agente agente = agenteOptional.get();
            return new User(agente.getUsername(), agente.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("Agente")));
        }

        Optional<Inmobiliaria> inmobiliariaOptional = inmobiliariaRepository.findByUsername(correo);
        if (inmobiliariaOptional.isPresent()) {
            Inmobiliaria inmobiliaria = inmobiliariaOptional.get();
            return new User(inmobiliaria.getUsername(), inmobiliaria.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("Inmobiliaria")));
        }
        throw new UsernameNotFoundException("Usuario no encontrado con correo: " + correo);
    }

    //Método para registar al cliente
    @Transactional("monolitoTransactionManager")
    public AuthResponse registerCliente(RegisterClienteRequest request){
        Cliente cliente = new Cliente();
        cliente.setUsername(request.getCorreo());
        cliente.setPassword(passwordEncoder.encode(request.getContrasena()));
        cliente.setRole("Cliente");
        cliente.setNombre(request.getNombre());
        cliente.setApellido(request.getApellido());
        cliente.setDni(request.getDni());
        cliente.setTelefono(request.getTelefono());
        clienteRepository.save(cliente);

        String token = jwtService.getToken(cliente);
        return AuthResponse.builder()
                .token(token)
                .role("Cliente")
                .build();
    }
}
