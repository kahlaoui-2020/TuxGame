/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import game.Dico;
import java.io.IOException;
import java.util.Scanner;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author kahlaoui
 */
public class TestDico {
    
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
     
        Dico dico = new Dico("src/test/dico.xml") ;
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
