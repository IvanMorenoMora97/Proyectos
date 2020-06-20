package Proyecto_Hibernate.Proyecto_Hibernate.repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import Proyecto_Hibernate.Proyecto_Hibernate.model.Message;
import Proyecto_Hibernate.Proyecto_Hibernate.model.Message.Tipo;
import Proyecto_Hibernate.Proyecto_Hibernate.model.User;

public class MessageDao extends GenericDao<Message, Integer> implements IMessageDao {

	public Set<Message> getMissatges(String username) {

		Set<Message> messages = new HashSet<Message>();
		List<Message> messaux = this.Llistar();

		for (Message m : messaux) {
			if (m.getSender().getUserName().equals(username) || m.getReceiver().getUserName().equals(username)) {
				messages.add(m);
				System.out.println(m.toString());
			}
		}

		return messages;
	}

	public boolean enviaMissatge(User usernameSender, User usernameReceiver, Tipo type, String content) {

		Message missatge = new Message(usernameSender, usernameReceiver, type, content);

		Session session = sessionFactory.getCurrentSession();

		try {
			session.beginTransaction();
			session.saveOrUpdate(missatge);
			session.getTransaction().commit();
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			if (session != null && session.getTransaction() != null) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				session.getTransaction().rollback();
			}
			e.printStackTrace();
			return false;

		}
	}

	public List<Message> Llistar() {

		List<Message> messages = new ArrayList<Message>();

		// EJECUTAR LA SQL MISSATGE.
		Session session = sessionFactory.getCurrentSession();

		try {
			session.beginTransaction();
			messages = this.list();
			session.getTransaction().commit();

			return messages;
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
}
