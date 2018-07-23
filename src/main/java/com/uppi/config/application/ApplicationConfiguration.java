package com.uppi.config.application;

import javax.jms.ConnectionFactory;
import javax.jms.Message;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;

import com.uppi.rx.subscriber.CustomSubscriber;
import com.uppi.rx.subscriber.JMSMessageDispatcherImpl;
import com.uppi.rx.subscriber.MessageDispatcher;
import com.uppi.rx.subscriber.Metrics;
import com.uppi.sa.extension.SpringExtension;

import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.KillSwitch;
import akka.stream.alpakka.jms.JmsSourceSettings;
import akka.stream.alpakka.jms.javadsl.JmsSource;
import akka.stream.javadsl.Source;

@Configuration
@ComponentScan
public class ApplicationConfiguration {
 
    @Autowired
    private ApplicationContext applicationContext;
 
    @Bean("actorSystem")
    public ActorSystem actorSystem() {
        ActorSystem system = ActorSystem.create("akka-int-spring");
        SpringExtension.SPRING_CONTEXT_PROVIDER.get(system)
          .initialize(applicationContext);
        return system;
    }
    @Bean
    @DependsOn(value= {"actorSystem"})
    public ActorMaterializer actorMaterializer() {
    	return ActorMaterializer.create(actorSystem());
    }
    @Bean("messageDispatcher")
    public MessageDispatcher<Message> messageDispatcher() {
    	return new JMSMessageDispatcherImpl(metrics());
    }
    @Bean("jmsSubscriber")
    @DependsOn(value= {"messageDispatcher"})
    public CustomSubscriber<Message> subscriber() {
    	return new CustomSubscriber<>(messageDispatcher(),metrics());
    }
    @Bean("connectionFactory")
    public ConnectionFactory connectionFactory() {
    	ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
		connectionFactory.setTrustAllPackages(true);
		return connectionFactory;
    }
    @Bean("jmsSource")
    @DependsOn(value= {"connectionFactory"})
    public Source<Message, KillSwitch> jmsSource() {
    	return JmsSource.create(JmsSourceSettings.create(connectionFactory())
		                .withQueue("test")
		                .withBufferSize(5));
		        
    }
    @Bean("metrics")
    @Scope("singleton")
    public Metrics metrics() {
    	return new Metrics();
    }
}
