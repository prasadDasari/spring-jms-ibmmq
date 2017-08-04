Integration Spring with IBM MQ

Connecting to IBM MQ with Spring Boot and JMS
Spring Boot integrates nearly seamlessly with IBM MQ, thanks to its JMS support. Plumbing must be done manually, as usual.
You can use Spring Initializr to generate project’s stub for simple application with JMS.
It’s mandatory to place all IBM MQ jars on classpath. The best solution is to add them to local Maven repository and refer to them from build descriptor (e.g. POM file or build.gradle).

JMS factory beans need to be set up to establish connection. 
javax.jms.ConnectionFactory is generic factory used by both message producers and consumers. 
org.springframework.jms.config.DefaultJmsListenerContainerFactory is responsible for message consumer’s settings,
org.springframework.jms.core.JmsTemplate can be used for dead simple message producers.
Session Acknowledge Mode is set to Session.CLIENT_ACKNOWLEDGE, meaning message delivery is acknowledged as a part of transaction. If transaction is rolled back, JMS messsage will be redelivered.
It is worth noting, that Connection Factory has Transport Type set to WMQConstants.WMQ_CM_CLIENT.

MQProperties.java to populate connection factory.
ConnectionConfiguration.java is the only bean which is tightly coupled with IBM MQ implementation.
For distributed transactions (XA), MQXAConnectionFactory  is the suitable approach.
If you don’t rely on distributed transactions, feel free to use MQConnectionFactory instead,This Project uses MQ ConnectionFactory.

Configuration of beans specific for current application is enclosed within separate class to make it easy to replace connection factory for tests. 
Nearly all beans from the class below depend on previously defined connection factory.
MQConfiguration.java used to configure the beans for this application.

Having defined all beans, it’s now high time to use them in concrete service, 
which serves as both message consumer and producer, which is the MQGateway.java

Git hub : https://github.com/cpiotr/blog/tree/master/blog-code/src/main/java/pl/ciruk/blog/mq
# spring-jms-ibmmq
