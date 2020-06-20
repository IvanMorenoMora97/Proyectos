package Proyecto_Hibernate.Proyecto_Hibernate.repository;

import java.util.List;

import Proyecto_Hibernate.Proyecto_Hibernate.model.Rol;


public interface IRolDao extends IGenericDao<Rol,Integer>{

	void saveOrUpdate(Rol m);

	Rol get(Integer id);

	List<Rol> list();

	void delete(Integer id);

}
