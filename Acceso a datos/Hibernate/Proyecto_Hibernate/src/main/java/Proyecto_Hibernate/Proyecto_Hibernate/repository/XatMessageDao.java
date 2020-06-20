package Proyecto_Hibernate.Proyecto_Hibernate.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import Proyecto_Hibernate.Proyecto_Hibernate.model.Partida;
import Proyecto_Hibernate.Proyecto_Hibernate.model.RolJugadorPartida;
import Proyecto_Hibernate.Proyecto_Hibernate.model.User;
import Proyecto_Hibernate.Proyecto_Hibernate.model.XatMessage;

public class XatMessageDao extends GenericDao<XatMessage, Integer> implements IXatMessageDao {

	public Set<XatMessage> getXat(Partida p) throws Exception {
        Set<XatMessage> missatges = p.getXatPartida();
        try {
            if(missatges.size()==0) {
                throw new Exception("No hi ha missatges en aquesta partida.");
            }
            else {
                for (XatMessage m : missatges) {
                    if (!m.getXatPartida().equals(p)) {
                        missatges.remove(m);
                    }
                }
                return missatges;
            }
        } catch (HibernateException e) {
            return null;
        }
    }

	
	public Set<XatMessage> getXatLlops(RolJugadorPartida u) {
        Set<XatMessage> missatges = u.getPartida().getXatPartida();
        Set<RolJugadorPartida> lobos = u.getPartida().getRolJugadorPartida();
        Set<XatMessage> missatges2 = new HashSet<XatMessage>();
        try {
            if (u.getRolJugador().getNom().equals("Lobo")) {
                if (u.getPartida().getTorn() % 2 == 0) {
                    for (XatMessage m : missatges) {
                        for (RolJugadorPartida rjp : lobos) {
                            if (!m.getSender().equals(rjp.getUser())) {
                                missatges2.add(m);
                            }
                        }
                    }
                }
            }

            return missatges2;
        } catch (HibernateException e) {
            return null;
        }
    }

	public XatMessage escriuMissatgeXat(RolJugadorPartida sender, String contenido, Date date) {

		XatMessage xm = new XatMessage(sender.getUser(), sender.getPartida(), contenido, date);

		Session session = sessionFactory.getCurrentSession();

		try {
			session.beginTransaction();
			session.saveOrUpdate(xm);
			session.getTransaction().commit();

			return xm;
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

	public XatMessage escriuMissatgeLlop(RolJugadorPartida sender, String contenido, Date date) {

		Session session = sessionFactory.getCurrentSession();

		try {
			if (sender.getRolJugador().getNom().equals("Lobo") && sender.getPartida().getTorn() % 2 == 0) {
				XatMessage xm = new XatMessage(sender.getUser(), sender.getPartida(), contenido, date);
				session.beginTransaction();
				session.saveOrUpdate(xm);
				session.getTransaction().commit();
				return xm;
			}
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