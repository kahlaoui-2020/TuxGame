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
import java.util.logging.Level;
import java.util.logging.Logger;
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
        MENU_SORTIE, MENU_CONTINUE, MENU_JOUE, MENU_DICO
    }
    
    private final Room mainRoom;
    private final Room menuRoom;
    //text (affichage des texte du jeu)
    EnvText textNomJoueur;
    EnvText textNiveauJeu ;
    EnvText textMotJeu ;
    EnvText textAvanceJeu ;
    EnvText textTimeJeu ;
    EnvText textDateNaissance;
    EnvText textDatePartie;
    EnvText textDateInvalide ;
    EnvText textMenuQuestion;
    EnvTextMap menuText;  
    EnvText textMenuJeu1;
    EnvText textMenuJeu2;
    EnvText textMenuJeu3;
    
    EnvText textMenuJeu4;
    EnvText textMenuPrincipal1;
    EnvText textMenuPrincipal2;
    EnvText textMenuPrincipal3;
    EnvText textMenuPrincipal4;

    EnvText textEtatJeu;
    
    EnvText textQuestionDico;
    EnvText textAfficheDico;
    EnvText textAjouteMot;
    EnvText textConntinue;
    
    
    EnvText textMenuJeu12;
    EnvText textMenuJeu13;
    
   
    
    private  Env env;
    private Tux tux;
    
    private  ArrayList<Letter> lettres ;
    String mot ;
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
        mainRoom = new Room("src/xmlFile/plateau.xml");

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
        menuText.addText("Saisir la date de partie  ?", "DatePartie", 200, 300);
        menuText.addText("date invalide\nTaper sur ENTRER pour continnuer !","DateInvalide",200,300);
        menuText.addText("3. Sortir du  jeu ?", "Jeu3", 250, 240);
        menuText.addText("4. Quitter le jeu ?", "Jeu4", 250, 220);
        menuText.addText("Choisissez un nom de joueur : ", "NomJoueur", 200, 300);
        menuText.addText("Choisissez le niveau de difficulté (1-5) : ", "NiveauJeu", 200, 300);
        menuText.addText("Saisir votre date de naissance (dd-mm-yyyy) : ", "DateNaissance", 200, 300);
        menuText.addText("1. Charger un profil de joueur existant ?", "Principal1", 250, 280);
        menuText.addText("2. Créer un nouveau joueur ?", "Principal2", 250, 260);
        menuText.addText("3. Gestion de dictionnaire ?", "Principal3", 250, 240);
        menuText.addText("4. Sortir du jeu ?", "Principal4", 250, 220);
        menuText.addText("Félicitation vous avez gagné la partie ! À la prochaine\nEntrer pour conntinuer", "TextEtatJeu", 200, 300);
        
        menuText.addText("2. Sortir de ce jeu ?", "Jeu12", 250, 260);
        menuText.addText("3. Quitter le jeu ?", "Jeu13", 250, 240);
        menuText.addText("","MotJeu",200,300);
        
        
        textAvanceJeu = new EnvText(env,"Mot ! : ",5,450);
        textAvanceJeu.setColorRGB(0.0,0.0,0.0); 

        textTimeJeu = new EnvText(env,"Remain time ! : ",450,450);
        textTimeJeu.setColorRGB(0.0,0.0,0.0); 
        
        
        menuText.addText("Voulez vous ?", "QuestionDico", 180, 300);
        menuText.addText("1.Afficher le Dico ?", "AfficheDico", 200, 280);
        menuText.addText("2.Ajouter un mot ?", "AjouteMot", 200, 260);
        menuText.addText("Entrer pour conntinuer ", "Conntinue", 200, 50);
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
    
   
    private Boolean isNumber(int codeKey) {
        return codeKey == Keyboard.KEY_0 || codeKey == Keyboard.KEY_1 || codeKey == Keyboard.KEY_2 || codeKey == Keyboard.KEY_3 ||
                codeKey == Keyboard.KEY_4 || codeKey == Keyboard.KEY_5 || codeKey == Keyboard.KEY_6 || codeKey == Keyboard.KEY_7 ||
                codeKey == Keyboard.KEY_8 || codeKey == Keyboard.KEY_9;
    
    
    }
    private int getNumber(int codeKey) {
       switch(codeKey) {
                                
           case Keyboard.KEY_1 : return 1 ;
           case Keyboard.KEY_2 : return 2 ;
           case Keyboard.KEY_3 : return 3 ;
           case Keyboard.KEY_4 : return 4 ;
           case Keyboard.KEY_5 : return 5 ;
           case Keyboard.KEY_6 : return 6 ;
           case Keyboard.KEY_7 : return 7 ;
           case Keyboard.KEY_8 : return 8 ;
           case Keyboard.KEY_9 : return 9 ;
           default: return 0;
       }
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


            // et décide quoi faire en fonction de la touche pressée
            switch (touche) {
                // -----------------------------------------
                // Touche 1 : Commencer une nouvelle partie
                // -----------------------------------------                
                case Keyboard.KEY_1: 
                    // choisi un niveau et charge un mot depuis le dico
                    int niveau = getNiveau() ;
                    this.dico = new Dico("src/xmlFile/dico.xml") ;
                    dico.lireDictionnaire();
                    String mot = this.dico.getMotDepuisListeNiveaux(niveau) ;
                    
                    // Preciser le date
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY") ;
                    String date = dateFormat.format(new Date()) ;
                    // afficher mot a cherche
                    afficheMot(mot) ;
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
                    String datePartie = getDatePartie();
                    ArrayList<Partie> parties = profil.getParties() ;
                    int i = 0 ;
                    while( i<parties.size() && !parties.get(i).getDate().equals(datePartie)) {
                        i++;
                    }
                    if(i<parties.size()) {
                        joue(parties.get(i));
                        profil.ajouterPartieXML(parties.get(i));
                    }else{
                        int key = 0;
                        menuText.getText("DateInvalide").display();
                        while(key != Keyboard.KEY_RETURN){
                            key = env.getKey();
                            env.advanceOneFrame();
                        }
                        menuText.getText("DateInvalide").clean();
                        
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
            

            

            // et décide quoi faire en fonction de la touche pressée
            switch (touche) {
                // -----------------------------------------
                // Touche 1 : Commencer une nouvelle partie
                // -----------------------------------------                
                case Keyboard.KEY_1: 
                    // choisi un niveau et charge un mot depuis le dico
                    int niveau = getNiveau() ;
                    this.dico = new Dico("src/xmlFile/dico.xml") ;
                    dico.lireDictionnaire();
                    String mot = this.dico.getMotDepuisListeNiveaux(niveau) ;
                    // Preciser le date
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY") ;
                    
                    String date = dateFormat.format(new Date()) ;
                   
                    // Afficher Mot
                    afficheMot(mot) ;
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
    
    private MENU_VAL menuDico() throws SAXException, IOException, ParserConfigurationException{
        
        MENU_VAL choix = MENU_VAL.MENU_CONTINUE;
        env.setRoom(menuRoom);
       
        do {
        menuText.getText("QuestionDico").display();
        menuText.getText("AfficheDico").display();
        menuText.getText("AjouteMot").display();
        menuText.getText("Jeu3").moveAndDisplay(200,240);
    
        int touche = 0;
        while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3  )) {
            touche = env.getKey();
            env.advanceOneFrame();
        }
        
        menuText.getText("QuestionDico").clean();
        menuText.getText("AfficheDico").clean();
        
        menuText.getText("AjouteMot").clean();
        menuText.getText("Jeu3").clean();
        EditeurDico editDico = new EditeurDico("dico");
        switch(touche) {
            
            case Keyboard.KEY_1 :
                
                editDico.lireDOM("dico");
                editDico.AfficheDico(env, menuText);
                choix = MENU_VAL.MENU_DICO;
                break;
                
                
                
            case Keyboard.KEY_2 :
                
                String motS = getNomJoueur() ;
                int niveau = getNiveau()  ;
                editDico.setFileName("dico");
                editDico.ajouterMot(motS,niveau);
                
                
                
                
                choix = MENU_VAL.MENU_DICO;
                break;


                
                
            case Keyboard.KEY_3:
                choix = MENU_VAL.MENU_CONTINUE;
                break;
                
            
        }
        }while(choix ==  MENU_VAL.MENU_DICO);
        return choix ;
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
        menuText.getText("Principal4").display();
               
        // vérifie qu'une touche 1, 2 ou 3 est pressée
        int touche = 0;
        while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3 || touche == Keyboard.KEY_4)) {
            touche = env.getKey();
            env.advanceOneFrame();
        }

        menuText.getText("Question").clean();
        menuText.getText("Principal1").clean();
        menuText.getText("Principal2").clean();
        menuText.getText("Principal3").clean();
        menuText.getText("Principal4").clean();

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
                dateNaissance = getDateNaissance() ;
                // crée un profil avec le nom d'un nouveau joueur
                profil = new Profil(nomJoueur,dateNaissance);
                choix = menuJeu1();
                break;
                
            // -------------------------------------
            // Touche 3 : Gestion de Dico
            // -------------------------------------
                
            case Keyboard.KEY_3:
                choix = menuDico();
                break;
            // -------------------------------------
            // Touche 4 : Sortir du jeu
            // -------------------------------------
            case Keyboard.KEY_4:
                choix = MENU_VAL.MENU_SORTIE;
        }
        return choix;
    }

    @SuppressWarnings("unchecked")
    public void joue(Partie partie) {

        env.setRoom(mainRoom);
        // Instancie un Tux
        tux = new Tux(env, mainRoom);
        textAvanceJeu.display();
        textTimeJeu.display();
        
        
        env.addObject(tux);
        lettres = new ArrayList() ;
        mot = "" ;
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
            textTimeJeu.addTextAndDisplay("",String.valueOf(getTime().getSpentTime()));
            textAvanceJeu.addTextAndDisplay("", mot);
            
            
            // Fait avancer le moteur de jeu (mise à jour de l'affichage, de l'écoute des événements clavier...)
            env.advanceOneFrame();
        }
        
        // Ici on peut calculer des valeurs lorsque la partie est terminée
        terminePartie(partie);
        textTimeJeu.clean();
        textAvanceJeu.clean();
        
    }
    
    private void afficheMot(String mot) {

        
        menuText.getText("MotJeu").modify("Le Mot à chercher ! "+mot);
        
        Chronometre chrono ;
        chrono = new Chronometre(1) ;
        chrono.start();
        while(chrono.remainsTime()) {
            env.advanceOneFrame();
        }
        chrono.stop();
        
        menuText.getText("MotJeu").clean();
   


    }
    
    private int getNiveau() {
        
        String niveau = "" ;
        menuText.getText("NiveauJeu").display();
        int key = 0;
        while(!(niveau.length() == 1 && key == Keyboard.KEY_RETURN)){
            do{
                key = env.getKey();
                env.advanceOneFrame();
            }while( !isNumber(key) && ( Integer.valueOf(getNumber(key)) > 5 || Integer.valueOf(getNumber(key))  <1 ) && key != Keyboard.KEY_BACK && key != Keyboard.KEY_RETURN);
            if(key == Keyboard.KEY_BACK){
                niveau = delete(niveau);
            }else if(isNumber(key)){
                niveau+=getLetter(key);
            }
            menuText.getText("NiveauJeu").addTextAndDisplay("",niveau);
           
        }
            menuText.getText("NiveauJeu").clean();
            return Integer.valueOf(niveau);    
    }

    private String getDatePartie() {
        
        String date = "";
        int key = 0;
        menuText.getText("DatePartie").display();
        
       while(!(date.length()==10 && key == Keyboard.KEY_RETURN)){
            do{
                key = env.getKey();
                env.advanceOneFrame();
            }while(!isNumber(key) && key != Keyboard.KEY_BACK && key != Keyboard.KEY_RETURN);
            if(key == Keyboard.KEY_BACK) {
                date = delete(date);
            }else if(isNumber(key)){
                date+=getLetter(key);
                if(date.length() == 2 || date.length() == 5) date+="/" ;
            }
            menuText.getText("DatePartie").addTextAndDisplay("",date);
            
        }
        
        menuText.getText("DatePartie").clean();
        return date ;    
    }
    private String getDateNaissance() {
        
        String date = "";
        int key = 0;
        menuText.getText("DateNaissance").display();
        while(!(date.length()==10 && key == Keyboard.KEY_RETURN)){
            do{
                key = env.getKey();
                env.advanceOneFrame();
            }while(!isNumber(key) && key != Keyboard.KEY_BACK && key != Keyboard.KEY_RETURN);
            if(key == Keyboard.KEY_BACK) {
                date = delete(date);
            }else if(isNumber(key)){
                date+=getLetter(key);
                if(date.length() == 2 || date.length() == 5) date+="/" ;
            }
            menuText.getText("DateNaissance").addTextAndDisplay("",date);
            
        }
       
        menuText.getText("DateNaissance").clean();  
        
        return date ;    
    }
    
    private String delete(String mot) {
        String res = "";
        for (int i = 0; i < mot.length() - 1; i++) {
            res += mot.charAt(i);
        }
        return res;
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
            
            rx = new Random(0.0,mainRoom.getWidth()) ;
            do {
                 lx = rx.get() ;
            }while(lx+6>tux.getX() && lx-6<tux.getX());
            
            rz = new Random(0.0,mainRoom.getDepth()) ;
            do {
                lz = rz.get() ;
                 
            }while(lz+6>tux.getZ() && lz-6<tux.getZ());
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
        mot+= l.getChar() ;
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
    
    protected abstract Chronometre getTime();
    
    protected abstract void demarrePartie(Partie partie);

    protected abstract void appliqueRegles(Partie partie);

    protected abstract void terminePartie(Partie partie);

}
