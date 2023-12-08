package EPA.Cuenta_Bancaria_Web.models.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Objects;

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

    public M_Cliente_DTO()
    {
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        M_Cliente_DTO that = (M_Cliente_DTO) object;
        return Objects.equals(id, that.id) && Objects.equals(nombre, that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }
}
