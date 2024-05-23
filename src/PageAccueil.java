import javafx.geometry.HorizontalDirection;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PageAccueil extends VBox {

    public PageAccueil(){
        start();
    }

    private void start(){
        Button lancerJeu = new Button("Lancer une partie");
        
        VBox niveaux = new VBox();
        niveaux.getChildren().addAll(new RadioButton("Facile"),new RadioButton("Moyen"),new RadioButton("Difficile"),new RadioButton("Expert"));
        TitledPane difficulte = new TitledPane("Niveau de difficulté",niveaux);
        this.getChildren().addAll(lancerJeu,difficulte);
        
    }
    
}
