package civilisation.inspecteur.simulation.amenagement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import civilisation.inspecteur.simulation.amenagement.PanelAmenagement;

public class ActionPanelAmenagement implements ActionListener{

	PanelAmenagement p;
	
	public ActionPanelAmenagement(PanelAmenagement p) {
		this.p = p;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		p.performChange();
	}

}
