/*
package EPA.Cuenta_Bancaria_Web.useCase;

import EPA.Cuenta_Bancaria_Web.drivenAdapters.bus.RabbitMqPublisher;
import EPA.Cuenta_Bancaria_Web.drivenAdapters.repositorios.I_RepositorioCuentaMongo;
import EPA.Cuenta_Bancaria_Web.models.DTO.M_Cliente_DTO;
import EPA.Cuenta_Bancaria_Web.models.DTO.M_Cuenta_DTO;
import EPA.Cuenta_Bancaria_Web.models.Mongo.M_ClienteMongo;
import EPA.Cuenta_Bancaria_Web.models.Mongo.M_CuentaMongo;
import EPA.Cuenta_Bancaria_Web.useCase.cuenta.useCaseCuentaCreate;
import EPA.Cuenta_Bancaria_Web.useCase.cuenta.useCaseCuentaGetAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
@SpringBootTest
public class UseCaseCuentaCreateTest
{
    @Mock
    private I_RepositorioCuentaMongo cuentaRepository;

    //@Mock
    //private RabbitMqPublisher eventBus;

    @InjectMocks
    private useCaseCuentaCreate createUseCase;

    @Test
    @DisplayName("UseCase Cuenta Test: Crear Cuenta")
    public void testCreateCuenta()
    {

        M_CuentaMongo cuentaNueva = new M_CuentaMongo("3",
                new M_ClienteMongo("3", "Juan"),
                BigDecimal.valueOf(500));

        M_Cuenta_DTO cuentaNuevaDTO = new M_Cuenta_DTO("3",
                new M_Cliente_DTO("3", "Juan"),
                BigDecimal.valueOf(500));

        // Mock del RabbitMqPublisher para evitar la conexión a RabbitMQ
        RabbitMqPublisher mockEventBus = mock(RabbitMqPublisher.class);
        doNothing().when(mockEventBus).publishMessageLog("");

        when(cuentaRepository.save(cuentaNueva)).thenReturn(Mono.just(cuentaNueva));
        // Usar el mock del EventBus
        //createUseCase.setEventBus(mockEventBus);

        StepVerifier.create(createUseCase.apply(cuentaNuevaDTO))
                .expectNext(cuentaNuevaDTO)
                .verifyComplete();


    }
}


 */