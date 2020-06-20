package quarantineWolf;

import java.io.Serializable;

public class CapEstudis extends Personatge implements Abatible, Serializable{
	
	public CapEstudis(String nom) {
		super(nom);
	}
	
	public void purgar(Poble p, int t) {
		//TO DO
	}
	
	@Override
	public String toString(){
		return ("C: "  + super.toString());
	}

	//no cal fer res aquí
	@Override
	public int compare(Personatge g1, Personatge g2) {
		return 0;
	}

}
