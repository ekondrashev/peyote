package org.peyote;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Optional;

public class SysClipboard implements org.peyote.Clipboard<String> {

    private static final int DELAY = 200;

    private Clipboard clipboard;
    private ClipboardContent content;
    private Monitor monitor;

    public SysClipboard() {
        this.clipboard = Clipboard.getSystemClipboard();
        this.content = new ClipboardContent();
        this.monitor = new Monitor(this);
    }

    @Override
    public Optional<String> value(String... data) {
        if (data.length == 1) {
            content.clear();
            content.putString(data[0]);
            clipboard.setContent(content);
        } else {
            if (clipboard.hasString()) {
                return Optional.of(clipboard.getString());
            }
        }
        return Optional.empty();
    }

    @Override
    public org.peyote.Clipboard.Monitor<String> change() {
        return monitor;
    }

    class Monitor implements org.peyote.Clipboard.Monitor<String> {

        private final SysClipboard clipboard;
        private Timeline timeline;

        public Monitor(SysClipboard clipboard) {
            this.clipboard = clipboard;
        }

        @Override
        public void monitor(Callback<String> callback) {
            final String[] oldString = {""};
            timeline = new Timeline(
                    new KeyFrame(
                            Duration.millis(DELAY),
                            event -> {
                                if (updated(oldString)) {
                                    callback.updated(oldString[0]);
                                }
                            }
                    ));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }

        @Override
        public void close() throws IOException {
            timeline.stop();
        }

        private boolean updated(String[] buf) {
            Optional<String> current = this.clipboard.value();
            if (current.isPresent() && !current.get().equals(buf[0])) {
                String newString = current.get();
                buf[0] = newString;
                return true;
            }
            return false;
        }
    }

}
