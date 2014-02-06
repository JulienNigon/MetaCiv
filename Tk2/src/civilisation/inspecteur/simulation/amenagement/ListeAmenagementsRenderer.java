package civilisation.inspecteur.simulation.amenagement;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import civilisation.amenagement.Amenagement;
import civilisation.world.Terrain;

public class ListeAmenagementsRenderer extends DefaultListCellRenderer{


    @Override
	public Component getListCellRendererComponent(JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus)
    {

        super.getListCellRendererComponent( list,
                 value,
                 index,
                 isSelected,
                 cellHasFocus);

       this.setText(((Amenagement)value).getNom());
       if(((Amenagement)value).getNom().contains("Batiment"))
       {
    	   this.setBackground(Color.green);
       }
       else
       {
    	   this.setBackground(Color.yellow);
       }
       
        
        return this;
    }

}
