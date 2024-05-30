import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/**
 * Contrôleur à activer sur le bouton paramètre
 */
public class ControleurParametre implements EventHandler<ActionEvent> {

     /* vue du jeu**/
    private Pendu vuePendu;

    /**
     * @param modelePendu modèle du jeu
     * @param vuePendu vue du jeu
     */
    public ControleurParametre( Pendu vuePendu) {
        this.vuePendu = vuePendu;
    }

    /**
     *L'action consiste a affichier les paramètres (ou pas (ou vraiment pas du tout même (on ne va pas mettre des paramètres)))
     * @param actionEvent l'événement action
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        vuePendu.modeParametres();
    }
}
