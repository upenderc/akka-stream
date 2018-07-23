package com.uppi.sa.producer;

import org.springframework.context.ApplicationContext;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;

public class SpringIndirectActorProducerImpl implements IndirectActorProducer {

	private final ApplicationContext applicationContext;
    private final String actorBeanName;
    
	public SpringIndirectActorProducerImpl(final ApplicationContext applicationContext,
			final String actorBeanName) {
		this.actorBeanName=actorBeanName;
		this.applicationContext=applicationContext;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<? extends Actor> actorClass() {
		return (Class<? extends Actor>)applicationContext.getType(actorBeanName);
	}

	@Override
	public Actor produce() {
		return (Actor) applicationContext.getBean(actorBeanName);
	}

}
