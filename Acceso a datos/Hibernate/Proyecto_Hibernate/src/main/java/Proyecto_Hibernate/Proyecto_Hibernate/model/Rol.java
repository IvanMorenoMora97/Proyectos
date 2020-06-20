package Proyecto_Hibernate.Proyecto_Hibernate.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Rol")
public class Rol {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;

	@Column(name = "nom")
	private String nom;

	@Column(name = "frequencia")
	private int freq;

	@Column(name = "pathImg")
	private String pathImg;

	@Lob
	@Column(name = "descripcio")
	private String descripcio;

	@OneToMany(mappedBy = "rolJugador", cascade = CascadeType.ALL)
	private Set<RolJugadorPartida> rolJugador;

	// CONSTRUCTOR

	public Rol() {
	}

	public Rol(String nom, int freq, String pathImg, String descripcio, Set<RolJugadorPartida> rolJugador) {
		super();
		this.nom = nom;
		this.freq = freq;
		this.pathImg = pathImg;
		this.descripcio = descripcio;
		this.rolJugador = rolJugador;
	}

	// PRUEBA
	public Rol(String nom, int freq, String pathImg, String descripcio) {
		super();
		this.nom = nom;
		this.freq = freq;
		this.pathImg = pathImg;
		this.descripcio = descripcio;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<RolJugadorPartida> getRolJugador() {
		return rolJugador;
	}

	public void setRolJugador(Set<RolJugadorPartida> rolJugador) {
		this.rolJugador = rolJugador;
	}

	// GETTERS Y SETTERS
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getFreq() {
		return freq;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	public String getPathImg() {
		return pathImg;
	}

	public void setPathImg(String pathImg) {
		this.pathImg = pathImg;
	}

	public String getDescripcio() {
		return descripcio;
	}

	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}

	@Override
	public String toString() {
		return "Rol [id=" + id + ", nom=" + nom + ", freq=" + freq + ", pathImg=" + pathImg + ", descripcio="
				+ descripcio + ", rolJugador=" + rolJugador + "]";
	}

}
