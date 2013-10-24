package civilisation.inspecteur.simulation.dialogues;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import civilisation.Configuration;
import civilisation.individu.cognitons.TypeDeCogniton;
import civilisation.individu.plan.action.Action;
import civilisation.individu.plan.action.OptionsActions;
import civilisation.inspecteur.simulation.GCogniton;
import civilisation.inspecteur.simulation.PanelArbreActions;
import civilisation.inspecteur.simulation.environnement.PanelEnvironnement;

public class DialogueEditerAction extends JDialog implements ActionListener, PropertyChangeListener{
	
	ArrayList<JComboBox> boxs = new ArrayList<JComboBox>();
    JOptionPane optionPane;
    PanelArbreActions p;
    Action a;
    ArrayList<String[]> schema;
    
	public DialogueEditerAction(Frame f , boolean modal , PanelArbreActions p , Action a) throws IOException{
		super(f,modal);

		this.setTitle("Editer une action");
		System.out.println("Chargement du logo");


		this.a = a;
		this.p = p;
		schema = a.getSchemaParametres();
		
		
		if (schema != null){
			for (int i = 0; i < schema.size(); i++){
				JComboBox box = new JComboBox();
				if (schema.get(i)[0].equals("**Objet**")){
					for (int j = 0; j < Configuration.objets.size(); j++){
						box.addItem(Configuration.objets.get(j).getNom());
					}
				}
				else{
					for (int j = 0; j < schema.get(i).length; j++){
						box.addItem(schema.get(i)[j]);
					}
				}
				boxs.add(box);
			}
		}

		Object[] array;
		if (boxs.size() != 0){
		    array = boxs.toArray();
		}
		else{
			JLabel label = new JLabel("Cette action n'est pas �ditable!");
		    array = new Object[1];
		    array[0] = label;
		}

		
		
	       

	    Object[] options = {"Valider" , "Annuler"};
	 
	    optionPane = new JOptionPane(array,
	                                    JOptionPane.QUESTION_MESSAGE,
	                                    JOptionPane.YES_NO_OPTION,
	                                    null,
	                                    options,
	                                    options[0]); 

	    setContentPane(optionPane);
	        
	    optionPane.addPropertyChangeListener(this);
	        
		
		ImageIcon icone = new ImageIcon(System.getProperty("user.dir")+"/bin/civilisation/graphismes/LogoMedium.png");
		optionPane.setIcon(icone);		
		this.pack();
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		System.out.println(optionPane.getValue());
		if (isVisible()){
			if (optionPane.getValue().equals("Valider")){
				a.clearOptions(); //Suppression des anciennes options
				for (int i = 0; i < boxs.size(); i++){
					OptionsActions opt = null;
					
					if (schema.get(i)[0].equals("**Objet**")){
						opt = new OptionsActions(schema.get(i)[1]); /*Le deuxi�me terme est toujours le nom du param�tre pour les param�tres complexes*/
						opt.addParametre(Configuration.getObjetByName((String)boxs.get(i).getSelectedItem()));
					}
					else if (schema.get(i)[0] != null){ /*Pas utile*/
						System.out.println(schema.get(i)[0]);
						opt = new OptionsActions((String)boxs.get(i).getSelectedItem());
					}
					System.out.println(opt);
					a.parametrerOption(opt);
				}
			}		
	        setVisible(false);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
