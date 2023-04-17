package server;

/**
 * interface fonctionnelle contenant la méthode <code>handle</code>
 */
@FunctionalInterface
public interface EventHandler {
    /**
     *
     * @param cmd la <code>String</code> de la commande
     * @param arg la <code>String</code> de la ligne d'arguments
     */
    void handle(String cmd, String arg);
}
