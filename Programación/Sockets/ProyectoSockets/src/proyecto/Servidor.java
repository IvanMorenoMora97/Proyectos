package proyecto;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {

	static ServerSocket servidor = null;
	static Socket sc = null;
	static DataInputStream in = null;
	static DataOutputStream out;
	static BufferedReader is;
	// Pasar objetos
	static ObjectOutputStream obout = null;
	static ObjectInputStream obin = null;
	final static int PUERTO = 5000;
	static ArrayList<Cliente> usuariosVivos = null;
	static String usuariosMuertos = null;

	public static void main(String[] args) throws ClassNotFoundException, InterruptedException {

		Partida partida;
		// puerto de nuestro servidor

		try {
			// Creamos el socket del servidor
			// Creamos la partida
			ArrayList<Cliente> p = new ArrayList();
			partida = new Partida(1, p, p, 1);

			// Creamos el arraylist de los personajes
			servidor = new ServerSocket(PUERTO);
			System.out.println("Servidor iniciado");
			// Para empezar, esperamos a que el arraylist de usuarios alcance los minimos
			// jugadores
			// Siempre estara escuchando peticiones
			while (p.size() < 6) {

				// Espero a que un cliente se conecte
				sc = servidor.accept();

				System.out.println("Cliente conectado");
				out = new DataOutputStream(sc.getOutputStream());
				obout = new ObjectOutputStream(sc.getOutputStream());
				obin = new ObjectInputStream(sc.getInputStream());
				is = new BufferedReader(new InputStreamReader(sc.getInputStream()));
				in = new DataInputStream(sc.getInputStream());
				Cliente per = (Cliente) obin.readObject();

				p.add(per);

				System.out.println(per.toString());

				// Le envio un mensaje
				out.writeUTF("¡Hola " + per.getUsuari() + " bienvenido a koronallops!, buscando jugadores..");
				sc.close();

			}

			// Cuando los 6 clientes se conectan comienza la partida, ahora asignaremos los
			// roles

			p = asignarRoles(p);

			System.out.println("ROLES ASIGNADOS");

			usuariosVivos = p;

			DatagramSocket enviador = new DatagramSocket();
			// Puede ser necesario habilitar el envío por broadcast.
			enviador.setBroadcast(true);
			// Convert serialize permite pasar el arraylist
			byte[] dato = Convert.serialize(p);
			// El destinatario es 192.20.20.255, que es la dirección de broadcast
			DatagramPacket dgp = new DatagramPacket(dato, dato.length, InetAddress.getByName("235.1.1.1"), 5000);
			enviador.send(dgp);
			// Esperamos respuesta de todos los usuarios
			esperarrespuesta(p);

			System.out.println("EL JUEGO VA A EMPEZAR");

			while (true) {

				System.out.println("Juego empezado");

				// TURNO DE NOCHE
				if (partida.getTurno() == 1) {

					String turno = "1";

					byte[] dato2 = turno.getBytes();
					DatagramPacket dgp2 = new DatagramPacket(dato2, dato2.length, InetAddress.getByName("235.1.1.1"),
							5000);

					enviador.send(dgp2);

					// Esperamos respuesta del turno
					esperarrespuesta(usuariosVivos);

					// Enviamos array list de jugadores vivos
					byte[] dato1 = Convert.serialize(usuariosVivos);
					// El destinatario es 192.20.20.255, que es la dirección de broadcast
					DatagramPacket dgp3 = new DatagramPacket(dato1, dato1.length, InetAddress.getByName("235.1.1.1"),
							5000);
					enviador.send(dgp3);
					// Esperamos respuesta de enviar el array list de jugadores vivos, excepto lobos
					// faltara implementar esto ultimo
					esperarrespuesta(usuariosVivos);

					// Esperamos a que voten los lobos
					ArrayList<Cliente> lobos = new ArrayList();
					// CONTAMOS LOS LOBOS QUE ESTAN VIVOS
					for (Cliente clienteLobo : usuariosVivos) {
						if (clienteLobo.getRol().equals("Lobo") && clienteLobo.getVivo()) {
							lobos.add(clienteLobo);
						}
					}

					// Espero los votos de los lobos de la partida (es decir otro diferente)
					esperarVoto(lobos, usuariosVivos);

					// Le pasamos la lista con los usuarios ya votados
					// matarUsuario(usuariosVivos);

					System.out.println("He pasado por aqui");

					// No se que coño hace aki
					System.out.println();
					System.out.println("USUARIOS VIVOS");
					for (int i = 0; i < usuariosVivos.size(); i++) {

						if (usuariosVivos.get(i).getVivo()) {

							System.out.println("Usuario vivo : " + usuariosVivos.get(i).toString());

						}

					}

					String respuesta = "Esperando a que salga el sol";
					byte[] dato3 = respuesta.getBytes();
					DatagramPacket dgp4 = new DatagramPacket(dato3, dato3.length, InetAddress.getByName("235.1.1.1"),
							5000);

					enviador.send(dgp4);

					// Esperamos respuesta del turno
					esperarrespuesta(usuariosVivos);

					/*
					 * for (int i = 0; i < lobos; i++) {
					 * 
					 * System.out.println("Vueltas: " + i);
					 * 
					 * Cliente usuarioVotado = (Cliente) obin.readObject();
					 * 
					 * System.out.println("NUMERO SELECCIONADO: " + usuarioVotado);
					 * 
					 * p.get(p.indexOf(usuarioVotado)).incrementarVoto();
					 * 
					 * }
					 */

					partida.setTurno(0);

					System.out.println("SE HA ACABADO LA NOCHE");

					Thread.sleep(5000);

				}

				// TURNO DE DIA
				else {

					String turno = "0";

					byte[] dato2 = turno.getBytes();
					DatagramPacket dgp2 = new DatagramPacket(dato2, dato2.length, InetAddress.getByName("235.1.1.1"),
							5000);

					enviador.send(dgp2);

					// Esperamos respuesta del turno

					esperarrespuesta(usuariosVivos);

					// Le pasamos la lista con los usuarios ya votados
					matarUsuario(usuariosVivos);
					
					
					
					// Enviamos array list de jugadores vivos
					byte[] dato1 = Convert.serialize(usuariosVivos);
					// El destinatario es 192.20.20.255, que es la dirección de broadcast
					DatagramPacket dgp4 = new DatagramPacket(dato1, dato1.length, InetAddress.getByName("235.1.1.1"),
							5000);
					// enviador.send(dgp4);
					// Esperamos respuesta de enviar el array list de jugadores vivos, excepto lobos
					// faltara implementar esto ultimo
					enviador.send(dgp4);

					esperarrespuesta(usuariosVivos);

					// ENVIAR QUE USUARIO HA MUERTO
					byte[] dato3 = usuariosMuertos.getBytes();
					DatagramPacket dgp3 = new DatagramPacket(dato3, dato3.length, InetAddress.getByName("235.1.1.1"),
							5000);

					enviador.send(dgp3);

					// Esperamos respuesta de enviar el array list de jugadores vivos, excepto lobos
					// faltara implementar esto ultimo

					esperarrespuesta(usuariosVivos);

					/* VOTACIONES DE DIA */

					// Esperamos a que voten todos los usuarios

					// Espero los votos de los lobos de la partida (es decir otro diferente)

					esperarVoto(usuariosVivos, usuariosVivos);

					// Le pasamos la lista con los usuarios ya votados
					matarUsuario(usuariosVivos);

					System.out.println("He pasado por aqui");

					System.out.println("SE HA ACABADO EL DIA");

					partida.setTurno(1);

					Thread.sleep(1000000000);

				}

				Thread.sleep(1000);

			}
		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	// Funcion que asignara los roles
	public static ArrayList<Cliente> asignarRoles(ArrayList<Cliente> p) {
		Random r = new Random();
		int random = r.nextInt(6);
		int lobo = r.nextInt(6);
		while (random == lobo) {

			lobo = r.nextInt(6);

		}
		for (int i = 0; i < 6; i++) {
			if (i == lobo || i == random) {
				p.get(i).setRol("Lobo");
			}

			else {
				p.get(i).setRol("Pueblo");

			}

		}

		return p;
	}

	public static void esperarrespuesta(ArrayList<Cliente> p) {
		try {
			System.out.println("Tamaño del array: " + p.size());
			for (int i = 0; i < p.size(); i++) {
				System.out.println("Estoy esperando............ ");
				Socket sc1 = servidor.accept();
				in = new DataInputStream(sc1.getInputStream());
				String mensaje = in.readUTF();
				System.out.println(mensaje);
				sc1.close();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Enviamos las listas de los jugadores (?)
	public void enviarListas() {

	}

	public static void esperarVoto(ArrayList<Cliente> lobos, ArrayList<Cliente> p) {

		try {

			// Añadimos los votos de los lobos a los usuarios
			for (int i = 0; i < lobos.size(); i++) {
				System.out.println("Estoy esperando el voto............ ");
				Socket sc1 = servidor.accept();
				in = new DataInputStream(sc1.getInputStream());
				String mensaje = in.readUTF();

				for (int j = 0; j < p.size(); j++) {

					if (p.get(j).getUsuari().equals(mensaje)) {
						System.out.println(mensaje);
						p.get(j).incrementarVoto();
						System.out.println(p.get(j).toString());
					}

				}

				sc1.close();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void matarUsuario(ArrayList<Cliente> p) {

		usuariosVivos = new ArrayList<Cliente>();

		// Matamos al usuario mas votado

		Collections.sort(p, new CompararVotos());
		Collections.reverse(p);
		p.get(0).setVivo(false);

		usuariosMuertos = "" + p.get(0).getUsuari();

		System.out.println("Ha muerto el usuario " + p.get(0).toString());

		for (Cliente cliente : p) {
			cliente.setVotos(0);

			if (cliente.getVivo()) {
				usuariosVivos.add(cliente);
			}

		}

	}

}