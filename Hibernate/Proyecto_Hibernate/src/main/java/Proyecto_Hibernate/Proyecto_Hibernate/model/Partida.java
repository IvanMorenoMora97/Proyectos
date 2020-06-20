package Proyecto_Hibernate.Proyecto_Hibernate.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Partida")
public class Partida {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;

	@Column(name = "torn")
	private int torn;

	@OneToMany(mappedBy = "partidaVot", cascade = CascadeType.ALL)
	private Set<Vot> partidaVot;

	@OneToMany(mappedBy = "xatPartida", cascade = CascadeType.ALL)
	private Set<XatMessage> xatPartida;

	@OneToMany(mappedBy = "partida", cascade = CascadeType.ALL)
	private Set<RolJugadorPartida> RolJugadorPartida;

	@ManyToMany(cascade = { CascadeType.ALL }, mappedBy = "partidas")
	private Set<User> userPartida = new HashSet<User>();

	@OneToMany(mappedBy = "partida", cascade = CascadeType.ALL)
	private Set<Mort> mortPartida = new HashSet<Mort>();

	// CONSTRUCTOR

	public Partida() {}
	
	public Partida(Set<User> userPartida) {
		super();
		this.torn = 0;
		this.userPartida = userPartida;
	}

	// GETTERS Y SETTERS
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTorn() {
		return torn;
	}

	public void setTorn(int torn) {
		this.torn = torn;
	}

	public Set<Vot> getPartidaVot() {
		return partidaVot;
	}

	public void setPartidaVot(Set<Vot> partidaVot) {
		this.partidaVot = partidaVot;
	}

	public Set<XatMessage> getXatPartida() {
		return xatPartida;
	}

	public void setXatPartida(Set<XatMessage> xatPartida) {
		this.xatPartida = xatPartida;
	}

	public Set<RolJugadorPartida> getRolJugadorPartida() {
		return RolJugadorPartida;
	}

	public void setRolJugadorPartida(Set<RolJugadorPartida> rolJugadorPartida) {
		RolJugadorPartida = rolJugadorPartida;
	}

	public Set<User> getUserPartida() {
		return userPartida;
	}

	public void setUserPartida(Set<User> userPartida) {
		this.userPartida = userPartida;
	}

	public Set<Mort> getMortPartida() {
		return mortPartida;
	}

	public void setMortPartida(Set<Mort> mortPartida) {
		this.mortPartida = mortPartida;
	}

	@Override
	public String toString() {
		return "Partida [id=" + id + ", torn=" + torn + ", partidaVot=" + partidaVot + ", xatPartida=" + xatPartida.toString()
				+ ", RolJugadorPartida=" + RolJugadorPartida.toString() + ", userPartida=" + userPartida.toString()
				+ ", mortPartida=" + mortPartida.toString() + "]";
	}

}