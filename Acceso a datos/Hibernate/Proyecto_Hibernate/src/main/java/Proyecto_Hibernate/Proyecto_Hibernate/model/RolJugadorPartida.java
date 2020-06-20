package Proyecto_Hibernate.Proyecto_Hibernate.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "RolJugadorPartida")
public class RolJugadorPartida {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;

	@ManyToOne
	@JoinColumn(name = "user")
	private User user;

	@ManyToOne
	@JoinColumn(name = "rolJugador")
	private Rol rolJugador;

	@ManyToOne
	@JoinColumn(name = "partida")
	private Partida partida;

	private boolean viu;

	// CONSTRUCTOR

	public RolJugadorPartida() {
	}

	public RolJugadorPartida(User user, Rol rolJugador, Partida partida, boolean viu) {
		super();
		this.user = user;
		this.rolJugador = rolJugador;
		this.partida = partida;
		this.viu = viu;
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

	public Rol getRolJugador() {
		return rolJugador;
	}

	public void setRolJugador(Rol rolJugador) {
		this.rolJugador = rolJugador;
	}

	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
	}

	public boolean isViu() {
		return viu;
	}

	public void setViu(boolean viu) {
		this.viu = viu;
	}

	@Override
	public String toString() {
		return "RolJugadorPartida [id=" + id + ", user=" + user.getUserName() + ", rolJugador=" + rolJugador.getNom() + ", partida=" + partida.getId()
				+ ", viu=" + viu + "]";
	}

}
