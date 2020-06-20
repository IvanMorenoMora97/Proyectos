package Proyecto_Hibernate.Proyecto_Hibernate.repository;

import java.util.List;

import Proyecto_Hibernate.Proyecto_Hibernate.model.Mort;


public interface IMortDao extends IGenericDao<Mort,Integer>{

	void saveOrUpdate(Mort m);

	Mort get(Integer id);

	List<Mort> list();

	void delete(Integer id);

}
