package Proyecto_Hibernate.Proyecto_Hibernate.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import Proyecto_Hibernate.Proyecto_Hibernate.model.Partida;
import Proyecto_Hibernate.Proyecto_Hibernate.model.RolJugadorPartida;
import Proyecto_Hibernate.Proyecto_Hibernate.model.User;
import Proyecto_Hibernate.Proyecto_Hibernate.model.Vot;


public class PartidaDao extends GenericDao<Partida, Integer> implements IPartidaDao {

	Scanner reader = new Scanner(System.in);

	public String fiTorn(Partida p) {

		String usuario = null;
		String usuario2 = null;
		boolean dia = false;
		boolean videnteVivo = false;

		Set<Vot> votos = p.getPartidaVot();

		HashMap<String, Integer> votosDia = new HashMap<String, Integer>();
		HashMap<String, Integer> votosNoche = new HashMap<String, Integer>();

		int contador = 0;

		// TURNO DE DIA
		if (p.getTorn() % 2 != 0) {

			dia = true;
		

			for (Vot vot : votos) {

				for (Vot vot2 : votos) {

					if (vot.getReceiver().getUserName().equals(vot2.getReceiver().getUserName())) {

						contador++;

					}

				}

				votosDia.put(vot.getReceiver().getUserName(), contador);
				contador = 0;
			}

			usuario = null;
			int totalVotos = 0;

			for (Entry<String, Integer> entry : votosDia.entrySet()) {

				if (usuario == null) {

					usuario = entry.getKey();

					totalVotos = entry.getValue();

				}

				else {

					if (totalVotos < entry.getValue()) {

						usuario = entry.getKey();

						totalVotos = entry.getValue();

					}

				}

			}

			// return usuario;
		}

		// TURNO DE NOCHE

		else if (p.getTorn() % 2 == 0) {

			dia=false;
			
			for (Vot vot : votos) {

				for (Vot vot2 : votos) {

					if (vot.getReceiver().getUserName().equals(vot2.getReceiver().getUserName())) {

						contador++;

					}

				}

				votosNoche.put(vot.getReceiver().getUserName(), contador);
				contador = 0;
			}

			int totalVotos2 = 0;

			for (Entry<String, Integer> entry : votosNoche.entrySet()) {

				if (usuario2 == null) {

					usuario2 = entry.getKey();

					totalVotos2 = entry.getValue();

				}

				else {

					if (totalVotos2 < entry.getValue()) {

						usuario2 = entry.getKey();

						totalVotos2 = entry.getValue();

					}

				}

			}

			RolJugadorPartida vidente = null;
			// SABER SI EL VIDENTE ESTA VIVO
			for (RolJugadorPartida rolJugadorPartida : p.getRolJugadorPartida()) {

				if (rolJugadorPartida.getRolJugador().getNom().equals("Vidente")) {

					if (rolJugadorPartida.isViu()) {

						vidente=rolJugadorPartida;
						videnteVivo = true;

					}

				}

			}

			if (videnteVivo) {

				// EL VIDENTE REVELARA EL ROL DE UN USUARIO
				System.out.println("Vidente " + vidente.getUser().getUserName()+ " has d'escollir");
				// MOSTRAR LISTA DE USUARIOS PARA REVELAR EL ROL DE UNO DE ELLOS
				System.out.println("LISTA DE USUARIOS PARA REVELAR EL ROL");

				for (RolJugadorPartida rolJugadorPartida : p.getRolJugadorPartida()) {
					if(rolJugadorPartida.isViu()) {
						if(rolJugadorPartida.getId()!=vidente.getId()) {
							System.out.println("Usuario: " + rolJugadorPartida.getUser().getUserName());
							
						}
					}

				}

				System.out.println("Selecciona un usuario para revelar su rol: ");
				String usuarioRevelar = reader.next();

				for (RolJugadorPartida rolJugadorPartida : p.getRolJugadorPartida()) {

					if (rolJugadorPartida.getUser().getUserName().equals(usuarioRevelar)) {

						System.out.println("Usuario: " + rolJugadorPartida.getUser().getUserName() + ", Rol: "
								+ rolJugadorPartida.getRolJugador().getNom());

					}

				}

			}

			// return usuario2;
		}

		p.setTorn(p.getTorn() + 1);

		if (dia) {
			return usuario;
		}

		else {
			return usuario2;
		}
	}

}
