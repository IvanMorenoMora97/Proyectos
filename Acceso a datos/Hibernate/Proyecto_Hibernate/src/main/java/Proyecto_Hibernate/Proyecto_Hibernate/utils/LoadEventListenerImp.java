package Proyecto_Hibernate.Proyecto_Hibernate.utils;

import org.hibernate.HibernateException;
import org.hibernate.event.spi.LoadEvent;
import org.hibernate.event.spi.LoadEventListener;


public class LoadEventListenerImp implements LoadEventListener {
   
   private static final long serialVersionUID = 1L;

public void onLoad(LoadEvent event, LoadType loadType) throws HibernateException {
	// TODO Auto-generated method stub
	
}
   
   /*@Override
   public void onLoad(LoadEvent e, LoadType type) throws HibernateException {
     Object obj = e.getResult();
      if (obj instanceof Profesor) {
    	 Profesor p = (Profesor) obj;
        
      }
   }*/

}