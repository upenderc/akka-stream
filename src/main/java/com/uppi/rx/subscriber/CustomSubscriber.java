package com.uppi.rx.subscriber;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public  class CustomSubscriber<T> implements Subscriber<T> {
	private static final Logger LOG=Logger.getLogger(CustomSubscriber.class);
	private final MessageDispatcher<T> messageDispatcher;
	private final Metrics metrics;
	private Subscription cs;
	public CustomSubscriber(final MessageDispatcher<T> messageDispatcher) {
		super();
		this.messageDispatcher=messageDispatcher;
		this.metrics=new Metrics();
	}
	public CustomSubscriber(final MessageDispatcher<T> messageDispatcher, Metrics metrics) {
		super();
		this.messageDispatcher=messageDispatcher;
		this.metrics=metrics;
	}
	@Override
	public void onSubscribe(Subscription s) {
		cs=s;
		cs.request(1);
		
	}

	@Override
	public void onNext(T t) {
		messageDispatcher.dispatch(t);
		if (metrics.size()>=50) {
			LOG.info("Sleeping =");
			try {
				TimeUnit.SECONDS.sleep(2);
				for(int i=1;i<=50;i++) {
					metrics.clear();
				}
				cs.request(1);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			LOG.info("Next Request =");
			cs.request(1);
		}
		
	}

	@Override
	public void onError(Throwable t) {
		LOG.error("Error >>>>",t);
		
	}

	@Override
	public void onComplete() {
		LOG.info("Completed");
		
	}
	
}
