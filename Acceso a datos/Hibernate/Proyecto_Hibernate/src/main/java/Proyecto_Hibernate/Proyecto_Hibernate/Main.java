package Proyecto_Hibernate.Proyecto_Hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import Proyecto_Hibernate.Proyecto_Hibernate.model.User;
import Proyecto_Hibernate.Proyecto_Hibernate.model.Vot;
import Proyecto_Hibernate.Proyecto_Hibernate.model.XatMessage;
import Proyecto_Hibernate.Proyecto_Hibernate.model.Message.Tipo;
import Proyecto_Hibernate.Proyecto_Hibernate.model.Mort;
import Proyecto_Hibernate.Proyecto_Hibernate.model.Partida;
import Proyecto_Hibernate.Proyecto_Hibernate.model.Rol;
import Proyecto_Hibernate.Proyecto_Hibernate.model.RolJugadorPartida;
import Proyecto_Hibernate.Proyecto_Hibernate.repository.MessageDao;
import Proyecto_Hibernate.Proyecto_Hibernate.repository.MortDao;
import Proyecto_Hibernate.Proyecto_Hibernate.repository.PartidaDao;
import Proyecto_Hibernate.Proyecto_Hibernate.repository.RolDao;
import Proyecto_Hibernate.Proyecto_Hibernate.repository.RolJugadorPartidaDao;
import Proyecto_Hibernate.Proyecto_Hibernate.repository.UserDao;
import Proyecto_Hibernate.Proyecto_Hibernate.repository.VotDao;
import Proyecto_Hibernate.Proyecto_Hibernate.repository.XatMessageDao;

public class Main {

	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);

		int opcion = 0;
		int contL = 0;
		int contV = 0;
		int contP = 0;

		String nom;
		String password;
		String alias;
		String pathAvatar;
		char letra;
		boolean aux;
		boolean salir = false;
		boolean entra = true;
		boolean jugadors = false;
		boolean partidaB = false;
		boolean partidaI = false;
		boolean votacions = false;
		boolean finalizar = false;

		// Creació dels sets
		Set<User> usuarios = new HashSet<User>();
		Set<Partida> partidas = new HashSet<Partida>();
		Set<RolJugadorPartida> usersVius = new HashSet<RolJugadorPartida>();
		Set<Vot> votos = new HashSet<Vot>();
		List<Mort> muertos = new ArrayList<Mort>();
		List<Vot> votosPartida = new ArrayList<Vot>();
		Set<XatMessage> mensajesLobos = new HashSet<XatMessage>();
		Set<XatMessage> mensajesUsuarios = new HashSet<XatMessage>();
		Set<XatMessage> MostrarMensajesLobos = new HashSet<XatMessage>();
		Set<XatMessage> mensajesGeneral = new HashSet<XatMessage>();

		// Creació de les classes que necessitem.
		Partida partides = null;
		Rol lobo = new Rol("Lobo", 4, "ruta", "Rol lobo");
		Rol vidente = new Rol("Vidente", -1, "ruta", "Rol vidente");
		Rol pueblo = new Rol("Pueblo", 0, "ruta", "Rol pueblo");
		RolJugadorPartida Sender = null;

		// fem la creació de tots els daos.
		System.out.println("Un moment, estem estructurant la BD.");
		UserDao usuari = new UserDao();
		MessageDao missatge = new MessageDao();
		PartidaDao partida = new PartidaDao();
		RolDao rol = new RolDao();
		RolJugadorPartidaDao RolJugadorPartida = new RolJugadorPartidaDao();
		MortDao usuariMort = new MortDao();
		XatMessageDao xat = new XatMessageDao();
		VotDao vot = new VotDao();

		do {

			System.out.println("1. Registrar un usuari.");
			System.out.println("2. Login de un usuari.");
			System.out.println("3. Enviar un missatge.");
			System.out.println("4. Rebre tots els missatges.");
			System.out.println("5. Unirse a una partida.");
			System.out.println("6. Inicia una partida.");
			System.out.println("7. Realitza les votacions.");
			System.out.println("8. Finalitza un torn.");
			System.out.println("9. Retorna el rol d'un jugador.");
			System.out.println("10. Veure jugadors vius.");
			System.out.println("11. Veure els rols vius.");
			System.out.println("12. Escriu un missatge al xat.");
			System.out.println("13. Rep tots els missatges del xat.");
			System.out.println("14. Escriu un missatge al xat de llops.");
			System.out.println("15. Rep missatges de llops.");
			System.out.println("16. Rep totes les votacions d'un torn.");
			System.out.println("17. Rep totes les morts d'un torn.");
			System.out.println("0. Sortir del menú.");
			System.out.println("Opció: ");

			opcion = reader.nextInt();

			switch (opcion) {

			case 1:
				int contJug = 0;
				salir = false;
				entra = true;

				if (!jugadors)
					System.out.println(
							"Has de crear un minim de 15 usuaris, per no haber d'escriure tota la estona les seves dades, deixem un fitxer txt per fer un \"CopyPaste\"");

				while (!salir) {
					if (entra) {

						System.out.println("Username:");
						nom = reader.next();

						System.out.println("Password:");
						password = reader.next();

						System.out.println("Alias:");
						alias = reader.next();

						System.out.println("pathAvatar:");
						pathAvatar = reader.next();

						User user = usuari.registre(nom, password, alias, pathAvatar);
						usuarios.add(user);
						System.out.println("Usuari creat.");
						contJug++;
					}

					System.out.println("Vols afegir un altre usuari? s/n");
					letra = reader.next().charAt(0);
					switch (letra) {
					case 's':
					case 'S':
						entra = true;
						break;
					case 'n':
					case 'N':
						if (contJug < 15) {
							System.out.println(
									"Has de crear com a minim 15 usuaris i portes " + contJug + " usuaris creats.");
						} else
							salir = true;

						break;
					default:
						entra = false;
						System.out.println("Has de posar s o n");
					}

				}
				jugadors = true;

				break;

			case 2:
				if (jugadors) {
					System.out.println("Username:");
					nom = reader.next();

					System.out.println("Password:");
					password = reader.next();

					aux = usuari.login(nom, password);
					if (aux)
						System.out.println(nom + " ha conseguit fer login.");
					else
						System.out.println(nom + " No ha conseguit fer login.");
				} else {
					System.out.println("Per intentar fer un login primer has de crear els usuaris.");
				}
				break;

			case 3:
				if (jugadors) {
					aux = true;
					entra = true;
					Tipo tipus = null;
					String contingut;
					System.out.println("Username sender:");
					nom = reader.next();
					User user = usuari.getUser(nom);
					if (user.equals(null)) {
						aux = false;
					}
					System.out.println("Username receiver:");
					nom = reader.next();
					User user2 = usuari.getUser(nom);
					if (user2.equals(null)) {
						aux = false;
					}

					while (entra) {
						System.out.println("Escull tipus de  missatge: t=TEXT, i=IMATGE, a=AUDIO, v=VIDEO.");
						letra = reader.next().charAt(0);
						switch (letra) {
						case 't':
						case 'T':
							tipus = Tipo.TEXT;
							entra = false;
							break;
						case 'i':
						case 'I':
							tipus = Tipo.IMATGE;
							entra = false;
							break;
						case 'a':
						case 'A':
							tipus = Tipo.AUDIO;
							entra = false;
							break;
						case 'v':
						case 'V':
							tipus = Tipo.VIDEO;
							entra = false;
							break;
						default:
							entra = true;
							System.out.println("Has de posar t, i, a, v.");
						}
					}

					System.out.println("Posa el contingut del missatge:");
					contingut = reader.nextLine();
					contingut = reader.nextLine();

					if (aux) {
						entra = missatge.enviaMissatge(user, user2, tipus, contingut);
						if (entra)
							System.out.println("S'ha enviat el missatge.");
						else
							System.out.println("NO s'ha pogut enviar el missatge.");
					}
				} else {
					System.out.println("Per enviar missatges primer has de crear els usuaris.");
				}
				break;

			case 4:
				if (jugadors) {
					System.out.println("Username dels que vols rebre missatges:");
					nom = reader.next();
					missatge.getMissatges(nom);
				} else {
					System.out.println("Per rebre els missatges primer has de crear els usuaris.");
				}
				break;

			case 5:
				if (jugadors) {
					partidaB = true;
					// CREAMOS LA PARTIDA Y LA GUARDAMOS EN LA BASE DE DATOS
					partides = new Partida();
					partidas.add(partides);
					partida.saveOrUpdate(partides);

					// CREAMOS LA TABLA TERNARIA DE USERPARTIDAS
					for (User usuario : usuarios) {
						usuario.setPartidas(partidas);
					}

					for (User users : usuarios) {
						usuari.saveOrUpdate(users);
					}
					System.out.println("Partida creada.");
				} else {
					System.out.println("Per unirse a una partida primer has de crear els usuaris.");
				}
				break;

			case 6:
				if (partidaB) {
					partidaI = true;
					// INICIAMOS UNA PARTIDA

					// Posem el torn 0 per que comencin els llops matant.
					partides.setTorn(0);
					partida.saveOrUpdate(partides);

					// GUARDAMOS LOS ROLES EN LA BASE DE DATOS
					rol.saveOrUpdate(lobo);
					rol.saveOrUpdate(vidente);
					rol.saveOrUpdate(pueblo);

					// ASSIGNAMOS LOS ROLES A LOS USUARIOS
					RolJugadorPartida.inici(partides, usuarios, lobo, vidente, pueblo);

					System.out.println("Partida iniciada.");

				} else {
					System.out.println("Per iniciar una partida primer has de crear la partida.");
				}

				break;

			case 7:
				if (partidaI) {
					votacions = true;
					// MOSTRAMOS LOS USUARIOS VIVOS
					votos.clear();
					usersVius = RolJugadorPartida.jugadorsVius(partides);
					if (partides.getTorn() % 2 != 0) {

						System.out.println();
						System.out.println("TORN DE DIA");
						System.out.println();
						System.out.println("-----------------------------------------");
						System.out.println("LLISTA D'USUARIS A VOTAR QUE ESTIGUIN VIUS");
						System.out.println("-----------------------------------------");
						System.out.println();

						for (RolJugadorPartida rolJugadorPartida : usersVius) {

							System.out.println(rolJugadorPartida.getUser().getUserName());

						}

						// INICIAMOS LAS VOTACIONES PARA CADA USUARIO
						System.out.println("RECOMPTE DE TOTS ELS USUARIS VIUS");
						boolean correcto;

						boolean encontrado;

						for (RolJugadorPartida rolJugadorPartida : usersVius) {
							User receiver = null;
							correcto = false;
							encontrado = false;

							while (!correcto) {

								System.out.println("A qui vols votar " + rolJugadorPartida.getUser().getUserName());
								String voto = reader.next();

								if (!voto.equals(rolJugadorPartida.getUser().getUserName())) {

									for (RolJugadorPartida users2 : usersVius) {

										if (users2.getUser().getUserName().equals(voto)) {

											receiver = users2.getUser();

											encontrado = true;

										}

									}
									if (encontrado) {

										if (!rolJugadorPartida.getUser().getUserName().equals(receiver.getUserName())) {

											Vot voto2 = new Vot(partides, partides.getTorn(),
													rolJugadorPartida.getUser(), receiver);
											vot.saveOrUpdate(voto2);
											votos.add(voto2);
											correcto = true;
										}
									} else {
										System.out.println("L' usuari no s'ha trobat.");
									}
								} else {
									System.out.println("No pots votar-te a tu mateix.");
								}
							}

						}

						partides.setPartidaVot(votos);

					} else {

						System.out.println();
						System.out.println("TORN DE NIT");
						System.out.println();
						System.out.println("-----------------------------------------");
						System.out.println("LLISTA D'USUARIS A VOTAR QUE ESTIGUIN VIUS");
						System.out.println("-----------------------------------------");
						System.out.println();

						for (RolJugadorPartida rolJugadorPartida : usersVius) {
							if (!rolJugadorPartida.getRolJugador().getNom().equals("Lobo")) {
								System.out.println(rolJugadorPartida.getUser().getUserName());
							}

						}

						System.out.println("RECOMPTE DELS LLOPS VIUS");
						// INICIAMOS LAS VOTACIONES PARA CADA USUARIO

						boolean correcto;

						boolean encontrado;

						for (RolJugadorPartida rolJugadorPartida : usersVius) {
							User receiver = null;
							correcto = false;
							encontrado = false;

							while (!correcto) {

								if (rolJugadorPartida.getRolJugador().getNom().equals("Lobo")) {

									System.out.println("A qui vols votar " + rolJugadorPartida.getUser().getUserName());
									String voto = reader.next();

									if (!voto.equals(rolJugadorPartida.getUser().getUserName())) {

										for (RolJugadorPartida users2 : usersVius) {

											if (users2.getUser().getUserName().equals(voto)
													&& !users2.getRolJugador().getNom().equals("Lobo")) {

												receiver = users2.getUser();

												encontrado = true;

											}

										}
										if (encontrado) {

											if (!rolJugadorPartida.getUser().getUserName()
													.equals(receiver.getUserName())) {
												Vot voto2 = new Vot(partides, partides.getTorn(),
														rolJugadorPartida.getUser(), receiver);
												vot.saveOrUpdate(voto2);
												votos.add(voto2);
												correcto = true;
											}
										} else {
											System.out.println("L' usuari no s'ha trobat.");
										}
									} else {
										System.out.println("No pots votar-te a tu mateix.");
									}
								} else {
									correcto = true;
								}
							}

						}

						partides.setPartidaVot(votos);

					}
				} else {
					System.out.println("Per iniciar les votacions primer has d'iniciar la partida.");
				}
				break;

			case 8:
				if (votacions) {
					boolean partidaAcabada = false;
					votacions = false;
					String usuario = partida.fiTorn(partides);

					for (RolJugadorPartida roljugador : partides.getRolJugadorPartida()) {

						if (roljugador.getUser().getUserName().equals(usuario)) {

							roljugador.setViu(false);

							RolJugadorPartida.saveOrUpdate(roljugador);

							usuariMort.matar(roljugador);

							System.out.println("L' usuari " + usuario + " a mort, el seu rol era "
									+ roljugador.getRolJugador().getNom());

						}

					}

					finalizar = true;

					usersVius = RolJugadorPartida.jugadorsVius(partides);

					for (RolJugadorPartida rolJugadorPartida : usersVius) {

						if (rolJugadorPartida.isViu() && rolJugadorPartida.getRolJugador().getNom().equals("Lobo")) {

							contL++;

						}

						if (rolJugadorPartida.isViu() && rolJugadorPartida.getRolJugador().getNom().equals("Vidente")) {

							contV++;

						}

						if (rolJugadorPartida.isViu() && rolJugadorPartida.getRolJugador().getNom().equals("Pueblo")) {

							contP++;

						}
					}
					if (contP + contV <= contL) {
						partidaAcabada = true;
						System.out.println("Han guanyat els llops.");
					}

					if (contL == 0) {
						partidaAcabada = true;
						System.out.println("Ha guanyat el poble.");
					}

					if (partidaAcabada) {
						votacions = false;
						partidaB = false;
						partidaI = false;
					}

					contP = 0;
					contL = 0;
					contV = 0;
				} else {
					System.out.println("Per terminar el torn primer has de fer les votacions.");
				}
				break;
			case 9:
				if (finalizar) {

					for (RolJugadorPartida roljugador : partides.getRolJugadorPartida()) {

						System.out.println(roljugador.getUser().getUserName());

					}

					System.out.println("Indica l'usuari del que vols saber el rol: ");
					String usuario2 = reader.next();

					boolean encontrado2 = false;
					RolJugadorPartida vidente2 = null;
					RolJugadorPartida destapat = null;

					for (RolJugadorPartida roljugador : partides.getRolJugadorPartida()) {

						if (roljugador.getUser().getUserName().equals(usuario2)) {

							encontrado2 = true;
							destapat = roljugador;

						}

						if (roljugador.getRolJugador().getNom().equals("Vidente")) {

							vidente2 = roljugador;

						}

					}

					if (encontrado2) {
						try {

							Rol rolDestapado = RolJugadorPartida.descobrirRol(vidente2, destapat);

							System.out.println(
									"L' usuari " + destapat.getUser().getUserName() + " és " + rolDestapado.getNom());

						} catch (Exception e) {
							System.out.println(e);
						}

					}

					else {

						System.out.println("Usuari no trobat.");

					}

				}

				else {

					System.out.println("El torn és de dia i no pots saber els rols.");

				}
				break;
			case 10:
				if (partidaI) {
					System.out.println();
					System.out.println("LLISTA D'USUARIS QUE ESTAN VIUS");
					System.out.println();

					usersVius = RolJugadorPartida.jugadorsVius(partides);

					for (RolJugadorPartida rolJugadorPartida : usersVius) {

						if (rolJugadorPartida.isViu()) {

							System.out.println(rolJugadorPartida.getUser().getUserName());

						}

					}
				} else {
					System.out.println("Per veure els usuaris que estan vius primer has de iniciar la partida.");
				}
				break;

			case 11:
				if (partidaI) {
					System.out.println();
					System.out.println("LLISTA DE ROLS QUE ESTAN VIUS");
					System.out.println();

					usersVius = RolJugadorPartida.jugadorsVius(partides);

					for (RolJugadorPartida rolJugadorPartida : usersVius) {
						if (rolJugadorPartida.isViu() && rolJugadorPartida.getRolJugador().getNom().equals("Lobo")) {
							contL++;
						}
						if (rolJugadorPartida.isViu() && rolJugadorPartida.getRolJugador().getNom().equals("Vidente")) {
							contV++;
						}
						if (rolJugadorPartida.isViu() && rolJugadorPartida.getRolJugador().getNom().equals("Pueblo")) {
							contP++;
						}
					}

					System.out.println("Queden " + contL + " llops.");
					System.out.println("Queda " + contV + " vident.");
					System.out.println("Queden " + contP + " pobles.");

				} else {
					System.out.println("Per veure els rols que estan vius primer has de iniciar la partida.");
				}
				break;
			case 12:
				if (partidaI) {
					boolean enviarMensaje = false;

					usersVius = RolJugadorPartida.jugadorsVius(partides);

					for (RolJugadorPartida rolJugadorPartida : usersVius) {
						System.out.println(rolJugadorPartida.getUser().getUserName());
					}
					System.out.println("Sel·lecciona l' usuari que enviara el missatge: ");
					String userSender = reader.next();
					for (RolJugadorPartida rolJugadorPartida : usersVius) {

						if (rolJugadorPartida.getUser().getUserName().equals(userSender)) {

							Sender = rolJugadorPartida;

							enviarMensaje = true;

						}

					}

					if (enviarMensaje) {

						Date fecha = new Date();

						System.out.println("Escriu un missatge: ");
						String contenido = reader.nextLine();
						contenido = reader.nextLine();

						XatMessage xm = new XatMessage(Sender.getUser(), partides, contenido, fecha);

						mensajesUsuarios.add(xm);

						partides.setXatPartida(mensajesUsuarios);

						xat.escriuMissatgeXat(Sender, contenido, fecha);

					}
				} else {
					System.out.println("Per escriure un missatge al xat primer has de iniciar la partida.");
				}
				break;
			case 13:
				if (partidaI) {

					try {

						mensajesGeneral = xat.getXat(partides);

						for (XatMessage xatMessage : mensajesGeneral) {

							System.out.println(xatMessage.toString());

						}

					} catch (Exception e) {

						System.out.println("No hi ha missatges.");

					}

				} else {
					System.out.println("Per veure els missatges del xat primer has de iniciar la partida.");
				}
				break;

			case 14:
				if (partidaI) {

					usersVius = RolJugadorPartida.jugadorsVius(partides);

					boolean enviar = false;

					for (RolJugadorPartida rolJugadorPartida : usersVius) {
						if (rolJugadorPartida.getRolJugador().getNom().equals("Lobo")) {
							System.out.println(rolJugadorPartida.getUser().getUserName());
						}
					}
					System.out.println("Sel·lecciona l' usuari que enviara el missatge: ");
					String lobito = reader.next();
					for (RolJugadorPartida rolJugadorPartida : usersVius) {
						if (rolJugadorPartida.getUser().getUserName().equals(lobito)) {
							if (rolJugadorPartida.getRolJugador().getNom().equals("Lobo")) {
								if (partides.getTorn() % 2 == 0) {
									Sender = rolJugadorPartida;
									enviar = true;
								} else {
									System.out.println(
											"No pot enviar un missatge per què es torn de dia, has de realitzar les votacions i finalitzar el torn.");
								}
							}
						}
					}

					if (enviar) {
						Date fecha = new Date();
						System.out.println("Escriu un missatge: ");
						String contenido = reader.nextLine();
						contenido = reader.nextLine();
						XatMessage xm = new XatMessage(Sender.getUser(), partides, contenido, fecha);
						mensajesLobos.add(xm);
						partides.setXatPartida(mensajesLobos);
						xat.escriuMissatgeLlop(Sender, contenido, fecha);
					}
				} else {
					System.out.println("Per escriure un missatge al xat dels llops primer has de iniciar la partida.");
				}
				break;
			case 15:
                if (partidaI) {

                    if (partides.getTorn() % 2 == 0) {
                        for (RolJugadorPartida roljugador : partides.getRolJugadorPartida()) {

                            if (roljugador.getRolJugador().getNom().equals("Lobo")) {

                                Sender = roljugador;

                            }

                        }

                        try {

                            MostrarMensajesLobos = xat.getXatLlops(Sender);

                            if (MostrarMensajesLobos.size() != 0) {

                                System.out.println();
                                System.out.println("MISSATGES DELS LLOPS");
                                System.out.println();

                                for (XatMessage xatMessage : MostrarMensajesLobos) {

                                    System.out.println(xatMessage.toString());

                                }

                            }

                        } catch (Exception e) {

                            System.out.println("No hi ha missatges llop.");

                        }

                    } else {
                        System.out.println(
                                "No es pot rebre missatges per què es torn de dia, has de realitzar les votacions i finalitzar el torn.");
                    }

                } else {
                    System.out.println("Per veure els missatges del xat dels llops primer has de iniciar la partida.");
                }
                break;
			case 16:

				try {

					System.out.println("Indica el torn de la partida per a saber els vots: ");
					int torn = reader.nextInt();

					votosPartida = vot.getHistorial(torn);

					if (votosPartida.size() == 0) {
						System.out.println("No hi ha cap vot en aquest torn.");
					} else {
						System.out.println();
						System.out.println("HISTORIAL DE VOTS");
						System.out.println();

					}

					for (Vot vots : votosPartida) {

						System.out.println(vots.toString());

					}
				} catch (Exception e) {

					System.out.println(e);

				}

				break;

			case 17:
				try {
					System.out.println("Indica el torn de la partida per a saber les morts: ");
					int torn = reader.nextInt();

					muertos = usuariMort.getMorts(torn);

					if (muertos.size() == 0) {
						System.out.println("No hi ha cap mort en aquest torn.");
					} else {
						System.out.println();
						System.out.println("LLISTA DE MORTS EN UN TORN");
						System.out.println();
					}
					for (Mort mort : muertos) {
						System.out.println(mort.toString());
					}
				} catch (Exception e) {
					System.out.println(e);
				}
				break;

			case 0:
				break;
			default:
				System.out.println("Opció incorrecte.");
			}

		} while (opcion != 0);
		System.out.println("Partida acabada.");
		reader.close();
	}

}
