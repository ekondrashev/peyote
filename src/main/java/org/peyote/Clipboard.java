package org.peyote;

import java.util.Optional;

public interface Clipboard<E> {

    Optional<E> value(E... data);

    void disable();

    void monitor(Callback<E> callback);

    interface Callback<E> {
        void updated(E data);
    }
}
