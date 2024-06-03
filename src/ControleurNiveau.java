import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.RadioButton;

/**
 * Controleur des radio boutons gérant le niveau
 */
public class ControleurNiveau implements EventHandler<ActionEvent> {

    /**
     * modèle du jeu
     */
    private MotMystere modelePendu;
    /**
     * vue du jeu
     */
    private Pendu vuePendu;


    /**
     * @param modelePendu modèle du jeu
     * * @param vuePendu vue du jeu
     */
    public ControleurNiveau(MotMystere modelePendu, Pendu vuePendu) {
        this.modelePendu = modelePendu;
        this.vuePendu = vuePendu;
    }

    /**
     * gère le changement de niveau
     * @param actionEvent
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        // A implémenter
        RadioButton radiobouton = (RadioButton) actionEvent.getTarget();
        String nomDuRadiobouton = radiobouton.getText();
        System.out.println(nomDuRadiobouton);
        switch(nomDuRadiobouton){
   
            case "Facile": 
                this.modelePendu.setNiveau(0);
                vuePendu.setLeNiveau("Facile");
                break;
            case "Moyen":
                this.modelePendu.setNiveau(1);
                vuePendu.setLeNiveau("Moyen");
                break;
            case "Difficile":
                this.modelePendu.setNiveau(2);
                vuePendu.setLeNiveau("Difficile");
                break;
            case "Expert":
                this.modelePendu.setNiveau(3);
                vuePendu.setLeNiveau("Expert");
                break;
            default:
                this.modelePendu.setNiveau(0);
                vuePendu.setLeNiveau("Facile");
                break;
        }
    }
}
