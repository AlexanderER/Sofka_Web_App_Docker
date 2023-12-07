package EPA.Cuenta_Bancaria_Web.models.Mongo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document("M_TransaccionMongo")
@Data
public class M_TransaccionMongo
{
    private String id;
    private BigDecimal monto_transaccion;
    private BigDecimal saldo_inicial;
    private BigDecimal saldo_final;
    private BigDecimal costo_transaccion;
    private String tipo;

    //-------------------------------------------------------------------------------------------------------------------------
    private M_CuentaMongo cuenta;

    //-------------------------------------------------------------------------------------------------------------------------

    public M_TransaccionMongo(M_CuentaMongo cuenta, BigDecimal monto_transaccion, BigDecimal saldo_inicial, BigDecimal saldo_final, BigDecimal costo_tansaccion, String tipo) {
        //this.id = id;
        this.cuenta = cuenta;
        this.monto_transaccion = monto_transaccion;
        this.saldo_inicial = saldo_inicial;
        this.saldo_final = saldo_final;
        this.costo_transaccion = costo_tansaccion;
        this.tipo = tipo;
    }

    public M_TransaccionMongo()
    {

    }

    public void setId(String id) {
        this.id = id;
    }

    public M_CuentaMongo getCuenta() {
        return cuenta;
    }

    public void setCuenta(M_CuentaMongo cuenta) {
        this.cuenta = cuenta;
    }

    public BigDecimal getMonto_transaccion() {
        return monto_transaccion;
    }

    public void setMonto_transaccion(BigDecimal monto_transaccion) {
        this.monto_transaccion = monto_transaccion;
    }

    public BigDecimal getSaldo_inicial() {
        return saldo_inicial;
    }

    public void setSaldo_inicial(BigDecimal saldo_inicial) {
        this.saldo_inicial = saldo_inicial;
    }

    public BigDecimal getSaldo_final() {
        return saldo_final;
    }

    public void setSaldo_final(BigDecimal saldo_final) {
        this.saldo_final = saldo_final;
    }

    public BigDecimal getCosto_tansaccion() {
        return costo_transaccion;
    }

    public void setCosto_tansaccion(BigDecimal costo_tansaccion) {
        this.costo_transaccion = costo_tansaccion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
