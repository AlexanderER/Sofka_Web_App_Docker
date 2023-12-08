package EPA.Cuenta_Bancaria_Web.useCase;

import EPA.Cuenta_Bancaria_Web.drivenAdapters.bus.RabbitMqPublisher;
import EPA.Cuenta_Bancaria_Web.drivenAdapters.repositorios.I_RepositorioCuentaMongo;
import EPA.Cuenta_Bancaria_Web.models.Mongo.M_ClienteMongo;
import EPA.Cuenta_Bancaria_Web.models.Mongo.M_CuentaMongo;
import EPA.Cuenta_Bancaria_Web.useCase.cuenta.useCaseCuentaCreate;
import EPA.Cuenta_Bancaria_Web.useCase.cuenta.useCaseCuentaGetAll;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import EPA.Cuenta_Bancaria_Web.models.DTO.M_Cliente_DTO;
import EPA.Cuenta_Bancaria_Web.models.DTO.M_Cuenta_DTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UseCaseCuentaTest
{
    @Mock
    private I_RepositorioCuentaMongo cuentaRepository;

    @Mock
    private RabbitMqPublisher eventBus;

    @InjectMocks
    private useCaseCuentaGetAll getAllUseCase;

    @Test
    @DisplayName("UseCase Cuenta Test: Listar Cuentas")
    public void testGetAllCuentas()
    {
        M_CuentaMongo cuenta1 = new M_CuentaMongo("1",
                                new M_ClienteMongo("1", "Alexander"),
                                BigDecimal.ZERO);

        M_CuentaMongo cuenta2 = new M_CuentaMongo("2",
                                new M_ClienteMongo("2", "Pepe"),
                                BigDecimal.ZERO);

        when(cuentaRepository.findAll()).thenReturn(Flux.just(cuenta1, cuenta2));

        Flux<M_Cuenta_DTO> result = getAllUseCase.get();

        List<M_Cuenta_DTO> expectedList = Arrays.asList(
                                                         new M_Cuenta_DTO("1", new M_Cliente_DTO("1", "Alexander"), BigDecimal.ZERO),
                                                         new M_Cuenta_DTO("2", new M_Cliente_DTO("2", "Pepe"), BigDecimal.ZERO)
                                                       );

        Assertions.assertThat(result.collectList().block()).isEqualTo(expectedList);
    }
}
