package com.uppi.config.application;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.jms.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.uppi.rx.subscriber.CustomSubscriber;

import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.KillSwitch;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

@SpringBootApplication
@Configuration
@Import(ApplicationConfiguration.class)
public class AkkaStreamKafkaBootApplication {

	@Autowired
	private Source<Message, KillSwitch> jmsSource;
	@Autowired
	private CustomSubscriber<Message> jmsSubscriber;
	@Autowired
	private ActorMaterializer actorMaterializer;
	@Autowired
	private ActorSystem actorSystem;
	@PostConstruct
	public void startConsumer() {
		jmsSource.runWith(Sink.fromSubscriber(jmsSubscriber), actorMaterializer);
	}
	@PreDestroy
	public void shutdownAkka() throws Exception {
		Await.result(actorSystem.terminate(),new FiniteDuration(3000,TimeUnit.MILLISECONDS));
	}
	public static void main(String[] args) {
		SpringApplication.run(AkkaStreamKafkaBootApplication.class,args);
		
	}

}
