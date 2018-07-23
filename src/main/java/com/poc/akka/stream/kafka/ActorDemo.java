
package com.poc.akka.stream.kafka;

import java.util.concurrent.CompletionStage;

import akka.Done;
import akka.NotUsed;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.japi.function.Function;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;

public class ActorDemo {

	public static void main(String ...args) {
		ActorSystem system=ActorSystem.create();
		ActorMaterializer actorMate=ActorMaterializer.create(system);
		Flow<String, Integer, NotUsed> flow=Flow.of(String.class).map(new Function<String, Integer>() {

			@Override
			public Integer apply(String value) throws Exception {
				return Integer.parseInt(value);
			}
		});
		Source<Integer,NotUsed> srcFlow=Source.single("10").via(flow);
		Sink<Integer, CompletionStage<Done>> sink=Sink.foreach(s->System.out.println(s>3));
		srcFlow.runWith(sink,actorMate);
		//ActorRef echoActor=system.actorOf(Props.create(EchoActor.class));
		//echoActor.tell("Hello,World", ActorRef.noSender());
		//echoActor.tell(akka.actor.Kill.getInstance(), ActorRef.noSender());
		//echoActor.tell(akka.actor.PoisonPill.getInstance(), ActorRef.noSender());
		//echoActor.tell("Hello,World!", ActorRef.noSender());
		//Duration.create(3, TimeUnit.SECONDS);
		system.terminate();
	}
	
}

class EchoActor extends AbstractActor {

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(String.class, s->{
			System.out.println("Received Message=>> "+s);}).build();
	}
	@Override
	public void postRestart(Throwable reason) {
		  System.out.println("postrestart");
	}
	@Override
	public void preStart() {
		System.out.println("prestart");
	}
	@Override
	public void postStop() {
		System.out.println("postStop");
	}
	
}