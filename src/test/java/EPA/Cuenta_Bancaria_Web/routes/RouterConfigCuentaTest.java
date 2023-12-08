package EPA.Cuenta_Bancaria_Web.routes;

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
public class RouterConfigCuentaTest
{
    private WebTestClient webTestClient;

    @Mock
    private useCaseCuentaGetAll getAllUseCase;

    @Mock
    private useCaseCuentaCreate createUseCase;

    @InjectMocks
    RouterConfigCuenta routerCuenta;

    @BeforeEach
    void setUp()
    {
        //webTestClient = WebTestClient.bindToRouterFunction(routerCuenta.getAllCuentaRouter(getAllUseCase)).build();
        webTestClient = WebTestClient
                .bindToRouterFunction(routerCuenta.getAllCuentaRouter(getAllUseCase)
                        .and(routerCuenta.createCuentaRouter(createUseCase)))
                .build();
    }

    @Test()
    @DisplayName("Route Cuenta Test: Listar Cuentas")
    public void testListarCuentas()
    {
        M_Cuenta_DTO cuenta = new M_Cuenta_DTO("1",
                                     new M_Cliente_DTO("1",
                                                    "Alexander"),
                                     BigDecimal.ZERO);

        when(getAllUseCase.get()).thenReturn(Flux.just(cuenta));


        webTestClient.get()
                .uri("/Cuentas/routes/listar_cuentas")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(M_Cuenta_DTO.class)
                //.contains(cuenta);
                .isEqualTo(Arrays.asList(cuenta));
    }

    @Test
    @DisplayName("Route Cuenta Test: Crear Cuenta")
    void testCrearCuenta()
    {
        M_Cuenta_DTO cuenta = new M_Cuenta_DTO("1",
                                               new M_Cliente_DTO("1",
                                        "Alexander"),
                                                BigDecimal.ZERO);

        M_Cuenta_DTO cuenta2 = new M_Cuenta_DTO("1",
                                                new M_Cliente_DTO("1",
                                         "Alexander"),
                                                BigDecimal.ZERO);

        when(createUseCase.apply(cuenta))
                .thenReturn(Mono.just(cuenta));


        webTestClient.post()
                .uri("/Cuentas/routes/Crear")
                .bodyValue(cuenta)
                .exchange()
                .expectStatus().isOk()
                .expectBody(M_Cuenta_DTO.class)
                .isEqualTo(cuenta2);
    }

    @Test
    @DisplayName("Route Cuenta Test: Crear Cuenta con error")
    void testCrearCuentaConError()
    {
        M_Cuenta_DTO cuenta = new M_Cuenta_DTO("1",
                new M_Cliente_DTO("1",
                        "Alexander"),
                BigDecimal.ZERO);

        M_Cuenta_DTO cuenta2 = new M_Cuenta_DTO("1",
                new M_Cliente_DTO("1",
                        "Pepe"),
                BigDecimal.ZERO);

        when(createUseCase.apply(cuenta))
                .thenReturn(Mono.just(cuenta));


        webTestClient.post()
                .uri("/Cuentas/routes/Crear")
                .bodyValue(cuenta)
                .exchange()
                .expectStatus().isOk()
                .expectBody(M_Cuenta_DTO.class)
                .value(response -> {
                    // Verificar que el resultado no sea igual a cuenta2
                    Assertions.assertThat(response).isNotEqualTo(cuenta2);
                });
    }


}
