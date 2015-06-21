import java.awt.Color;
import info.gridworld.actor.Actor;
import info.gridworld.grid.Location;
import info.gridworld.grid.Grid;
import java.util.ArrayList;
public class Pig extends Actor
{
	public Pig()
	{
		setColor(Color.GREEN);
		setDirection(Location.NORTH);
	}
	public void act()
	{
		if(!findBird())
			if(!findEgg())
			{
				randomMove();
			}
	}
	public boolean findBird()
	{
		return false;
	}
	public boolean findEgg()
	{
		for(Actor a: getGrid().getNeighbors(getLocation()))
		{
				if(a instanceof Egg)
				{
					moveAroundEgg(a);
					return true;
				}
		}
		ArrayList<Actor> eggs = new ArrayList<Actor>();
		ArrayList<Location> locs = getGrid().getOccupiedLocations();
		for(Location a : locs)
		{
			if(getGrid().get(a) instanceof Egg)
				eggs.add(getGrid().get(a));
		}
		if(eggs.size() == 0)
			return false;
		moveToEgg(eggs);
		return true;
	}
	public void moveToEgg(ArrayList<Actor> eggs)
	{
		int closestDistance = distanceFrom(getLocation(),eggs.get(0).getLocation()); 
		Actor temp = eggs.get(0);
		ArrayList<Actor> tied = new ArrayList<Actor>();
		for(int i = 1; i < eggs.size(); i++)
		{
			int d = distanceFrom(getLocation(),eggs.get(i).getLocation());
			if( d < closestDistance)
			{
				closestDistance = d;
				temp = eggs.get(i);
				tied.clear();
			}
			else if(d == closestDistance)
			{
				tied.add(temp);
				tied.add(eggs.get(i));
			}
		}
		if(tied.size() > 0)
		{
			int r = (int)(Math.random()*2);
			temp = eggs.get(r);
		}
		int dir = getLocation().getDirectionToward(temp.getLocation());
		moveTo(getLocation().getAdjacentLocation(dir));
	}
	public void randomMove()
    {
		ArrayList<Location> locs = getGrid().getEmptyAdjacentLocations(getLocation());
        int n = locs.size();
        Location loc;
        if (n == 0)
            loc = getLocation();
        int r = (int) (Math.random() * n);
        loc = locs.get(r);
        moveTo(loc);
    }
    public int distanceFrom(Location loc1, Location loc2)
	{
		int x1 = loc1.getRow();
		int y1 = loc1.getCol();
		int x2 = loc2.getRow();
		int y2 = loc2.getCol();
		double dist = Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2)) + .5;
		return (int)Math.floor(dist);
	}
	public void moveAroundEgg(Actor a)
	{
		ArrayList<Location> locs = getGrid().getEmptyAdjacentLocations(a.getLocation());
		if(locs.size() > 0)
		{	
			int ran = (int)(Math.random()*locs.size());
			moveTo(locs.get(ran));
		}
	}
 
}