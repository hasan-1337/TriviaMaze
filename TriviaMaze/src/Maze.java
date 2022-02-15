import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class Maze extends TriviaMaze {
	
	public Maze(final Scanner input) {
    	
    	final HashMap<Integer, String> list = select("SELECT id, name FROM saves");
    	final Iterator<Integer> itr = list.keySet().iterator();
    	
    	while (itr.hasNext()) {
    		final int id = itr.next();
    		System.out.println("Id: " + id + "\tName: " + list.get(id));
    	}
	}
	


}
