package com.uppi.rx.subscriber;

import javax.jms.Message;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

public class JMSMessageDispatcherImpl implements MessageDispatcher<Message> {

	private static final Logger LOG = Logger.getLogger(JMSMessageDispatcherImpl.class);
	private final Metrics metrics;
	public JMSMessageDispatcherImpl() {
		metrics=new Metrics();
	}
	public JMSMessageDispatcherImpl(final Metrics metrics) {
		this.metrics=metrics;
	}
	@Override
	public void dispatch(final Message message) {
		if (TextMessage.class.isInstance(message)) {
			metrics.tick();
			try {
				LOG.info("Received Message "+TextMessage.class.cast(message).getText());
				LOG.info("size "+metrics.size());
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		
	}

}
