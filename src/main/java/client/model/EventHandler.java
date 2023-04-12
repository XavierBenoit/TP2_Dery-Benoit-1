package client.model;

@FunctionalInterface
public interface EventHandler {
    void handle(String cmd, String arg);
}
