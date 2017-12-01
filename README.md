# Peyote
Peyote is a library to provide fork for os. This will allow you to change some flow for different os.
## Download
#### Maven
Add dependency to your `pom.xml`
```xml
<dependency>
    <groupId>com.github.ekondrashev.peyote</groupId>
    <artifactId>peyote</artifactId>
    <version>{LAST_VERSION}</version>
</dependency>
```
## Requirements
* java 1.8
* OSX
* Windows
* Linux
## Example
```java
class Linux implements OS {
    private static final String NAME = "linux";
    private final LinuxClipboard clipboard;

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
        return new LinuxHotkeys();
    }
}
```