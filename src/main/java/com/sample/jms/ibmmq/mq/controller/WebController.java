package com.sample.jms.ibmmq.mq.controller;

import com.sample.jms.ibmmq.mq.client.JmsClient;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class WebController {
    @Autowired
    private JmsClient jmsClient;

    @ApiOperation(value = "To publish message to IBM MQ", nickname = "Spring Integration with IBM-MQ ",
          notes = "Endpoint to send message..."
                    + " as input .")
    @RequestMapping(value = "/produce" , method = RequestMethod.POST)
    @ResponseBody
    public String produce(@RequestParam("msg") String msg) {

        jmsClient.send(msg);
        return "Message Sent Successfully from Client WebBrowser";

    }

}
