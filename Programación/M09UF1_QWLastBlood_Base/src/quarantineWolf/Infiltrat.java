package quarantineWolf;

import java.io.Serializable;

//classe dels "malotes"
public class Infiltrat extends Personatge implements Resucitable, Serializable{

	int insignies = 0;
	public int getInsignies() {
		return insignies;
	}
	public void setInsignies(int insignies) {
		this.insignies = insignies;
	}
	public void incInsignies() {
		insignies ++;
	}

	public Infiltrat(String nom) {
		this.nom = nom;
	}	
	
	
	@Override
	public String toString(){
		return ("I: "  + super.toString());
	}
	//no cal fer res aquí
	@Override
	public int compare(Personatge g1, Personatge g2) {
		return 0; 	//això es fa per si en un futur s'afegeix alguna classe nova
}

}
