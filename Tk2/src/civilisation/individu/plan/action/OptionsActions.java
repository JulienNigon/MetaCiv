package civilisation.individu.plan.action;

import java.util.ArrayList;

import civilisation.inventaire.Objet;


public class OptionsActions {

	String name;
	ArrayList<Object> parametres = new ArrayList<Object>();
    
    public OptionsActions(String name){
    	this.name = name;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
	@Override
	public String toString(){
		return name + " : " + parametres.size();
	}
    
	public void addParametre(Object o){
		parametres.add(o);
	}

	public ArrayList<Object> getParametres() {
		return parametres;
	}
	
	/**
	 * 
	 * @return String au format des fichiers textes .metaciv
	 */
	public String toFormatedString(){
		if (parametres.size() == 0){
			return name;
		}
		else{
			String s = "(";
			for (int i = 0; i < parametres.size(); i++){
				if (parametres.get(i).getClass().equals(Objet.class)){
					s += "Objet ";
					s += ((Objet) parametres.get(i)).getNom();
					if (i < parametres.size() - 1){
						s += ";"; /*Separateur pour parametres multiples*/
					}
				}
				if (parametres.get(i).getClass().equals(Integer.class)){
					s += "Integer ";
					s += parametres.get(i).toString();
					if (i < parametres.size() - 1){
						s += ";";
					}
				}
				if (parametres.get(i).getClass().equals(String.class)){
					s += "Attribute ";
					s += parametres.get(i);
					if (i < parametres.size() - 1){
						s += ";";
					}
				}
			}
			s+=")";
			return name + s;
		}
	}
	
}