package Proyecto_Hibernate.Proyecto_Hibernate.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Message")
public class Message {

	// id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;

	@ManyToOne
	@JoinColumn(name = "messageSender")
	private User messageSender;

	@ManyToOne
	@JoinColumn(name = "receiver")
	private User receiver;

	// tipo del contenido
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo")
	private Tipo tipo;

	// contenido
	@Lob
	@Column(name = "contingut")
	private String contingut;

	// date
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	public enum Tipo {
		// TEXT("TEXT"), IMATGE("IMATGE"), AUDIO("AUDIO"), VIDEO("VIDEO");
		TEXT, IMATGE, AUDIO, VIDEO;

		/*
		 * private final String value;
		 * 
		 * Tipo(String valor) {
		 * 
		 * this.value = valor;
		 * 
		 * }
		 * 
		 * public String getValue() { return value; }
		 */
	}

	// CONSTRUCTOR
	public Message() {
	}

	public Message(User sender, User receiver, Tipo tipo, String contingut) {
		super();
		this.messageSender = sender;
		this.receiver = receiver;
		this.tipo = tipo;
		this.contingut = contingut;
		this.date = new Date();
	}

	// METODOS
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getSender() {
		return messageSender;
	}

	public void setSender(User sender) {
		this.messageSender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public String getContingut() {
		return contingut;
	}

	public void setContingut(String contingut) {
		this.contingut = contingut;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", sender=" + messageSender.getUserName() + ", receiver=" + receiver.getUserName()
				+ ", tipo=" + tipo + ", contingut=" + contingut + ", date=" + date + "]";
	}

}
