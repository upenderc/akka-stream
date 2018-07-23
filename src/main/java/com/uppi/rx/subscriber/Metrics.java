package com.uppi.rx.subscriber;

import java.util.concurrent.atomic.AtomicLong;

public class Metrics {
	private final AtomicLong count;
	
	public Metrics() {
		this.count = new AtomicLong(0);
	}
	public void tick() {
        count.incrementAndGet();
    }
	public long size() {
		return count.get();
	}
	public void clear() {
		count.decrementAndGet();
	}
}
