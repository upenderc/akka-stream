package com.poc.akka.stream.kafka;

import java.util.Arrays;
import java.util.concurrent.CompletionStage;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Keep;
import akka.stream.javadsl.RunnableGraph;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;

public class RunnableGraphDemo {

	public static void main(String[] args) {
		final ActorSystem system = ActorSystem.create("kafka-stream");
		final ActorMaterializer materializer=ActorMaterializer.create(system);
		final Source<Integer, NotUsed> source =
			    Source.from(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
		final Sink<Integer, CompletionStage<Integer>> sink =
			    Sink.<Integer, Integer> fold(0, (aggr, next) -> aggr + next);
		final RunnableGraph<CompletionStage<Integer>> runnable =
			    source.toMat(sink, Keep.right());
		CompletionStage<Integer> sum=runnable.run(materializer);
		sum.thenAccept(s->System.out.println("Sum="+s));
		sum.thenRun(()->system.terminate());
	}

}
