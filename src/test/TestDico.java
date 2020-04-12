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
import static game.Profil.xmlDateToProfileDate;
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
     
        Profil profil = new Profil() ;
        
        
        
       
        
        String nomFichier = "/home/kahlaoui/Bureau/ghaieth/TuxLetterGame_template/src/xmlFile/profil.xml";
        
        @SuppressWarnings("unchecked")
        ArrayList<Partie> parties;
        parties = new ArrayList();
        
        Document _doc = profil.fromXML(nomFichier) ;
    
        Element racine = (Element) _doc.getChildNodes().item(1) ;
        
        Element nomElt =  (Element) racine.getChildNodes().item(1);
        Element avatarElt = (Element) racine.getElementsByTagName("avatar").item(0);
        Element NaissanceElt = (Element) racine.getElementsByTagName("anniversaire").item(0);
        NodeList partiesElt = racine.getElementsByTagName("parties").item(0).getChildNodes();
        
        String nom = nomElt.getTextContent() ;
        String avatar = avatarElt.getTextContent() ;
        String dateNaissance = xmlDateToProfileDate(NaissanceElt.getTextContent()) ;
      
        
        Partie partie ;
        Node partieNode ;
        String date ;
        String mot ;
        int niveau ;
        double temps ;
        
        for(int i = 0; i < partiesElt.getLength(); i++) {
            partieNode = partiesElt.item(i);
            date = xmlDateToProfileDate(((Element)partieNode).getAttribute("date")) ;
            temps = Double.valueOf(partieNode.getFirstChild().getNodeValue()) ;
            mot = partieNode.getLastChild().getNodeValue() ;
            niveau = Integer.valueOf(((Element)(partieNode.getLastChild())).getAttribute("niveau")) ;
            partie = new Partie(date,mot,niveau) ;
            partie.setTemps(temps);
            parties.add(partie) ;
        }
        System.out.println(nom);
        System.out.println(avatar);
        System.out.println(dateNaissance);
       
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
    

