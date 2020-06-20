package Proyecto_Hibernate.Proyecto_Hibernate.utils;

import java.io.Serializable;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

public class LoggingInterceptor extends EmptyInterceptor {

   private static final long serialVersionUID = 1L;
   // Define a static logger
   //private static Logger logger = LogManager.getLogger(LoggingInterceptor.class);

   @Override
   public boolean onSave(Object entity, Serializable id, Object[] state,
            String[] propertyNames, Type[] types) {
     System.out.println("onSave method is called.");
      /*if (entity instanceof Profesor) {
    	  Profesor p = (Profesor) entity;
         System.out.println(p.toString());
      }*/
      return super.onSave(entity, id, state, propertyNames, types);
      
   }
   
   @Override
   public boolean onLoad(Object entity, Serializable id, Object[] state,
            String[] propertyNames, Type[] types) {
     System.out.println("onLoad method is called.");
     System.out.println(id);
     /* if (entity instanceof Profesor) {
    	  Profesor p = (Profesor) entity;
    	  int horas = p.getHorasAsignadas();
    	  for (Object o: state) {
			System.out.println(o);
    	  }
    	  if(horas<10) {
    		  System.out.println("Dale mas curro a este profesor que esta vagote, Alex");
    	  }
    	  System.out.println(p);
    	  String nom = p.getNombre();
    	  System.out.println(nom);
    	  //if(nom.equals("Gregorio")) {
    		//  System.out.println("MICROSOFT ACCEEEEEES!!!!!!!!");
    	  //}
    			  
         
      }*/
      return super.onLoad(entity, id, state, propertyNames, types);
      
   }
   
   
   
   
   @Override
   public String onPrepareStatement(String sql) {
      //logger.info(sql);
      return super.onPrepareStatement(sql);
   }
   
   

}