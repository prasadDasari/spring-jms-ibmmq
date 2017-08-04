package com.sample.jms.ibmmq.mq;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/*
Connection properties are enclosed in a convenience class called MQProperties
*/

@ConfigurationProperties(prefix = "ibm.mq")
@Data
public class MQProperties {

    String queueManager;
    String host;
    int port;
    String channel;
    String user;
    String password;
    String incomingQueue;
    String outgoingQueue;
}
