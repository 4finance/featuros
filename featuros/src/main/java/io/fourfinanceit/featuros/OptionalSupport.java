package io.fourfinanceit.featuros;

import java.util.Optional;

interface OptionalSupport<T> {

    @SuppressWarnings("unchecked")
    default Optional<T> chain() {
        return Optional.of((T) this);
    }
}
