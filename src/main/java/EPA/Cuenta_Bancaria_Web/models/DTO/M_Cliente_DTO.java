package EPA.Cuenta_Bancaria_Web.models.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class M_Cliente_DTO
{
    @NotNull(message = "[CLIENTE] [id] Campo Requerido: Id.")
    private String id;

    @NotNull(message = "[CLIENTE] [nombre] Campo Requerido: Id.")
    private String nombre;

    public M_Cliente_DTO(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}
