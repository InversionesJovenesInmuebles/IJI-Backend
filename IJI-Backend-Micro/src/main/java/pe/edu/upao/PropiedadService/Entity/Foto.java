package pe.edu.upao.PropiedadService.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "Foto")
@DiscriminatorValue("Foto")
public class Foto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foto_seq")
    @SequenceGenerator(name = "foto_seq", sequenceName = "foto_sequence", allocationSize = 1)
    private Long idFoto;

    private String nombreFoto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPropiedad", nullable = false)
    @JsonBackReference
    private Propiedad propiedad;
}
