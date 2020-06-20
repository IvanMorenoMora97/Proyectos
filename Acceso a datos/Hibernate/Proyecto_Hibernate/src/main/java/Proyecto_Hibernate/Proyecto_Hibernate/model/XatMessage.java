package Proyecto_Hibernate.Proyecto_Hibernate.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "XatMessage")
public class XatMessage {

	// id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;

	// destinatario
	@ManyToOne
	@JoinColumn(name = "sender")
	private User sender;

	// partida
	@ManyToOne
	@JoinColumn(name = "xatPartida")
	private Partida xatPartida;

	// contenido
	@Lob
	@Column(name = "contenido")
	private String contenido;

	// date
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	// CONSTRUCTOR

	public XatMessage() {
	}

	public XatMessage(User sender, Partida xatPartida, String contenido, Date date) {
		super();
		this.sender = sender;
		this.xatPartida = xatPartida;
		this.contenido = contenido;
		this.date = date;
	}

	// GETTERS Y SETTERS
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public Partida getXatPartida() {
		return xatPartida;
	}

	public void setXatPartida(Partida partida) {
		this.xatPartida = partida;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
    public String toString() {
        return "XatMessage [id=" + id + ", sender=" + sender.getUserName() + ", xatPartida=" + xatPartida.getId() + ", contenido=" + contenido
                + ", date=" + date + "]";
    }

}
