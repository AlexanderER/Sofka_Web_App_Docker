package EPA.Cuenta_Bancaria_Web.routes;

import EPA.Cuenta_Bancaria_Web.models.DTO.M_Transaccion_DTO;
import EPA.Cuenta_Bancaria_Web.useCase.transaccion.useCaseTransaccionGetAll;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfigTransaccion
{
    @Bean
    public RouterFunction<ServerResponse> getAllTransaccionRouter(useCaseTransaccionGetAll useCase){
        return route(GET("/Transacciones/routes/listar_transacciones"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase.get(), M_Transaccion_DTO.class)));
    }
}
