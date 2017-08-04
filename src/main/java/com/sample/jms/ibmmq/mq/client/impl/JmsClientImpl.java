package com.sample.jms.ibmmq.mq.client.impl;

import com.sample.jms.ibmmq.mq.MQGateway;
import com.sample.jms.ibmmq.mq.client.JmsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JmsClientImpl implements JmsClient {

    @Autowired
    MQGateway mqGateway;

    @Override
    public void send(String msg) {
        mqGateway.send(msg);

    }


}
