package pe.edu.upao.InversionesJI.MicroServiceEntity;

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
    @Column(name = "id_foto")
    private Long idFoto;

    @Column(name = "nombre_foto")
    private String nombreFoto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_propiedad", nullable = false)
    @JsonBackReference
    private Propiedad propiedad;
}

