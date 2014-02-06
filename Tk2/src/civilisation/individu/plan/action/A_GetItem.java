package civilisation.individu.plan.action;

import java.util.ArrayList;

import civilisation.individu.Humain;
import civilisation.inventaire.Objet;

public class A_GetItem extends Action{

	Objet obj;
	
	@Override
	public Action effectuer(Humain h) {

		System.out.println(obj.getName()); //TODO
		return nextAction;
	}

	@Override
	public void parametrerOption(OptionsActions option){
		super.parametrerOption(option);
		
		if (option.getParametres().get(0).getClass().equals(Objet.class)){
			obj = (Objet) option.getParametres().get(0);
		}
		else
		{
			System.out.println("Mauvaise initialisation d'une action!");
		}

	}
	

	/**
	 * Retourne la structure des param�tres.
	 * Permet de dŽterminer la prŽsentation de la fen�tre de rŽglages.
	 */
	@Override
	public ArrayList<String[]> getSchemaParametres(){
		
		if (schemaParametres == null){
			schemaParametres = new ArrayList<String[]>();
			String[] objet = {"**Objet**" , "ObjetGagne"};
			schemaParametres.add(objet);
		}
		return schemaParametres;	
	}
	
}
