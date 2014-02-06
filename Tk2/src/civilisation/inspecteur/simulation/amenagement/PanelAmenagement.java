package civilisation.inspecteur.simulation.amenagement;



import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import civilisation.Civilisation;
import civilisation.amenagement.Amenagement;
import civilisation.inspecteur.simulation.PanelModificationSimulation;
import civilisation.inspecteur.simulation.civilisations.ActionPanelCivilisation;
import civilisation.inspecteur.simulation.civilisations.PanelListeCivilisations;

public class PanelAmenagement extends JPanel implements ActionListener{
	PanelModificationSimulation panelParent;
	PanelListeAmenagements panelListeAmenagements;
	JTextField nameField;
	JComboBox comboIcon;
	JButton boutonEffets;
	JPanel panelNom;
	JPanel panelBoxEffet;
	JButton boutonInflu;
	
	public PanelAmenagement (PanelModificationSimulation panelParent , PanelListeAmenagements panelListeAmenagements){
		super();
		this.panelParent = panelParent;
		this.panelListeAmenagements = panelListeAmenagements;
		this.setLayout(new GridLayout(10,1));
	
		TitledBorder bordure = BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), "Edition d'aménagements");
		bordure.setTitleJustification(TitledBorder.LEFT);
		this.setBorder(bordure);
		
		panelNom = new JPanel();
		panelNom.add(new JLabel("Amenagement name : "));
		nameField = new JTextField(40);
		nameField.addActionListener(new ActionPanelAmenagement(this));
		panelNom.add(nameField);
		panelNom.add(new JLabel("Effets"));

		ImageIcon iconeplus = new ImageIcon(this.getClass().getResource("../../icones/plus.png"));	
		boutonEffets = new JButton(iconeplus);
		boutonEffets.addActionListener(this);
		panelNom.add(boutonEffets);
		panelNom.setVisible(true);
		
		boutonInflu = new JButton(iconeplus);
		boutonInflu.addActionListener(this);
		panelNom.add(boutonInflu);
		
		this.add(panelNom);
	}
	
	public void performChange() {
		// TODO Auto-generated method stub
		if (panelListeAmenagements != null){
			if ( panelListeAmenagements.getListeAmenagements().getSelectedValue() != null) {
				Amenagement c = ((Amenagement) panelListeAmenagements.getListeAmenagements().getSelectedValue());
				//c.setNom(nameField.getText());
			}
		}
	}
	
	public void update() {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		 
		if(source == boutonEffets)
		{
			this.add(new RadioBoxEffets());
		} 
		else if(source == boutonInflu) 
		{
			this.add(new RadioBoxInfluences());
		}
		else
		{
			
		}
	}

}
