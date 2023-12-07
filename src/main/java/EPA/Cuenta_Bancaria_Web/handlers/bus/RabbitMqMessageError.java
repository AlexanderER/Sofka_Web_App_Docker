package EPA.Cuenta_Bancaria_Web.handlers.bus;

import EPA.Cuenta_Bancaria_Web.RabbitConfig;
import EPA.Cuenta_Bancaria_Web.drivenAdapters.repositorios.I_RepositorioCuentaMongo;
import EPA.Cuenta_Bancaria_Web.drivenAdapters.repositorios.I_Repositorio_TransaccionMongo;
import EPA.Cuenta_Bancaria_Web.models.DTO.M_Cuenta_DTO;
import EPA.Cuenta_Bancaria_Web.models.Mongo.M_CuentaMongo;
import EPA.Cuenta_Bancaria_Web.models.Mongo.M_TransaccionMongo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.rabbitmq.Receiver;

@Component
public class RabbitMqMessageError  implements CommandLineRunner
{
    @Autowired
    private Receiver receiver;

    @Autowired
    private Gson gson;

    @Autowired
    I_Repositorio_TransaccionMongo transaccion_repositorio;

    @Autowired
    I_RepositorioCuentaMongo cuenta_repositorio;

    @Override
    public void run(String... args) throws Exception
    {
        receiver.consumeAutoAck(RabbitConfig.QUEUE_NAME_ERROR)
                .map(message -> {
                                    System.out.println("[--------------------- Inicio Rollback ---------------------]\n");
                                    M_TransaccionMongo transaccion = gson.fromJson(new String(message.getBody()), M_TransaccionMongo.class);
                                    System.out.println("[Detalle Transacción]" + "\n" + transaccion + "\n");


                                    System.out.println("[Aplicando Rollback a la Cuenta asociada]");
                                    M_CuentaMongo cuenta = transaccion.getCuenta();
                                    cuenta.setSaldo_Global(transaccion.getSaldo_inicial());
                                    cuenta_repositorio.save(cuenta).subscribe(); //.block();
                                    System.out.println("Rollback OK\n");

                                    System.out.println("[Aplicando Rollback a la Transaccion]");
                                    transaccion_repositorio.delete(transaccion).subscribe(); //.block();
                                    System.out.println("Rollback OK\n");

                                    System.out.println("[--------------------- Fin Rollback ---------------------]");
                                    return transaccion;
                                }).subscribe();

    }
}
