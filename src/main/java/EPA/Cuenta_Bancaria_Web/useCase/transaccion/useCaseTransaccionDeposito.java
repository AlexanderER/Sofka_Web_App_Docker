package EPA.Cuenta_Bancaria_Web.useCase.transaccion;

import EPA.Cuenta_Bancaria_Web.models.DTO.M_Transaccion_DTO;
import EPA.Cuenta_Bancaria_Web.models.Enum_Tipos_Deposito;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@FunctionalInterface
public interface useCaseTransaccionDeposito
{
    Mono<M_Transaccion_DTO> apply(String idCuenta, Enum_Tipos_Deposito tipo, BigDecimal monto);
}
