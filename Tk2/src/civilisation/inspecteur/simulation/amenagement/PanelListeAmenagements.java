package civilisation.inspecteur.simulation.amenagement;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.border.TitledBorder;

import org.jfree.base.modules.PackageManager;

import civilisation.Civilisation;
import civilisation.Configuration;
import civilisation.amenagement.Amenagement;
import civilisation.inspecteur.animations.JJPanel;
import civilisation.inspecteur.simulation.civilisations.ListeCivilisationsRenderer;
import civilisation.inspecteur.simulation.civilisations.MouseListeCivilisations;
import civilisation.inspecteur.simulation.civilisations.PanelCivilisations;

public class PanelListeAmenagements extends JJPanel {

	JList listeAmenagements;
	DefaultListModel listModel;
	PanelAmenagement panelAmenagement;
	
	public PanelListeAmenagements(PanelAmenagement panelAmenagement) {
		
		this.panelAmenagement = panelAmenagement;
		
		this.setLayout(new BorderLayout());
		
		setupList();
		
		this.add(listeAmenagements , BorderLayout.CENTER);
		
		TitledBorder bordure = BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), "Liste des amenagements");
		bordure.setTitleJustification(TitledBorder.LEFT);
		this.setBorder(bordure);
		
	}
	
	public JList getListeAmenagements() {
		return listeAmenagements;
	}

	public void setListeAmenagements(JList listeAmenagements) {
		this.listeAmenagements = listeAmenagements;
	}

	public void addAmenagement(Amenagement a) {
		((javax.swing.DefaultListModel)listeAmenagements.getModel()).addElement(a);			
	}
	


	private void setupList() {
		
		listModel = new DefaultListModel();
		listeAmenagements = new JList(listModel);
		System.out.println("SetupList");
		for(int i = 0; i < Configuration.amenagements.size();++i)
		{
			Amenagement amena =  Configuration.amenagements.get(i);
			System.out.println("Test des listes d'amenagements "+amena.getNom());
			if(amena.getNom().contains("Batiment"))
			{
				listModel.addElement(amena);
			}
			
		}
		
		listeAmenagements.addMouseListener(new MouseListeAmenagements(this));
		listeAmenagements.setCellRenderer(new ListeAmenagementsRenderer());
	}

	public PanelAmenagement getPanelCivilisations() {
		return panelAmenagement;
	}

	public void setPanelCivilisations(PanelAmenagement panelAmenagement) {
		this.panelAmenagement = panelAmenagement;
	}
}
