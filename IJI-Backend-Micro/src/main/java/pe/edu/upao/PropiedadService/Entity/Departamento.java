package pe.edu.upao.PropiedadService.Entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Departamento")
@DiscriminatorValue("Departamento")
public class Departamento extends Propiedad {

    private int pisos;
    private int interior;
    private boolean ascensor;
    private boolean areasComunes;
    private String areasComunesEspecificas;
}
