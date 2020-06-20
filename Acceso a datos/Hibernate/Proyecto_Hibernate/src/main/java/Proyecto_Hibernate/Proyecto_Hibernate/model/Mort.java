package Proyecto_Hibernate.Proyecto_Hibernate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Mort")
public class Mort {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;

	@ManyToOne
	@JoinColumn(name = "user")
	private User user;

	@ManyToOne
	@JoinColumn(name = "partida")
	private Partida partida;

	@Column(name = "torn")
	private int torn;

	// CONSTRUCTOR

	public Mort() {
		super();
	}

	public Mort(User user, Partida partida, int torn) {
		super();
		this.user = user;
		this.partida = partida;
		this.torn = torn;
	}

	// GETTERS Y SETTERS
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
	}

	public int getTorn() {
		return torn;
	}

	public void setTorn(int torn) {
		this.torn = torn;
	}

	@Override
	public String toString() {
		return "Mort [id=" + id + ", user=" + user.getUserName() + ", partida=" + partida.getId() + ", torn=" + torn + "]";
	}

}
