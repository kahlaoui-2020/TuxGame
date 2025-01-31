package game;

import java.io.File;
import java.io.IOException;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
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
import org.xml.sax.SAXException;
import xmlUtil.XMLUtil;

public class Profil {

    private String nom ;
    private String dateNaissance ;
    private String avatar ;
    private ArrayList<Partie> parties;
    
    private Document _doc;
    
    
    public Profil(String nom, String dateNaissance) throws ParserConfigurationException, TransformerException {
    
        this.nom = nom ;
        this.dateNaissance = dateNaissance ;
        this.parties = new ArrayList();
        sauvegarder();
        
    }
    
   
    
    // Cree un DOM à partir d'un fichier XML
    public Profil(String nomFichier) throws ParserConfigurationException, SAXException, IOException {
        this.parties = new ArrayList();
        
        _doc = fromXML("src/xmlFile/"+nomFichier+".xml") ;
    
        this.nom = _doc.getElementsByTagName("nom").item(0).getTextContent();
        this.dateNaissance = xmlDateToProfileDate(_doc.getElementsByTagName("anniversaire").item(0).getTextContent());
        this.avatar = _doc.getElementsByTagName("avatar").item(0).getTextContent();
        
        NodeList node = _doc.getElementsByTagName("partie");
        Partie partie ;
        String date ;
        String mot ;
        int niveau ;
        int trouve ;
        double temps ;
        for(int i = 0; i<node.getLength();i++) {
            date = xmlDateToProfileDate(((Element)node.item(i)).getAttribute("date")) ;
            trouve = Integer.valueOf(((Element)node.item(i)).getAttribute("trouvé"));
            mot = ((Element)node.item(i)).getElementsByTagName("mot").item(0).getTextContent();
            temps = Double.valueOf(((Element)node.item(i)).getElementsByTagName("temps").item(0).getTextContent());
            niveau = Integer.valueOf( ((Element)((Element)node.item(i)).getElementsByTagName("mot").item(0)).getAttribute("niveau") );
            partie = new Partie(date,mot,niveau) ;
            partie.setTemps(temps);
            partie.setTrouve(trouve);
            this.parties.add(partie);        
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
            XMLUtil.DocumentTransform.writeDoc(getDoc(), nomFichier);
        } catch (Exception ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ajouterPartie(Partie p) {
        this.parties.add(p) ;
    }
    public void ajouterPartieXML(Partie p) {
        
        _doc = fromXML("src/xmlFile/"+this.nom+".xml");
        
        Element partiesElt = (Element) _doc.getElementsByTagName("parties").item(0) ;
        
        Element partieElt = _doc.createElement("partie");
        partieElt.setAttribute("date",profileDateToXmlDate(p.getDate()));
        partieElt.setAttribute("trouvé",Integer.toString(p.getTrouve()));
           
        Element tempsElt = _doc.createElement("temps");
        tempsElt.setTextContent(Double.toString(p.getTemps()));
            
        Element motElt = _doc.createElement("mot");
        motElt.setAttribute("niveau",Integer.toString(p.getNiveau()));
        motElt.setTextContent(p.getMot());
            
        partieElt.appendChild(tempsElt);
        partieElt.appendChild(motElt);
        partiesElt.appendChild(partieElt);
        toXML("src/xmlFile/"+this.nom+".xml") ;
        
    }
    public int getDernierNiveau() {
        return parties.get(parties.size()-1).getNiveau() ;
    }
    public String toString() {
        return " nom du joueur "+nom+"\nNé(e) le "+dateNaissance ;
    
    }
  
    public void sauvegarder() throws ParserConfigurationException, TransformerConfigurationException, TransformerException {
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();
        
        Element profileElt = document.createElement("profil");
        profileElt.setAttribute("xmlns", "http://myGame/tux");
        profileElt.setAttribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");
        profileElt.setAttribute("xsi:schemaLocation","http://myGame/tux src/xmlFile/profil.xsd");
        
        Element nomJoueurElt = document.createElement("nom");
        nomJoueurElt.setTextContent(this.nom);
        
        Element avatarElt = document.createElement("avatar");
        avatarElt.setTextContent(this.avatar);
        
        Element dateAnnElt = document.createElement("anniversaire");
        dateAnnElt.setTextContent(profileDateToXmlDate(this.dateNaissance));
        
        Element partiesElt = document.createElement("parties");
        
        profileElt.appendChild(nomJoueurElt);
        profileElt.appendChild(avatarElt);
        profileElt.appendChild(dateAnnElt);
        profileElt.appendChild(partiesElt);
        document.appendChild(profileElt);
        
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document) ;
        StreamResult sortie = new StreamResult(new File("src/xmlFile/"+this.nom+".xml")) ;
        
        //prologue
	transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
	transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
          
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        
        transformer.transform(source, sortie);
        
    }
    /// Takes a date in XML format (i.e. ????-??-??) and returns a date
    /// in profile format: dd/mm/yyy    
     public ArrayList<Partie> getParties() {
        return parties;
    }

    String getNomJoueur() {
        return nom;
    }

    /**
     * @return the _doc
     */
    public Document getDoc() {
        return _doc;
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

} 