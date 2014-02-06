package civilisation.inspecteur.simulation.amenagement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import civilisation.Civilisation;
import civilisation.Configuration;
import civilisation.amenagement.Amenagement;
import civilisation.inspecteur.simulation.civilisations.PanelListeCivilisations;

public class ActionsToolBarListeAmenagements implements ActionListener{

	PanelListeAmenagements p;
	int index;
	
	public ActionsToolBarListeAmenagements(PanelListeAmenagements p, int i)
	{
		this.p = p;
		index = i;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (index == 0){
			System.out.println("--New Amenagement--");
			/*Amenagement a = new Amenagement();
			a.setNom("Default_Civ_" + Configuration.civilisations.size());
			Configuration.civilisations.add(a);
			p.addAmenagement(c);*/
			
		}
	}

}
