package EPA.Cuenta_Bancaria_Web.useCase.transaccion;

import EPA.Cuenta_Bancaria_Web.drivenAdapters.bus.RabbitMqPublisher;
import EPA.Cuenta_Bancaria_Web.drivenAdapters.repositorios.I_RepositorioCuentaMongo;
import EPA.Cuenta_Bancaria_Web.drivenAdapters.repositorios.I_Repositorio_TransaccionMongo;
import EPA.Cuenta_Bancaria_Web.models.DTO.M_Cliente_DTO;
import EPA.Cuenta_Bancaria_Web.models.DTO.M_Cuenta_DTO;
import EPA.Cuenta_Bancaria_Web.models.DTO.M_Transaccion_DTO;
import EPA.Cuenta_Bancaria_Web.models.Mongo.M_TransaccionMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
public class useCaseTransaccionGetAll implements Supplier<Flux<M_Transaccion_DTO>>
{
    @Autowired
    I_Repositorio_TransaccionMongo repositorio_transaccion;

    @Autowired
    private RabbitMqPublisher eventBus;

    @Override
    public Flux<M_Transaccion_DTO> get()
    {
        eventBus.publishMessageLog("[UseCase Transaccion: GetAll] Obteniendo Listado Completo de Transacciones");

        return repositorio_transaccion.findAll()
                .map(this::getTransaccionDTO);
    }

    private M_Transaccion_DTO getTransaccionDTO(M_TransaccionMongo transactionModel)
    {
        return new M_Transaccion_DTO(
                transactionModel.getId(),
                new M_Cuenta_DTO(
                        transactionModel.getCuenta().getId(),
                        new M_Cliente_DTO(
                                transactionModel.getCuenta().getCliente().getId(),
                                transactionModel.getCuenta().getCliente().getNombre()
                        ),
                        transactionModel.getCuenta().getSaldo_Global()
                ),
                transactionModel.getMonto_transaccion(),
                transactionModel.getSaldo_inicial(),
                transactionModel.getSaldo_final(),
                transactionModel.getCosto_tansaccion(),
                transactionModel.getTipo()
        );
    }

}
