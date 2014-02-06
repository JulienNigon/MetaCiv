package civilisation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import civilisation.world.World;

public class ItemPheromone {

	String nom;
	
	public  ItemPheromone(String nom){
		World.getInstance().addFlavor(nom);
		this.nom = nom;

	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
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
			output.write("<Pheromone>\n");
					output.write("\t<Name>"+getNom()+"</Name>\n");
			output.write("</Pheromone>");
			output.flush();
			output.close();
			System.out.println("fichier créé");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		 
	}
	
	
}
