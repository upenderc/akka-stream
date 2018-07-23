package com.poc.akka.stream.kafka;

import java.util.Arrays;
import java.util.concurrent.CompletionStage;

/*import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import akka.Done;
import akka.actor.ActorSystem;
import akka.kafka.ProducerSettings;
import akka.kafka.javadsl.Producer;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Source;*/

public class KafkaProducerStream {

	public static void main(String ...args) {
		/*final ActorSystem system = ActorSystem.create("kafka-stream");
		
		final ActorMaterializer materializer=ActorMaterializer.create(system);
		
		final ProducerSettings<byte[], String> kafkaProducerSettings=
				ProducerSettings.create(system, new ByteArraySerializer(), new StringSerializer())
				.withBootstrapServers("localhost:9092");
		final KafkaProducer<byte[], String> kafkaProducer = kafkaProducerSettings.createKafkaProducer();
		
		final CompletionStage<Done> cmpl=Source.from(Arrays.asList("u.s","india","japan","germany"))
				.map(s->s.toUpperCase())
				.map(s->new ProducerRecord<byte[],String>("topic1", s))
				.runWith(Producer.plainSink(kafkaProducerSettings,kafkaProducer),materializer);
		cmpl.thenAccept(s->System.out.println(s));*/
				
		
	}
}
