package org.peyote;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Iterator;

/**
 * Doc.
 *
 * @author Eugene Kondrashev (eugene.kondrashev@gmail.com)
 */
public interface OS {

    String name();

    Clipboard clipboard();

    Hotkeys hotkeys();

    class OSX implements OS {
        private static final String NAME = "osx";
        private final Clipboard clipboard;

        public OSX(Clipboard clipboard) {
            this.clipboard = clipboard;
        }

        @Override
        public String name() {
            return OSX.NAME;
        }

        @Override
        public Clipboard clipboard() {
            return this.clipboard;
        }

        @Override
        public Hotkeys hotkeys() {
            return new Hotkeys.OSX();
        }
    }

    class Windows implements OS {
        private static final String NAME = "windows";
        private final Clipboard clipboard;

        public Windows(Clipboard clipboard) {
            this.clipboard = clipboard;
        }

        @Override
        public String name() {
            return Windows.NAME;
        }

        @Override
        public Clipboard clipboard() {
            return this.clipboard;
        }

        @Override
        public Hotkeys hotkeys() {
            return new Hotkeys.Windows();
        }
    }

    class Linux implements OS {
        private static final String NAME = "linux";
        private final Clipboard clipboard;

        public Linux(Clipboard clipboard) {
            this.clipboard = clipboard;
        }

        @Override
        public String name() {
            return Linux.NAME;
        }

        @Override
        public Clipboard clipboard() {
            return this.clipboard;
        }

        @Override
        public Hotkeys hotkeys() {
            return new Hotkeys.Windows();
        }
    }

    class Wrap implements OS {

        private final OS origin;

        public Wrap(OS origin) {
            this.origin = origin;
        }

        @Override
        public String name() {
            return this.origin.name();
        }

        @Override
        public Clipboard clipboard() {
            return this.origin.clipboard();
        }

        @Override
        public Hotkeys hotkeys() {
            return this.origin.hotkeys();
        }
    }

    class Default extends Wrap {

        public Default() {
            this(new SysClipboard());
        }

        public Default(Clipboard clipboard) {
            super(new Fork(clipboard));
        }
    }

    class Fork extends Wrap {

        public Fork(Clipboard clipboard) {
            this(System.getProperty("os.name"), clipboard);
        }

        public Fork(String name, Clipboard clipboard) {
            super(Fork.os(name.toLowerCase(), clipboard));
        }

        private static OS os(String name, Clipboard clipboard) {
            if (name.contains("mac")) {
                return new OSX(clipboard);
            } else if (name.contains("win")) {
                return new Windows(clipboard);
            } else if (name.contains("nux")) {
                return new Linux(clipboard);
            }
            throw new IllegalStateException(
                    String.format("Unsupported os name %s", name));
        }
    }

    interface Hotkeys extends Iterable<Hotkeys.Hotkey> {
        Hotkey paste();

        interface Hotkey {
            String path();

            String combination();

            void trigger();
        }

        class Wrap implements Hotkeys {

            private final Hotkeys origin;

            public Wrap(Hotkeys origin) {
                this.origin = origin;
            }

            @Override
            public Hotkey paste() {
                return this.origin.paste();
            }

            @Override
            public Iterator<Hotkey> iterator() {
                return this.origin.iterator();
            }
        }

        class OSX implements Hotkeys {

            private final Robot robot;

            public OSX() {
                this.robot = Windows.robot();
            }

            @Override
            public Hotkey paste() {
                return new Hotkey() {
                    @Override
                    public String path() {
                        throw new UnsupportedOperationException("#path()");
                    }

                    @Override
                    public String combination() {
                        throw new UnsupportedOperationException(
                                "#combination()");
                    }

                    @Override
                    public void trigger() {
                        robot.keyPress(KeyEvent.VK_META);
                        robot.keyPress(KeyEvent.VK_V);
                        robot.keyRelease(KeyEvent.VK_V);
                        robot.keyRelease(KeyEvent.VK_META);
                    }
                };
            }

            @Override
            public Iterator<Hotkey> iterator() {
                throw new UnsupportedOperationException("#iterator()");
            }
        }

        class Windows implements Hotkeys {

            private final Robot robot;

            public Windows() {
                this.robot = Windows.robot();
            }

            @Override
            public Hotkey paste() {
                return new Hotkey() {
                    @Override
                    public String path() {
                        throw new UnsupportedOperationException("#path()");
                    }

                    @Override
                    public String combination() {
                        throw new UnsupportedOperationException(
                                "#combination()");
                    }

                    @Override
                    public void trigger() {
                        robot.keyPress(KeyEvent.VK_CONTROL);
                        robot.keyPress(KeyEvent.VK_V);
                        robot.keyRelease(KeyEvent.VK_V);
                        robot.keyRelease(KeyEvent.VK_CONTROL);
                    }
                };
            }

            @Override
            public Iterator<Hotkey> iterator() {
                throw new UnsupportedOperationException("#iterator()");
            }

            private static Robot robot() {
                try {
                    return new Robot();
                } catch (AWTException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    }
}
