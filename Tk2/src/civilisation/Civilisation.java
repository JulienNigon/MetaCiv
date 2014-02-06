package civilisation;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import civilisation.world.World;


/** 
 * Classe représentant une civilisation (une "équipe" d'agents)
 * @author DTEAM
 * @version 1.0 - 2/2013
*/

public class Civilisation {

	String nom;
	Color couleur;
	static int nombreCiv = 0;
	int indexCiv;
	int agentsInitiaux;
	static ArrayList<Civilisation> listeCiv = new ArrayList<Civilisation>();
	
	public Civilisation ()
	{
		couleur = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
		indexCiv = nombreCiv; //Les civilisations sont indexées à partir de 0
		nombreCiv ++;
		listeCiv.add(this);
		System.out.println(World.getInstance().gridVariables.size());
		System.out.println("OK : " +  World.getInstance().gridVariables.containsKey("civ"+indexCiv) + " " + "civ"+indexCiv);
	}


	
	public void enregistrer(File cible) {		
		File file = new File(cible+"/"+getNom()+".xml");
		File[] fichiers = cible.listFiles();
		if(fichiers != null)
		{
			for(int i = 0; i < fichiers.length; i++)
			{
				if(fichiers[i].getName() == file.getName())
				{
					fichiers[i].delete();
					
				}
				
			}
			
		}
		
		FileWriter fw;
		try {
			file.createNewFile();
			fw = new FileWriter(cible+"/"+getNom()+".xml", true);
			BufferedWriter output = new BufferedWriter(fw);
			output.write("<Civilisation>\n");
					output.write("\t<Nom>"+getNom()+"</Nom>\n");
					output.write("\t<Agent>"+agentsInitiaux+"</Agent>\n");
					output.write("\t<Couleur>\n");
						output.write("\t\t<R>"+couleur.getRed()+"</R>\n");
						output.write("\t\t<G>"+couleur.getGreen()+"</G>\n");
						output.write("\t\t<B>"+couleur.getBlue()+"</B>\n");
					output.write("\t</Couleur>\n");
			output.write("</Civilisation>");
			output.flush();
			output.close();
			System.out.println("fichier crÈÈ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//-------------GETTERS-------------
	
	public Color getCouleur() {
		return couleur;
	}
	
	public int getIndexCiv()
	{
		return indexCiv;
	}
	
	public static int getNombreCiv()
	{
		return nombreCiv;
	}
	
	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public static ArrayList<Civilisation> getListeCiv() {
		return listeCiv;
	}


	public static void setListeCiv(ArrayList<Civilisation> listeCiv) {
		Civilisation.listeCiv = listeCiv;
	}


	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}


	public int getAgentsInitiaux() {
		return agentsInitiaux;
	}


	public void setAgentsInitiaux(int agentsInitiaux) {
		this.agentsInitiaux = agentsInitiaux;
	}

	public void postWorldSetup() {
		World.getInstance().addFlavor("civ"+indexCiv);		
	}
	
}
