import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.ButtonBar.ButtonData ;

import java.util.List;

import javax.swing.ImageIcon;

import java.util.Arrays;
import java.io.File;
import java.util.ArrayList;


/**
 * Vue du jeu du pendu
 */
public class Pendu extends Application {
    /**
     * modèle du jeu
     **/
    private MotMystere modelePendu;
    /**
     * Liste qui contient les images du jeu
     */
    private ArrayList<Image> lesImages;
    /**
     * Liste qui contient les noms des niveaux
     */    
    public List<String> niveaux;

    // les différents contrôles qui seront mis à jour ou consultés pour l'affichage
    /**
     * le dessin du pendu
     */
    private ImageView dessin;
    /**
     * le mot à trouver avec les lettres déjà trouvé
     */
    private Text motCrypte;
    /**
     * la barre de progression qui indique le nombre de tentatives
     */
    private ProgressBar pg;
    /**
     * le clavier qui sera géré par une classe à implémenter
     */
    private Clavier clavier;
    /**
     * le text qui indique le niveau de difficulté
     */
    private Text leNiveau;
    /**
     * le chronomètre qui sera géré par une clasee à implémenter
     */
    private Chronometre chrono;
    /**
     * le panel Central qui pourra être modifié selon le mode (accueil ou jeu)
     */
    private BorderPane panelCentral;
    /**
     * le bouton Paramètre / Engrenage
     */
    private Button boutonParametres;
    /**
     * le bouton Accueil / Maison
     */    
    private Button boutonMaison;
    /**
     * le bouton qui permet de (lancer ou relancer une partie
     */ 
    private Button bJouer;

    /**
     * initialise les attributs (créer le modèle, charge les images, crée le chrono ...)
     */
    @Override
    public void init() {
        this.modelePendu = new MotMystere("/usr/share/dict/french", 3, 10, MotMystere.FACILE, 10);
        this.lesImages = new ArrayList<Image>();
        this.chargerImages("./img");
        this.panelCentral = new BorderPane();
        // A terminer d'implementer
    }

    /**
     * @return  le graphe de scène de la vue à partir de methodes précédantes
     */
    private Scene laScene(){
        BorderPane fenetre = new BorderPane();
        fenetre.setTop(this.titre());
        fenetre.setCenter(this.panelCentral);
        return new Scene(fenetre, 800, 1000);
    }

    /**
     * @return le panel contenant le titre du jeu
     */
    private Pane titre(){
        // A implementer          
        BorderPane entete = new BorderPane();
        entete.setMinWidth(65);
        Label titre = new Label("Jeu du Pendu");
        titre.setFont(new Font(55));
        entete.setLeft(titre);
        ImageView image1 = new ImageView(new Image("file:img/home.png"));
        ImageView image2 = new ImageView("file:img/parametres.png");
        ImageView image3 = new ImageView("file:img/info.png");
        image1.setFitWidth(25);
        image2.setFitWidth(25);
        image3.setFitWidth(25);
        image1.setFitHeight(25);
        image2.setFitHeight(25);
        image3.setFitHeight(25);

        Button home = new Button("",image1);
        Button parametres = new Button("",image2);
        Button info = new Button("",image3);
        info.setOnAction(new ControleurInfos(this));
        home.setOnAction(new RetourAccueil(modelePendu, this));
        home.setMinWidth(5);
        info.setMinWidth(5);
        parametres.setMinWidth(5);
        HBox droite = new HBox();
        droite.getChildren().addAll(home,parametres,info);
        entete.setRight(droite);
        entete.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE,CornerRadii.EMPTY,Insets.EMPTY)));
        return entete;
    }

    // /**
     // * @return le panel du chronomètre
     // */
    // private TitledPane leChrono(){
        // A implementer
        // TitledPane res = new TitledPane();
        // return res;
    // }

    // /**
     // * @return la fenêtre de jeu avec le mot crypté, l'image, la barre
     // *         de progression et le clavier
     // */
    // private Pane fenetreJeu(){
        // A implementer
        // Pane res = new Pane();
        // return res;
    // }

    // /**
     // * @return la fenêtre d'accueil sur laquelle on peut choisir les paramètres de jeu
     // */
    // private Pane fenetreAccueil(){
        // A implementer    
        // Pane res = new Pane();
        // return res;
    // }

    /**
     * charge les images à afficher en fonction des erreurs
     * @param repertoire répertoire où se trouvent les images
     */
    private void chargerImages(String repertoire){
        for (int i=0; i<this.modelePendu.getNbErreursMax()+1; i++){
            File file = new File(repertoire+"/pendu"+i+".png");
            System.out.println(file.toURI().toString());
            this.lesImages.add(new Image(file.toURI().toString()));
        }
    }

    public void modeAccueil(){
        VBox pageAccueil = new VBox();
        pageAccueil.setPadding(new Insets(10));
        Button lancerJeu = new Button("Lancer une partie");
        lancerJeu.setOnAction(new ControleurLancerPartie(modelePendu, this));
        VBox niveaux = new VBox();
        ToggleGroup group = new ToggleGroup();
        RadioButton facile = new RadioButton("Facile");
        facile.setToggleGroup(group);
        RadioButton moyen = new RadioButton("Moyen");
        moyen.setToggleGroup(group);
        RadioButton difficile = new RadioButton("Difficile");
        difficile.setToggleGroup(group);
        RadioButton expert = new RadioButton("Expert");
        expert.setToggleGroup(group);


        niveaux.getChildren().addAll(facile,moyen,difficile,expert);
        TitledPane difficulte = new TitledPane("Niveau de difficulté",niveaux);
        pageAccueil.getChildren().addAll(lancerJeu,difficulte);
        this.panelCentral.setCenter(pageAccueil);
    }
    
    public void modeJeu(){
        BorderPane pageJeu = new BorderPane();
        VBox centre = new VBox();
        Label motCrypte = new Label(modelePendu.getMotCrypte());
        
        centre.getChildren().add(new Clavier("abcdefghijklmnopqrstuvwxyz", new ControleurLettres(modelePendu, this), 8));
        this.panelCentral.setCenter(pageJeu);
    }
    
    public void modeParametres(){
        // A implémenter
    }

    /** lance une partie */
    public void lancePartie(){
        // A implementer
    }

    /**
     * raffraichit l'affichage selon les données du modèle
     */
    public void majAffichage(){
        // A implementer
    }

    /**
     * accesseur du chronomètre (pour les controleur du jeu)
     * @return le chronomètre du jeu
     */
    public Chronometre getChrono(){
        // A implémenter
        return null; // A enlever
    }

    public Alert popUpPartieEnCours(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"La partie est en cours!\n Etes-vous sûr de l'interrompre ?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Attention");
        return alert;
    }
        
    public Alert popUpReglesDuJeu(){
        // A implementer
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        return alert;
    }
    
    public Alert popUpMessageGagne(){
        // A implementer
        Alert alert = new Alert(Alert.AlertType.INFORMATION);        
        return alert;
    }
    
    public Alert popUpMessagePerdu(){
        // A implementer    
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        return alert;
    }

    /**
     * créer le graphe de scène et lance le jeu
     * @param stage la fenêtre principale
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("IUTEAM'S - La plateforme de jeux de l'IUTO");
        stage.setScene(this.laScene());
        stage.show();
        this.modeJeu();
    }

    /**
     * Programme principal
     * @param args inutilisé
     */
    public static void main(String[] args) {
        launch(args);
    }    
}
