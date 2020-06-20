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
@Table(name = "Vot")
public class Vot {

	// id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;

	// partida
	@ManyToOne
	@JoinColumn(name = "partidaVot")
	private Partida partidaVot;

	// torn
	@Column(name = "torn")
	private int torn;

	@ManyToOne
	@JoinColumn(name = "sender")
	private User sender;

	@ManyToOne
	@JoinColumn(name = "receiver")
	private User receiver;

	// CONSTRUCTOR

	public Vot() {
	}

	public Vot(Partida partidaVot, int torn, User sender, User receiver) {
		super();
		this.partidaVot = partidaVot;
		this.torn = torn;
		this.sender = sender;
		this.receiver = receiver;
	}

	// GETTERS Y SETTERS

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Partida getPartidaVot() {
		return partidaVot;
	}

	public void setPartidaVot(Partida partidaVot) {
		this.partidaVot = partidaVot;
	}

	public int getTorn() {
		return torn;
	}

	public void setTorn(int torn) {
		this.torn = torn;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	@Override
	public String toString() {
		return "Vot [id=" + id + ", partidaVot=" + partidaVot.getId() + ", torn=" + torn + ", sender=" + sender.getUserName() + ", receiver="
				+ receiver.getUserName() + "]";
	}

}
