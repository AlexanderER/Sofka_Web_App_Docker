package EPA.Cuenta_Bancaria_Web.routes;

import EPA.Cuenta_Bancaria_Web.models.DTO.M_Transaccion_DTO;
import EPA.Cuenta_Bancaria_Web.models.Enum_Tipos_Deposito;
import EPA.Cuenta_Bancaria_Web.useCase.transaccion.useCaseTransaccionDepositoImp;
import EPA.Cuenta_Bancaria_Web.useCase.transaccion.useCaseTransaccionGetAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import EPA.Cuenta_Bancaria_Web.models.DTO.M_Cliente_DTO;
import EPA.Cuenta_Bancaria_Web.models.DTO.M_Cuenta_DTO;
import EPA.Cuenta_Bancaria_Web.useCase.cuenta.useCaseCuentaCreate;
import EPA.Cuenta_Bancaria_Web.useCase.cuenta.useCaseCuentaGetAll;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RouterConfigTransaccionTest
{
    private WebTestClient webTestClient;

    @Mock
    private useCaseTransaccionGetAll getAllUseCase;

    @Mock
    private useCaseTransaccionDepositoImp createUseCase;

    @InjectMocks
    RouterConfigTransaccion routerTransaccion;

    @BeforeEach
    void setUp()
    {
        webTestClient = WebTestClient
                        .bindToRouterFunction(routerTransaccion.getAllTransaccionRouter(getAllUseCase)
                                .and(routerTransaccion.procesarDepositoCajeroRouter(createUseCase)))
                .build();
    }

    @Test()
    @DisplayName("Route Transaccion Test: Listar Transacciones")
    public void testListarCuentas()
    {
        M_Transaccion_DTO transaccion = new M_Transaccion_DTO(
                                                                "1",
                                                                new M_Cuenta_DTO(
                                                                                "1",
                                                                                    new M_Cliente_DTO("1", "Alexander"),
                                                                                    BigDecimal.valueOf(3000)
                                                                                ),
                                                                BigDecimal.valueOf(30),
                                                                BigDecimal.valueOf(3000),
                                                                BigDecimal.valueOf(3030),
                                                                BigDecimal.valueOf(0),
                                                                "CAJERO");

        when(getAllUseCase.get()).thenReturn(Flux.just(transaccion));


        webTestClient.get()
                .uri("/Transacciones/routes/listar_transacciones")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(M_Transaccion_DTO.class)
                //.contains(cuenta);
                .isEqualTo(Arrays.asList(transaccion));
    }

    @Test
    @DisplayName("Route Transaccion Test: Crear Transaccion")
    void testCrearCuenta()
    {
        M_Transaccion_DTO transaccion = new M_Transaccion_DTO(
                                                             "1",
                                                                new M_Cuenta_DTO(
                                                                        "1",
                                                                        new M_Cliente_DTO("1", "Alexander"),
                                                                        BigDecimal.valueOf(3000)
                                                                ),
                                                                BigDecimal.valueOf(30),
                                                                BigDecimal.valueOf(3000),
                                                                BigDecimal.valueOf(3030),
                                                                BigDecimal.valueOf(0),
                                                                "CAJERO");

        M_Transaccion_DTO transaccion2 = new M_Transaccion_DTO(
                                                                "1",
                                                                new M_Cuenta_DTO(
                                                                        "1",
                                                                        new M_Cliente_DTO("1", "Alexander"),
                                                                        BigDecimal.valueOf(3000)
                                                                ),
                                                                BigDecimal.valueOf(30),
                                                                BigDecimal.valueOf(3000),
                                                                BigDecimal.valueOf(3030),
                                                                BigDecimal.valueOf(0),
                                                                "CAJERO");

        when(createUseCase.apply("1", Enum_Tipos_Deposito.CAJERO, BigDecimal.valueOf(30)))
                .thenReturn(Mono.just(transaccion));

        // Proporciona valores para los parámetros de la URI
        String idCuenta = "1";
        BigDecimal monto = BigDecimal.valueOf(30);

        webTestClient.post()
                .uri("/Transacciones/routes/Crear/Deposito/Cajero/{id_Cuenta}/{monto}", idCuenta, monto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(M_Transaccion_DTO.class)
                .isEqualTo(transaccion);
    }

}
