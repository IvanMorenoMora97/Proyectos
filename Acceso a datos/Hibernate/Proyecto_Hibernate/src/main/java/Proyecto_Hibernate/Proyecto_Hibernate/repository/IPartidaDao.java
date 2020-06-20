package Proyecto_Hibernate.Proyecto_Hibernate.repository;

import java.util.List;

import Proyecto_Hibernate.Proyecto_Hibernate.model.Partida;


public interface IPartidaDao extends IGenericDao<Partida,Integer>{

	void saveOrUpdate(Partida m);

	Partida get(Integer id);

	List<Partida> list();

	void delete(Integer id);

}
