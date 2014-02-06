package civilisation.inspecteur.simulation.amenagement;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RadioBoxInfluences extends JPanel implements ActionListener{

	JPanel nom;
	JPanel influ;
	JComboBox<String> listedel1;
	JComboBox<String> listedel2;
	JComboBox<String> listedel3;
	JTextField text1;
	JTextField text2;
	JComboBox<String> listedel4;
	public RadioBoxInfluences()
	{
		super();
		this.setLayout(new GridLayout(2,1));
		nom = new JPanel();
		nom.add(new JLabel("Influence : "));
		
		influ = new JPanel();
		listedel1 = new JComboBox<String>();
		listedel1.addItem("Valeur du terrain ");
		listedel1.addItem("Terrains voisins");
		listedel1.addItem("Objet possédé");
		influ.add(listedel1);
		
		listedel2 = new JComboBox<String>();
		influ.add(listedel2);
		
		listedel3 = new JComboBox<String>();
		listedel3.addItem("<");
		listedel3.addItem("<=");
		listedel3.addItem("=");
		listedel3.addItem("=>");
		listedel3.addItem(">");
		listedel3.addItem("!=");
		influ.add(listedel3);
		
		text1 = new JTextField(4);
		influ.add(text1);
		
		influ.add(new JLabel("Action"));
		
		listedel4 = new JComboBox<String>();
		influ.add(listedel4);
		
		influ.add(new JLabel("+"));
		
		text2 = new JTextField(4);
		influ.add(text2);
		
		this.add(nom);
		this.add(influ);
		this.setVisible(true);
		
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	

}
