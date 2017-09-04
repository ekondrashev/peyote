package org.peyote;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class SysClipboard implements org.peyote.Clipboard<String> {

    private static final int DELAY = 200;

    private Clipboard clipboard;
    private ClipboardContent content;
    private List<Timeline> timelines = new LinkedList<>();

    public SysClipboard() {
        clipboard = Clipboard.getSystemClipboard();
        content = new ClipboardContent();
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
    public void disable() {
        for (Timeline timeline : timelines) {
            timeline.stop();
        }
    }

    @Override
    public void monitor(Callback<String> callback) {
        final String[] oldString = { "" };
        Timeline timeline = new Timeline(
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
        timelines.add(timeline);
    }

    private boolean updated(String[] buf) {
        Optional<String> current = this.value();
        if (current.isPresent() && !current.get().equals(buf[0])) {
            String newString = current.get();
            buf[0] = newString;
            return true;
        }
        return false;
    }

}
