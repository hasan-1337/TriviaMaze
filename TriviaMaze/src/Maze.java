import java.util.HashMap;
import java.util.Iterator;

public class Maze extends TriviaMaze {
	
	public Maze() {
    	
    	final HashMap<Integer, String> list = select("SELECT id, name FROM saves");
    	final Iterator<Integer> itr = list.keySet().iterator();
    	
    	while (itr.hasNext()) {
    		final int id = itr.next();
    		System.out.println("Id: " + id + "\tName: " + list.get(id));
    	}
	}
	


}
