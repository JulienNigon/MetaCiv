package civilisation.inspecteur.arbreCognitif;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;

import civilisation.inspecteur.animations.JJComponent;

/** 
 * G�re l'interaction avec les cognitons graphiques (GCognitons)
 * @author DTEAM
 * @version 1.1 - 2/2013
*/

public class MouseGCognitonListener implements MouseListener{

	GCogniton c;
	
	public MouseGCognitonListener(GCogniton c)
	{
		this.c = c;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		c.selectionner();
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
