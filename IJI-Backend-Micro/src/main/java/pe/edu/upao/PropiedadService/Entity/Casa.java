package pe.edu.upao.PropiedadService.Entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Casa")
@DiscriminatorValue("Casa")
public class Casa extends Propiedad{

    private int cantPisos;
    private int areaJardin;
    private boolean jardin;
    private boolean atico;
    private boolean sotano;
}
