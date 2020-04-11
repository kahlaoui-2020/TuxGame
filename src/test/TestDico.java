/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import game.Dico;
import game.Jeu;
import game.Letter;
import game.Partie;
import game.Profil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
/**
 *
 * @author kahlaoui
 */
public class TestDico {
    
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {
     
        Dico dico = new Dico("src/xmlFile/dico.xml") ;
        int niveau = 3 ;
        String mot = dico.getMotDepuisListeNiveaux(niveau) ;
        Jeu jeu = new Jeu() {
            @Override
            protected void demarrePartie(Partie partie) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            protected void appliqueRegles(Partie partie) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            protected void terminePartie(Partie partie) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        } ;
        jeu.getListLetterFromMot(mot) ;
        ArrayList<Letter> lettres = jeu.getLetters() ;
        System.out.println(mot);
        for(Letter l : lettres) {
            System.out.println("lettre : '"+l.getChar()+"' x = "+l.getZ()+", z = "+l.getX()) ;
        
        
        }
        //Document _doc = fromXML("scr/xmlFile/profil.xml");
//        Profil profil1 = new Profil("profil");
    //    ArrayList<Partie> parties = profil1.getParties() ;
        //Profil profil = new Profil("Sami","00-00-0000") ;
        System.out.println("Done");
        /*for(int i = 0; i<5; i++) {
            
            Scanner sc = new Scanner(System.in);
            System.out.println("donner un mot");
            String mot = sc.nextLine() ;
            System.out.println("donner son niveau");
            int niveau = sc.nextInt() ;
            dico.ajouteMotDico(niveau, mot);

        }*/
        // dico.lireDictionnaireDOM("src/test/","dico.xml");
        /*System.out.println("Mot niveau 1 : ") ;
        for(String mot : dico.getList1()) {
            System.out.println(mot) ;
        }
        System.out.println("Mot niveau 2 : ") ;
        for(String mot : dico.getList2()) {
            System.out.println(mot) ;
        }*/
       /*System.out.println("list1  : "+dico.getMotDepuisListeNiveaux(1)) ;
       System.out.println("list3  : "+dico.getMotDepuisListeNiveaux(2)) ;
       System.out.println("list2  : "+dico.getMotDepuisListeNiveaux(2)) ;
       System.out.println("list1  : "+dico.getMotDepuisListeNiveaux(1)) ;
       System.out.println("list1  : "+dico.getMotDepuisListeNiveaux(1)) ;*/
        
        
    }
    
}
