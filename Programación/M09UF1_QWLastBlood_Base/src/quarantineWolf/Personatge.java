package quarantineWolf;

import java.io.Serializable;
import java.util.Comparator;


// Classe base per tots els Personatges que hi ha.

public abstract class Personatge implements Comparable<Personatge>, Comparator<Personatge>, Serializable {
	protected String nom;
	protected int tornMort = 0;
	protected int vegadesVotat = 0;
	protected int vegadesResucitat = 0;

	Personatge(){
		nom = "noName";
	}

	public int getVegadesResucitat() {
		return vegadesResucitat;
	}

	public void setVegadesResucitat(int vegadesResucitat) {
		this.vegadesResucitat = vegadesResucitat;
	}
	public void incVegadesResucitat() {
		vegadesResucitat ++;
	}
	
	
	Personatge(String nom){
		this.nom = nom;
	}
	
	public int getVegadesVotat() {
		return vegadesVotat;
	}

	public int getVegadesCandidat() {
		return vegadesVotat;
	}

	public void setVegadesVotat(int vegadesVotat) {
		this.vegadesVotat = vegadesVotat;
	}

	public void incVotat() {
		vegadesVotat ++;
	}
	
	public int getTornMort() {
		return tornMort;
	}

	public void setTornMort(int tornMort) {
		this.tornMort = tornMort;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getNom(){
		return nom;
	}
	
	@Override
	public String toString() {
			return nom;
	}

	public String toStringAmpliat() {
		if (tornMort == 0)
			return nom;
		else
			return nom + "(" + tornMort + ")";
	}
	
	public String toStringVots() {
		return nom + "(" + vegadesVotat + ")";
	}
	
	public  void resetVotat() {
		vegadesVotat = 0;
	}
	
	@Override 
	public int compareTo(Personatge p) {
		//TO DO  
		
		return 0;
	

	}
}
