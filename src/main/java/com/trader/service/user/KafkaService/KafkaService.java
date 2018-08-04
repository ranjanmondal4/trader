package com.trader.service.user.KafkaService;

import com.trader.domain.kafka.Receiver;
import com.trader.domain.kafka.Sender;
import com.trader.dto.common.ServiceDTO;
import com.trader.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class KafkaService {

    private static final String TEST_TOPIC = "test";
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    /*@ClassRule
    public static KafkaEmbedded embeddedKafka =
            new KafkaEmbedded(1, true, HELLOWORLD_TOPIC);*/

    @Autowired
    private Receiver receiver;

    @Autowired
    private Sender sender;

    public ServiceDTO login(){
        LOGGER.debug("service is invoked");
        testReceive();
        return ServiceDTO.getServiceResponse(true, "Success", "Success");
    }

    public void testReceive(){
        try {
            sender.send(TEST_TOPIC, "Hello Spring Kafka!");
            receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        }catch(Exception e){

        }

    }
}
