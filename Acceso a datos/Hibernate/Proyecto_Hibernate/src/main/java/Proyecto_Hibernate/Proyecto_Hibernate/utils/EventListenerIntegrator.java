package Proyecto_Hibernate.Proyecto_Hibernate.utils;

import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

public class EventListenerIntegrator implements Integrator {

  // @Override
   public void integrate(Metadata metadata, SessionFactoryImplementor 
         sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {

      EventListenerRegistry eventListenerRegistry = 
            serviceRegistry.getService(EventListenerRegistry.class);

      eventListenerRegistry.getEventListenerGroup(EventType.SAVE)
                     .appendListener(new SaveUpdateEventListenerImp());
      ///Descomentar y crear las clases si lo necesitais no?
      /*
      eventListenerRegistry.getEventListenerGroup(EventType.LOAD)
                     .appendListener(new LoadEventListenerImp());
      
      eventListenerRegistry.getEventListenerGroup(EventType.REFRESH)
                     .appendListener(new RefreshEventListenerImp());
                     */
   }

public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
	// TODO Auto-generated method stub
	
}

  /*@Override
   public void disintegrate(SessionFactoryImplementor sessionFactory,
         SessionFactoryServiceRegistry serviceRegistry) {

   }*/
}
