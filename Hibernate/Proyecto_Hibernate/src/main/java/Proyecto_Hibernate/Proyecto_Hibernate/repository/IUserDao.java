package Proyecto_Hibernate.Proyecto_Hibernate.repository;

import java.util.List;

import Proyecto_Hibernate.Proyecto_Hibernate.model.User;


public interface IUserDao extends IGenericDao<User,Integer>{

	void saveOrUpdate(User m);

	User get(Integer id);

	List<User> list();

	void delete(Integer id);

}
