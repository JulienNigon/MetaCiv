package civilisation.individu.plan;

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
import civilisation.individu.cognitons.Cogniton;
import civilisation.individu.plan.action.Action;

public class NPlan {

	String nom;
	ArrayList<Action> actions;

	public NPlan(){
		actions = new ArrayList<Action>();
	}

	/**
	 * Active le plan en effectuant l'action correspondant � la progression de l'agent
	 * @param h : L'agent effectuant l'action
	 */
	public void activer(Humain h , Action action){
		
		if (action == null){
		//	System.out.println("plan lance : " + nom);
			h.getEsprit().setActionEnCours(actions.get(0).effectuer(h));
		}
		else {
			h.getEsprit().setActionEnCours(action.effectuer(h));
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
				System.out.println("Action ajout�e : " + (i+1));
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
				System.out.println("Action ajout�e : " + (i+1));
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
				System.out.println("Action ajout�e : " + (i+1));
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
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String toString(){
		return nom;
	}
	
	public void enregistrer(File cible) {
		PrintWriter out;
		System.out.println("Sauvegarde du plan : " + nom);
		try {
			out = new PrintWriter(new FileWriter(cible.getPath()+"/"+getNom()+Configuration.getExtension()));
			out.println("Nom : " + getNom());
			if (actions.isEmpty() !=  true){
				ecrireAction(out,0,actions.get(0));
			}
			
			
			out.close();
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
	
	/*A des fins de debuggage�*/
	public void seDecrire(){
		for (int i = 0 ; i < actions.size(); i++){
			System.out.println(actions.get(i).toString());
		}
	}


}
