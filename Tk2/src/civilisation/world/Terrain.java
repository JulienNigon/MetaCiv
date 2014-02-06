package civilisation.world;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import civilisation.Configuration;
import civilisation.ItemPheromone;

public class Terrain {

	/** 
	 * Conserve les informations relatives aux diffŽrents types de territoires qui compose l'environnement des agents.
	 * @version MetaCiv 1.0
	*/
	
	String nom;
	Color couleur;
	int passabilite;
	ArrayList<ItemPheromone> pheromones;
	ArrayList<Double> pheroInitiales;
	ArrayList<Double> pheroCroissance;
	static final int passabiliteParDefaut = 35;
	Boolean infranchissable;
	
	public Terrain(String nom){
		this.nom = nom;
		pheromones = new ArrayList<ItemPheromone>();
		pheroInitiales = new ArrayList<Double>();
		pheroCroissance = new ArrayList<Double>();
		couleur = Color.BLACK;
		passabilite = passabiliteParDefaut;
		infranchissable = false;
		//Configuration.couleurs_terrains.put(couleur, this);
	}

	public int getPassabilite() {
		return passabilite;
	}

	public void setPassabilite(int passabilite) {
		this.passabilite = passabilite;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Color getCouleur() {
		return couleur;
	}

	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}
	
	public Boolean getInfranchissable() {
		return infranchissable;
	}

	public void setInfranchissable(Boolean infranchissable) {
		this.infranchissable = infranchissable;
	}

	public void addPheromoneLiee(ItemPheromone phero, Double init, Double croissance){
		pheromones.add(phero);
		pheroInitiales.add(init);
		pheroCroissance.add(croissance);
	}
	
	public void clearPheromones() {
		pheromones.clear();
		pheroInitiales.clear();
		pheroCroissance.clear();
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
			fw = new FileWriter(cible+"/"+getNom()+".xml", true);
			BufferedWriter output = new BufferedWriter(fw);
			output.write("<Terrain>\n");
					output.write("\t<Name>"+getNom()+"</Name>\n");
					output.write("\t<Passabilite>"+passabilite+"</Passabilite>\n");
					output.write("\t<Infranchissable>"+infranchissable+"</Infranchissable>\n");
					output.write("\t<Pheromones>\n");
				for(int i = 0; i < pheromones.size(); i++)
				{
						output.write("\t\t<Phero>\n");
							if(pheromones.get(i) != null)
							{
								output.write("\t\t\t<Nom>"+pheromones.get(i).getNom()+"</Nom>\n");
								output.write("\t\t\t<Val1>"+pheroInitiales.get(i)+"</Val1>\n");
								output.write("\t\t\t<Val2>"+pheroCroissance.get(i)+"</Val2>\n");
							}
							
						output.write("\t\t</Phero>\n");
				}
					output.write("\t</Pheromones>\n");
					output.write("\t<Couleur>\n");
						output.write("\t\t<R>"+couleur.getRed()+"</R>\n");
						output.write("\t\t<G>"+couleur.getGreen()+"</G>\n");
						output.write("\t\t<B>"+couleur.getBlue()+"</B>\n");
					output.write("\t</Couleur>\n");
			output.write("</Terrain>");
			output.flush();
			output.close();
			System.out.println("fichier créé");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public int getPheromoneIndexByName(String s){
		for (int i = 0 ; i < pheromones.size(); i++){
			if (pheromones.get(i).getNom().equals(s)){
				return i;
			}
		}
		return -1;
	}

	public ArrayList<ItemPheromone> getPheromones() {
		return pheromones;
	}

	public void setPheromones(ArrayList<ItemPheromone> pheromones) {
		this.pheromones = pheromones;
	}

	public ArrayList<Double> getPheroInitiales() {
		return pheroInitiales;
	}

	public void setPheroInitiales(ArrayList<Double> pheroInitiales) {
		this.pheroInitiales = pheroInitiales;
	}

	public ArrayList<Double> getPheroCroissance() {
		return pheroCroissance;
	}

	public void setPheroCroissance(ArrayList<Double> pheroCroissance) {
		this.pheroCroissance = pheroCroissance;
	}

	
	
	
}
