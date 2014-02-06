package civilisation.inventaire;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import civilisation.Configuration;
import civilisation.effects.Effect;

public class Objet {

	protected int weight;
	protected String name;
	protected ArrayList<Effect> effets = new ArrayList<Effect>();
	protected ImageIcon icone;
	protected String nomIcone;
	protected String description;
	
	public Objet(int weight,String name)
	{
		this.weight = weight;
		this.name = name;
		icone = new ImageIcon(this.getClass().getResource("../inspecteur/icones/arrow-000-medium.png"));
	}
	
	public Objet(int weight,String name,ArrayList<Effect> effets)
	{
		this.weight = weight;
		this.name = name;
		this.effets = effets;
		icone = new ImageIcon(this.getClass().getResource("../inspecteur/icones/arrow-000-medium.png"));
	}
	public Objet()
	{
		
	}
	
	public void enregistrer(File cible) {
		File file = new File(cible+"/"+getName()+".xml");
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
				fw = new FileWriter(cible+"/"+getName()+".xml", true);
				BufferedWriter output = new BufferedWriter(fw);
				output.write("<Object>\n");
						output.write("\t<Name>"+getName()+"</Name>\n");
						output.write("\t<Weight>"+weight+"</Weight>\n");
					for(int i=0;i< effets.size();i++)
					{
						Effect var = effets.get(i);
						output.write("\t<Effect>\n");
							output.write("\t\t<Nom>"+var.getName()+"</Nom>\n");
							output.write("\t\t<Target>"+var.getTarget()+"</Target>\n");
							output.write("\t\t<Varget>"+var.getVarget()+"</Varget>\n");
							output.write("\t\t<Value>"+var.getValue()+"</Value>\n");
							output.write("\t\t<Permanent>"+var.isPermanent()+"</Permanent>\n");
							output.write("\t\t<Type>"+var.getType()+"</Type>\n");
						output.write("\t<Effect>\n");
					}
				output.write("</Object>");
				output.flush();
				output.close();
				System.out.println("fichier créé");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
	}
	
	public boolean haveEffect()
	{
		return this.effets.size() > 0;
	}
	
	public ArrayList<Effect> getEffect()
	{
		return this.effets;
	}
	
	public int getWeight()
	{
		return this.weight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public ImageIcon getIcone() {
		return icone;
	}

	public void setIcone(ImageIcon icone) {
		this.icone = icone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setIconeFromString(String s){
		icone = new ImageIcon(this.getClass().getResource("../inspecteur/icones/"+s));
		nomIcone = s;
	}
}
