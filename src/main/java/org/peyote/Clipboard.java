package org.peyote;

import java.util.Optional;

public interface Clipboard<E> {

    Optional<E> value(E... data);

    Monitor monitor(Callback<E> callback);

    interface Monitor extends AutoCloseable { }

    interface Callback<E> {
        void updated(E data);
    }
}
