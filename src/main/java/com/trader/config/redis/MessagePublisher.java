package com.trader.config.redis;

public interface MessagePublisher {
    void publish(final String message);
}
