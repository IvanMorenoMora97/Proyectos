package Proyecto_Hibernate.Proyecto_Hibernate.repository;

import java.util.List;

import Proyecto_Hibernate.Proyecto_Hibernate.model.Vot;


public interface IVotDao extends IGenericDao<Vot,Integer>{

	void saveOrUpdate(Vot m);

	Vot get(Integer id);

	List<Vot> list();

	void delete(Integer id);

}
