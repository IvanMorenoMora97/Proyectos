package Proyecto_Hibernate.Proyecto_Hibernate.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import Proyecto_Hibernate.Proyecto_Hibernate.model.Partida;
import Proyecto_Hibernate.Proyecto_Hibernate.model.Rol;
import Proyecto_Hibernate.Proyecto_Hibernate.model.RolJugadorPartida;
import Proyecto_Hibernate.Proyecto_Hibernate.model.User;


public class RolDao extends GenericDao<Rol, Integer> implements IRolDao{

	public List<Rol> jugadorsVius(Partida p) {
		
		Set<RolJugadorPartida> users = p.getRolJugadorPartida();
		List<Rol> rols = new ArrayList<Rol>();

		try {
			for (RolJugadorPartida u : users) {
				if (u.isViu()) {
					rols.add(u.getRolJugador());
				}
			}

			return rols;
		} catch (HibernateException e) {

			return null;
		}
	

	}

	
}