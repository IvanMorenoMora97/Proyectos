package Proyecto_Hibernate.Proyecto_Hibernate.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "User")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;

	@Column(name = "userName")
	private String userName;

	@Column(name = "password")
	private String password;

	@Column(name = "alias")
	private String alias;

	@Column(name = "dataRegistre")
	private Date dataRegistre;

	@Column(name = "pathAvatar")
	private String pathAvatar;

	@Column(name = "percentatgeVict")
	private int percentatgeVict;

	@OneToMany(mappedBy = "messageSender", cascade = CascadeType.ALL)
	private Set<Message> messageSender;

	@OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
	private Set<Message> messageReceiver;

	@OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
	private Set<Vot> votsSender;

	@OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
	private Set<Vot> votsReceiver;

	@OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
	private Set<XatMessage> xatMessageSender;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<RolJugadorPartida> userRolJugadorPartida;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Mort> userMort;

	@ManyToMany(cascade = { CascadeType.REFRESH })
	@JoinTable(name = "UserPartida", joinColumns = { @JoinColumn(name = "IdUsername") }, inverseJoinColumns = {
			@JoinColumn(name = "IdPartida") })
	private Set<Partida> partidas = new HashSet<Partida>();

	// CONSTRUCTOR
	public User() {
	}

	public User(String username, String pw, String alias, String pAvathar) {

		this.userName = username;
		this.password = pw;
		this.alias = alias;
		this.pathAvatar = pAvathar;
		this.dataRegistre = new Date();

	}

	// GETTERS Y SETTERS
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Date getDataRegistre() {
		return dataRegistre;
	}

	public void setDataRegistre(Date dataRegistre) {
		this.dataRegistre = dataRegistre;
	}

	public String getPathAvatar() {
		return pathAvatar;
	}

	public void setPathAvatar(String pathAvatar) {
		this.pathAvatar = pathAvatar;
	}

	public int getPercentatgeVict() {
		return percentatgeVict;
	}

	public void setPercentatgeVict(int percentatgeVict) {
		this.percentatgeVict = percentatgeVict;
	}

	public Set<Message> getMessageSender() {
		return messageSender;
	}

	public void setMessageSender(Set<Message> messageSender) {
		this.messageSender = messageSender;
	}

	public Set<Message> getMessageReceiver() {
		return messageReceiver;
	}

	public void setMessageReceiver(Set<Message> messageReceiver) {
		this.messageReceiver = messageReceiver;
	}

	public Set<Vot> getVotsSender() {
		return votsSender;
	}

	public void setVotsSender(Set<Vot> votsSender) {
		this.votsSender = votsSender;
	}

	public Set<Vot> getVotsReceiver() {
		return votsReceiver;
	}

	public void setVotsReceiver(Set<Vot> votsReceiver) {
		this.votsReceiver = votsReceiver;
	}

	public Set<XatMessage> getXatMessageSender() {
		return xatMessageSender;
	}

	public void setXatMessageSender(Set<XatMessage> xatMessageSender) {
		this.xatMessageSender = xatMessageSender;
	}

	public Set<RolJugadorPartida> getUserRolJugadorPartida() {
		return userRolJugadorPartida;
	}

	public void setUserRolJugadorPartida(Set<RolJugadorPartida> userRolJugadorPartida) {
		this.userRolJugadorPartida = userRolJugadorPartida;
	}

	public Set<Mort> getUserMort() {
		return userMort;
	}

	public void setUserMort(Set<Mort> userMort) {
		this.userMort = userMort;
	}

	public Set<Partida> getPartidas() {
		return partidas;
	}

	public void setPartidas(Set<Partida> partidas) {
		this.partidas = partidas;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", alias=" + alias
				+ ", dataRegistre=" + dataRegistre + ", pathAvatar=" + pathAvatar + ", percentatgeVict="
				+ percentatgeVict + "]";
	}

}
