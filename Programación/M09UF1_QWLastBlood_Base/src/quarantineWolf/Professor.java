package quarantineWolf;

import java.io.Serializable;

public class Professor extends Personatge implements Abatible, Serializable{

	public Professor(String nom) {
		super(nom);
	}
	
	@Override
	public String toString(){
		return ("P: "  + super.toString());
	}
	//no cal fer res aquí
	@Override
	public int compare(Personatge g1, Personatge g2) {
		return 0; 	
}

}
