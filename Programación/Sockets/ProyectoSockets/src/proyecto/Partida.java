package proyecto;

import java.util.ArrayList;

public class Partida {

	private int idpartida;
	private ArrayList<Cliente> personajes;
	private ArrayList<Cliente> personajesvivos;
	// Noche 1 o dia 0
	private int turno;

	public Partida(int idpartida, ArrayList<Cliente> personajes, ArrayList<Cliente> personajesvivos, int turno) {
		super();
		this.idpartida = idpartida;
		this.personajes = personajes;
		this.personajesvivos = personajesvivos;
		this.turno = turno;
	}

	public int getIdpartida() {
		return idpartida;
	}

	public void setIdpartida(int idpartida) {
		this.idpartida = idpartida;
	}

	public ArrayList<Cliente> getPersonajes() {
		return personajes;
	}

	public void setPersonajes(ArrayList<Cliente> personajes) {
		this.personajes = personajes;
	}

	public ArrayList<Cliente> getPersonajesvivos() {
		return personajesvivos;
	}

	public void setPersonajesvivos(ArrayList<Cliente> personajesvivos) {
		this.personajesvivos = personajesvivos;
	}

	public int getTurno() {
		return turno;
	}

	public void setTurno(int turno) {
		this.turno = turno;
	}

}
