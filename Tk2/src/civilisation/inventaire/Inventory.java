package civilisation.inventaire;

import java.util.HashMap;

import civilisation.Configuration;
import civilisation.individu.Humain;

public class Inventory {

	private int capacity;
	private int weight = 0;
	private HashMap<Objet,Integer> objets;
	private HashMap<String,Breakables> breakables;
	private Humain h;
	
	public Inventory(Humain h)
	{
		this.setCapacity(Configuration.CapacityBag);
		this.objets = new HashMap<Objet,Integer>();
	}

	public void add(Objet o)
	{
		if(this.capacity > this.weight + o.getWeight())
		{
			if(this.objets.containsKey(o))
			{
				int temp = this.objets.get(o);
				this.objets.remove(o);
				this.objets.put(o, temp + 1);
				this.weight = this.weight + o.getWeight();
			}
			else
			{
				this.objets.put(o,1);
				this.weight = this.weight + o.getWeight();
			}
			if(o.haveEffect())
			{
				for(int i = 0; i < o.getEffect().size();i++)
				{
					if(o.getEffect().get(i).getType() == "Equipe")
					{
						o.getEffect().get(i).Activer(h,h.getEsprit());
					}
				}
			}
			
		}
	}
	
	public void add(Breakables o)
	{
		if(this.capacity > this.weight + o.getWeight())
		{
			this.breakables.put(o.getName(), o);
			this.weight = this.weight + o.getWeight();
			if(o.haveEffect())
			{
				for(int i = 0; i < o.getEffect().size();i++)
				{
					if(o.getEffect().get(i).getType() == "Equipe")
					{
						o.getEffect().get(i).Activer(h,h.getEsprit());
					}
				}
			}
		}
	}
	
	public void addObjects(Objet o, int nombre)
	{
		if(this.capacity > this.weight + o.getWeight() * nombre)
		{
			if(this.objets.containsKey(o))
			{
				int temp = this.objets.get(o);
				this.objets.remove(o);
				this.objets.put(o, temp + nombre);
				this.weight = this.weight + o.getWeight() * nombre;
			}
			else
			{
				this.objets.put(o,nombre);
				this.weight = this.weight + o.getWeight()*nombre;
			}
			for(int i = 0; i < o.getEffect().size();i++)
			{
				if(o.getEffect().get(i).getType() == "Equipe")
				{
					for(int j = 0; j < nombre; j++)
					{
						o.getEffect().get(i).Activer(h,h.getEsprit());
					}
				}
			}
		
		}
	}
	
	public void addObjects(Breakables o, int nombre)
	{
		int j = 0;
		while(j < nombre && this.weight + o.getWeight() < this.capacity)
		{
			this.breakables.put(o.getName(), o);
			this.weight = this.weight + o.getWeight();
			j++;
		}
		for(int i = 0; i < o.getEffect().size();i++)
		{
			if(o.getEffect().get(i).getType() == "Equipe")
			{
				for(int k = 0; k < nombre; k++)
				{
					o.getEffect().get(i).Activer(h,h.getEsprit());
				}
			}
		}
	}
	
	public int get(Objet o)
	{
		return this.objets.get(o);
	}
	
	public Breakables get(Breakables o)
	{
		return this.breakables.get(o.getName());
	}
	
	public Breakables get(String o)
	{
		return this.breakables.get(o);
	}

	public void remove(Objet o)
	{
		int temp = this.objets.get(o);
		if(temp > 1)
		{
			this.objets.remove(o);
			this.objets.put(o, temp - 1);
		}
		else
		{
			this.objets.remove(o);
		}
		if(o.haveEffect())
		{
			for(int i = 0; i < o.getEffect().size();++i)
			{
				if(!o.getEffect().get(i).isPermanent())
				{
					o.getEffect().get(i).Desactiver(h, h.getEsprit());
				}
			}
		}
	}
	
	public void removeObjects(Objet o, int i)
	{
		int temp = this.objets.get(o);
		if(temp > i)
		{
			this.objets.remove(o);
			this.objets.put(o, temp - i);
			if(o.haveEffect())
			{
				for(int j = 0; j < o.getEffect().size();++j)
				{
					if(!o.getEffect().get(j).isPermanent())
					{
						for(int k = 0; k < i; ++k)
						{
							o.getEffect().get(j).Desactiver(h, h.getEsprit());
						}
					}
				}
			}
		}
		else
		{
			this.objets.remove(o);
			if(o.haveEffect())
			{
				for(int j = 0; j < o.getEffect().size();++j)
				{
					if(!o.getEffect().get(j).isPermanent())
					{
						for(int k = 0; k < temp; ++k)
						{
							o.getEffect().get(j).Desactiver(h, h.getEsprit());
						}
					}
				}
			}
		}
		
	}
	
	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public HashMap<Objet,Integer> getObjets() {
		return objets;
	}

	public void setObjets(HashMap<Objet,Integer> objets) {
		this.objets = objets;
	}

	public HashMap<String, Breakables> getBreakables() {
		return breakables;
	}

	public void setBreakables(HashMap<String, Breakables> breakables) {
		this.breakables = breakables;
	}

}