package io.fourfinanceit.featuros.core;

import org.springframework.context.ApplicationEvent;

/**
 * Typed event filtering support for spring 4.2+
 *
 * @param <T>
 */
public class TypedEvent<T> extends ApplicationEvent {

    public TypedEvent(T source) {
        super(source);
    }

    @SuppressWarnings("unchecked")
    public T eventObject() {
        return (T) source;
    }
}
