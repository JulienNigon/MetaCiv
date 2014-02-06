package civilisation.individu.plan;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import civilisation.Configuration;
import civilisation.individu.Esprit;
import civilisation.individu.Humain;
import civilisation.individu.plan.action.Action;
import civilisation.inventaire.Objet;

public class NPlan {

	String nom;
	ArrayList<Action> actions;
	Boolean isBirthPlan = false;
	Boolean isSelfPlan = false;
	int i = 0;
	public NPlan(){
		actions = new ArrayList<Action>();
	}

	/**
	 * Active le plan en effectuant l'action correspondant ˆ la progression de l'agent
	 * @param h : L'agent effectuant l'action
	 */
	public void activer(Humain h , Action action){
	//    System.out.println("this lance : " + this.getNom());

		if (action == null){
			Action a = actions.get(i).effectuer(h);
			if (a != null) h.getEsprit().getActions().push(a);
			h.getEsprit().setActionEnCours(a);
		}
		else {
			Action a = action.effectuer(h);
			if (a != null) h.getEsprit().getActions().push(a);
		}
		
	}
	
	public ArrayList<Action> getActions() {
		return actions;
	}

	public void setActions(ArrayList<Action> actions) {
		this.actions = actions;
	}
	
	public void addAction(Action action) {
			actions.add(action);
	}
	
	public void addActionAfter(Action action , Action ref) {
		for (int i = 0 ; i < actions.size(); i++){
			if (actions.get(i).equals(ref)){
				System.out.println("Action ajoutŽe : " + (i+1));
				actions.add(i+1,action);
				actions.get(i).setNextAction(action); /*On reconstruit le chainage*/
				if (i+2 < actions.size()){
					action.setNextAction(actions.get(i+2)); /*On reconstruit le chainage*/
				}
				break;
			}
			if (actions.get(i).getListeActions() != null){
				actions.get(i).addActionAfter( action , ref);
			}
		}
	}
	
	public void addActionBefore(Action action , Action ref) {
		for (int i = 0 ; i < actions.size(); i++){
			if (actions.get(i).equals(ref)){
				System.out.println("Action ajoutŽe : " + (i+1));
				actions.add(i,action);
				if (i>0){
					actions.get(i-1).setNextAction(action);
				}
				action.setNextAction(actions.get(i+1)); /*On reconstruit le chainage*/
				break;
			}
			if (actions.get(i).getListeActions() != null){
				actions.get(i).addActionBefore( action , ref);
			}
		}
	}
	
	public void addSubAction(Action action, Action ref) {
		for (int i = 0 ; i < actions.size(); i++){
			if (actions.get(i).equals(ref)){
				System.out.println("Action ajoutŽe : " + (i+1));
				actions.get(i).getListeActions().add(0,action);
				if (actions.get(i).getListeActions().size()>1){
					action.setNextAction(actions.get(i).getListeActions().get(1));
				}
				break;
			}
			if (actions.get(i).getListeActions() != null){
				actions.get(i).addSubAction( action , ref);
			}
		}		
	}
	
	public void addFirstAction(Action action) {
		actions.add(0,action);
		if (actions.size() > 1){
			action.setNextAction(actions.get(1));
		}
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	@Override
	public String toString(){
		return nom;
	}
	
	public Boolean getIsBirthPlan() {
		return isBirthPlan;
	}

	public void setIsBirthPlan(Boolean isBirthPlan) {
		this.isBirthPlan = isBirthPlan;
	}

	public Boolean getIsSelfPlan() {
		return isSelfPlan;
	}

	public void setIsSelfPlan(Boolean isSelfPlan) {
		this.isSelfPlan = isSelfPlan;
	}

	public void enregistrer(File cible)
	{
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
			output.write("<Plan>\n");
					output.write("\t<Nom>"+getNom()+"</Nom>\n");
					output.write("\t<Birth>"+isBirthPlan+"</Birth>\n");
					output.write("\t<Self>"+isSelfPlan+"</Self>\n");
					output.write("\t<Actions>\n");
					for (int i = 0; i < this.actions.size() ;i++)
					{
						output.write("\t\t<Action>\n");
								output.write("\t\t\t<NomAction>"+this.actions.get(i).getSimpleName()+"</NomAction> \n");
								output.write("\t\t\t<Variables>\n");
							for(int j = 0; j< this.actions.get(i).getOptions().size();j++)
							{
								output.write("\t\t\t\t<Variable>\n");
											output.write("\t\t\t\t\t<NomVariable>"+this.actions.get(i).getOptions().get(j).getName()+"</NomVariable>\n");
									ArrayList<Object> parametres = this.actions.get(i).getOptions().get(j).getParametres();
									for (int k = 0; k < parametres.size(); k++){
										if (parametres.get(k).getClass().equals(Objet.class)){
											output.write("\t\t\t\t\t<TypeVariable>Objet</TypeVariable>\n");
										}
										if (parametres.get(k).getClass().equals(Integer.class)){
											output.write("\t\t\t\t\t<TypeVariable>Integer</TypeVariable>\n");
										}
										if (parametres.get(k).getClass().equals(String.class)){
											output.write("\t\t\t\t\t<TypeVariable>Attribute</TypeVariable>\n");
										}
										output.write("\t\t\t\t\t<ValeurVariable>"+parametres.get(k)+"</ValeurVariable>\n");
									}			
									output.write("\t\t\t\t</Variable>\n");
							}
									output.write("\t\t\t</Variables>\n");
							/*		System.out.println("Test action " + 0 + " : "+this.actions.get(i).getName());
									System.out.println("Taille action " + this.actions.size());*/
									RecursAction(this.actions.get(i),0,output);
						output.write("\t\t</Action>\n");
					}
					output.write("\t</Actions>\n");
			output.write("</Plan>");
			output.flush();
			output.close();
			System.out.println("fichier créé");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void RecursAction(Action action , int niveau, BufferedWriter output)
	{
		try
		{
			if(action.getListeActions().size() > 0)
			{
				for(int i= 0; i < niveau ; ++i){output.write("\t\t\t"); }output.write("\t\t\t<SousActions"+niveau+">\n");
				System.out.println("Slots : "+action.getListeActions().size());
					for(int j = 0; j< action.getListeActions().size();j++)
					{
						for(int i= 0; i < niveau ; ++i){output.write("\t\t\t\t"); }output.write("\t\t\t\t<SousAction"+niveau+">\n");
						Action sact = action.getListeActions().get(j);
						for(int i= 0; i < niveau ; ++i){output.write("\t\t\t\t\t"); }output.write("\t\t\t\t\t<SousActionName"+niveau+">"+ sact.getSimpleName() +"</SousActionName"+niveau+">\n");
						for(int i= 0; i < niveau ; ++i){output.write("\t\t\t\t\t"); }output.write("\t\t\t\t\t<SousActionVariables"+niveau+">\n");
						for(int k = 0; k < sact.getOptions().size();k++)
						{
							for(int i= 0; i < niveau ; ++i){output.write("\t\t\t\t\t"); }output.write("\t\t\t\t\t<SousActionVariable"+niveau+">\n");
							for(int i= 0; i < niveau ; ++i){output.write("\t\t\t\t\t\t"); }output.write("\t\t\t\t\t\t<SousActionNomVariable"+niveau+">"+sact.getOptions().get(k).getName()+"</SousActionNomVariable"+niveau+">\n");
								ArrayList<Object> parametres = sact.getOptions().get(k).getParametres();
								for (int l = 0; l < parametres.size(); l++){
									if (parametres.get(l).getClass().equals(Objet.class)){
										for(int i= 0; i < niveau; ++i){output.write("\t\t\t\t\t\t"); }output.write("\t\t\t\t\t\t<SousActionTypeVariable"+niveau+">Objet</SousActionTypeVariable"+niveau+">\n");
									}
									if (parametres.get(l).getClass().equals(Integer.class)){
										for(int i= 0; i < niveau ; ++i){output.write("\t\t\t\t\t\t"); }output.write("\t\t\t\t\t\t<SousActionTypeVariable"+niveau+">Integer</SousActionTypeVariable"+niveau+">\n");
									}
									if (parametres.get(l).getClass().equals(String.class)){
										for(int i= 0; i < niveau; ++i){output.write("\t\t\t\t\t\t"); }output.write("\t\t\t\t\t\t<SousActionTypeVariable"+niveau+">Attribute</SousActionTypeVariable"+niveau+">\n");
									}
									for(int i= 0; i < niveau; ++i){output.write("\t\t\t\t\t\t"); }output.write("\t\t\t\t\t\t<SousActionValeurVariable"+niveau+">"+parametres.get(l)+"</SousActionValeurVariable"+niveau+">\n");
								}			
								for(int i= 0; i < niveau; ++i){output.write("\t\t\t\t\t"); }output.write("\t\t\t\t\t</SousActionVariable"+niveau+">\n");
						}
						for(int i= 0; i < niveau; ++i){output.write("\t\t\t\t\t"); }output.write("\t\t\t\t\t</SousActionVariables"+niveau+">\n");
						RecursAction(sact,niveau + 1,output);
						for(int i= 0; i < niveau; ++i){output.write("\t\t\t\t"); }output.write("\t\t\t\t</SousAction"+niveau+">\n");
					}
					
					for(int i= 0; i < niveau; ++i){output.write("\t\t\t"); }output.write("\t\t\t</SousActions"+niveau+">\n");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void ecrireAction(PrintWriter out, int iteration, Action a){
		for (int j = 0; j < iteration; j++){
			out.print("\t");
		}
		System.out.println("Sauvegarde de l'action : " + a.getName());

		out.print("Action : " + a.toFormatedString());
		out.println();

		if (!a.getListeActions().isEmpty()){
			ecrireAction(out,iteration+1,a.getListeActions().get(0));
		}
		if (a.getNextAction()!=null){
			ecrireAction(out,iteration,a.getNextAction());
		}
		
	}
	
	/*A des fins de debuggageÉ*/
	public void seDecrire(){
		for (int i = 0 ; i < actions.size(); i++){
			System.out.println(actions.get(i).toString());
		}
	}

	public void removeAction(Action action) {
		for (int i = 0 ; i < actions.size(); i++){
			if (actions.get(i).equals(action)){
				actions.remove(i);
				if (i>0 && i<actions.size()){
					actions.get(i-1).setNextAction(actions.get(i));
				} else if (i>0) {
					actions.get(i-1).setNextAction(null);
				}
				break;
			}
			if (actions.get(i).getListeActions() != null){
				actions.get(i).removeAction(action);
			}
		}		
	}

	



}
