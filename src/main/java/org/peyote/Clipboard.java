package org.peyote;

import java.io.Closeable;
import java.io.IOException;
import java.util.Optional;

public interface Clipboard<E> {

    Optional<E> value(E... data);

    Monitor<E> monitor(Callback<E> callback);

    interface Monitor<E> extends Closeable {
        Monitor<E> monitor(Callback<E> callback);

        @Override
        void close() throws IOException;
    }

    interface Callback<E> {
        void updated(E data);
    }
}
