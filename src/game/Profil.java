package game;

import game.Partie;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import xml.XMLUtil;

public class Profil {

    private String nom ;
    private String dateNaissance ;
    private String avatar ;
    private ArrayList<Partie> parties ;
    
    public Document _doc;
    
    
    public Profil(String nom, String dateNaissance) throws ParserConfigurationException {
        this.nom = nom ;
        this.dateNaissance = dateNaissance ;
        sauvegarder(nom) ;
    }
    
    // Cree un DOM à partir d'un fichier XML
    public Profil(String nomFichier) {
        _doc = fromXML("scr/xmlFile/"+nomFichier+".xml");
        
    
        Node nodeProfil = _doc.getFirstChild() ;
        
        this.nom = nodeProfil.getChildNodes().item(0).getNodeValue() ;
        this.dateNaissance = xmlDateToProfileDate(nodeProfil.getChildNodes().item(2).getNodeValue()) ;
        this.avatar = nodeProfil.getChildNodes().item(1).getNodeValue() ;
        NodeList nodeParties = nodeProfil.getChildNodes().item(3).getChildNodes();
        
        Partie partie ;
        Node partieNode ;
        String date ;
        String mot ;
        int niveau ;
        double temps ;
        
        for(int i = 0; i < nodeParties.getLength(); i++) {
            partieNode = nodeParties.item(i);
            date = xmlDateToProfileDate(((Element)partieNode).getAttribute("date")) ;
            temps = Double.valueOf(partieNode.getFirstChild().getNodeValue()) ;
            mot = partieNode.getLastChild().getNodeValue() ;
            niveau = Integer.valueOf(((Element)(partieNode.getLastChild())).getAttribute("niveau")) ;
            partie = new Partie(date,mot,niveau) ;
            partie.setTemps(temps);
            parties.add(partie) ;
        }
       
    }

    // Cree un DOM à partir d'un fichier XML
    public Document fromXML(String nomFichier) {
        try {
            return XMLUtil.DocumentFactory.fromFile(nomFichier);
        } catch (Exception ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // Sauvegarde un DOM en XML
    public void toXML(String nomFichier) {
        try {
            XMLUtil.DocumentTransform.writeDoc(_doc, nomFichier);
        } catch (Exception ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ajouterPartie(Partie p) {
        this.parties.add(p) ;
    }
    public int getDernierNiveau() {
        return parties.get(parties.size()-1).getNiveau() ;
    }
    public String toString() {
        return " nom du joueur "+nom+"\nNé(e) le "+dateNaissance ;
    
    }
    public void sauvegarder(String filename) throws ParserConfigurationException, TransformerConfigurationException, TransformerException {
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();
        /**/
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        
        DOMSource source = new DOMSource(document) ;
        StreamResult sortie = new StreamResult(new File("src/xmlFile/"+filename+".xml")) ;
        transformer.transform(source, sortie);
    }
    /// Takes a date in XML format (i.e. ????-??-??) and returns a date
    /// in profile format: dd/mm/yyyy
    public static String xmlDateToProfileDate(String xmlDate) {
        String date;
        // récupérer le jour
        date = xmlDate.substring(xmlDate.lastIndexOf("-") + 1, xmlDate.length());
        date += "/";
        // récupérer le mois
        date += xmlDate.substring(xmlDate.indexOf("-") + 1, xmlDate.lastIndexOf("-"));
        date += "/";
        // récupérer l'année
        date += xmlDate.substring(0, xmlDate.indexOf("-"));

        return date;
    }

    /// Takes a date in profile format: dd/mm/yyyy and returns a date
    /// in XML format (i.e. ????-??-??)
    public static String profileDateToXmlDate(String profileDate) {
        String date;
        // Récupérer l'année
        date = profileDate.substring(profileDate.lastIndexOf("/") + 1, profileDate.length());
        date += "-";
        // Récupérer  le mois
        date += profileDate.substring(profileDate.indexOf("/") + 1, profileDate.lastIndexOf("/"));
        date += "-";
        // Récupérer le jour
        date += profileDate.substring(0, profileDate.indexOf("/"));

        return date;
    }

    boolean charger(String nomJoueur) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    ArrayList<Partie> getParties() {
        return parties;
    }

    String getNomJoueur() {
        return nom;
    }

} 