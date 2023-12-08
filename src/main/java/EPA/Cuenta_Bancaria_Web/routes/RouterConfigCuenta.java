package EPA.Cuenta_Bancaria_Web.routes;

import EPA.Cuenta_Bancaria_Web.handlers.HandlerCuenta;
import EPA.Cuenta_Bancaria_Web.models.DTO.M_Cuenta_DTO;
import EPA.Cuenta_Bancaria_Web.useCase.cuenta.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfigCuenta
{
    /*
    @Autowired
    private HandlerCuenta cuenta;

    @Bean
    public RouterFunction<ServerResponse> routerCuenta()
    {
        return RouterFunctions.route()
                .GET("/Cuentas/routes/listar_cuentas", cuenta::listarTodasLasCuentas)
                .build();
    }
     */

    @Bean
    public RouterFunction<ServerResponse> getAllCuentaRouter(useCaseCuentaGetAll useCase){
        return route(GET("/Cuentas/routes/listar_cuentas"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase.get(), M_Cuenta_DTO.class)));
    }

    @Bean
    public RouterFunction<ServerResponse> createCuentaRouter(useCaseCuentaCreate useCase){
        return route(POST("/Cuentas/routes/Crear").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(M_Cuenta_DTO.class)
                        .flatMap(useCase::apply)
                        .flatMap(result -> ServerResponse.ok()
                                                         .contentType(MediaType.APPLICATION_JSON)
                                                         .bodyValue(result))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build())
        );
    }
}
