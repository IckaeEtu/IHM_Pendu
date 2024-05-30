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
     * @param modelePendu modèle du jeu
     */
    public ControleurNiveau(MotMystere modelePendu) {
        this.modelePendu = modelePendu;
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
            case "Moyen":
                this.modelePendu.setNiveau(1);
            case "Difficile":
                this.modelePendu.setNiveau(2);
            case "Expert":
                this.modelePendu.setNiveau(3);
            default:
                this.modelePendu.setNiveau(0);
                break;
        }
    }
}
