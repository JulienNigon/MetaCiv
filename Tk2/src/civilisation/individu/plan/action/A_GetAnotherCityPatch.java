package civilisation.individu.plan.action;

import civilisation.individu.Humain;
import edu.turtlekit2.kernel.agents.Turtle;
import edu.turtlekit2.kernel.environment.Patch;

public class A_GetAnotherCityPatch extends Action {
	
	public Action effectuer(Humain h)
	{
		int rand = (int)Math.random()* h.TurtlesWithRole("Communaute").size();
		h.setCible(h.TurtlesWithRole("Communaute").get(rand).position);
		return nextAction;
	}
	
	@Override
	public String getInfo() {
		return super.getInfo() + " Met la cible de Allervers sur une ville au hasard.<html>";
	}

}
