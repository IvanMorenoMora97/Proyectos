package quarantineWolf;

import java.io.Serializable;

public class Alumne extends Personatge implements Abatible, Resucitable, Serializable{
	
	public Alumne(String nom) {
		this.nom = nom;
	}
	
	@Override
	public String toString(){
		return ("A: "  + super.toString());
	}

	//no cal fer res aquí
	@Override
	public int compare(Personatge o1, Personatge o2) {
		return 0;
	}

}
