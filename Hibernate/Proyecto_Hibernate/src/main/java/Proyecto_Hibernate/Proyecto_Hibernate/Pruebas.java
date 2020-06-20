package Proyecto_Hibernate.Proyecto_Hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.Set;

import Proyecto_Hibernate.Proyecto_Hibernate.model.Message;
import Proyecto_Hibernate.Proyecto_Hibernate.model.User;
import Proyecto_Hibernate.Proyecto_Hibernate.model.Vot;
import Proyecto_Hibernate.Proyecto_Hibernate.model.XatMessage;
import Proyecto_Hibernate.Proyecto_Hibernate.repository.MessageDao;
import Proyecto_Hibernate.Proyecto_Hibernate.repository.MortDao;
import Proyecto_Hibernate.Proyecto_Hibernate.repository.PartidaDao;
import Proyecto_Hibernate.Proyecto_Hibernate.repository.RolDao;
import Proyecto_Hibernate.Proyecto_Hibernate.repository.UserDao;
import Proyecto_Hibernate.Proyecto_Hibernate.repository.VotDao;
import Proyecto_Hibernate.Proyecto_Hibernate.repository.XatMessageDao;
import Proyecto_Hibernate.Proyecto_Hibernate.model.Message.Tipo;
import Proyecto_Hibernate.Proyecto_Hibernate.model.Mort;
import Proyecto_Hibernate.Proyecto_Hibernate.model.Partida;
import Proyecto_Hibernate.Proyecto_Hibernate.model.Rol;
import Proyecto_Hibernate.Proyecto_Hibernate.model.RolJugadorPartida;
import Proyecto_Hibernate.Proyecto_Hibernate.repository.RolJugadorPartidaDao;

public class Pruebas {
	
	
	/**
	 * 
	 * 
	 * 
	 * ESTO SOLO SON PRUEBAS PARA COMPROBAR QUE FUNCIONE EL PROGRAMA.
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		Set<User> usuarios = new HashSet<User>();

		User u1 = new User("username", "pw", "alias", "pAvathar");
		User u2 = new User("username2", "pw2", "alias2", "pAvathar2");
		User u3 = new User("username3", "pw3", "alias3", "pAvathar3");
		User u4 = new User("username4", "pw4", "alias4", "pAvathar4");
		User u5 = new User("username5", "pw5", "alias5", "pAvathar5");
		User u6 = new User("username6", "pw6", "alias6", "pAvathar6");
		User u7 = new User("username7", "pw7", "alias7", "pAvathar7");
		User u8 = new User("username8", "pw8", "alias8", "pAvathar8");
		UserDao uDAO2 = new UserDao();
		User r = uDAO2.registre("raul", "pw", "raul", "pAvathar");
		User i = uDAO2.registre("ivan", "pw2", "ivan", "pAvathar2");
		UserDao uDAO = new UserDao();
		uDAO.saveOrUpdate(u1);
		uDAO.saveOrUpdate(u2);
		uDAO.saveOrUpdate(u3);
		uDAO.saveOrUpdate(u4);
		uDAO.saveOrUpdate(u5);
		uDAO.saveOrUpdate(u6);
		uDAO.saveOrUpdate(u7);
		uDAO.saveOrUpdate(u8);

		usuarios.add(u1);
		usuarios.add(u2);
		usuarios.add(u3);
		usuarios.add(u4);
		usuarios.add(u5);
		usuarios.add(u6);
		usuarios.add(u7);
		usuarios.add(u8);
		usuarios.add(r);
		usuarios.add(i);

		Message m = new Message(u1, u2, Tipo.AUDIO, "algo");
		MessageDao mD = new MessageDao();
		boolean t = mD.enviaMissatge(u1, u2, Tipo.AUDIO, "algo");
		System.out.println(t);
		mD.saveOrUpdate(m);

		// User r= new User("raul","pw","alias","pAvathar");
		// User i= new User("ivan","pw2","alias2","pAvathar2");
		/*
		 * UserDao uDAO2 = new UserDao(); User r = uDAO2.registre("raul", "pw", "raul",
		 * "pAvathar"); User i = uDAO2.registre("ivan", "pw2", "ivan", "pAvathar2");
		 */
		// uDAO2.saveOrUpdate(r);
		// uDAO2.saveOrUpdate(i);

		Message m2 = new Message(r, i, Tipo.AUDIO, "algo");
		MessageDao mD2 = new MessageDao();
		mD2.saveOrUpdate(m2);

		boolean t2 = uDAO2.login("raul", "pw");
		System.out.println(t2 + " FUNCIONA?");
		boolean t3 = uDAO2.login("raul", "pwdfsd");
		System.out.println(t3 + " FUNCIONA?");

		// List<User> usersaux = uDAO.Llistar();

		// List<Message> messageaux = mD2.Llistar();

		boolean env = mD2.enviaMissatge(r, i, Tipo.AUDIO, "algo");
		System.out.println(env);

		mD2.getMissatges("raul");

		/* UNIRSE() */

		// CREAMOS LA PARTIDA

		Partida partida = new Partida();
		PartidaDao pDAO = new PartidaDao();
		pDAO.saveOrUpdate(partida);

		// AÑADIMOS LOS USUARIOS A LA PARTIDA CREADA
		Set<User> users = new HashSet<User>();

		for (User user : usuarios) {
			users.add(user);
		}

		Set<Partida> partidas = new HashSet();
		partidas.add(partida);

		// CREAMOS LA TABLA TERNARIA DE USERPARTIDAS
		for (User user : usuarios) {
			user.setPartidas(partidas);
		}

		for (User user : usuarios) {
			uDAO.saveOrUpdate(user);
		}

		/* PRUEBAS DE LA FUNCION INICI() AUN NO ESTA ACABADA */

		/* INICI() */

		// INICIAR UNA PARTIDA
		partida.setTorn(1);
		pDAO.saveOrUpdate(partida);

		// CREAR LOS ROLES Y AÑADIRLOS A LA BASE DE DATOS
		// PRUEBAS
		Rol lobo = new Rol("Lobo", 4, "ruta", "Rol lobo");
		Rol vidente = new Rol("Vidente", -1, "ruta", "Rol vidente");
		Rol pueblo = new Rol("Pueblo", 0, "ruta", "Rol pueblo");

		RolDao rDAO = new RolDao();

		rDAO.saveOrUpdate(lobo);
		rDAO.saveOrUpdate(vidente);
		rDAO.saveOrUpdate(pueblo);

		// ASSIGNAR LOS ROLES A LOS USUARIOS
		// 1)PRIMER PASO: CREAR LOS ROLJUGAODRPARTIDA
		RolJugadorPartidaDao rjpDAO = new RolJugadorPartidaDao();

		rjpDAO.inici(partida, usuarios, lobo, vidente, pueblo);

		/* VOTA() */

		// MOSTRAMOS LOS USUARIOS VIVOS QUE HAY EN UNA PARTIDA

		Set<RolJugadorPartida> usersVius = rjpDAO.jugadorsVius(partida);

		System.out.println();
		System.out.println("LISTA DE USUARIOS A VOTAR QUE ESTAN VIVOS");
		System.out.println();

		for (RolJugadorPartida rolJugadorPartida : usersVius) {

			System.out.println(rolJugadorPartida.toString());

		}

		// CADA USUARIO VOTARA A UNO QUE NO SEA EL

		boolean correcto;

		boolean encontrado;
		
		Set<Vot> votos = new HashSet<Vot>();

		for (RolJugadorPartida rolJugadorPartida : usersVius) {
			User receiver = null;
			correcto = false;
			encontrado = false;
			
			System.out.println("hola");

			while (!correcto) {

				System.out.println("A quien quieres votar " + rolJugadorPartida.getUser().getUserName());
				String voto = reader.next();

				System.out.println("que");

				if (!voto.equals(rolJugadorPartida.getUser().getUserName())) {

					System.out.println("tal");

					for (User users2 : users) {

						if (users2.getUserName().equals(voto)) {

							receiver = users2;
							
							encontrado=true;
							
						}

					}
					if(encontrado) {
						
						if (!rolJugadorPartida.getUser().equals(receiver.getUserName())) {

							System.out.println("hola2");

							Vot voto2 = new Vot(partida, partida.getTorn(), rolJugadorPartida.getUser(), receiver);
							VotDao vDAO = new VotDao();

							vDAO.saveOrUpdate(voto2);

							votos.add(voto2);

							correcto = true;

						}
						
					}
					
					else {
						
						System.out.println("El usuario no se ha encontrado.");
						
					}
					

				}

				else {

					System.out.println("No te puedes votar a ti mismo.");

				}

			}

		}

		partida.setPartidaVot(votos);
		
		/* fiTorn() */

        String usuario = pDAO.fiTorn(partida);

        MortDao mortDAO = new MortDao();
        
        for (RolJugadorPartida roljugador : partida.getRolJugadorPartida()) {

            if (roljugador.getUser().getUserName().equals(usuario)) {

                roljugador.setViu(false);
                rjpDAO.saveOrUpdate(roljugador);

                Mort usuarioMuerto=mortDAO.matar(roljugador);
                System.out.println(
                        "El usuario " + usuario + " ha muerto, su rol era " + roljugador.getRolJugador().getNom());
            }

        }

		/* jugadorsVius() */

        // MOSTRAMOS LOS USUARIOS VIVOS QUE HAY EN UNA PARTIDA

        System.out.println();
        System.out.println("LISTA DE USUARIOS QUE ESTAN VIVOS");
        System.out.println();

        for (RolJugadorPartida rolJugadorPartida : usersVius) {

            if (rolJugadorPartida.isViu()) {

                System.out.println(rolJugadorPartida.getUser().getUserName());

            }

        }
        /* rolsVius() */

        System.out.println();
        System.out.println("LISTA DE LOS ROLES QUE ESTAN VIVOS");
        System.out.println();

        for (RolJugadorPartida rolJugadorPartida : usersVius) {

            if (rolJugadorPartida.isViu()) {

                System.out.println(rolJugadorPartida.getRolJugador().getNom());

            }
        }
		
        /* getMorts() */

        MortDao mortDAO2 = new MortDao();

         List<Mort> muertos = mortDAO2.getMorts(partida.getTorn());

         System.out.println(); System.out.println("LISTA DE MUERTOS EN UN TURNO");
         System.out.println();

         for (Mort mort : muertos) {

        	 System.out.println(mort.toString());

         }
        
         
         
         /* descobrirRol() */

         for (RolJugadorPartida roljugador : partida.getRolJugadorPartida()) {

             System.out.println(roljugador.getUser().getUserName());

         }

         System.out.println("Indica quin rol vols saber dels usuaris mostrats: ");
         String usuario2 = reader.next();
         boolean encontrado2 = false;
         RolJugadorPartida vidente2 = null;
         RolJugadorPartida destapat = null;

         for (RolJugadorPartida roljugador : partida.getRolJugadorPartida()) {

             if (roljugador.getUser().getUserName().equals(usuario2)) {

                 encontrado2 = true;
                 destapat = roljugador;

             }

             if (roljugador.getRolJugador().getNom().equals("Vidente")) {

                 vidente2 = roljugador;

             }

         }

         if (encontrado2) {

             System.out.println("Usuario encontrado.");

            // Rol rolDestapado = rjpDAO.descobrirRol(vidente2, destapat);

            // System.out.println(rolDestapado.toString());

         }

         else {

             System.out.println("Usuario no encontrado.");

         }
         /* escriuMissatgeXat() */
         XatMessageDao xmDAO = new XatMessageDao();
         System.out.println("USUARIO QUE ENVIA EL MENSAJE");
         Set<XatMessage> mensajes = new HashSet<XatMessage>();
         RolJugadorPartida sender = null;

         boolean enviar = false;

         for (RolJugadorPartida rolJugadorPartida : usersVius) {

             System.out.println(rolJugadorPartida.getUser().getUserName());

         }
         System.out.println("Selecciona el usuario que enviara el mensaje: ");
         String userSender = reader.next();

         for (RolJugadorPartida rolJugadorPartida : usersVius) {

             if (rolJugadorPartida.getUser().getUserName().equals(userSender)) {

                 sender = rolJugadorPartida;

                 enviar = true;

             }

         }

         if (enviar) {

             Date fecha = new Date();

             System.out.println("Escribe un mensaje: ");
             String contenido = reader.nextLine();
             contenido = reader.nextLine();

             XatMessage xm = new XatMessage(sender.getUser(), partida, contenido, fecha);

             mensajes.add(xm);

             partida.setXatPartida(mensajes);

             xmDAO.escriuMissatgeXat(sender, contenido, fecha);

         }
        
         /* getXat(idPartida) /

         System.out.println();
         System.out.println("XATMESSAGEDAO");
         System.out.println();

         Set<XatMessage> mensajes2 = xmDAO.getXat(partida);

         if (mensajes2 != null) {

             for (XatMessage xatMessage : mensajes2) {

                 System.out.println(xatMessage.toString());

             }

         } else {

             System.out.println("No hay mensajes");

         }

         / escriuMissatgeLlop() */

         System.out.println("LOBO QUE ENVIARA EL MENSAJE");
         Set<XatMessage> mensajes3 = new HashSet<XatMessage>();
         RolJugadorPartida sender2 = null;

         boolean enviar2 = false;

         for (RolJugadorPartida rolJugadorPartida : usersVius) {

             System.out.println(rolJugadorPartida.getUser().getUserName());

         }
         System.out.println("Selecciona el usuario que enviara el mensaje: ");
         String userSender2 = reader.next();

         for (RolJugadorPartida rolJugadorPartida : usersVius) {

             if (rolJugadorPartida.getUser().getUserName().equals(userSender2)) {

                 if (rolJugadorPartida.getRolJugador().getNom().equals("Lobo")) {

                     if (partida.getTorn() % 2 == 0) {

                         sender2 = rolJugadorPartida;

                         enviar2 = true;

                     }

                 }

             }

         }

         if (enviar2) {

             Date fecha = new Date();

             System.out.println("Escribe un mensaje: ");
             String contenido = reader.nextLine();
             contenido = reader.nextLine();

             XatMessage xm = new XatMessage(sender2.getUser(), partida, contenido, fecha);

             mensajes3.add(xm);

             partida.setXatPartida(mensajes3);

             xmDAO.escriuMissatgeLlop(sender2, contenido, fecha);

         }
         
         
         
         /* getXatLlops(idPartida, username) */

         System.out.println("MENSAJES DE LOS LOBOS");



         // MOSTRAR USUARIOS QUE SON LOBOS

         for (RolJugadorPartida roljugador : partida.getRolJugadorPartida()) {

             if (roljugador.getRolJugador().getNom().equals("Lobo")) {

                 System.out.println(roljugador.getUser().getUserName());

             }

         }

         System.out.println("Selecciona un usuario lobo para mostrar sus mensajes: ");
         String usuarioLobo = reader.next();

         RolJugadorPartida roljugadorLobo = null;

         for (RolJugadorPartida roljugador : partida.getRolJugadorPartida()) {

             if (roljugador.getUser().getUserName().equals(usuarioLobo)) {



                 roljugadorLobo = roljugador;



             }

         }

         Set<XatMessage> mensajesLobo = xmDAO.getXatLlops(roljugadorLobo);

         for (XatMessage xatMessage : mensajesLobo) {

             System.out.println(xatMessage.toString());

         }
		reader.close();

	}

}
