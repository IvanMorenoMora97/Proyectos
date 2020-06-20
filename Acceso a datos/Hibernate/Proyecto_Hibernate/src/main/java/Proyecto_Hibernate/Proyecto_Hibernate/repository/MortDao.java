package Proyecto_Hibernate.Proyecto_Hibernate.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import Proyecto_Hibernate.Proyecto_Hibernate.model.Mort;
import Proyecto_Hibernate.Proyecto_Hibernate.model.RolJugadorPartida;
import Proyecto_Hibernate.Proyecto_Hibernate.model.User;

public class MortDao extends GenericDao<Mort, Integer> implements IMortDao {

	public Mort matar(RolJugadorPartida usuario) {

		Mort m = new Mort(usuario.getUser(), usuario.getPartida(), usuario.getPartida().getTorn());

		Session session = sessionFactory.getCurrentSession();

		try {
			session.beginTransaction();
			session.saveOrUpdate(m);
			session.getTransaction().commit();

			return m;
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

	public List<Mort> getMorts(int torn) {
		List<Mort> morts = new ArrayList<Mort>();

		Session session = sessionFactory.getCurrentSession();

		try {
			session.beginTransaction();
			morts = this.list();
			Iterator<Mort> itVot = morts.iterator();
			while (itVot.hasNext()) {
				System.out.println(itVot.toString());
				if (itVot.next().getTorn() != torn) {
					itVot.remove();
				}
			}
			session.getTransaction().commit();
			return morts;

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
