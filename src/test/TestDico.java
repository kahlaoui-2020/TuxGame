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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import xml.XMLUtil;
/**
 *
 * @author kahlaoui
 */
public class TestDico {
    
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        
        
        Dico dico = new Dico("src/xmlFile/dico.xml") ;
        dico.lireDictionnaire();
        System.out.println("----------------------------------");
        System.out.println("niveau 1");
        System.out.println("----------------------------------");
        for(String e : dico.getList1()) {
            System.out.println("mot : "+e);
        }
        System.out.println("\n----------------------------------");
        System.out.println("niveau 2");
        System.out.println("----------------------------------");
        for(String e : dico.getList2()) {
            System.out.println("mot : "+e);
        }
        System.out.println("^\n----------------------------------");
        System.out.println("niveau 3");
        System.out.println("----------------------------------");
        for(String e : dico.getList3()) {
            System.out.println("mot : "+e);
        }
        System.out.println("\n----------------------------------");
        System.out.println("niveau 4");
        System.out.println("----------------------------------");
        for(String e : dico.getList4()) {
            System.out.println("mot : "+e);
        }
        System.out.println("\n----------------------------------");
        System.out.println("niveau 5");
        System.out.println("----------------------------------");
        for(String e : dico.getList5()) {
            System.out.println("mot : "+e);
        }
        
        
    
    
    
    
    
    }









     /*@SuppressWarnings("unchecked")
        ArrayList<String> lettres = new ArrayList() ;
        lettres.add("a") ;
        lettres.add("b") ;
        lettres.add("3") ;
        lettres.add("n") ;
        lettres.add("b") ;
        for(String e : lettres) {
            System.out.println(e);
        
        }
        lettres.remove(1) ;
        System.out.println("---------------------------");
        for(String e : lettres) {
            System.out.println(e);
        
        }
        /*Dico dico = new Dico("src/xmlFile/dico.xml") ;
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
