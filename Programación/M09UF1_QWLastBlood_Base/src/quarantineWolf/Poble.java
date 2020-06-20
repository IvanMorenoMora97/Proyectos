package quarantineWolf;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Random;

public class Poble implements Serializable{
	private List<Personatge> personatges = new ArrayList <Personatge>();
	private Deque<Personatge> morts = new ArrayDeque<Personatge>();
	private Integer resucitats=0;
		
	private static final Random random = new Random();
	
	public List<Personatge> getPersonatges() {
		return personatges;
	}

	public void afegirPersonatge (Personatge p) {
		this.personatges.add(p);
	}
	public void setPersonatges(List<Personatge> personatges) {
		this.personatges = personatges;
	}

	public Deque<Personatge> getMorts() {
		return morts;
	}

	public void setMorts(Deque<Personatge> morts) {
		this.morts = morts;
	}

	public boolean add(Personatge e) {
		personatges.add(personatges.size(), e);
		return true;
	}
	
	public Integer getResucitats(){
		return resucitats;
	}
	
	public void setResucitats(Integer valor){
		resucitats = 0;
	}
	
	public void resucita (){
		resucitats ++;
	}
	
	public void add(int index, Personatge e) {
		personatges.add(index, e);
	}
	
	public List<Personatge> getHabitants(){
		return this.personatges;
	}
			
	public void afegirAleatori(Personatge h) {

		add(random.nextInt(personatges.size()+1), h);
	}
	
	@Override
	public String toString() {
		String s = "";
		
		for (Personatge e : personatges)
			s += e.toString() + "\n";
		
		return s;
	}
	
	public String situacio(){
		int c=0;
		int m=0;
		int z=0;
		int v=0;
		for (Personatge actiu : personatges) {
				if (actiu instanceof CapEstudis) 	c++;
				if (actiu instanceof Professor) 	m++;
				if (actiu instanceof Alumne) 		v++;
				if (actiu instanceof Infiltrat) 	z++;
		}

		return "Alumnes " + v + " Infiltrats " + z + " Professors " + m + " CapEstudis " + c;
	}
}
