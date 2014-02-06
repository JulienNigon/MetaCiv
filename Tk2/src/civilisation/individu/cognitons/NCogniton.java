package civilisation.individu.cognitons;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import civilisation.Configuration;
import civilisation.individu.Esprit;
import civilisation.individu.plan.NPlan;

public class NCogniton{

	TypeDeCogniton typeDeCogniton;
	String nom;
	String description;
	boolean recuAuDemarrage = false;
	int startChance = 0;
	ArrayList<LienCogniton> liens; /*Lien avec les cognitons debloques par ce cogniton*/
	ArrayList<LienPlan> liensPlans; /*Lien avec les plans qu'influence ce cogniton*/
	ArrayList<NPlan> plansAutorises;
	ArrayList<Object[]> triggeringAttributes;
	
	
	public final static int nHues = 7;
	Integer[] hues = new Integer[nHues];
	public final static Color[] hueColors = {Color.gray , Color.pink , Color.red , Color.blue, Color.orange , Color.green , Color.yellow};
		/* Color from developement spiral (hue):
		 * 0 = brown
		 * 1 = purple
		 * 2 = red
		 * 3 = blue
		 * 4 = orange
		 * 5 = green
		 * 6 = yellow
		 */
	

	
	public NCogniton(){
		liens = new ArrayList<LienCogniton>();
		liensPlans = new ArrayList<LienPlan>();
		plansAutorises = new ArrayList<NPlan>();
		triggeringAttributes = new ArrayList<Object[]>();

		for (int i = 0 ; i < hues.length; i++){
			hues[i] = 0;
		}
	}
	
	@Override
	public String toString(){
		System.out.println(" Nom cogniton qui toString : " +nom);
		return (" Nom : " + nom
				+ "\n Description : " + description
				+ " \n Type : " + typeDeCogniton
				+ " \n Liens : " + liens
				+ " \n Liens avec les plans : " + liensPlans
				+ " \n Plans autorisŽs : " + plansAutorises
				+"\n\n"
				);
	}

	public void mettreEnPlace(Esprit e,double weight){
		System.out.println("test");
		modifierPlans(true , e);
		if (plansAutorises.isEmpty()){ 
			System.out.println("No plans autorises");
			appliquerPoids(e,weight);
		}
		else{
			System.out.println("Plan autorise "+plansAutorises.get(0).getNom());
			e.redefinirPoids();
		}
	}
	
	
	
	
	public void appliquerPoids(Esprit e, double weight)
	{
		for (int i = 0 ; i < liensPlans.size() ; i++)
		{
			e.modifierPoids(liensPlans.get(i).getP(), weight);
		}
	}
	
	
	/**
	 * Ajoute ou retire des projets a l'esprit associŽ
	 * @param nouveauxPlans : liste des plans ˆ ajouter ou enlever
	 * @param add : true indique qu'il faut les ajouter, false les enlever
	 * @param e : l'esprit associŽ
	 */
	public void modifierPlans(Boolean add, Esprit e)
	{
		//Plan nPlans[] = getTabNouveauxPlans(e);
		if (!plansAutorises.isEmpty())
		{
			
			int taille = plansAutorises.size();
			if (add)
			{
				for (int i = 0 ; i < taille ; i++)
				{
					e.addPlan(plansAutorises.get(i));
				}
				e.redefinirPoids();
			}
			else
			{
				for (int i = 0 ; i < taille ; i++)
				{
					e.removePlan(plansAutorises.get(i));
				}
			}
		}
	}
	
	
	
	public TypeDeCogniton getType() {
		return typeDeCogniton;
	}

	public void setType(TypeDeCogniton typeDeCogniton) {
		this.typeDeCogniton = typeDeCogniton;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<LienCogniton> getLiens() {
		return liens;
	}

	public void setLiens(ArrayList<LienCogniton> liens) {
		this.liens = liens;
	}

	public ArrayList<LienPlan> getLiensPlans() {
		return liensPlans;
	}

	public void setLiensPlans(ArrayList<LienPlan> liensP) {
		this.liensPlans = liensP;
	}

	public ArrayList<NPlan> getPlansAutorises() {
		return plansAutorises;
	}

	public void setPlansAutorises(ArrayList<NPlan> plansAutorises) {
		this.plansAutorises = plansAutorises;
	}
	
	public void creerCognitonLambda(){
		nom = "nouveauCogniton"; /*TODO : faire un nom unique*/
		description = "Un nouveau cogniton";
		typeDeCogniton = TypeDeCogniton.BELIEF;
	}

	public boolean isRecuAuDemarrage() {
		return recuAuDemarrage;
	}

	public void setRecuAuDemarrage(boolean recuAuDemarrage) {
		this.recuAuDemarrage = recuAuDemarrage;
	}

	public Integer[] getHues() {
		return hues;
	}

	public void setHues(Integer[] spiralColors) {
		this.hues = spiralColors;
	}
	
	public int getStartChance() {
		return startChance;
	}

	public void setStartChance(int startChance) {
		this.startChance = startChance;
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
			output.write("<Cogniton>\n");
					output.write("\t<Name>"+getNom()+"</Name>\n");
					output.write("\t<Description>"+this.getDescription()+"</Description>\n");
					output.write("\t<Type>"+this.getType()+"</Type>\n");
					if(this.recuAuDemarrage)
					{
						output.write("\t<Initial>"+1+"</Initial>\n");
					}
					else
					{
						output.write("\t<Initial>"+0+"</Initial>\n");
					}
					output.write("\t<Permet>\n");
					for (int i = 0; i < this.plansAutorises.size() ;i++)
					{
						output.write("\t\t<ActionPermet>"+plansAutorises.get(i).getNom()+"</ActionPermet>\n");
					}
					output.write("\t</Permet>\n");
					output.write("\t<Influences>\n");
					for (int i = 0; i < this.liensPlans.size() ;i++)
					{
						output.write("\t\t<Influence>\n");
							output.write("\t\t\t<ActionInflus>"+liensPlans.get(i).getP().getNom()+"</ActionInflus>\n");
							output.write("\t\t\t<ValInflus>"+liensPlans.get(i).getPoids()+"</ValInflus>\n");
						output.write("\t\t</Influence>\n");
					}
					output.write("\t</Influences>\n");
					output.write("\t<Chaine>\n");
					if(this.liens != null)
					{
						for (int i = 0; i < this.liens.size() ;i++)
						{
							output.write("\t\t<Val1>"+this.liens.get(i).getC().getNom()+"</Val1>\n");
							output.write("\t\t<Val2>"+this.liens.get(i).getPoids()+"</Val2>\n");
						}
					}
					
					output.write("\t</Chaine>\n");
					output.write("\t<Hues>\n");
					if(this.liens != null)
					{

							output.write("\t\t<ValHue>"+0+"</ValHue>\n");
					}
					output.write("\t</Hues>\n");
					output.write("\t<Triggers>\n");
					if(this.liens != null)
					{
						for (int i = 0; i < triggeringAttributes.size() ;i++)
						{
							output.write("\t\t<Trigger>"+this.hues[i]+"\n");
								output.write("\t\t\t<ValTrig1>"+triggeringAttributes.get(i)[0]+"</ValTrig1>\n");
								output.write("\t\t\t<ValTrig2>"+triggeringAttributes.get(i)[1]+"</ValTrig2>\n");
								output.write("\t\t\t<ValTrig3>"+triggeringAttributes.get(i)[2]+"</ValTrig3>\n");
							output.write("\t</Triggers>\n");
						}
					}
					output.write("\t</Triggers>\n");
			output.write("</Cogniton>");
			output.flush();
			output.close();
			System.out.println("fichier créé");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		 
	}
	public ArrayList<Object[]> getTriggeringAttributes() {
		return triggeringAttributes;
	}

	public void setTriggeringAttributes(ArrayList<Object[]> triggeringAttributes) {
		this.triggeringAttributes = triggeringAttributes;
	}
	
	
	
	
}
