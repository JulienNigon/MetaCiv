package civilisation.inspecteur.simulation.amenagement;

import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import civilisation.Configuration;

public class RadioBoxEffets extends JPanel implements ActionListener{
	JRadioButton bouton1;
	JRadioButton bouton2;
	JRadioButton bouton3;
	JRadioButton bouton4;
	JRadioButton bouton5;
	JRadioButton bouton6;
	JComboBox listedel1 = new JComboBox();
	JPanel Valeurs;
	JPanel nom;
	public RadioBoxEffets()
	{
		super();
		
		this.setLayout(new GridLayout(5,1));
		
		nom = new JPanel();
		nom.add(new JLabel("Effets"));
		this.add(nom);
		
		JPanel listebouton1 = new JPanel();
		
		ButtonGroup group = new ButtonGroup();
		bouton1 = new JRadioButton("Toujours Actif");
		bouton1.setMnemonic(KeyEvent.VK_1);
		bouton1.setActionCommand("Toujours_Actif");
		bouton1.setSelected(true);
		
		bouton2 = new JRadioButton("A la possession");
		bouton2.setMnemonic(KeyEvent.VK_2);
		bouton2.setActionCommand("Possession");
		bouton2.setSelected(false);
		
		bouton3 = new JRadioButton("A l'utilisation");
		bouton3.setMnemonic(KeyEvent.VK_3);
		bouton3.setActionCommand("Utilisation");
		bouton3.setSelected(false);
		
		group.add(bouton1);
		group.add(bouton2);
		group.add(bouton3);

		JPanel listebouton2 = new JPanel();
		ButtonGroup group2 = new ButtonGroup();
		
		bouton4 = new JRadioButton("Inventaire");
		bouton4.setMnemonic(KeyEvent.VK_4);
		bouton4.setActionCommand("Inventaire");
		bouton4.setSelected(false);
		bouton4.addActionListener(this);

		bouton5 = new JRadioButton("Agent");
		bouton5.setMnemonic(KeyEvent.VK_5);
		bouton5.setActionCommand("Agent");
		bouton5.setSelected(false);
		bouton5.addActionListener(this);
		
		bouton6 = new JRadioButton("Cogniton");
		bouton6.setMnemonic(KeyEvent.VK_6);
		bouton6.setActionCommand("Cogniton");
		bouton6.setSelected(false);
		bouton6.addActionListener(this);
		
		
		group2.add(bouton4);
		group2.add(bouton5);
		group2.add(bouton6);

		Valeurs = new JPanel();
		
		Valeurs.add(new JLabel("Valeur de l' effet :"));
		Valeurs.add(new JTextField(4));
		Valeurs.setVisible(false);
		
		listebouton1.add(bouton1);
		listebouton1.add(bouton2);
		listebouton1.add(bouton3);
		
		listebouton2.add(bouton4);
		listebouton2.add(bouton5);
		listebouton2.add(bouton6);
		listedel1.setVisible(false);

		this.add(listebouton1);
		this.add(listebouton2);
		this.add(listedel1);
		this.add(Valeurs);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		
		if(source == bouton4)
		{
			listedel1.removeAll();
			listedel1.removeAllItems();
			for(int i = 0;i < Configuration.objets.size();i++)
			{
				listedel1.addItem(Configuration.objets.get(i).getName());
			}	
			listedel1.setVisible(true);
			Valeurs.setVisible(true);
		}
		else if(source == bouton6)
		{
			listedel1.removeAll();
			for(int i = 0;i < Configuration.cognitons.size();i++)
			{
				listedel1.addItem(Configuration.cognitons.get(i).getNom());
			}	
			listedel1.setVisible(true);
			Valeurs.setVisible(true);
		}
		else
		{
			listedel1.setVisible(false);
			Valeurs.setVisible(true);
		}
	}
}
