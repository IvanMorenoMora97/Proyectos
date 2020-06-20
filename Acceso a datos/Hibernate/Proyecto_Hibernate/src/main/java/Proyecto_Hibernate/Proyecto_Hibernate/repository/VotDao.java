package Proyecto_Hibernate.Proyecto_Hibernate.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import Proyecto_Hibernate.Proyecto_Hibernate.model.Vot;

public class VotDao extends GenericDao<Vot, Integer> implements IVotDao {

	public List<Vot> getHistorial(int torn) {
		List<Vot> vots = new ArrayList<Vot>();
		
		Session session = sessionFactory.getCurrentSession();

		try {
			if (torn % 2 != 0) {
				session.beginTransaction();
				vots = this.list();

				Iterator<Vot> itVot = vots.iterator();
				while (itVot.hasNext()) {
					if (itVot.next().getTorn() != torn) {
						itVot.remove();
					}
				}
				session.getTransaction().commit();
				return vots;
			}
			else
				return null;
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
