package Proyecto_Hibernate.Proyecto_Hibernate.repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import Proyecto_Hibernate.Proyecto_Hibernate.model.Partida;
import Proyecto_Hibernate.Proyecto_Hibernate.model.Rol;
import Proyecto_Hibernate.Proyecto_Hibernate.model.RolJugadorPartida;
import Proyecto_Hibernate.Proyecto_Hibernate.model.User;

public class RolJugadorPartidaDao extends GenericDao<RolJugadorPartida, Integer> implements IRolJugadoPartidaDao {

	public Rol descobrirRol(RolJugadorPartida vident, RolJugadorPartida destapat) throws Exception {

		if (vident.getRolJugador().getNom().equals("Vidente")) {
			if (vident.getPartida().getTorn() % 2 == 0) {
				return destapat.getRolJugador();
			}
			else {
                throw new Exception("El turno no es de dia.");
            }
		}
		return null;
	}

	public Set<RolJugadorPartida> jugadorsVius(Partida p) {

		Set<RolJugadorPartida> users = p.getRolJugadorPartida();

		Iterator<RolJugadorPartida> itUsuarios = users.iterator();

		try {

			while (itUsuarios.hasNext()) {

				if (!itUsuarios.next().isViu()) {

					itUsuarios.remove();

				}

			}

			return users;
		} catch (HibernateException e) {
			return null;
		}
	}

	public void inici(Partida p, Set<User> usuarios, Rol lobo, Rol vidente, Rol pueblo) {

		Set<RolJugadorPartida> usersRol = new HashSet<RolJugadorPartida>();
		boolean lobos = false;
		boolean vidente2 = false;
		int contadorLobos = 0;
		RolJugadorPartida rjp = null;

		int random = 0;

		Iterator<User> itUsuarios = usuarios.iterator();

		while (itUsuarios.hasNext()) {

			random = (int) (Math.random() * usuarios.size());

			if (random >= 0 && random <= 5 && !lobos) {

				rjp = new RolJugadorPartida(itUsuarios.next(), lobo, p, true);
				saveOrUpdate(rjp);

				usersRol.add(rjp);

				contadorLobos++;

				if (contadorLobos == 4) {

					lobos = true;

				}

			}

			if (random >= 6 && random <= 9) {

				if (!lobos && !vidente2 || lobos && !vidente2) {

					rjp = new RolJugadorPartida(itUsuarios.next(), vidente, p, true);
					saveOrUpdate(rjp);

					usersRol.add(rjp);

					vidente2 = true;

				}

				else {

					rjp = new RolJugadorPartida(itUsuarios.next(), pueblo, p, true);
					saveOrUpdate(rjp);

					usersRol.add(rjp);

				}

			}

		}

		p.setRolJugadorPartida(usersRol);

	}
	
}