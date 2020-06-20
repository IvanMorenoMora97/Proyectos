package Proyecto_Hibernate.Proyecto_Hibernate.repository;

import java.util.List;

import Proyecto_Hibernate.Proyecto_Hibernate.model.XatMessage;


public interface IXatMessageDao extends IGenericDao<XatMessage,Integer>{

	void saveOrUpdate(XatMessage m);

	XatMessage get(Integer id);

	List<XatMessage> list();

	void delete(Integer id);

}
