package EPA.Cuenta_Bancaria_Web.routes;

import EPA.Cuenta_Bancaria_Web.models.DTO.M_Transaccion_DTO;
import EPA.Cuenta_Bancaria_Web.models.Enum_Tipos_Deposito;
import EPA.Cuenta_Bancaria_Web.useCase.transaccion.useCaseTransaccionDepositoErrorImp;
import EPA.Cuenta_Bancaria_Web.useCase.transaccion.useCaseTransaccionDepositoImp;
import EPA.Cuenta_Bancaria_Web.useCase.transaccion.useCaseTransaccionGetAll;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.math.BigDecimal;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfigTransaccion
{
    @Bean
    public RouterFunction<ServerResponse> getAllTransaccionRouter(useCaseTransaccionGetAll useCase)
    {
        return route(GET("/Transacciones/routes/listar_transacciones"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase.get(), M_Transaccion_DTO.class)));
    }

    ///////////////
    //   CAJERO  //
    ///////////////
    @Bean
    public RouterFunction<ServerResponse> procesarDepositoCajeroRouter(useCaseTransaccionDepositoImp useCase)
    {
        return route(POST("/Transacciones/routes/Crear/Deposito/Cajero/{id_Cuenta}/{monto}")
                        .and(accept(MediaType.APPLICATION_JSON)),
                request -> {
                    String idCuenta = request.pathVariable("id_Cuenta");
                    BigDecimal monto = new BigDecimal(request.pathVariable("monto"));

                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(BodyInserters.fromPublisher(useCase.apply(idCuenta, Enum_Tipos_Deposito.CAJERO, monto), M_Transaccion_DTO.class))
                            .onErrorResume(throwable -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                }
        );
    }


    ////////////////
    //  SUCURSAL  //
    ////////////////
    @Bean
    public RouterFunction<ServerResponse> procesarDepositoSucursalRouter(useCaseTransaccionDepositoImp useCase)
    {
        return route(POST("/Transacciones/routes/Crear/Deposito/Sucursal/{id_Cuenta}/{monto}")
                        .and(accept(MediaType.APPLICATION_JSON)),
                request -> {
                    String idCuenta = request.pathVariable("id_Cuenta");
                    BigDecimal monto = new BigDecimal(request.pathVariable("monto"));

                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(BodyInserters.fromPublisher(useCase.apply(idCuenta, Enum_Tipos_Deposito.SUCURSAL, monto), M_Transaccion_DTO.class))
                            .onErrorResume(throwable -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                }
        );
    }


    ////////////////
    //  SUCURSAL  //
    ////////////////
    @Bean
    public RouterFunction<ServerResponse> procesarDepositoOtraCuentaRouter(useCaseTransaccionDepositoImp useCase)
    {
        return route(POST("/Transacciones/routes/Crear/Deposito/OtraCuenta/{id_Cuenta}/{monto}")
                        .and(accept(MediaType.APPLICATION_JSON)),
                request -> {
                    String idCuenta = request.pathVariable("id_Cuenta");
                    BigDecimal monto = new BigDecimal(request.pathVariable("monto"));

                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(BodyInserters.fromPublisher(useCase.apply(idCuenta, Enum_Tipos_Deposito.OTRA_CUENTA, monto), M_Transaccion_DTO.class))
                            .onErrorResume(throwable -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                }
        );
    }


    /////////////////////
    //   CAJERO ERROR  //
    /////////////////////
    @Bean
    public RouterFunction<ServerResponse> procesarDepositoCajeroManejoErrorRouter(useCaseTransaccionDepositoErrorImp useCase)
    {
        return route(POST("/Transacciones/routes/Crear/DepositoError/Cajero/{id_Cuenta}/{monto}")
                        .and(accept(MediaType.APPLICATION_JSON)),
                request -> {
                    String idCuenta = request.pathVariable("id_Cuenta");
                    BigDecimal monto = new BigDecimal(request.pathVariable("monto"));

                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(BodyInserters.fromPublisher(useCase.apply(idCuenta, Enum_Tipos_Deposito.CAJERO, monto), M_Transaccion_DTO.class))
                            .onErrorResume(throwable -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                }
        );
    }
}
