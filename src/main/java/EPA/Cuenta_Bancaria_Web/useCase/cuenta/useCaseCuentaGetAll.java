package EPA.Cuenta_Bancaria_Web.useCase.cuenta;

import EPA.Cuenta_Bancaria_Web.drivenAdapters.bus.RabbitMqPublisher;
import EPA.Cuenta_Bancaria_Web.drivenAdapters.repositorios.I_RepositorioCuentaMongo;
import EPA.Cuenta_Bancaria_Web.models.DTO.M_Cliente_DTO;
import EPA.Cuenta_Bancaria_Web.models.DTO.M_Cuenta_DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
public class useCaseCuentaGetAll implements Supplier<Flux<M_Cuenta_DTO>>
{
    @Autowired
    I_RepositorioCuentaMongo repositorio_Cuenta;

    @Autowired
    private RabbitMqPublisher eventBus;

    @Override
    public Flux<M_Cuenta_DTO> get()
    {
        eventBus.publishMessageLog("[UseCase Cuenta: GetAll] Obteniendo Listado Completo de Cuentas");

        return repositorio_Cuenta.findAll()
                .map(cuentaModel -> new M_Cuenta_DTO(cuentaModel.getId(),
                                                     new M_Cliente_DTO(cuentaModel.getCliente().getId(),
                                                     cuentaModel.getCliente().getNombre()),
                                                     cuentaModel.getSaldo_Global()));
    }
}
