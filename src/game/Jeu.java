/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;
import env3d.Env;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.xml.parsers.ParserConfigurationException;
import org.lwjgl.input.Keyboard;
import org.xml.sax.SAXException;

/**
 *
 * @author kahlaoui
 */
public abstract class Jeu {

    private int getNiveau() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private String getDate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    // Une énumération pour définir les choix de l'utilisateur
    @SuppressWarnings("PackageVisibleInnerClass")
enum MENU_VAL {
        MENU_SORTIE, MENU_CONTINUE, MENU_JOUE
    }
    // attributs de classe
    private final Room menuRoom;
    EnvText textNomJoueur;
    EnvText textMenuQuestion;
    EnvText textMenuJeu1;
    EnvText textMenuJeu2;
    EnvText textMenuJeu3;
    EnvText textMenuJeu4;
    EnvText textMenuPrincipal1;
    EnvText textMenuPrincipal2;
    EnvText textMenuPrincipal3;
    // ... attributs existants ...
    private Env env ;
    private Room mainRoom ;
    private Profil profil ;
    private Tux tux ;
    private  ArrayList<Letter> lettres ;
    Dico dico ;
    
    
    public Jeu() throws ParserConfigurationException, SAXException, IOException {
        env = new Env() ;
        mainRoom = new Room() ;
        menuRoom = new Room() ;
        menuRoom.setTextureEast("textures/black.png");
        menuRoom.setTextureWest("textures/black.png");
        menuRoom.setTextureNorth("textures/black.png");
        menuRoom.setTextureBottom("textures/black.png");
        env.setCameraXYZ(50, 60,175) ;
        env.setCameraPitch(-20) ;
        env.setDefaultControl(false) ;
        profil = new Profil() ;
        lettres = new ArrayList<Letter>() ;
        // Textes affichés à l'écran
        textMenuQuestion = new EnvText(env, "Voulez vous ?", 200, 300);
        textMenuJeu1 = new EnvText(env, "1. Commencer une nouvelle partie ?", 250, 280);
        textMenuJeu2 = new EnvText(env, "2. Charger une partie existante ?", 250, 260);
        textMenuJeu3 = new EnvText(env, "3. Sortir de ce jeu ?", 250, 240);
        textMenuJeu4 = new EnvText(env, "4. Quitter le jeu ?", 250, 220);
        textNomJoueur = new EnvText(env, "Choisissez un nom de joueur : ", 200, 300);
        textMenuPrincipal1 = new EnvText(env, "1. Charger un profil de joueur existant ?", 250, 280);
        textMenuPrincipal2 = new EnvText(env, "2. Créer un nouveau joueur ?", 250, 260);
        textMenuPrincipal3 = new EnvText(env, "3. Sortir du jeu ?", 250, 240);
    }
    public void execute() throws ParserConfigurationException, SAXException, IOException {
        MENU_VAL mainLoop; 
        mainLoop = MENU_VAL.MENU_SORTIE;
        do {
            mainLoop = menuPrincipal();
        } while (mainLoop != MENU_VAL.MENU_SORTIE);
        this.env.setDisplayStr("Au revoir !", 300, 30);
        // Détruit l'environnement et provoque la sortie du programme 
        env.exit();
    }
    /**
     * Teste si une code clavier correspond bien à une lettre
     *
     * @return true si le code est une lettre
     */
    private Boolean isLetter(int codeKey) {
        return codeKey == Keyboard.KEY_A || codeKey == Keyboard.KEY_B || codeKey == Keyboard.KEY_C || codeKey == Keyboard.KEY_D || codeKey == Keyboard.KEY_E
                || codeKey == Keyboard.KEY_F || codeKey == Keyboard.KEY_G || codeKey == Keyboard.KEY_H || codeKey == Keyboard.KEY_I || codeKey == Keyboard.KEY_J
                || codeKey == Keyboard.KEY_K || codeKey == Keyboard.KEY_L || codeKey == Keyboard.KEY_M || codeKey == Keyboard.KEY_N || codeKey == Keyboard.KEY_O
                || codeKey == Keyboard.KEY_P || codeKey == Keyboard.KEY_Q || codeKey == Keyboard.KEY_R || codeKey == Keyboard.KEY_S || codeKey == Keyboard.KEY_T
                || codeKey == Keyboard.KEY_U || codeKey == Keyboard.KEY_V || codeKey == Keyboard.KEY_W || codeKey == Keyboard.KEY_X || codeKey == Keyboard.KEY_Y
                || codeKey == Keyboard.KEY_Z;
    }
 
    /**
     * Récupère une lettre à partir d'un code clavier
     *
     * @return une lettre au format String
     */
    private String getLetter(int letterKeyCode) {
        Boolean shift = false;
        if (this.env.getKeyDown(Keyboard.KEY_LSHIFT) || this.env.getKeyDown(Keyboard.KEY_RSHIFT)) {
            shift = true;
        }
        env.advanceOneFrame();
        String letterStr = "";
        if ((letterKeyCode == Keyboard.KEY_SUBTRACT || letterKeyCode == Keyboard.KEY_MINUS)) {
            letterStr = "-";
        } else {
            letterStr = Keyboard.getKeyName(letterKeyCode);
        }
        if (shift) {
            return letterStr.toUpperCase();
        }
        return letterStr.toLowerCase();
    }
    /**
     * Permet de saisir le nom d'un joueur et de l'affiche à l'écran durant la saisie
     *
     * @return le nom du joueur au format String
     */
    private String getNomJoueur() {
        textNomJoueur.modify("Choisissez un nom de joueur : ");
        int touche = 0;
        String nomJoueur = "";
        while (!(nomJoueur.length() > 0 && touche == Keyboard.KEY_RETURN)) {
            touche = 0;
            while (!isLetter(touche) && touche != Keyboard.KEY_MINUS && touche != Keyboard.KEY_SUBTRACT && touche != Keyboard.KEY_RETURN) {
                touche = env.getKey();
                env.advanceOneFrame();
            }
            if (touche != Keyboard.KEY_RETURN) {
                if ((touche == Keyboard.KEY_SUBTRACT || touche == Keyboard.KEY_MINUS) && nomJoueur.length() > 0) {
                    nomJoueur += "-";
                } else {
                    nomJoueur += getLetter(touche);
                }
                textNomJoueur.modify("Choisissez un nom de joueur : " + nomJoueur);
            }
        }
        textNomJoueur.clean();
        return nomJoueur;
    }
    /**
     * Menu principal
     *
     * @return le choix du joueur
     */
    private MENU_VAL menuPrincipal() throws ParserConfigurationException, SAXException, IOException {

        MENU_VAL choix = MENU_VAL.MENU_CONTINUE;
        String nomJoueur;

        // restaure la room du menu
        env.setRoom(menuRoom);

        // affiche le menu principal
        textMenuQuestion.display();
        textMenuPrincipal1.display();
        textMenuPrincipal2.display();
        textMenuPrincipal3.display();

        // vérifie qu'une touche 1, 2 ou 3 est pressée
        int touche = 0;
        while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3)) {
            touche = env.getKey();
            env.advanceOneFrame();
        }

        // efface le menu
        textMenuQuestion.clean();
        textMenuPrincipal1.clean();
        textMenuPrincipal2.clean();
        textMenuPrincipal3.clean();

        // et décide quoi faire en fonction de la touche pressée
        switch (touche) {
            // -------------------------------------
            // Touche 1 : Charger un profil existant
            // -------------------------------------
            case Keyboard.KEY_1:
                // demande le nom du joueur existant
                nomJoueur = getNomJoueur();
                // charge le profil de ce joueur si possible
                if (profil.charge(nomJoueur)) {
                    // lance le menu de jeu et récupère le choix à la sortie de ce menu de jeu
                    choix = menuJeu();
                } else {
                    // sinon continue (et boucle dans ce menu)
                    choix = MENU_VAL.MENU_CONTINUE;
                }
                break;

            // -------------------------------------
            // Touche 2 : Créer un nouveau joueur
            // -------------------------------------
            case Keyboard.KEY_2:
                // demande le nom du nouveau joueur
                nomJoueur = getNomJoueur();
                // crée un profil avec le nom d'un nouveau joueur
                profil = new Profil(nomJoueur);
                // lance le menu de jeu et récupère le choix à la sortie de ce menu de jeu
                choix = menuJeu();
                break;

            // -------------------------------------
            // Touche 3 : Sortir du jeu
            // -------------------------------------
            case Keyboard.KEY_3:
                // le choix est de sortir du jeu (quitter l'application)
                choix = MENU_VAL.MENU_SORTIE;
        }
        return choix;
    }
    /**
     * Menu de jeu
     *
     * @return le choix du joueur une fois la partie terminée
     */
    private MENU_VAL menuJeu() throws ParserConfigurationException, SAXException, IOException {

        MENU_VAL playTheGame;
        playTheGame = MENU_VAL.MENU_JOUE;
        Partie partie;
        do {
            // restaure la room du menu
            env.setRoom(menuRoom);

            // affiche le menu de jeu
            textMenuQuestion.display();
            textMenuJeu1.display();
            textMenuJeu2.display();
            textMenuJeu3.display();
            textMenuJeu4.display();

            // vérifie qu'une touche 1, 2, 3 ou 4 est pressée
            int touche = 0;
            while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3 || touche == Keyboard.KEY_4)) {
                touche = env.getKey();
                env.advanceOneFrame();
            }

            // efface le menu
            textMenuQuestion.clean();
            textMenuJeu1.clean();
            textMenuJeu2.clean();
            textMenuJeu3.clean();
            textMenuJeu4.clean();

            // restaure la room du jeu
            env.setRoom(mainRoom);

            // et décide quoi faire en fonction de la touche pressée
            switch (touche) {
                // -----------------------------------------
                // Touche 1 : Commencer une nouvelle partie
                // -----------------------------------------                
                case Keyboard.KEY_1: 
                    // choisi un niveau et charge un mot depuis le dico
                    int niveau = getNiveau() ;
                    this.dico = new Dico("src/test/dico.xml") ;
                    String mot = this.dico.getMotDepuisListeNiveaux(niveau) ;
                    // crée un nouvelle partie
                    SimpleDateFormat dateFormat = new SimpleDateFormat("DD MMM YYY 'à' HH:MM") ;
                    String date = dateFormat.format(new Date()) ;
                    partie = new Partie(date,mot,niveau);
                    // joue
                    joue(partie);
                    // enregistre la partie dans le profil --> enregistre le profil
                    profil.ajouterPartie(partie) ;
                    profil.sauvegarder("");
                    // .......... profil .........
                    playTheGame = MENU_VAL.MENU_JOUE;
                    break;

                // -----------------------------------------
                // Touche 2 : Charger une partie existante
                // -----------------------------------------                
                case Keyboard.KEY_2: // charge une partie existante
                    // demander de fournir une date
                    String datePartie ;
                    datePartie = getDate() ;
                    // tenter de trouver une partie à cette date
                    Partie partieChercher = profil.getPartie(datePartie) ;
                    // Si partie trouvée, recupère le mot de la partie existante, sinon ???
                    if(partieChercher!=null) {
                       // joue
                       joue(partieChercher);
                       // enregistre la partie dans le profil --> enregistre le profil
                       profil.ajouterPartie(partieChercher) ;
                       profil.sauvegarder("");
                       playTheGame = MENU_VAL.MENU_JOUE ;
                    }
                    else {
                       playTheGame = MENU_VAL.MENU_JOUE ;
                    }
                   
             
                    break;

                // -----------------------------------------
                // Touche 3 : Sortie de ce jeu
                // -----------------------------------------                
                case Keyboard.KEY_3:
                    playTheGame = MENU_VAL.MENU_CONTINUE;
                    break;

                // -----------------------------------------
                // Touche 4 : Quitter le jeu
                // -----------------------------------------                
                case Keyboard.KEY_4:
                    playTheGame = MENU_VAL.MENU_SORTIE;
            }
        } while (playTheGame == MENU_VAL.MENU_JOUE);
        return playTheGame;
    }

    public void joue(Partie partie) {
   
        // Instancie un Tux
        tux = new Tux(env,mainRoom) ;
        env.addObject(tux);

        // Ici, on peut initialiser des valeurs pour une nouvelle partie
        demarrePartie(partie);
        // Boucle de jeu
        boolean finished = false;
        while (!finished) {
 
            // Contrôles globaux du jeu (sortie, ...)
            //1 is for escape key
            if (env.getKey() == 1) {
                finished = true ;
            }            

            // Contrôles des déplacements de Tux (gauche, droite, ...)
            tux.deplacer();
 
            // Ici, on applique les regles
            appliqueRegles(partie);
 
            // Fait avancer le moteur de jeu (mise à jour de l'affichage, de l'écoute des événements clavier...)
            env.advanceOneFrame();
        }
 
        // Ici on peut calculer des valeurs lorsque la partie est terminée
        terminePartie(partie);
                 System.out.println("1111111111");

    }
    protected abstract void demarrePartie(Partie partie);
    protected abstract void appliqueRegles(Partie partie);
    protected abstract void terminePartie(Partie partie);
    protected double distance(Letter letter) {
        return Math.sqrt(Math.pow(tux.getX()-letter.getX(), 2)+Math.pow(tux.getY()-letter.getY(), 2)+Math.pow(tux.getZ()-letter.getZ(), 2));
    }
    protected boolean collision(Letter letter) {
        return (distance(letter) < 1.0) ;
        
    }
    public ArrayList<Letter> getListLetters() {
        return lettres ;
    
    }
    
    
}
