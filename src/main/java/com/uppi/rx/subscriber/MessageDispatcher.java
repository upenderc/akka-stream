package com.uppi.rx.subscriber;

public interface MessageDispatcher<T> {
    void dispatch(T message);
}
