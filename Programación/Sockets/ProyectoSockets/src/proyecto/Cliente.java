package proyecto;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente implements Serializable, Runnable {
	private static Scanner reader = new Scanner(System.in);

	// Host del servidor
	final static String HOST = "127.0.0.1";
	// Puerto del servidor
	final static int PUERTO = 5000;
	private String usuari;
	private int Imagen;
	private String rol;
	private int votos;
	private boolean vivo;
	DataInputStream in;
	DataOutputStream out;

	public Cliente(String usuari, int imagen, String rol) {
		super();
		this.usuari = usuari;
		Imagen = imagen;
		this.rol = rol;
		this.vivo = true;
	}

	public Cliente(Cliente c) {
		this.usuari = c.getUsuari();
		Imagen = c.getImagen();
		this.rol = c.getRol();
		this.vivo = true;

	}

	public String getUsuari() {
		return usuari;
	}

	public void setUsuari(String usuari) {
		this.usuari = usuari;
	}

	public int getImagen() {
		return Imagen;
	}

	public void setImagen(int imagen) {
		Imagen = imagen;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public synchronized void incrementarVoto() {
		this.votos++;
	}

	public int getVotos() {
		return votos;
	}

	public void setVotos(int votos) {
		this.votos = votos;
	}

	public boolean getVivo() {
		return vivo;
	}

	public void setVivo(boolean vivo) {
		this.vivo = vivo;
	}

	@Override
	public String toString() {
		return "Cliente [usuari=" + usuari + ", rol=" + rol + ", votos=" + votos + ", vivo=" + vivo + "]";
	}

	public static void main(String[] args) throws InterruptedException {

		DataInputStream in;
		DataOutputStream out;
		// Pasar objetos
		ObjectOutputStream obout = null;
		ObjectInputStream obin;
		Cliente p = null;
		try {
			// Creo el socket para conectarme con el cliente
			Socket sc = new Socket(HOST, PUERTO);

			System.out.println("Di tu nombre:");
			String x = reader.nextLine();

			p = new Cliente(x, 0, null);
			in = new DataInputStream(sc.getInputStream());
			out = new DataOutputStream(sc.getOutputStream());
			obout = new ObjectOutputStream(sc.getOutputStream());
			obin = new ObjectInputStream(sc.getInputStream());

			// Envio mi objeto al servidor, para que sepa mi nombre de usuario, por ejemplo
			obout.writeObject(p);

			// Recibo el mensaje del servidor
			String mensaje = in.readUTF();

			System.out.println(mensaje);

			sc.close();
			// Hacemos el thread para la partida
			Runnable nuevoCliente = new Cliente(p);
			Thread hilo = new Thread(nuevoCliente);
			hilo.start();

		} catch (IOException ex) {
			Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	@Override
	public void run() {

		try {
			byte[] b = new byte[1024];
			DatagramPacket dgram = new DatagramPacket(b, b.length);
			MulticastSocket socket = new MulticastSocket(5000);
			socket.joinGroup(InetAddress.getByName("235.1.1.1"));
			socket.receive(dgram); // Se bloquea hasta que llegue un datagrama

			// Usamos la clase convert para convertir el array de bytes en un array list
			ArrayList<Cliente> clientes = (ArrayList<Cliente>) Convert.deserialize(dgram.getData());

			// Buscamos el rol

			for (Cliente c : clientes) {

				if (this.usuari.equals(c.usuari)) {

					this.setRol(c.rol);

				}

			}

			// System.out.println(clientes.toString());
			/*
			 * System.out.println("Recivido " + dgram.getLength() + " bytes de " +
			 * dgram.getAddress() + " "+ new String(dgram.getData()));
			 */

			System.out.println("Hola " + this.usuari + " ! eres un " + this.rol);

			// Le enviamos un okey al servidor conforme he recibido el datagrama
			Socket sc = new Socket(HOST, PUERTO);
			out = new DataOutputStream(sc.getOutputStream());
			out.writeUTF("OK desde" + this.usuari);

		} catch (Exception e) {

			System.out.println(e.getMessage());
		}

		// EMPEZAMOS LA PARTIDA
		while (true) {

			String turno = null;

			try {

				DatagramPacket dgram = null;
				Socket sc = null;
				MulticastSocket socket = null;

				if (this.getVivo()) {

					System.out.println("NOMBRE DEL USUARIOOOOOOOOOOOOOOOOOOOOOO: " + this.getUsuari());
					System.out.println("ESTA VIVOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO?: " + this.getVivo());

					byte[] b = new byte[1024];
					dgram = new DatagramPacket(b, b.length);

					socket = new MulticastSocket(5000);
					socket.joinGroup(InetAddress.getByName("235.1.1.1"));
					socket.receive(dgram); // Se bloquea hasta que llegue un datagrama

					turno = new String(dgram.getData(), 0, dgram.getLength());
					// turno = dgram.getData().toString();

					System.out.println("Turno " + turno);

					// Le enviamos un okey al servidor conforme he recibido el datagrama
					sc = new Socket(HOST, PUERTO);
					out = new DataOutputStream(sc.getOutputStream());
					out.writeUTF("OK desde " + this.usuari);
					sc.close();
					// Cuando llega el siguiente datagrama no lo muestra al cliente excepto si es
					// lobo y esta en turno de noche
					socket.receive(dgram); // Se bloquea hasta que llegue un datagrama

					sc = new Socket(HOST, PUERTO);
					out = new DataOutputStream(sc.getOutputStream());
					out.writeUTF("OK2 desde " + this.usuari);
					sc.close();

					System.out.println("ROL: " + this.getRol());
					System.out.println("VIVO: " + this.getVivo());

				}

				// TURNO 1
				if (turno.equals("1")) {

					System.out.println("TURNO DE NOCHE");

					if (this.getRol().equals("Lobo") && this.getVivo()) {

						int i = 1;
						ArrayList<Cliente> clientes2 = (ArrayList<Cliente>) Convert.deserialize(dgram.getData());

						System.out.println("Lista de usuarios posibles para votar");
						for (Cliente cliente : clientes2) {
							if (cliente.getRol().equals("Pueblo") && cliente.getVivo()) {
								System.out.println(i + " " + cliente.getUsuari());
								i++;
							}

						}

						// EN EL SERVIDOR HACER UN BUCLE PARA RECEIBIR LAS PETICIONES DE LOS CLIENTES,
						// ENVIAR UN ACK DEL CLIENTE AL SERVIDOR Y CUANDO EL SERVIDOR RECIBA EL ACK
						// HACER UN DELAY

						System.out.println("Selecciona un usuario al que votar: ");
						String voto = reader.nextLine();
						int posicion = Integer.parseInt(voto) - 1;
						int j = 0;

						Cliente c = null;
						for (Cliente cliente : clientes2) {

							if (cliente.getRol().equals("Pueblo") && cliente.getVivo()) {

								if (posicion == j) {
									c = cliente;
								}

								j++;
							}

						}

						System.out.println("Has seleccionado al siguiente usuario: " + c.getUsuari());
						sc = new Socket(HOST, PUERTO);
						out = new DataOutputStream(sc.getOutputStream());
						out.writeUTF(c.getUsuari());
						sc.close();

					}

					String respuesta = null;
					byte[] b = new byte[1024];
					dgram = new DatagramPacket(b, b.length);

					socket = new MulticastSocket(5000);
					socket.joinGroup(InetAddress.getByName("235.1.1.1"));
					socket.receive(dgram); // Se bloquea hasta que llegue un datagrama

					respuesta = new String(dgram.getData(), 0, dgram.getLength());

					System.out.println(respuesta);
					
					sc = new Socket(HOST, PUERTO);
					out = new DataOutputStream(sc.getOutputStream());
					out.writeUTF("ESPERANDO DIA DESDE  " + this.usuari);
					sc.close();

				}

				// TURNO DE DIA
				else {
					ArrayList<Cliente> clientes2 = null;
					if (turno.equals("0")) {
						// ACABAR EL CLIENTE MUERTO

						System.out.println("TURNO DE DIA");

						// RECIBIMOS ARRAYLIST DE USUARIOS
						clientes2 = (ArrayList<Cliente>) Convert.deserialize(dgram.getData());

						// RECIBIMOS USUARIO MUERTO EN EL TURNO DE NOCHE
						byte[] b2 = new byte[1024];
						DatagramPacket dgram4 = new DatagramPacket(b2, b2.length);
						socket.receive(dgram4); // Se bloquea hasta que llegue un datagrama
						String usuarioMuerto = new String(dgram4.getData(), 0, dgram4.getLength());

						System.out.println("USUARIO QUE HA MUERTO EN EL TURNO DE NOCHE: " + usuarioMuerto);

						// AQUI SE MATA EL PROGRAMA
						if (this.getUsuari().equals(usuarioMuerto)) {
							System.out.println("Has muerto");
							break;
						}
					}
					if (turno.equals("0") && this.getVivo()) {

						sc = new Socket(HOST, PUERTO);
						out = new DataOutputStream(sc.getOutputStream());
						out.writeUTF("OK3 usuarioMuerto recibido");
						sc.close();

						System.out.println("TURNO DE DIA");

						int ii = 1;

						boolean salir = false;
						Cliente c = null;

						while (!salir) {
							ii = 1;
							System.out.println("Lista de usuarios posibles para votar");
							for (Cliente cliente : clientes2) {

								System.out.println(ii + " " + cliente.getUsuari());
								ii++;

							}

							System.out.println("Selecciona un usuario al que votar: ");
							String voto = reader.nextLine();
							int posicion = Integer.parseInt(voto) - 1;

							// COMPROBAMOS QUE NO SE HAYA VOTADO A EL MISMO

							int j = 0;

							for (Cliente cliente : clientes2) {

								if (cliente.getVivo()) {

									System.out.println("USUARIO VIVO: " + cliente.getUsuari());

									if (posicion == j) {
										c = cliente;
										System.out.println("USUARIO COINCIDIDO QUE HA SIDO VOTADO: " + c.getUsuari());
									}

									j++;
								}

							}

							if (!c.getUsuari().equals(this.getUsuari())) {

								System.out.println("Has votado a " + c.getUsuari());
								System.out.println("TU ERES " + this.getUsuari());
								salir = true;

							} else {
								System.out.println("No te puedes votar a ti mismo, prueba otra vez.");

							}

						}

						sc = new Socket(HOST, PUERTO);
						out = new DataOutputStream(sc.getOutputStream());
						out.writeUTF(c.getUsuari());
						sc.close();

					}
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
	// Ahora se trataria de poder recibir el ArrayList con todos los objetos

}