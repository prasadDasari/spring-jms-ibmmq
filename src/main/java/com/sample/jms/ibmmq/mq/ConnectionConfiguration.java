package com.sample.jms.ibmmq.mq;

import com.ibm.mq.jms.MQConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import com.ibm.msg.client.wmq.compat.jms.internal.JMSC;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.util.StringUtils;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

/* The only bean which is tightly coupled with IBM MQ implementation.*/

@Configuration
@EnableConfigurationProperties(MQProperties.class)
@EnableJms
@Slf4j
public class ConnectionConfiguration {
    @Autowired
    MQProperties mqProperties;

    @Bean
     public ConnectionFactory connectionFactory(
            @Value("${ibm.mq.queue.manager}") final String queueManager,
            @Value("${ibm.mq.host}") final String mqHost,
            @Value("${ibm.mq.port}") final String mqPort,
            @Value("${ibm.mq.channel}") final String mqChannel,
            @Value("${ibm.mq.user}") final String mqUserName,
            @Value("${ibm.mq.password}") final String mqPassword) throws JMSException {
         MQConnectionFactory factory = null;
         try {
             factory = new MQConnectionFactory();
             factory.setHostName(mqHost);
             log.info("Host ==> {}", mqHost);
             factory.setPort(Integer.parseInt((mqPort)));
             log.info("Port ==> {}", mqPort);
             factory.setQueueManager(queueManager);
             log.info("Queue Manager ==> {} ", queueManager);
             factory.setChannel(mqChannel);
             log.info("Channel ==>" +mqChannel);
             //factory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
             //factory.setTransportType(1);
             factory.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP);
             if (!StringUtils.isEmpty(mqUserName)) {
                 factory.setStringProperty(WMQConstants.USERID,
                         mqUserName);
                 log.info("user ==>" + mqUserName);
             }
             if (!StringUtils.isEmpty(mqPassword)) {
                 factory.setStringProperty(WMQConstants.PASSWORD,
                         mqPassword);
                 log.info("password ==>" + mqUserName);
             }
         } catch (Exception e) {
             throw new RuntimeException(e);
         }

         return factory;
     }

}