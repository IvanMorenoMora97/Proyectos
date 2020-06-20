package Proyecto_Hibernate.Proyecto_Hibernate.repository;

import java.util.List;

import Proyecto_Hibernate.Proyecto_Hibernate.model.Message;


public interface IMessageDao extends IGenericDao<Message,Integer>{

	void saveOrUpdate(Message m);

	Message get(Integer id);

	List<Message> list();

	void delete(Integer id);

}
