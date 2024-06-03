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
     * la barre de progression quiniveauxPane indique le nombre de tentatives
     */
    private ProgressBar pg;
    /**
     * le clavier qui sera géré par une classe à implémenter
     */
    private Clavier clavier;
    /**
     * le text qui indique le niveau de difficulté
     */
    private String leNiveau;
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
        this.clavier = new Clavier("ABCDEFGHIJKLMNOPQRSTUVWXYZ", new ControleurLettres(modelePendu, this), 8);
        this.chrono = new Chronometre();
        pg = new ProgressBar();

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
        titre.setFont(Font.font("Arial", 40));
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

        this.boutonMaison = new Button("",image1);
        this.boutonParametres = new Button("",image2);
        Button info = new Button("",image3);
        info.setOnAction(new ControleurInfos(this));
        this.boutonMaison.setOnAction(new RetourAccueil(modelePendu, this));
        boutonParametres.setOnAction(new ControleurParametre(this));
        this.boutonMaison.setMinWidth(5);
        info.setMinWidth(5);
        this.boutonParametres.setMinWidth(5);
        HBox droite = new HBox();
        droite.getChildren().addAll(boutonMaison,boutonParametres,info);
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
        // return res;        this.clavier = new Clavier("abcdefghijklmnopqrstuvwxyz", new ControleurLettres(modelePendu, this), 8);
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
    /**Met l'application en mode accueil */
    public void modeAccueil(){
        VBox pageAccueil = new VBox();
        pageAccueil.setPadding(new Insets(10));
        this.bJouer = new Button("Lancer une partie");
        bJouer.setOnAction(new ControleurLancerPartie(modelePendu, this));

        VBox niveauxPane = new VBox();
        ToggleGroup group = new ToggleGroup();
        RadioButton facile = new RadioButton("Facile");
        facile.setOnAction(new ControleurNiveau(modelePendu,this));
        facile.setToggleGroup(group);
        RadioButton moyen = new RadioButton("Moyen");
        moyen.setOnAction(new ControleurNiveau(modelePendu,this));
        moyen.setToggleGroup(group);
        RadioButton difficile = new RadioButton("Difficile");
        difficile.setOnAction(new ControleurNiveau(modelePendu,this));
        difficile.setToggleGroup(group);
        RadioButton expert = new RadioButton("Expert");
        expert.setOnAction(new ControleurNiveau(modelePendu,this));
        expert.setToggleGroup(group);
        niveauxPane.getChildren().addAll(facile,moyen,difficile,expert);
        TitledPane difficulte = new TitledPane("Niveau de difficulté",niveauxPane);
        difficulte.setCollapsible(false);

        pageAccueil.getChildren().addAll(this.bJouer,difficulte);
        boutonMaison.setDisable(true);
        boutonParametres.setDisable(false);
        this.panelCentral.setCenter(pageAccueil);
    }

    /**Met l'application en mode jeu */
    public void modeJeu(){
        HBox pageJeu = new HBox();
        motCrypte = new Text();
        VBox centre = new VBox();
        centre.setPadding(new Insets(5));
        pageJeu.setAlignment(Pos.BASELINE_CENTER);
        this.motCrypte.setText(this.leNiveau);
        centre.getChildren().add(motCrypte);
        dessin = new ImageView();
        dessin.setImage(lesImages.get(0));
        centre.getChildren().add(dessin);
        centre.getChildren().addAll(pg,this.clavier);

        pageJeu.getChildren().add(centre);

        VBox droite = new VBox();
        droite.setPadding(new Insets(5));
        droite.getChildren().add(new Label("Niveau " + leNiveau));
        TitledPane chronoPane = new TitledPane("Chronomètre", this.chrono);
        chronoPane.setCollapsible(false);
        bJouer.setText("Nouveau Mot");
        droite.getChildren().addAll(chronoPane,bJouer);
        pageJeu.getChildren().add(droite);
        boutonMaison.setDisable(false);
        boutonParametres.setDisable(true);

        this.panelCentral.setCenter(pageJeu);
    }
    
    public void modeParametres(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Ah bah non on va pas mettre des paramètres dans un pendu \n"
        +"Retournez jouer");
        alert.setTitle("Nope");
        boutonParametres.setDisable(true);
        alert.showAndWait();
        boutonParametres.setDisable(false);
        
    }

    /** lance une partie */
    public void lancePartie(){
        modeJeu();
        modelePendu.setMotATrouver();
        chrono.resetTime();
        chrono.start();
        majAffichage();
    }

    /**
     * raffraichit l'affichage selon les données du modèle
     */
    public void majAffichage(){
        this.clavier.desactiveTouches(modelePendu.getLettresEssayees());
        dessin.setImage(lesImages.get(modelePendu.getNbErreursMax() - modelePendu.getNbErreursRestants()));
        
        motCrypte.setText(modelePendu.getMotCrypte());
        double avancement = (double)(modelePendu.getMotATrouve().length()-modelePendu.getNbLettresRestantes())/modelePendu.getMotATrouve().length();
        System.out.println((modelePendu.getMotATrouve().length()-modelePendu.getNbLettresRestantes()));
        System.out.println(modelePendu.getMotATrouve().length());
        System.out.println(avancement);
        pg.setProgress(avancement);
    }

    /**
     * accesseur du chronomètre (pour les controleur du jeu)
     * @return le chronomètre du jeu
     */
    public Chronometre getChrono(){
        return this.chrono;
    }
    public Alert popUpPartieEnCours(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"La partie est en cours!\n Etes-vous sûr de l'interrompre ?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Attention");
        return alert;
    }
        
    public Alert popUpReglesDuJeu(){
        // A implementer
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Voici les règles du jeu :\n"
        + "Essayez des lettres\n"
        + "Si c'est une lettre du mot, elle sera révélée.\n"
        + "Si c'est pas dans le mot, vous perdez une chance et le dessin avance.\n"
        + "Si le pendu est entier, vous perdez la partie !");
        alert.setTitle("Règles du jeu");
        return alert;
    }
    
    public Alert popUpMessageGagne(){
        // A implementer
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Vous avez gagné, bien ouej chef");   
        alert.setTitle("C'est la win");     
        return alert;
    }
    
    public Alert popUpMessagePerdu(){
        // A implementer    
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Trop nul, skill issue\n Le mot était "+modelePendu.getMotATrouve());
        alert.setTitle("Nuuuuuuuuuuul perdu");
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
        this.modeAccueil();
    }

    /**
     * Programme principal
     * @param args inutilisé
     */
    public static void main(String[] args) {
        launch(args);
    }   
    
    /**Change le niveau affiché */
    public void setLeNiveau(String niv){leNiveau = niv;}
}
