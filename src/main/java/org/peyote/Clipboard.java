package org.peyote;

import java.util.Optional;

public interface Clipboard<E> {

    void set(E data);

    Optional<E> get();

    void disable();

    void monitor(Callback<E> callback);

    interface Callback<E> {
        void updated(E data);
    }
}
