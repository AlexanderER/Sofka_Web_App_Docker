package EPA.Cuenta_Bancaria_Web.handlers.bus;

import EPA.Cuenta_Bancaria_Web.RabbitConfig;
import EPA.Cuenta_Bancaria_Web.models.Mongo.M_CuentaMongo;
import EPA.Cuenta_Bancaria_Web.models.Mongo.M_TransaccionMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.Receiver;

@Component
public class RabbitMqMessageLog implements CommandLineRunner
{
    @Autowired
    private Receiver receiver;

    @Override
    public void run(String... args) throws Exception
    {
/*
        receiver.consumeAutoAck(RabbitConfig.QUEUE_NAME_LOG)
                .map(message -> {
                    System.out.println(new String(message.getBody()));
                    return Mono.empty();
                }).subscribe();
 */
    }
}
