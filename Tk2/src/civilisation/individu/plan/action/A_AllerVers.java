package civilisation.individu.plan.action;

import java.awt.Color;
import java.util.ArrayList;

import civilisation.Communaute;
import civilisation.Configuration;
import civilisation.amenagement.Amenagement;
import civilisation.amenagement.Amenagement_Route;
import civilisation.individu.Humain;
import civilisation.inventaire.Objet;
import civilisation.pathfinder.Noeud;
import edu.turtlekit2.kernel.environment.Patch;

public class A_AllerVers extends Action{
	Patch cible;
	Object obj;

	public Action effectuer(Humain h) {
		if(obj.getClass() == Communaute.class || obj.getClass() == Humain.class || obj.getClass() == Amenagement.class )
		{
			if(obj.getClass() == Communaute.class)
			{
				cible = ((Communaute)obj).position;
			}
			else if(obj.getClass() == Humain.class)
			{
				cible = ((Humain)obj).position;
			}
			else
			{
				cible = ((Amenagement)obj).getPosition();
			}
			
			
			if(h.xcor() != cible.x || h.ycor() != cible.y)
			{
				if(h.getChemin().isEmpty())
				{
					h.setColor(Color.black);
					h.face(cible);
					h.getChemin().addAll(AStar(h,cible));
				/*	for(int i = 0; i < h.getChemin().size();i++)
					{
						h.getChemin().get(i).color = Color.red;
					}*/

				}
				
				if(h.getChemin() != null && !h.getChemin().isEmpty())
				{
					h.setColor(Color.green);
					Patch cible = h.getChemin().get(0);
					h.face(cible);
				}

				h.fd(1);
				if(h.getChemin() != null && !h.getChemin().isEmpty() && h.getChemin().get(0) != null )
				{
					h.getChemin().remove(0);
				}
				h.emit("passage", 1.0);
				if(h.smell("passage") > Configuration.passagesPourCreerRoute && !h.isMarkPresent("Route"))
				{
					Amenagement_Route troncon = new Amenagement_Route(h.position);
					//this.addAmenagement(troncon);  /*TODO : adapter les amenagements*/
					h.dropMark("Route", troncon);
				}
			}
		}
		else
		{
			
		}
		
		
		return nextAction;
	}

	
	@Override
	public String getInfo() {
		return super.getInfo() + " Agent back to his house.<html>";
	}

	
	public ArrayList<Patch> AStar(Humain h,Patch cible)
	{
		System.out.println("\n\n");
		System.out.println("x cible : "+cible.x +" y : "+cible.y + " radius : "+ Configuration.VisionRadius);
		int[][] map = new int[h.getWorldWidth()][h.getWorldHeight()];
		int minx = Math.min(cible.x, h.xcor());
		int maxx = Math.max(cible.x, h.xcor());
		int miny = Math.min(cible.y, h.ycor());
		int maxy = Math.max(cible.y, h.ycor());

		for(int i = minx - 10;i< maxx + 10;i++)
		{
			for(int j = miny - 10; j < maxy + 10 ; j++)
			{
				map[i][j] = Configuration.VitesseEstimeeParDefaut;
			}
		}
		
		for(int i = 0; i < Configuration.VisionRadius ; i++)
		{
			for(int j = 0; j < Configuration.VisionRadius * 2 ; j++)
			{
					//	Color couleur = h.getPatchColorAt(i - h.getVisionRadius(), j - h.getVisionRadius());
						
						int passabilite = Configuration.couleurs_terrains.get(h.getPatchColorAt(i -Configuration.VisionRadius, j - Configuration.VisionRadius)).getPassabilite();
						if(h.smellAt("passage", i - Configuration.VisionRadius, j - Configuration.VisionRadius) > 0)
						{
							map[h.xcor() + i - Configuration.VisionRadius][h.ycor()+j - Configuration.VisionRadius] = (int) (passabilite - (passabilite/2*1/h.smellAt("passage", i - Configuration.VisionRadius, j - Configuration.VisionRadius)));
						}
						else
						{
							map[h.xcor() + i - Configuration.VisionRadius][h.ycor()+j - Configuration.VisionRadius] = passabilite;
						}
					
						

				if(h.isMarkPresentAt("Route", i -Configuration.VisionRadius, j - Configuration.VisionRadius))
				{
					map[h.xcor() + i - Configuration.VisionRadius][h.ycor()+j - Configuration.VisionRadius] -= 1;
				}
					
			}
		}
	/*	for(int i = 0; i < map.length; i++)
		{
			System.out.print("[");
			for(int j = 0;j < map[i].length;j++)
			{
				if(map[i][j] != 1000)
				{
					System.out.print(map[i][j]);
				}
			}
			System.out.println("]");
		}*/
		ArrayList<Noeud> liste_noeud = new ArrayList<Noeud>();
		ArrayList<Noeud> open_list = new ArrayList<Noeud>();
		ArrayList<Noeud> close_list = new ArrayList<Noeud>();
		Noeud noeud = new Noeud(h.position.x,h.position.y,0,0);
		noeud.setDistanceRacine(0);
		close_list.add(noeud);
		liste_noeud.add(noeud);
		int cpt = 1;
		for(int i = -1; i < 2;i++)
		{
			for(int j = -1;j < 2 ; j++)
			{
				int x = noeud.getPosX();
				int y = noeud.getPosY();
				if( (x+i < h.getWorldWidth() && x+i > 0) && (y+j < h.getWorldHeight() && y+j > 0) && (i!= 0 || j != 0) && map[x+i][y+j] != 0 )
				{
					Noeud noeu = new Noeud(x+i,y+j,0,cpt);
					int distanceRacine = map[x+i][y+j];
					noeu.setDistanceRacine(distanceRacine);
					open_list.add(noeu);
					liste_noeud.add(noeu);
					cpt++;
				}
			}
		}
		/*System.out.println("Open_list 1 : ");
		for(int i = 0; i < open_list.size();i++)
		{
			System.out.println("Noeud : "+open_list.get(i).getId()+" x : "+open_list.get(i).getPosX()+" y : "+open_list.get(i).getPosY()+ " distance : "+open_list.get(i).getDistanceRacine());
		}*/
		Noeud suivant = h.PlusProcheNoeud(open_list, cible);
		if(suivant != null)
		{
			/*if(suivant.getParent() != noeud.getId())
			{
				
				for(int i = 0; i< close_list.size();i++)
				{
					if(close_list.get(i).getId() > suivant.getParent())
					{
						close_list.remove(i);
					}
				}
				
			}*/
			close_list.add(suivant);
		}
		//System.out.println("close_list 1 : " + close_list);
		noeud = suivant;
		
		while(noeud != null && (noeud.getPosX() != cible.x || noeud.getPosY() != cible.y) )
		{
			System.out.println("Noeud suivant : "+noeud.getId()+ " x : "+noeud.getPosX()+ " y : "+noeud.getPosY()+ " parent : "+noeud.getParent());
			open_list.remove(noeud);
			for(int i = -1; i < 2;i++)
			{
				for(int j = -1;j < 2 ; j++)
				{
					int x = noeud.getPosX();
					int y = noeud.getPosY();
					if( (x+i < h.getWorldWidth() && x+i > 0) && (y+j < h.getWorldHeight() && y+j > 0) && (i!= 0 || j != 0) && map[x+i][y+j] != 0)
					{
						Noeud noeu = new Noeud(x+i,y+j,noeud.getId(),cpt);
						if(! doublons(open_list,noeud))
						{
							int distanceRacine = map[x+i][y+j] + noeud.getDistanceRacine();
							noeu.setDistanceRacine(distanceRacine);
							open_list.add(noeu);
							liste_noeud.add(noeu);
							cpt++;
						//	System.out.println("Nouveau noeud "+noeu.getId()+" x : "+noeu.getPosX() + " y : "+noeu.getPosY());
						}	
					}
				}
			}
			suivant = h.PlusProcheNoeud(open_list, cible);
			if(suivant != null)
			{
				/*if(suivant.getParent() != noeud.getId())
				{
					
					for(int i = 0; i< close_list.size();i++)
					{
						if(close_list.get(i).getId() > suivant.getParent())
						{
							close_list.remove(i);
						}
					}
					
				}*/
				close_list.add(suivant);
			}
			noeud = suivant;
		}
		
		
		
		
		ArrayList<Patch> liste = new ArrayList<Patch>();
		
		
	/*	for(int i = 0;i < close_list.size();i++)
		{
			int x = close_list.get(i).getPosX();
			int y = close_list.get(i).getPosY();
			if(map[x][y] >= Configuration.VitesseEstimeeParDefaut)
			{
				return liste;
			}
			else
			{
				liste.add(0,h.getPatchAt(x - h.position.x, y - h.position.y));
			}
		}*/
		Noeud nodesui = close_list.get(close_list.size() - 1);
		while(!(nodesui.getPosX() == h.position.x && nodesui.getPosY() == h.position.y) )
		{
			int x = nodesui.getPosX();
			int y = nodesui.getPosY();
			liste.add(0,h.getPatchAt(x - h.position.x, y - h.position.y));
			nodesui = liste_noeud.get(nodesui.getParent());
		}
		System.out.println("Pos => x : "+h.xcor() + " y : "+h.ycor());
		for(int i = 0; i < liste.size();i++)
		{
			System.out.println("Patch : "+ i + " x : "+liste.get(i).x + " y : "+ liste.get(i).y);

		}
		System.out.println("Cible x : " + cible.x + " y : "+cible.y);
		return liste;
	}
	public boolean doublons(ArrayList<Noeud> liste, Noeud noeud )
	{
		for(int i = 0; i < liste.size(); i++)
		{
			if(liste.get(i).getPosX() == noeud.getPosX() && liste.get(i).getPosY() == noeud.getPosY())
			{
				return true;
			}
		}
		return false;
	}
	
	public void parametrerOption(OptionsActions option){
		super.parametrerOption(option);
		
		if (option.getParametres().get(0).getClass().equals(Communaute.class)){
			obj = (Communaute) option.getParametres().get(0);
		}
		else if(option.getParametres().get(0).getClass().equals(Humain.class))
		{
			obj = (Humain) option.getParametres().get(0);
		}
		else if(option.getParametres().get(0).getClass().equals(Amenagement.class))
		{
			obj = (Amenagement) option.getParametres().get(0);
		}
		else
		{
			System.out.println("Mauvaise initialisation d'une action!");
		}

	}
	
	public ArrayList<String[]> getSchemaParametres(){
		
		if (schemaParametres == null)
		{
			schemaParametres = new ArrayList<String[]>();
			String[] objet = {"**Objet**" , "ObjetGagne"};
			schemaParametres.add(objet);
		}
		return schemaParametres;	
	}
}
