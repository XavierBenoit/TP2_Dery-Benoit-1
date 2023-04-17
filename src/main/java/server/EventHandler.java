package server;

/**
 * Interface fonctionnelle contenant la méthode abstraite <code>handle</code>, pour créer des
 * classes anonymes qui gèrent des événements.
 */
@FunctionalInterface
public interface EventHandler {
    /**
     * méthode abstraite qui servira à gérer un événement
     *
     * @param cmd la <code>String</code> de la commande
     * @param arg la <code>String</code> de la ligne d'arguments
     */
    void handle(String cmd, String arg);
}
