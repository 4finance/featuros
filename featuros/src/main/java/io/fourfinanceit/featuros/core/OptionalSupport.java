package io.fourfinanceit.featuros.core;

import java.util.Optional;

public interface OptionalSupport<T> {

    @SuppressWarnings("unchecked")
    default Optional<T> chain() {
        return Optional.of((T) this);
    }
}
