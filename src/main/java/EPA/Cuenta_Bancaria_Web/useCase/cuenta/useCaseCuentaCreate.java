package EPA.Cuenta_Bancaria_Web.useCase.cuenta;

import EPA.Cuenta_Bancaria_Web.drivenAdapters.bus.RabbitMqPublisher;
import EPA.Cuenta_Bancaria_Web.drivenAdapters.repositorios.I_RepositorioCuentaMongo;
import EPA.Cuenta_Bancaria_Web.models.DTO.M_Cliente_DTO;
import EPA.Cuenta_Bancaria_Web.models.DTO.M_Cuenta_DTO;
import EPA.Cuenta_Bancaria_Web.models.Mongo.M_ClienteMongo;
import EPA.Cuenta_Bancaria_Web.models.Mongo.M_CuentaMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
public class useCaseCuentaCreate implements Function<M_Cuenta_DTO, Mono<M_Cuenta_DTO>>
{
    @Autowired
    I_RepositorioCuentaMongo repositorio_Cuenta;

    @Autowired
    private RabbitMqPublisher eventBus;

    @Override
    public Mono<M_Cuenta_DTO> apply(M_Cuenta_DTO mCuentaDto)
    {
        eventBus.publishMessageLog("[UseCase Cuenta: Create] Creando una nueva Cuenta");

        M_CuentaMongo cuenta = new M_CuentaMongo(mCuentaDto.getId(),
                                                 new M_ClienteMongo(mCuentaDto.getCliente().getId(), mCuentaDto.getCliente().getNombre()),
                                                 mCuentaDto.getSaldo_Global());

        return repositorio_Cuenta.save(cuenta)
                .doOnSuccess(cuentaModel -> {
                                                eventBus.publishMessageLog("[UseCase Cuenta: Create] Cuenta Creada: Id: " + cuentaModel.getId() + " Saldo Inicial: " + cuentaModel.getSaldo_Global().toString());
                                            })
                .map(cuentaModel-> {
                    return new M_Cuenta_DTO(cuentaModel.getId(),
                                            new M_Cliente_DTO(cuentaModel.getCliente().getId(),
                                            cuentaModel.getCliente().getNombre()),
                                            cuentaModel.getSaldo_Global());
                });
    }
}
