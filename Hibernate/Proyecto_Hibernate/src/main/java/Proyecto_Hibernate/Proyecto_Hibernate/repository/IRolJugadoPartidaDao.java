package Proyecto_Hibernate.Proyecto_Hibernate.repository;

import java.util.List;

import Proyecto_Hibernate.Proyecto_Hibernate.model.RolJugadorPartida;


public interface IRolJugadoPartidaDao extends IGenericDao<RolJugadorPartida,Integer>{

	void saveOrUpdate(RolJugadorPartida m);

	RolJugadorPartida get(Integer id);

	List<RolJugadorPartida> list();

	void delete(Integer id);

}
