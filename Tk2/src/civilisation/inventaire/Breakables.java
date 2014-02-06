package civilisation.inventaire;

import java.util.ArrayList;

import civilisation.effects.Effect;

public class Breakables extends Objet{

	private int life;
	
	public Breakables(int weight, String name,int life)
	{
		super(weight, name);
		// TODO Auto-generated constructor stub
		this.setLife(life);
	}
	
	public Breakables(int weight, String name,ArrayList<Effect> effets,int life)
	{
		super(weight, name,effets);
		// TODO Auto-generated constructor stub
		this.setLife(life);
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

}
