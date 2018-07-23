package com.poc.akka.stream.kafka;

import java.util.concurrent.CompletionStage;

import akka.Done;
import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;

public class SimpleAkkaStream {

	public static void main(String[] args) {
		final ActorSystem system = ActorSystem.create("kafka-stream");
		final ActorMaterializer materializer=ActorMaterializer.create(system);
		Source<Integer, NotUsed> source = Source.range(1, 100);
		CompletionStage<Done> done=source.runWith(Sink.foreach(i->System.out.println(i)),materializer);
		done.thenRun(()->system.terminate());
		
	}

}
