package Proyecto_Hibernate.Proyecto_Hibernate.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import Proyecto_Hibernate.Proyecto_Hibernate.model.Message;
import Proyecto_Hibernate.Proyecto_Hibernate.model.Message.Tipo;
import Proyecto_Hibernate.Proyecto_Hibernate.model.Partida;
import Proyecto_Hibernate.Proyecto_Hibernate.model.RolJugadorPartida;
import Proyecto_Hibernate.Proyecto_Hibernate.model.User;

public class UserDao extends GenericDao<User, Integer> implements IUserDao {

	public User registre(String username, String pw, String alias, String pathAvatar) {
		User u = new User(username, pw, alias, pathAvatar);

		Session session = sessionFactory.getCurrentSession();

		try {
			session.beginTransaction();
			session.saveOrUpdate(u);
			session.getTransaction().commit();
			return u;
		} catch (HibernateException e) {
			e.printStackTrace();
			if (session != null && session.getTransaction() != null) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				session.getTransaction().rollback();
			}
			e.printStackTrace();
			return null;

		}
	}

	public boolean login(String username, String pw) {
		//Mirem cada cop per llistar i així ens assegurem que no ens deixem cap usuari.
		List<User> usuarios=Llistar();
		for (int i = 0; i < usuarios.size(); i++) {
			if (usuarios.get(i).getUserName().equals(username)) {
				if (usuarios.get(i).getPassword().equals(pw)) {
					return true;
				}
			}
		}

		return false;
	}

	public List<User> Llistar() {

		List<User> users = new ArrayList<User>();
		
		Session session = sessionFactory.getCurrentSession();

		try {
			session.beginTransaction();
			users = this.list();
			session.getTransaction().commit();
			return users;
		} catch (HibernateException e) {
			e.printStackTrace();
			if (session != null && session.getTransaction() != null) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				session.getTransaction().rollback();
			}
			e.printStackTrace();
			return null;

		}
	}
	
	public User getUser(String username) {
		//Mirem cada cop per llistar i així ens assegurem que no ens deixem cap usuari.
		List<User> usuarios=Llistar();
		for (int i = 0; i < usuarios.size(); i++) {
			if (usuarios.get(i).getUserName().equals(username)) {
				
				return usuarios.get(i);
				
			}
		}

		return null;
	}

	

}