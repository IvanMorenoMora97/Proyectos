package Proyecto_Hibernate.Proyecto_Hibernate.utils;

import org.hibernate.HibernateException;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.hibernate.event.spi.SaveOrUpdateEventListener;



public class SaveUpdateEventListenerImp implements SaveOrUpdateEventListener {
   
   private static final long serialVersionUID = 1L;

  // @Override
   public void onSaveOrUpdate(SaveOrUpdateEvent e) throws HibernateException {

	  /*System.out.println("SOME SHIT HAPPENED");
      Object obj = e.getEntity();
      if (obj instanceof Profesor) {
    	  Profesor p = (Profesor) obj;
          System.out.println("Hola Profesor "+p.getNombre());
      }*/
   }
}
