package EPA.Cuenta_Bancaria_Web.models.Mongo;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("M_ClienteMongo")
public class M_ClienteMongo
{
    @Id
    private String id;
    private String nombre;

    public M_ClienteMongo(String id, String nombre)
    {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
