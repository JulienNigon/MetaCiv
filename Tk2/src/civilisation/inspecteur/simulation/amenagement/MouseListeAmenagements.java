package civilisation.inspecteur.simulation.amenagement;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

import civilisation.inspecteur.simulation.environnement.PanelTerrains;

public class MouseListeAmenagements implements MouseListener {

PanelListeAmenagements p;
	
	public MouseListeAmenagements(PanelListeAmenagements panelListeAmenagements)
	{
		this.p = panelListeAmenagements;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		p.getListeAmenagements().locationToIndex(e.getPoint());
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
