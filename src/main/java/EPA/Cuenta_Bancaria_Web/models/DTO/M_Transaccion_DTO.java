package EPA.Cuenta_Bancaria_Web.models.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class M_Transaccion_DTO
{
    private String id;
    private M_Cuenta_DTO cuenta;
    private BigDecimal monto_transaccion;
    private BigDecimal saldo_inicial;
    private BigDecimal saldo_final;
    private BigDecimal costo_tansaccion;
    private String tipo;

    public M_Transaccion_DTO(String id, M_Cuenta_DTO cuenta, BigDecimal monto_transaccion, BigDecimal saldo_inicial, BigDecimal saldo_final, BigDecimal costo_tansaccion, String tipo) {
        this.id = id;
        this.cuenta = cuenta;
        this.monto_transaccion = monto_transaccion;
        this.saldo_inicial = saldo_inicial;
        this.saldo_final = saldo_final;
        this.costo_tansaccion = costo_tansaccion;
        this.tipo = tipo;
    }
}
