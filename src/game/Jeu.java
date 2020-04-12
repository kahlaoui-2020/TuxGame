/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;
import env3d.Env;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import static jdk.nashorn.internal.runtime.JSType.isNumber;
import org.lwjgl.input.Keyboard;
import org.xml.sax.SAXException;

/**
 *
 * @author gladen
 */
public abstract class Jeu {

    

    enum MENU_VAL {
        MENU_SORTIE, MENU_CONTINUE, MENU_JOUE
    }
    
    private final Room mainRoom;
    private final Room menuRoom;
    //text (affichage des texte du jeu)
    EnvText textNomJoueur;
    EnvText textNiveauJeu ;
    EnvText textDateNaissance;
    EnvText textDatePartie;
    EnvText textMenuQuestion;
    EnvTextMap menuText;  
    EnvText textMenuJeu1;
    EnvText textMenuJeu2;
    EnvText textMenuJeu3;
    
    EnvText textMenuJeu4;
    EnvText textMenuPrincipal1;
    EnvText textMenuPrincipal2;
    EnvText textMenuPrincipal3;
    EnvText textEtatJeu;
    
    EnvText textMenuJeu12;
    EnvText textMenuJeu13;
    
   
    
    private  Env env;
    private Tux tux;
    
    private  ArrayList<Letter> lettres ;
    private Profil profil;
    private  Dico dico;
    Boolean finished;
    
    /**
     *
     * @return
     */
    protected EnvTextMap getMenuText() {
        return menuText;
    }
    
    
    
    public Jeu() throws ParserConfigurationException, SAXException, IOException {

        // Crée un nouvel environnement
        env = new Env();

        // Instancie une Room
        mainRoom = new Room();

        // Instancie une autre Room pour les menus
        menuRoom = new Room();
        menuRoom.setTextureEast("textures/black.png");
        menuRoom.setTextureWest("textures/black.png");
        menuRoom.setTextureNorth("textures/black.png");
        menuRoom.setTextureBottom("textures/black.png");

        // Règle la camera
        env.setCameraXYZ(50, 60, 175);
        env.setCameraPitch(-20);

        // Désactive les contrôles par défaut
        env.setDefaultControl(false);

        // instancie le menuText
        menuText = new EnvTextMap(env);
        
        // Textes affichés à l'écran
        menuText.addText("Voulez vous ?", "Question", 200, 300);
        menuText.addText("1. Commencer une nouvelle partie ?", "Jeu1", 250, 280);
        menuText.addText("2. Charger une partie existante ?", "Jeu2", 250, 260);
        menuText.addText("3. Saisir la date de partie  ?", "DatePartie", 200, 300);
        menuText.addText("3. Sortir de ce jeu ?", "Jeu3", 250, 240);
        menuText.addText("4. Quitter le jeu ?", "Jeu4", 250, 220);
        menuText.addText("Choisissez un nom de joueur : ", "NomJoueur", 200, 300);
        menuText.addText("Choisissez le niveau de difficulté (1-5) : ", "NiveauJeu", 200, 300);
        menuText.addText("Saisir votre date de naissance (dd-mm-yyyy) : ", "DateNaissance", 200, 300);
        menuText.addText("1. Charger un profil de joueur existant ?", "Principal1", 250, 280);
        menuText.addText("2. Créer un nouveau joueur ?", "Principal2", 250, 260);
        menuText.addText("3. Sortir du jeu ?", "Principal3", 250, 240);
        menuText.addText("Félicitation vous avez gagné la partie ! À la prochaine\nEntrer pour conntinuer", "TextEtatJeu", 200, 300);
        
        menuText.addText("2. Sortir de ce jeu ?", "Jeu12", 250, 260);
        menuText.addText("3. Quitter le jeu ?", "Jeu13", 250, 240);
    }

    /**
     * Gère le menu principal
     *
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws org.xml.sax.SAXException
     * @throws java.io.IOException
     * @throws javax.xml.transform.TransformerException
     */
    public void execute() throws ParserConfigurationException, SAXException, IOException, TransformerException {

        MENU_VAL mainLoop;
        mainLoop = MENU_VAL.MENU_SORTIE;
        do {
            mainLoop = menuPrincipal();
        } while (mainLoop != MENU_VAL.MENU_SORTIE);
        this.env.setDisplayStr("Au revoir !", 300, 30);
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
    // fourni
    private String getNomJoueur() {
        String nomJoueur = "";
        menuText.getText("NomJoueur").display();
        nomJoueur = menuText.getText("NomJoueur").lire(true);
        menuText.getText("NomJoueur").clean();
        return nomJoueur;
    }

    
    // fourni, à compléter
    private MENU_VAL menuJeu() throws ParserConfigurationException, SAXException, IOException, TransformerException {

        MENU_VAL playTheGame;
        playTheGame = MENU_VAL.MENU_JOUE;
        Partie partie = null;
        do {
            // restaure la room du menu
            env.setRoom(menuRoom);
            // affiche menu
            menuText.getText("Question").display();
            menuText.getText("Jeu1").display();
            menuText.getText("Jeu2").display();
            menuText.getText("Jeu3").display();
            menuText.getText("Jeu4").display();
            
            // vérifie qu'une touche 1, 2, 3 ou 4 est pressée
            int touche = 0;
            while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3 || touche == Keyboard.KEY_4)) {
                touche = env.getKey();
                env.advanceOneFrame();
            }

            // nettoie l'environnement du texte
            menuText.getText("Question").clean();
            menuText.getText("Jeu1").clean();
            menuText.getText("Jeu2").clean();
            menuText.getText("Jeu3").clean();
            menuText.getText("Jeu4").clean();

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
                    this.dico = new Dico("src/xmlFile/dico.xml") ;
                    
                    String mot = this.dico.getMotDepuisListeNiveaux(niveau) ;
                    
                    // Preciser le date
                    SimpleDateFormat dateFormat = new SimpleDateFormat("DD/MM/YYYY") ;
                    String date = dateFormat.format(new Date()) ;
                    // crée un nouvelle partie
                    partie = new Partie(date,mot,niveau);
                    // joue
                    joue(partie);
                    // enregistre la partie dans le profil --> enregistre le profil
                    profil.ajouterPartieXML(partie) ;
                    playTheGame = MENU_VAL.MENU_JOUE;
                    break;

                // -----------------------------------------
                // Touche 2 : Charger une partie existante
                // -----------------------------------------                
                case Keyboard.KEY_2: 
                    // charger une partie existante
                    String datePartie = getDate();
                    ArrayList<Partie> parties = profil.getParties() ;
                    int i = 0 ;
                    while(!parties.get(i).getDate().equals(datePartie) && i<parties.size()) {
                        i++;
                    }
                    if(i<parties.size()) {
                        joue(parties.get(i));
                        profil.ajouterPartieXML(parties.get(i));
                    }
                    
                    
                    playTheGame = MENU_VAL.MENU_JOUE;
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
    private MENU_VAL menuJeu1() throws ParserConfigurationException, SAXException, IOException, TransformerException {

        MENU_VAL playTheGame;
        playTheGame = MENU_VAL.MENU_JOUE;
        Partie partie = null;
        do {
            // restaure la room du menu
            env.setRoom(menuRoom);
            // affiche menu
            menuText.getText("Question").display();
            
            menuText.getText("Jeu1").display();
            menuText.getText("Jeu12").display(); 
            menuText.getText("Jeu13").display(); 
    
            
            // vérifie qu'une touche 1, 2, 3 ou 4 est pressée
            int touche = 0;
            while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3  )) {
                touche = env.getKey();
                env.advanceOneFrame();
            }

            // nettoie l'environnement du texte
            menuText.getText("Question").clean();
            menuText.getText("Jeu1").clean();
            menuText.getText("Jeu12").clean();
            menuText.getText("Jeu13").clean();
            

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
                    // Preciser le date
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy  HH:MM") ;
                    String date = dateFormat.format(new Date()) ;
                    // crée un nouvelle partie
                    partie = new Partie(date,mot,niveau);
                    // joue
                    joue(partie);
                    // enregistre la partie dans le profil --> enregistre le profil
                    profil.ajouterPartieXML(partie) ;
                    playTheGame = MENU_VAL.MENU_JOUE;
                    break;
                    
                // -----------------------------------------
                // Touche 3 : Sortie de ce jeu
                // -----------------------------------------                
                case Keyboard.KEY_2:
                    playTheGame = MENU_VAL.MENU_CONTINUE;
                    break;

                // -----------------------------------------
                // Touche 4 : Quitter le jeu
                // -----------------------------------------                
                case Keyboard.KEY_3:
                    playTheGame = MENU_VAL.MENU_SORTIE;
            }
        } while (playTheGame == MENU_VAL.MENU_JOUE);
        return playTheGame;
    }
    private MENU_VAL menuPrincipal() throws ParserConfigurationException, SAXException, IOException, TransformerException {

        MENU_VAL choix = MENU_VAL.MENU_CONTINUE;
        String nomJoueur;
        String dateNaissance ;

        // restaure la room du menu
        env.setRoom(menuRoom);

        menuText.getText("Question").display();
        menuText.getText("Principal1").display();
        menuText.getText("Principal2").display();
        menuText.getText("Principal3").display();
               
        // vérifie qu'une touche 1, 2 ou 3 est pressée
        int touche = 0;
        while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3)) {
            touche = env.getKey();
            env.advanceOneFrame();
        }

        menuText.getText("Question").clean();
        menuText.getText("Principal1").clean();
        menuText.getText("Principal2").clean();
        menuText.getText("Principal3").clean();

        // et décide quoi faire en fonction de la touche pressée
        switch (touche) {
            // -------------------------------------
            // Touche 1 : Charger un profil existant
            // -------------------------------------
            case Keyboard.KEY_1:
                // demande le nom du joueur existant
                nomJoueur = getNomJoueur();
                System.out.println(nomJoueur);
                // charge le profil de ce joueur si possible
                if (charger(nomJoueur)) {
                    profil = new Profil(nomJoueur) ;
                    choix = menuJeu();
                } else {
                    choix = MENU_VAL.MENU_SORTIE;//CONTINUE;
                }
                break;

            // -------------------------------------
            // Touche 2 : Créer un nouveau joueur
            // -------------------------------------
            case Keyboard.KEY_2:
                // demande le nom du nouveau joueur
                nomJoueur = getNomJoueur();
                // demande la date de naissance
                dateNaissance = getDate() ;
                // crée un profil avec le nom d'un nouveau joueur
                profil = new Profil(nomJoueur,dateNaissance);
                profil.sauvegarder();
                choix = menuJeu1();
                break;

            // -------------------------------------
            // Touche 3 : Sortir du jeu
            // -------------------------------------
            case Keyboard.KEY_3:
                choix = MENU_VAL.MENU_SORTIE;
        }
        return choix;
    }

    @SuppressWarnings("unchecked")
    public void joue(Partie partie) {

        // Instancie un Tux
        tux = new Tux(env, mainRoom);
        env.addObject(tux);
        lettres = new ArrayList() ;
        getListLetterFromMot(partie.getMot()) ;
       
        for(Letter l : lettres) {
            env.addObject(l);
        
        }

        // Ici, on peut initialiser des valeurs pour une nouvelle partie
        demarrePartie(partie);

        // Boucle de jeu
        finished = false;
        while (!finished) {

            // Contrôles globaux du jeu (sortie, ...)
            //1 is for escape key
            if (env.getKey() == 1) {
                finished = true;
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

    }
    
    private int getNiveau() {
        int niveau = 0;
        menuText.getText("NiveauJeu").display();
        niveau = Integer.parseInt(menuText.getText("NiveauJeu").lire(true));
        /*int key = 0;
        while(niveau > 5 && niveau < 1 && key !=Keyboard.KEY_RETURN) {
            key =  env.getKey();
            if(isNumber(key)) niveau = key ;
            env.advanceOneFrame();
        
        }*/
        menuText.getText("NiveauJeu").clean();
        return niveau ;    
    }

    private String getDate() {
        String date = null ;
        
        menuText.getText("DatePartie").display();
        
       
       // menuText.getText("DatePartie").addTextAndDisplay(date, date);
       date =   menuText.getText("DatePartie").lire(true) ;
       /* int key = 0 ;
        while(date==null || !(date.length()>0 && key == Keyboard.KEY_RETURN)) {
            key = 0 ;
            while(!(isNumber(key) || key == Keyboard.KEY_BACKSLASH || key == Keyboard.KEY_RETURN)) {
                key = env.getKey() ;
                env.advanceOneFrame();
            }
            if(key != Keyboard.KEY_RETURN) {
              menuText.getText("DatePartie").modify(menuText.getText("DatePartie").toString()+String.valueOf(key));
              date += String.valueOf(key) ;
            }
    
    
        }*/
        menuText.getText("DateNaissance").clean();  
        return date ;    
    }
    
    protected double distance(Letter letter) {
        return tux.distance(letter) ;
    }
    protected boolean collision(Letter letter) {
        return (distance(letter) < 3) ;
        
    }
    public void getListLetterFromMot(String mot) {
        Random rx ;
        Random rz ;
        double lx;
        double lz;
        for(char l : mot.toCharArray()) {
            
            rx = new Random(0.0,tux.getX()) ;
            do {
                 lx = rx.get() ;
            }while(lx+6>tux.getX() && lx-6<tux.getX() && lx+2 > mainRoom.getWidth() && l-2 > 0);
            
            rz = new Random(0.0,tux.getZ()) ;
            do {
                lz = rz.get() ;
                 
            }while(lz+6>tux.getZ() && lz-6<tux.getZ() && lx+2 > mainRoom.getDepth() && l-2 > 2);
            lettres.add(new Letter(l,lx,lz)) ;
        }
    }
    
    protected void removeLetterFromEnv(Letter l) {
        env.removeObject(l);
        env.advanceOneFrame();
    }
    public ArrayList<Letter> getLetters() {
        return lettres;
    }
    public void removeLetterFromList(Letter l) {
        lettres.remove(l) ;
    }
    protected Tux getTux() {
        return tux ;
    }
    protected Env getEnv() {
        return env ;
    }
    public Room getRoom() {
        return mainRoom ;
    }
    public void setFinished(boolean bool) {
        finished = bool ;
    }
    
    boolean charger(String nomJoueur) {
        String nomFichier = "src/xmlFile/"+nomJoueur+".xml" ;
        File fichier = new File(nomFichier);
        return fichier.exists() ;
    }
    protected abstract void demarrePartie(Partie partie);

    protected abstract void appliqueRegles(Partie partie);

    protected abstract void terminePartie(Partie partie);

}