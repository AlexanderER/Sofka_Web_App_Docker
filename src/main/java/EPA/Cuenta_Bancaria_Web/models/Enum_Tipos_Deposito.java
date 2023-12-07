package EPA.Cuenta_Bancaria_Web.models;

public enum Enum_Tipos_Deposito
{
    CAJERO,
    SUCURSAL,
    OTRA_CUENTA;

    @Override
    public String toString()
    {
        String sTipo = "Indefinido";

        switch (this)
        {
            case CAJERO: sTipo = "CAJERO"; break;
            case SUCURSAL: sTipo = "SUCURSAL"; break;
            case OTRA_CUENTA: sTipo = "OTRA_CUENTA"; break;
        }

        return sTipo;
    }
}
