package civilisation;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import civilisation.amenagement.Amenagement;
import civilisation.effects.Effect;
import civilisation.individu.cognitons.*;
import civilisation.individu.plan.NPlan;
import civilisation.individu.plan.action.Action;
import civilisation.individu.plan.action.OptionsActions;
import civilisation.inventaire.Objet;
import civilisation.world.Terrain;



public class Initialiseur {

	HashMap<String, NCogniton> listeCognitons;
	HashMap<String, Culturon> listCloudCognitons;
	HashMap<String, NPlan> listePlans;
	HashMap<Color, Terrain> couleurs_terrains; //GŽrer le cas ou la m�me couleur est utilisŽe pour deux terrains
	final int passabiliteParDefaut = 30;

	public Initialiseur(){
		ArrayList<Amenagement> amenagements = new ArrayList<Amenagement>();
		listeCognitons = new HashMap<String, NCogniton>();
		listCloudCognitons = new HashMap<String, Culturon>();
		listePlans = new HashMap<String, NPlan>();
		couleurs_terrains = new HashMap<Color, Terrain>();
		ArrayList<NCogniton> cognitonsDeBase = new ArrayList<NCogniton>();
		ArrayList<NCogniton> tousLesCognitons = new ArrayList<NCogniton>();
		ArrayList<Culturon> allCloudCogniton = new ArrayList<Culturon>();
		ArrayList<NPlan> tousLesPlans = new ArrayList<NPlan>();

		String nom;
		
		System.out.println("Attributes loading...");
		File[] filesAttributes = new File(System.getProperty("user.dir")+"/bin/civilisation/ressources/attributes").listFiles();
		ArrayList<String> attributesNames = new ArrayList<String>();
		ArrayList<Integer> attributesStartingValues = new ArrayList<Integer>();
		for (File file : filesAttributes) {			
			try{
				// création d'une fabrique de documents
				DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
				
				// création d'un constructeur de documents
				DocumentBuilder constructeur = fabrique.newDocumentBuilder();
				
				// lecture du contenu d'un fichier XML avec DOM
				Document document = constructeur.parse(file);
				
				//traitement du document
				Element racine = document.getDocumentElement();
				NodeList nameList = racine.getElementsByTagName("Name");	
				NodeList ValueList = racine.getElementsByTagName("Value");	
				//System.out.print(nameList.item(0).getChildNodes().item(0).getNodeValue());
				if(nameList.item(0) != null)
				{
					nom = nameList.item(0).getChildNodes().item(0).getNodeValue();
					attributesNames.add(nom);
				}
				if(ValueList.item(0) != null)
				{
					int val = Integer.parseInt(ValueList.item(0).getChildNodes().item(0).getNodeValue());
					attributesStartingValues.add(val);
				}
				
				
			}catch(ParserConfigurationException pce){
				System.out.println("Erreur de configuration du parseur DOM");
				System.out.println("lors de l'appel à fabrique.newDocumentBuilder();");
			}catch(SAXException se){
				System.out.println("Erreur lors du parsing du document");
				System.out.println("lors de l'appel à construteur.parse(xml)");
			}catch(IOException ioe){
				System.out.println("Erreur d'entrée/sortie");
				System.out.println("lors de l'appel à construteur.parse(xml)");
			}			
			
		}	
		Configuration.attributesNames = attributesNames;
		Configuration.attributesStartingValues = attributesStartingValues;
		
		//Loading Amenagements 
		
		System.out.println("Chargement des amenagements");
		
		Configuration.amenagements = amenagements; 
		
		//Loading Pheromones
		System.out.println("Chargement des pheromones");
		File[] filesPhero = new File(System.getProperty("user.dir")+"/bin/civilisation/ressources/itemPheromones").listFiles();
		ArrayList<ItemPheromone> phero = new ArrayList<ItemPheromone>();
		for (File file : filesPhero) {			
			try{
				// création d'une fabrique de documents
				DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
				
				// création d'un constructeur de documents
				DocumentBuilder constructeur = fabrique.newDocumentBuilder();
				
				// lecture du contenu d'un fichier XML avec DOM
				Document document = constructeur.parse(file);
				
				//traitement du document
				Element racine = document.getDocumentElement();
				NodeList nameList = racine.getElementsByTagName("Name");	
				//System.out.print(nameList.item(0).getChildNodes().item(0).getNodeValue());
				if(nameList.item(0) != null)
				{
					
					nom = nameList.item(0).getChildNodes().item(0).getNodeValue();
					System.out.println("Creation phéromone " + nom);
					phero.add(new ItemPheromone(nom));
				}
				
				
			}catch(ParserConfigurationException pce){
				System.out.println("Erreur de configuration du parseur DOM");
				System.out.println("lors de l'appel à fabrique.newDocumentBuilder();");
			}catch(SAXException se){
				System.out.println("Erreur lors du parsing du document");
				System.out.println("lors de l'appel à construteur.parse(xml)");
			}catch(IOException ioe){
				System.out.println("Erreur d'entrée/sortie");
				System.out.println("lors de l'appel à construteur.parse(xml)");
			}			
			
		}	
		Configuration.itemsPheromones = phero;
		
		System.out.println("Chargement des terrains");
		File[] filesTerrains = new File(System.getProperty("user.dir")+"/bin/civilisation/ressources/terrains").listFiles();
		ArrayList<Terrain> terrains = new ArrayList<Terrain>();
		for (File file : filesTerrains) {			
			try{
				// création d'une fabrique de documents
				DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
				
				// création d'un constructeur de documents
				DocumentBuilder constructeur = fabrique.newDocumentBuilder();
				
				// lecture du contenu d'un fichier XML avec DOM
				Document document = constructeur.parse(file);
				
				//traitement du document
				Element racine = document.getDocumentElement();
				NodeList Infranchissables = racine.getElementsByTagName("Infranchissable");
				NodeList Passabilites = racine.getElementsByTagName("Passabilite");
				NodeList nameList = racine.getElementsByTagName("Name");
				NodeList Nom = racine.getElementsByTagName("Nom");
				NodeList val1 = racine.getElementsByTagName("Val1");
				NodeList val2 = racine.getElementsByTagName("Val2");
				NodeList R = racine.getElementsByTagName("R");
				NodeList G = racine.getElementsByTagName("G");
				NodeList B = racine.getElementsByTagName("B");
				String name = nameList.item(0).getChildNodes().item(0).getNodeValue();;
				int passabilite = -1;
				if(Passabilites != null)
				{
					passabilite = Integer.parseInt(Passabilites.item(0).getChildNodes().item(0).getNodeValue());
				}
				
				boolean infranchissable = false;
				if(Infranchissables.getLength() > 0)
				{
					Boolean.parseBoolean(Infranchissables.item(0).getChildNodes().item(0).getNodeValue());
				}	
				String[] HSB = new String[3];
				
				HSB[0] = R.item(0).getChildNodes().item(0).getNodeValue();
				HSB[1] = G.item(0).getChildNodes().item(0).getNodeValue();
				HSB[2] = B.item(0).getChildNodes().item(0).getNodeValue();
				ArrayList<String> nomPhero = new ArrayList<String>();
				ArrayList<Double> value1 = new ArrayList<Double>();
				ArrayList<Double> value2 = new ArrayList<Double>();
				for(int i = 0; i < Nom.getLength(); i++)
				{
					
					nomPhero.add(Nom.item(i).getChildNodes().item(0).getNodeValue());
					value1.add(Double.parseDouble((val1.item(i).getChildNodes().item(0).getNodeValue())));
					value2.add(Double.parseDouble((val2.item(i).getChildNodes().item(0).getNodeValue())));
				}
				Terrain t = new Terrain(name);
				System.out.println("Chargement du terrain "+name);
		    	terrains.add(t);
		    	Color couleur;
		    	couleur = new Color(Float.parseFloat(HSB[0])/255, Float.parseFloat(HSB[1])/255, Float.parseFloat(HSB[2])/255);
		    	t.setCouleur(couleur);
			    for(int i = 0; i < nomPhero.size();i++)
			    {
			    	t.addPheromoneLiee(Configuration.getPheromoneByName(nomPhero.get(i)), value1.get(i), value2.get(i));
			    }
			    if (passabilite != -1){
		       		t.setPassabilite(passabilite);
		       	}
		       	else{
		       		t.setPassabilite(passabiliteParDefaut);
		       	}
		       	t.setInfranchissable(infranchissable);
		       			       	
		       	couleurs_terrains.put(t.getCouleur(), t);
			}catch(ParserConfigurationException pce){
				System.out.println("Erreur de configuration du parseur DOM");
				System.out.println("lors de l'appel à fabrique.newDocumentBuilder();");
			}catch(SAXException se){
				System.out.println("Erreur lors du parsing du document");
				System.out.println("lors de l'appel à construteur.parse(xml)");
			}catch(IOException ioe){
				System.out.println("Erreur d'entrée/sortie");
				System.out.println("lors de l'appel à construteur.parse(xml)");
			}
		}	
		
		
		if (terrains.size() == 0){
			/*Si il n'y a aucun terrain disponible, un terrain pas dŽfaut est crŽŽ*/
			Terrain tDefault = new Terrain("Default");
			terrains.add(tDefault);
			tDefault.setCouleur(Color.GREEN);
			tDefault.setPassabilite(passabiliteParDefaut);
       		tDefault.setInfranchissable(false);
	       	couleurs_terrains.put(tDefault.getCouleur(), tDefault);

		}
		
		Configuration.terrains = terrains;
		Configuration.couleurs_terrains = couleurs_terrains;	
		
		
		System.out.println("Chargement des cognitons");
		File[] files = new File(System.getProperty("user.dir")+"/bin/civilisation/ressources/cognitons").listFiles();
		for (File file : files) {
			try{
				// création d'une fabrique de documents
				DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
				
				// création d'un constructeur de documents
				DocumentBuilder constructeur = fabrique.newDocumentBuilder();
				
				// lecture du contenu d'un fichier XML avec DOM
				Document document = constructeur.parse(file);
				
				//traitement du document
				Element racine = document.getDocumentElement();
				NodeList Descriptions = racine.getElementsByTagName("Description");
				NodeList Types = racine.getElementsByTagName("Type");
				NodeList nameList = racine.getElementsByTagName("Name");
				NodeList Initiaux = racine.getElementsByTagName("Initial");
				NodeList ValHue = racine.getElementsByTagName("ValHue");
				NodeList ValTrig1 = racine.getElementsByTagName("ValTrig1");
				NodeList ValTrig2 = racine.getElementsByTagName("ValTrig2");
				NodeList ValTrig3 = racine.getElementsByTagName("ValTrig3");
				String name = nameList.item(0).getChildNodes().item(0).getNodeValue();
				String description = Descriptions.item(0).getChildNodes().item(0).getNodeValue();
				String type = Types.item(0).getChildNodes().item(0).getNodeValue();
				int initial = Integer.parseInt(Initiaux.item(0).getChildNodes().item(0).getNodeValue());
				
				Integer ListeValHue[] = new Integer[ValHue.getLength()];
				if(ValHue.getLength() > 0)
				{
					for(int i = 0; i < ValHue.getLength(); i++)
					{
						ListeValHue[i] = Integer.parseInt(ValHue.item(i).getChildNodes().item(0).getNodeValue());
					}
				}
				
				
				
		    	listeCognitons.put(name , new NCogniton());
		    	NCogniton cogni = listeCognitons.get(name);
		    	cogni.setNom(name);
		    	cogni.setDescription(description);
		    	cogni.setType(TypeDeCogniton.toType(type));
		    	if (initial == 1){
		    		cognitonsDeBase.add(cogni);
			    	cogni.setRecuAuDemarrage(true);
		    	}
		    	Integer ListeValHues[] = {0,0,0,0,0,0,0};
		    	cogni.setHues(ListeValHues);
		    	
		    	//Load triggering attributes

		       	for(int i = 0 ; i < ValTrig1.getLength(); i++) {
		       		Object[] trig = new Object[3];
		       		trig[0] = ValTrig1.item(i).getChildNodes().item(0).getNodeValue();
		       		trig[1] = ValTrig2.item(i).getChildNodes().item(0).getNodeValue();
		       		trig[2] = ValTrig3.item(i).getChildNodes().item(0).getNodeValue();
		       		System.out.println(trig[0] + " " + trig[1] + " " + trig[2]);
		       		if(Configuration.attributesTrigerringValues.get(ValTrig1.item(i).getChildNodes().item(0).getNodeValue()) == null) { Configuration.attributesTrigerringValues.put(ValTrig1.item(i).getChildNodes().item(0).getNodeValue() , new ArrayList<Object[]>()); }
		       		Configuration.attributesTrigerringValues.get(ValTrig1.item(i).getChildNodes().item(0).getNodeValue()).add(trig);
		       		
		       		//Now we add trigger to cognitons to keep the model easy to understand
		       		trig = new Object[3];
		       		trig[0] = ValTrig1.item(i).getChildNodes().item(0).getNodeValue();
		       		trig[1] = ValTrig2.item(i).getChildNodes().item(0).getNodeValue();
		       		trig[2] = ValTrig3.item(i).getChildNodes().item(0).getNodeValue();
		       		cogni.getTriggeringAttributes().add(trig);
		       	}
		    	tousLesCognitons.add(cogni);
				
			
			}catch(ParserConfigurationException pce){
				System.out.println("Erreur de configuration du parseur DOM");
				System.out.println("lors de l'appel à fabrique.newDocumentBuilder();");
			}catch(SAXException se){
				System.out.println("Erreur lors du parsing du document");
				System.out.println("lors de l'appel à construteur.parse(xml)");
			}catch(IOException ioe){
				System.out.println("Erreur d'entrée/sortie");
				System.out.println("lors de l'appel à construteur.parse(xml)");
			}
		}	

		System.out.println("Chargement des cloud cognitons");
		files = new File(System.getProperty("user.dir")+"/bin/civilisation/ressources/cloudCognitons").listFiles();
		for (File file : files) {
			try{
				// création d'une fabrique de documents
				DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
				
				// création d'un constructeur de documents
				DocumentBuilder constructeur = fabrique.newDocumentBuilder();
				
				// lecture du contenu d'un fichier XML avec DOM
				Document document = constructeur.parse(file);
				
				//traitement du document
				Element racine = document.getDocumentElement();
				NodeList Descriptions = racine.getElementsByTagName("Description");
				NodeList Types = racine.getElementsByTagName("Type");
				NodeList nameList = racine.getElementsByTagName("Name");
				NodeList Initiaux = racine.getElementsByTagName("Initial");
				NodeList ValHue = racine.getElementsByTagName("ValHue");
				String name = nameList.item(0).getChildNodes().item(0).getNodeValue();
				String description = Descriptions.item(0).getChildNodes().item(0).getNodeValue();
				String type = Types.item(0).getChildNodes().item(0).getNodeValue();
				int initial = Integer.parseInt(Initiaux.item(0).getChildNodes().item(0).getNodeValue());
				
				Integer ListeValHue[] = new Integer[ValHue.getLength()];
				for(int i = 0; i < ValHue.getLength(); i++)
				{
					ListeValHue[i] = Integer.parseInt(ValHue.item(i).getChildNodes().item(0).getNodeValue());
				}
				
				
				listCloudCognitons.put(name , new Culturon());
				Culturon cogni = listCloudCognitons.get(name);
		    	cogni.setNom(name);
		    	cogni.setDescription(getChamp("Description" , file));
		    	cogni.setType(TypeDeCogniton.toType( getChamp("Type" , file)));
		    	if (initial == 1){
		    		cognitonsDeBase.add(cogni);
			    	cogni.setRecuAuDemarrage(true);
		    	}
		    	cogni.setHues(ListeValHue);
		    	allCloudCogniton.add(cogni);
				
			
			}catch(ParserConfigurationException pce){
				System.out.println("Erreur de configuration du parseur DOM");
				System.out.println("lors de l'appel à fabrique.newDocumentBuilder();");
			}catch(SAXException se){
				System.out.println("Erreur lors du parsing du document");
				System.out.println("lors de l'appel à construteur.parse(xml)");
			}catch(IOException ioe){
				System.out.println("Erreur d'entrée/sortie");
				System.out.println("lors de l'appel à construteur.parse(xml)");
			}
		}
		
		
		System.out.println("Chargement des objets d'inventaire");
		Configuration.objets = new ArrayList<Objet>();
		files = new File(System.getProperty("user.dir")+"/bin/civilisation/ressources/objets").listFiles();
		for (File file : files) 
		{
			try{
				// création d'une fabrique de documents
				DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
				
				// création d'un constructeur de documents
				DocumentBuilder constructeur = fabrique.newDocumentBuilder();
				
				// lecture du contenu d'un fichier XML avec DOM
				Document document = constructeur.parse(file);
				//traitement du document
				Element racine = document.getDocumentElement();
				
				NodeList poid = racine.getElementsByTagName("Weight");
				NodeList nameList = racine.getElementsByTagName("Name");
				NodeList Nom = racine.getElementsByTagName("Nom");
				NodeList Target = racine.getElementsByTagName("Target");
				NodeList Varget = racine.getElementsByTagName("Varget");
				NodeList Value = racine.getElementsByTagName("Value");
				NodeList Permanent = racine.getElementsByTagName("Permanent");
				NodeList Type = racine.getElementsByTagName("Type");
				String name = nameList.item(0).getChildNodes().item(0).getNodeValue();
				int poids = Integer.parseInt(poid.item(0).getChildNodes().item(0).getNodeValue());
				ArrayList<Effect> listeEffets = new ArrayList<Effect>();
				for(int i = 0;i < Nom.getLength();i++)
				{
					String nameE = Nom.item(i).getChildNodes().item(0).getNodeValue();
					String target = Target.item(i).getChildNodes().item(0).getNodeValue();
					String varget = Varget.item(i).getChildNodes().item(0).getNodeValue();
					int value = Integer.parseInt(Value.item(i).getChildNodes().item(0).getNodeValue());
					boolean permanent = Boolean.parseBoolean(Permanent.item(i).getChildNodes().item(0).getNodeValue());
					String type = Type.item(i).getChildNodes().item(0).getNodeValue();
					Effect effet = new Effect(nameE,target,varget,value,permanent,type);
					listeEffets.add(effet);
				}
				Configuration.objets.add(new Objet(poids,name,listeEffets));
				
			
			}catch(ParserConfigurationException pce){
				System.out.println("Erreur de configuration du parseur DOM");
				System.out.println("lors de l'appel à fabrique.newDocumentBuilder();");
			}catch(SAXException se){
				System.out.println("Erreur lors du parsing du document");
				System.out.println("lors de l'appel à construteur.parse(xml)");
			}catch(IOException ioe){
				System.out.println("Erreur d'entrée/sortie");
				System.out.println("lors de l'appel à construteur.parse(xml)");
			}
		}	
		
		/**
		 * TODO
		 */
		System.out.println("Creation des plans");
		File[] filesPlans = new File(System.getProperty("user.dir")+"/bin/civilisation/ressources/plans").listFiles();
		for (File file : filesPlans) {
			if (!file.isHidden() && file.getName().endsWith(".xml")){
			System.out.println("Creation du plan : " + file.getName());
		    if (file.isFile()) {
		      
		       	try{
					// création d'une fabrique de documents
					DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
					
					// création d'un constructeur de documents
					DocumentBuilder constructeur = fabrique.newDocumentBuilder();
					
					// lecture du contenu d'un fichier XML avec DOM
					Document document = constructeur.parse(file);
					//traitement du document
					Element racine = document.getDocumentElement();
					
					NodeList Nom = racine.getElementsByTagName("Nom");
					NodeList Birth = racine.getElementsByTagName("Birth");
					NodeList Self = racine.getElementsByTagName("Self");
					NodeList Action = racine.getElementsByTagName("Action");
					NodeList NomAction = racine.getElementsByTagName("NomAction");
					NodeList Variables = racine.getElementsByTagName("Variables");
					NodeList NomVariable = racine.getElementsByTagName("NomVariable");
					NodeList TypeVariable = racine.getElementsByTagName("TypeVariable");
					NodeList ValeurVariable = racine.getElementsByTagName("ValeurVariable");
					
					String name = Nom.item(0).getChildNodes().item(0).getNodeValue();
					Boolean births =Boolean.parseBoolean(Birth.item(0).getChildNodes().item(0).getNodeValue());
					Boolean selfs =Boolean.parseBoolean(Self.item(0).getChildNodes().item(0).getNodeValue());
					
					listePlans.put(name , new NPlan());
					listePlans.get(name).setNom(name);
					File fileA = new File(System.getProperty("user.dir")+"/bin/civilisation/ressources/plans"+"/"+name+".metaciv");
					File cible = new File(System.getProperty("user.dir")+"/bin/civilisation/ressources/plans");
					cible.mkdir();
					File[] fichiers = cible.listFiles();
					if(fichiers != null)
					{
						for(int i = 0; i < fichiers.length; i++)
						{
							if(fichiers[i].getName().equals(name+".metaciv"))
							{
								fichiers[i].delete();
								
							}
							
						}
						
					}
					FileWriter fwe;
					fileA.createNewFile();
					fwe = new FileWriter(fileA, true);
					BufferedWriter output = new BufferedWriter(fwe);
					output.write("Nom : " + name+"\n" );
					for(int i = 0; i < Action.getLength(); ++i)
					{

						output.write("Action : " + NomAction.item(i).getChildNodes().item(0).getNodeValue());
						
						for(int j = 0; j < Variables.getLength();++j)
						{
							
							if(NomVariable.getLength() > j + 1)
							{
								output.write(",");
								if(NomVariable.item(i).getChildNodes().item(0).getNodeValue() != "")
								{
									output.write(NomVariable.item(i).getChildNodes().item(0).getNodeValue() + "(");
									output.write(TypeVariable.item(i).getChildNodes().item(0).getNodeValue() + " ");
									output.write(ValeurVariable.item(i).getChildNodes().item(0).getNodeValue() + ")");
								}
								else
								{
									output.write(ValeurVariable.item(i).getChildNodes().item(0).getNodeValue());
								}
							}
						}
						if(Action.item(i).getNodeType() == Node.ELEMENT_NODE)
						{
							this.RecursLectureAction(output,(Element) Action.item(i), 0);
						}
						output.write("\n");
					}
					output.flush();
					output.close();
					setupPlans(listePlans.get(name), fileA, 0, 0, null);
					listePlans.get(name).setIsBirthPlan(births);
			       	listePlans.get(name).setIsSelfPlan(selfs);
			       	
			       	
			       	if (listePlans.get(name).getIsSelfPlan()) {
			    		Configuration.autoPlan = listePlans.get(name);
			    	}
			    	else if (listePlans.get(name).getIsBirthPlan()) {
			    		Configuration.birthPlan = listePlans.get(name);
			    	}
			       	System.out.println("liste des plans "+listePlans);
				    tousLesPlans.add(listePlans.get(name));
			    	

			       	System.out.println(" PLAN : " + listePlans.get(name).getActions());
				}catch(ParserConfigurationException pce){
					System.out.println("Erreur de configuration du parseur DOM");
					System.out.println("lors de l'appel à fabrique.newDocumentBuilder();");
				}catch(SAXException se){
					System.out.println("Erreur lors du parsing du document");
					System.out.println("lors de l'appel à construteur.parse(xml)");
				}catch(IOException ioe){
					System.out.println("Erreur d'entrée/sortie");
					System.out.println("lors de l'appel à construteur.parse(xml)");
				}
		    	
		       	
		       	
		       	
		    	

		       	
		    }
			}
		}	
		
		System.out.println("Creation des liens");
		files = new File(System.getProperty("user.dir")+"/bin/civilisation/ressources/cognitons").listFiles();
		for (File file : files) {
			if (!file.isHidden() && file.getName().endsWith(".xml")){
			System.out.println("Creation des liens de : " + file.getName());
		    if (file.isFile()) {
		    	
		    	try{
					// création d'une fabrique de documents
					DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
					
					// création d'un constructeur de documents
					DocumentBuilder constructeur = fabrique.newDocumentBuilder();
					
					// lecture du contenu d'un fichier XML avec DOM
					Document document = constructeur.parse(file);
					
					//traitement du document
					Element racine = document.getDocumentElement();
					NodeList Descriptions = racine.getElementsByTagName("Description");
					NodeList Types = racine.getElementsByTagName("Type");
					NodeList nameList = racine.getElementsByTagName("Name");
					NodeList ActionInflus = racine.getElementsByTagName("ActionInflus");
					NodeList ValInflus = racine.getElementsByTagName("ValInflus");
					NodeList ActionPermet = racine.getElementsByTagName("ActionPermet");
					NodeList Val1Chaine = racine.getElementsByTagName("Val1");
					NodeList Val2Chaine = racine.getElementsByTagName("Val2");
					
					String name = nameList.item(0).getChildNodes().item(0).getNodeValue();		
					
					ArrayList<String> listeChaine1 = new ArrayList<String>();
					ArrayList<String> listeChaine2 = new ArrayList<String>();
					for(int i = 0; i < Val1Chaine.getLength(); i++)
					{
						listeChaine1.add(Val1Chaine.item(i).getChildNodes().item(0).getNodeValue());
						listeChaine2.add(Val2Chaine.item(i).getChildNodes().item(0).getNodeValue());
					}
					if(listeChaine1.size() != 0)
					{
						ArrayList<LienCogniton> liens = new ArrayList<LienCogniton>();		      
				       	for (int i = 0 ; i < listeChaine1.size(); i++){
				       		liens.add(new LienCogniton(listeCognitons.get(listeChaine1.get(i)), Integer.parseInt(listeChaine2.get(i))));
				       	}
			       		listeCognitons.get(name).setLiens(liens);
					}
					else
					{
						listeCognitons.get(name).setLiens(null);
					}
		       		ArrayList<String> listeActionInflus = new ArrayList<String>();
					ArrayList<String> listeValInflus = new ArrayList<String>();
					for(int i = 0; i < ActionInflus.getLength(); i++)
					{
						listeActionInflus.add(ActionInflus.item(i).getChildNodes().item(0).getNodeValue());
						listeValInflus.add(ValInflus.item(i).getChildNodes().item(0).getNodeValue());
					}
				 	ArrayList<LienPlan> liensP = new ArrayList<LienPlan>();		      
			       	for (int i = 0 ; i < listeActionInflus.size(); i++){
			       		liensP.add(new LienPlan(listePlans.get(listeActionInflus.get(i)), Integer.parseInt(listeValInflus.get(i))));
			       	}
		       		listeCognitons.get(name).setLiensPlans(liensP);
					
					ArrayList<String> listeActionPermet = new ArrayList<String>();
					for(int i = 0; i < ActionPermet.getLength(); i++)
					{
						listeActionPermet.add(ActionPermet.item(i).getChildNodes().item(0).getNodeValue());
					}
					//System.out.println("arrayListe : "+ listeActionPermet.get(0));
					ArrayList<NPlan> plans = new ArrayList<NPlan>();		      
			       	for (int i = 0 ; i < listeActionPermet.size(); i++){
				       	//System.out.println("Le nom qu'on trouve : " + liste.get(i)[0]);
				       	//System.out.println("Hach assoccie : " + listePlans.get(liste.get(i)[0]));
			       		plans.add(listePlans.get(listeActionPermet.get(i)));
			       	}
			       	//System.out.println("plans autorises : "+ nom + "  : "+plans.toString());
			       	System.out.println("array : "+ plans);

		       		listeCognitons.get(name).setPlansAutorises(plans);
		       		
				}catch(ParserConfigurationException pce){
					System.out.println("Erreur de configuration du parseur DOM");
					System.out.println("lors de l'appel à fabrique.newDocumentBuilder();");
				}catch(SAXException se){
					System.out.println("Erreur lors du parsing du document");
					System.out.println("lors de l'appel à construteur.parse(xml)");
				}catch(IOException ioe){
					System.out.println("Erreur d'entrée/sortie");
					System.out.println("lors de l'appel à construteur.parse(xml)");
				}
		    	
		    }
		}	
		}
		
		/*On transmet les informations ˆ la classe de configuration*/
		Configuration.cognitonsDeBase = cognitonsDeBase;
		Configuration.cognitons = tousLesCognitons;
		Configuration.cloudCognitons = allCloudCogniton;
		Configuration.plans = tousLesPlans;

		//System.out.println("Verification");	
		
		//printAllCognitons();
		
		/*Lecture des civilisations*/
		System.out.println("Creation des civilisations");
		Configuration.civilisations = new ArrayList<Civilisation>();
		files = new File(System.getProperty("user.dir")+"/bin/civilisation/ressources/civilisations").listFiles();
		if(files != null)
		{
			
		for (File file : files) {			
			try{
				// création d'une fabrique de documents
				DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
				
				// création d'un constructeur de documents
				DocumentBuilder constructeur = fabrique.newDocumentBuilder();
				
				// lecture du contenu d'un fichier XML avec DOM
				Document document = constructeur.parse(file);
				
				//traitement du document
				Element racine = document.getDocumentElement();
				NodeList nameList = racine.getElementsByTagName("Nom");		
				NodeList R = racine.getElementsByTagName("R");
				NodeList G = racine.getElementsByTagName("G");
				NodeList B = racine.getElementsByTagName("B");
				NodeList Agents = racine.getElementsByTagName("Agent");
				String[] HSB = new String[3];
				HSB[0] = R.item(0).getChildNodes().item(0).getNodeValue();
				HSB[1] = G.item(0).getChildNodes().item(0).getNodeValue();
				HSB[2] = B.item(0).getChildNodes().item(0).getNodeValue();
				
				nom = nameList.item(0).getChildNodes().item(0).getNodeValue();
				int agents = Integer.parseInt(Agents.item(0).getChildNodes().item(0).getNodeValue());
				Civilisation civ = new Civilisation();
		    	civ.setNom(nom);
		    	civ.setAgentsInitiaux(agents);
				civ.setCouleur(Color.getHSBColor((float)Double.parseDouble(HSB[0]), (float)Double.parseDouble(HSB[1]), (float)Double.parseDouble(HSB[2])));
				Configuration.civilisations.add(civ);
			}catch(ParserConfigurationException pce){
				System.out.println("Erreur de configuration du parseur DOM");
				System.out.println("lors de l'appel à fabrique.newDocumentBuilder();");
			}catch(SAXException se){
				System.out.println("Erreur lors du parsing du document");
				System.out.println("lors de l'appel à construteur.parse(xml)");
			}catch(IOException ioe){
				System.out.println("Erreur d'entrée/sortie");
				System.out.println("lors de l'appel à construteur.parse(xml)");
			}
		}
		}	
		
		/*Lecture des param�tres*/
		System.out.println("Lecture des param�tres de la simulation");
		File params = new File(System.getProperty("user.dir")+"/bin/civilisation/ressources/parametres.xml"/*+Configuration.getExtension()*/);
		String s = null;
		if (params.exists()){
			try{
				// création d'une fabrique de documents
				DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
				
				// création d'un constructeur de documents
				DocumentBuilder constructeur = fabrique.newDocumentBuilder();
				
				// lecture du contenu d'un fichier XML avec DOM
				Document document = constructeur.parse(params);
				
				//traitement du document
				Element racine = document.getDocumentElement();
				NodeList nameList = racine.getElementsByTagName("Carte");		
				
				s = nameList.item(0).getChildNodes().item(0).getNodeValue();
			
			}catch(ParserConfigurationException pce){
				System.out.println("Erreur de configuration du parseur DOM");
				System.out.println("lors de l'appel à fabrique.newDocumentBuilder();");
			}catch(SAXException se){
				System.out.println("Erreur lors du parsing du document");
				System.out.println("lors de l'appel à construteur.parse(xml)");
			}catch(IOException ioe){
				System.out.println("Erreur d'entrée/sortie");
				System.out.println("lors de l'appel à construteur.parse(xml)");
			}
	       	if (s != null){
				System.out.println("Pas aucune");
	    		File carte = new File(System.getProperty("user.dir")+"/bin/civilisation/ressources/environnements/"+s);
	    		if (carte.isFile()){
	    			System.out.println("Chargement de la carte : "+s);
	    			Configuration.environnementACharger = s.split("\\.")[0];
	    		}
	       	}
		}

		
       	
       	/*Preparation d'un jeu d'actions, pour pouvoir facilement les manipuler dans le reste du programme*/
       	
		System.out.println("Mise en place des actions");
       	Class action;
       	Configuration.actions = new ArrayList<Action>();
		File[] sourcesActions = new File(System.getProperty("user.dir")+"/bin/civilisation/individu/plan/action").listFiles();
		for (File file : sourcesActions) {
		    if (file.isFile() && file.getName().endsWith(".class") && file.getName().charAt(1) == '_') {
		    	try {
					action = Class.forName("civilisation.individu.plan.action."+file.getName().split("\\.")[0]);
					System.out.println("Chargement de l'action : " + action.getName());
					Configuration.actions.add((Action) action.newInstance());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
		    }
		}
		
		
		System.out.println("Load groups");
		Configuration.groups = new ArrayList<GroupModel>();
		files = new File(System.getProperty("user.dir")+"/bin/civilisation/ressources/groups").listFiles();
		for (File file : files) {
			if (!file.isHidden() && file.getName().endsWith(Configuration.getExtension())){
			System.out.println("\tLoad group : " + file.getName());
		    if (file.isFile()) {
		    	try{
					// création d'une fabrique de documents
					DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
					
					// création d'un constructeur de documents
					DocumentBuilder constructeur = fabrique.newDocumentBuilder();
					
					// lecture du contenu d'un fichier XML avec DOM
					Document document = constructeur.parse(file);
					//traitement du document
					Element racine = document.getDocumentElement();
					
					NodeList Nom = racine.getElementsByTagName("Name");
					NodeList Chef = racine.getElementsByTagName("Chef");
					NodeList Self = racine.getElementsByTagName("NomCulturon");
					nom = Nom.item(0).getChildNodes().item(0).getNodeValue();
		    	GroupModel g = new GroupModel(nom);
		    	
		    	for (int i = 0 ; i < Chef.getLength() ; i++) {
		    		g.addCulturonToRole(Chef.item(i).getChildNodes().item(0).getNodeValue(), new CCogniton(Configuration.getCognitonByName(Self.item(i).getChildNodes().item(0).getNodeValue())));
		    	}

		    	Configuration.groups.add(g);
		    	} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		}
		}
       
		
       	
	}
	
	/*Retourne la valeur du premier champ passŽ en param�tre rencontrŽ*/
	static public String getChampM(String champ ,  File f){
		
		 Scanner scanner;
		try {
			scanner = new Scanner(new FileReader(f));
			 String str = null;
			 while (scanner.hasNextLine()) {
			     str = scanner.nextLine();
			     if(str.split(" : ")[0].equals(champ)){
			    	 String[] s = str.split(" : ")[1].split("\n");
			    	 String ret = s[0];
			    	 return ret;
			     }
			 }
			 
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
		return null;
	}
	
	static public String getChamp(String champ ,  File f){
			File file = f;
			if(file != null)
			{
						
				try{
					// création d'une fabrique de documents
					DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
					
					// création d'un constructeur de documents
					DocumentBuilder constructeur = fabrique.newDocumentBuilder();
					
					// lecture du contenu d'un fichier XML avec DOM
					Document document = constructeur.parse(file);
					
					//traitement du document
					Element racine = document.getDocumentElement();
					NodeList nameList = racine.getElementsByTagName(champ);		
					
					return nameList.item(0).getChildNodes().item(0).getNodeValue();
				
				}catch(ParserConfigurationException pce){
					System.out.println("Erreur de configuration du parseur DOM");
					System.out.println("lors de l'appel à fabrique.newDocumentBuilder();");
				}catch(SAXException se){
					System.out.println("Erreur lors du parsing du document");
					System.out.println("lors de l'appel à construteur.parse(xml)");
				}catch(IOException ioe){
					System.out.println("Erreur d'entrée/sortie");
					System.out.println("lors de l'appel à construteur.parse(xml)");
				}
			}
		return null;
	}
	
	
	public static ArrayList<String[]> getListeChamp(String champ ,  File f){
		
		 Scanner scanner;
		 ArrayList<String[]> liste = new ArrayList<String[]>();
		 
		try {
			scanner = new Scanner(new FileReader(f));
			 String str = null;
			 while (scanner.hasNextLine()) {
			     str = scanner.nextLine();			     
			     if(str.split(" : ")[0].equals(champ)){
			    	 liste.add(str.split(" : ")[1].split(","));
			     }
			 }
			 
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
		return liste;
	}

	//setupPlans(listePlans.get(name), file, 0, 0, null);
	private void setupPlans(NPlan p , File f, int iteration, int ligne , Action a){
		
		
		 int ligneActuelle = 0;
	     Action nouvelleAction = null;
	     Action ancienneAction = null;
		 Scanner scanner;
		 String champ = "";
		 for (int i = 0; i < iteration; i++){
			 champ += "\t";
		 }
		 champ += "Action";
		 
		 ArrayList<String[]> liste = new ArrayList<String[]>();
		 
		try {
			scanner = new Scanner(new FileReader(f));
			 String str = null;
			 boolean recursionLancee = false;

			 while (scanner.hasNextLine()) {
				 int nTab = -1;
			     str = scanner.nextLine();
			     if (str.split("Action").length > 1){
				     nTab = str.split("Action")[0].length();
			     }

			     ligneActuelle++;
			     
			     if (ligneActuelle+1 > ligne){
				     if(str.split(" : ")[0].equals(champ) && nTab == iteration){
					     ancienneAction = nouvelleAction;
					     
					     nouvelleAction = Action.actionFactory(str.split(" : ")[1].split(","));
					     if (ancienneAction != null){
					    	 System.out.println("Effectue "+ancienneAction.getSimpleName()+ " puis "+nouvelleAction.getSimpleName());
					    	 ancienneAction.setNextAction(nouvelleAction);
					     }
					     if (a == null){
					    	 p.addAction(nouvelleAction);
					    	 System.out.println("Ajoute "+nouvelleAction.getSimpleName()+ " au plan "+p.getNom());
					     }
					     else {
					    	 a.addSousAction(nouvelleAction);
					    	//System.out.println("Action a : "+ a.toFormatedString()+ "Sous action :" + a.getListeActions().get(0).toFormatedString());
					     }
				    	 recursionLancee = false;
				     }
				     else if(nTab > iteration && !recursionLancee){
				    	 recursionLancee = true;
				    	 setupPlans(p,f,iteration+1,ligneActuelle, nouvelleAction);
				     }
				     else if (nTab < iteration && nTab != -1){
				    	 break;
				     }
			     }

			     
			 }
			 
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	/*    for (int i = 0; i < actions.size(); i++){
	    	listePlans.get(p.getNom()).addAction(actions.get(i));
	    	if (i > 0){
	    		listePlans.get(p.getNom()).getActions().get(i-1).setNextAction(listePlans.get(p.getNom()).getActions().get(i));
	    	}
	    }*/
	}
	
	/*Pour tester*/
	private void printAllCognitons(){
		System.out.println(listeCognitons.values().toString());
		

	}
		
	private void RecursLectureAction(BufferedWriter output, Element element, int niveau)
	{
		NodeList action = element.getElementsByTagName("SousAction"+niveau);
		Element Eaction = (Element) action.item(0);

		if(Eaction != null)
		{
			NodeList NomAction = Eaction.getElementsByTagName("SousActionName"+niveau);
			NodeList Variables = Eaction.getElementsByTagName("SousActionVariables"+niveau);
			NodeList NomVariable = Eaction.getElementsByTagName("NomVariable");
			NodeList TypeVariable = Eaction.getElementsByTagName("TypeVariable");
			NodeList ValeurVariable = Eaction.getElementsByTagName("ValeurVariable");

			try
			{
				for(int i = 0; i < action.getLength(); ++i)
				{
					output.write("\n");
					for(int j = 0; j <= niveau;j++)
					{
						output.write("\t");
					}
					output.write("Action : " + NomAction.item(i).getChildNodes().item(0).getNodeValue());
					for(int j = 0; j < Variables.getLength();++j)
					{
						if(NomVariable.getLength() > j )
						{
							output.write(",");
							if(NomVariable.item(i).getChildNodes().item(0).getNodeValue() != "")
							{
								output.write(NomVariable.item(i).getChildNodes().item(0).getNodeValue() + "(");
								output.write(TypeVariable.item(i).getChildNodes().item(0).getNodeValue() + " ");
								output.write(ValeurVariable.item(i).getChildNodes().item(0).getNodeValue() + ")");
							}
							else
							{
								output.write(ValeurVariable.item(i).getChildNodes().item(0).getNodeValue());
							}
						}
						
					}
					if(action.item(i).getNodeType() == Node.ELEMENT_NODE)
					{

						this.RecursLectureAction(output,(Element) action.item(i), niveau + 1);
					}
					
				}
			}catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		
	}

}
