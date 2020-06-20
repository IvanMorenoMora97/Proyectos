package programa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import files.Serialization;
import lib.CryptoKeys;
import quarantineWolf.Personatge;
import quarantineWolf.Poble;
import quarantineWolf.Utils;

public class Programa {

	static Scanner input;
	static String missatge;
	static PrivateKey privateKey;
	static PublicKey publicKey;
	static SecretKey secretKey;
	static SecretKey secretKey2;
	static Cipher cipher;
	static boolean generat = false;

	public static void main(String[] args) {

		Poble sbd = new Poble();
		menu(sbd);
	}

	static void menu(Poble sbd) {
		input = new Scanner(System.in);
		int seleccionado = 0;
		List<Personatge> per;
		System.out.println("\n\n\n");

		do {
			seleccionado = displayMenu();

			switch (seleccionado) {
			case 1:
				sbd.getPersonatges().clear();
				Utils.afegirAlumnes(sbd);
				Utils.afegirProfessors(sbd);
				Utils.afegirCapEstudis(sbd);
				Utils.afegirInfiltrats(sbd);
				generat = true;
				break;
			case 2:
				System.out.println(sbd.toString());
				break;
			case 3:
				Utils.afegirAlumneExtra(sbd);
				break;
			case 4:
				Serialization.serializeIterable("src\\data\\comunitatClar.dat", sbd.getPersonatges());
				break;
			case 5:
				per = Serialization.<Personatge>deserialize("src\\data\\comunitatClar.dat");
				sbd.getPersonatges().clear();
				sbd.getPersonatges().addAll(per);
				break;
			case 6:
				System.out.println("Posa el teu missatge:");
				missatge = input.nextLine();
				break;
			case 7:
				System.out.println("El teu missatge és:");
				System.out.println(missatge);
				break;
			case 8:
				FileWriter fichero = null;
				PrintWriter pw = null;
				try {
					fichero = new FileWriter("src\\data\\missatge.txt");
					pw = new PrintWriter(fichero);
					pw.println(missatge);

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (null != fichero)
							fichero.close();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				break;
			case 9:
				File archivo = null;
				FileReader fr = null;
				BufferedReader br = null;

				try {
					archivo = new File("src\\data\\missatge.txt");
					fr = new FileReader(archivo);
					br = new BufferedReader(fr);
					missatge = br.readLine();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (null != fr) {
							fr.close();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				break;
			case 10:

				try {
					KeyPairGenerator KPGenerator = KeyPairGenerator.getInstance("RSA");
					KPGenerator.initialize(1024);
					try {
						Cipher.getInstance("RSA/ECB/PKCS1Padding");
					} catch (NoSuchPaddingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// GENERAR CLAVES PUBLICA Y PRIVADA
					KeyPair KeyPair = KPGenerator.generateKeyPair();
					PublicKey publicKey = KeyPair.getPublic();
					PrivateKey privateKey = KeyPair.getPrivate();

					// GUARDAR CLAVES PUBLICA Y PRIVADA
					try {
						saveKey(publicKey, "src\\keys\\IvanMoreno.pub");
						saveKey(privateKey, "src\\keys\\IvanMoreno.prv");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println("Claus generades.");
				break;
			case 11:

				try {
					Key myKey = CryptoKeys.generateSecretKey("AES", 256);
					try {
						Cipher.getInstance("AES/ECB/PKCS5Padding");
					} catch (NoSuchPaddingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					byte[] encodedKey = myKey.getEncoded();

					SecretKey secretKey = new SecretKeySpec(encodedKey, "AES");

					try {
						saveKey(secretKey, "src\\keys\\IvanMoreno.sim");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			case 12:

				String directorio = "src\\keys";
				File f = new File(directorio);
				File[] claves = f.listFiles();

				for (File file : claves) {

					if (file.getName().contains(".pub")) {
						System.out.println(file.getName());
					}

				}

				System.out.println("Sel·lecciona la clau publica a recuperar: (no cal que fiquis l'extensió)");
				String recuperarPu = input.next();

				try {
					publicKey = loadPublicKey("src\\keys\\" + recuperarPu + ".pub");

					if (publicKey != null) {
						System.out.println("Clau pública recuperada.");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			case 13:

				String directorio2 = "src\\keys";
				File ff = new File(directorio2);
				File[] claves2 = ff.listFiles();

				for (File file : claves2) {

					if (file.getName().contains(".prv")) {
						System.out.println(file.getName());
					}

				}

				System.out.println("Sel·lecciona la clau privada a recuperar: (no cal que fiquis l'extensió)");
				String recuperar = input.next();

				try {
					privateKey = loadPrivateKey("src\\keys\\" + recuperar + ".prv");

					if (privateKey != null) {
						System.out.println("Clau privada recuperada.");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			case 14:

				String directorio3 = "src\\keys";
				File fff = new File(directorio3);
				File[] claves3 = fff.listFiles();

				for (File file : claves3) {

					if (file.getName().contains(".sim")) {
						System.out.println(file.getName());
					}

				}

				System.out.println("Sel·lecciona la clau privada a recuperar: (no cal que fiquis l'extensió)");
				String simetrica = input.next();

				try {
					secretKey = loadSimetricKey("src\\keys\\" + simetrica + ".sim");

					if (secretKey != null) {
						System.out.println("Clau simètrica recuperada.");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			case 15:

				int opcion = 0;

				if (publicKey != null) {

					System.out.println("1. Crear fitxer i encriptar-lo.");
					System.out.println("2. Encriptar fitxer ja existent.");
					opcion = input.nextInt();

					switch (opcion) {

					case 1:

						// CREAR FICHERO DE TEXTO CON EL CONTENIDO
						System.out.println("Indica el nom del fitxer: ");
						String nomFitxer = input.next();

						File fitxer = new File("src\\data\\" + nomFitxer + ".txt");
						BufferedWriter bw = null;
						PrintWriter escribir = null;
						if (fitxer.exists()) {
							System.out.println("El fitxer ja existeix.");
						} else {

							System.out.println("Escriu el que vols introduir al fitxer:");
							String contingut = input.nextLine();
							contingut = input.nextLine();

							try {
								bw = new BufferedWriter(new FileWriter(fitxer));

								escribir = new PrintWriter(bw);
								escribir.println(contingut);
								escribir.flush();
								escribir.close();
								bw.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							System.out.println("Fichero creado");
						}

						// ENCRIPTAR EL FICHERO CREADO ANTERIORMENTE CON LA CLAVE PUBLICA

						try {
							byte[] bytesFitxer = Files.readAllBytes(Paths.get("src\\data\\" + nomFitxer + ".txt"));

							try {

								try {
									cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
								} catch (NoSuchAlgorithmException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (NoSuchPaddingException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								cipher.init(Cipher.ENCRYPT_MODE, publicKey);

								try {

									byte[] byteFinal = cipher.doFinal(bytesFitxer);

									OutputStream out = new FileOutputStream("src\\data\\" + nomFitxer + ".epb");
									out.write(byteFinal);
									out.close();

								} catch (IllegalBlockSizeException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (BadPaddingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							} catch (InvalidKeyException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						break;

					case 2:

						String data = "src\\data";
						File fdata = new File(data);
						File[] ficherosData = fdata.listFiles();

						for (File file : ficherosData) {

							if (file.getName().contains(".txt")) {
								System.out.println(file.getName());
							}

						}

						System.out.println("Sel·lecciona el fitxer a encriptar: ");
						String seleccio = input.next();

						File fileEncrypt = null;

						if (seleccio.contains(".txt")) {

							for (File file : ficherosData) {

								if (file.getName().equals(seleccio)) {
									fileEncrypt = file;
								}

							}

							if (fileEncrypt != null) {

								encriptarFicheroPublico(fileEncrypt, seleccio);

							}
						} else {

							seleccio = seleccio + ".txt";

							for (File file : ficherosData) {

								if (file.getName().equals(seleccio)) {
									fileEncrypt = file;
								}

							}

							if (fileEncrypt != null) {

								encriptarFicheroPublico(fileEncrypt, seleccio);

							}

						}

						break;

					default:
						System.out.println("Opció equivocada.");
						break;

					}

				} else {
					System.out.println("No tens una clau pública, selecciona una clau pública per a recuperar.");
				}

				break;
			case 16:

				if (privateKey != null) {

					System.out.println("Indica que vols encriptar amb la clau privada:");
					System.out.println("1. Clau simétrica.");
					System.out.println("2. Fitxer de text.");
					int opcion2 = input.nextInt();

					switch (opcion2) {

					case 1:
						// ENCRIPTAR CLAU SIMETRICA

						String keys = "src\\keys";
						File fkeys = new File(keys);
						File[] keys3 = fkeys.listFiles();

						for (File file : keys3) {

							if (file.getName().contains(".sim")) {
								System.out.println(file.getName());
							}

						}

						System.out.println("Sel·lecciona la clau simetrica a encriptar: ");
						String simetric = input.next();

						if (simetric.contains(".sim")) {

							File fileSimetric = null;

							for (File file : keys3) {

								if (file.getName().equals(simetric)) {
									fileSimetric = file;
								}

							}

							if (fileSimetric != null) {

								encriptarClaveSimetrica(fileSimetric, simetric);

							} else {

								System.out.println("Clave simetrica no encontrada.");

							}

						} else {

							simetric = simetric + ".sim";

							File fileSimetric = null;

							for (File file : keys3) {

								if (file.getName().equals(simetric)) {
									fileSimetric = file;
								}

							}

							if (fileSimetric != null) {

								encriptarClaveSimetrica(fileSimetric, simetric);

							} else {

								System.out.println("Clave simetrica no encontrada.");

							}

						}

						break;

					case 2:
						// ENCRIPTAR FITXERS
						String data = "src\\data";
						File fdata = new File(data);
						File[] ficherosData = fdata.listFiles();

						for (File file : ficherosData) {

							if (file.getName().contains(".txt")) {
								System.out.println(file.getName());
							}

						}

						System.out.println("Sel·lecciona el fitxer a encriptar: ");
						String seleccio = input.next();

						File filePrivate = null;

						if (seleccio.contains(".txt")) {

							for (File file : ficherosData) {

								if (file.getName().equals(seleccio)) {
									filePrivate = file;
								}

							}

							encriptarFicheroPrivado(filePrivate, seleccio);

						} else {
							seleccio = seleccio + ".txt";
							for (File file : ficherosData) {

								if (file.getName().equals(seleccio)) {
									filePrivate = file;
								}

							}

							encriptarFicheroPrivado(filePrivate, seleccio);

						}

						break;

					default:
						System.out.println("Opció incorrecta.");
						break;

					}

				} else {
					System.out.println("Tens que recuperar la calu privada.");
				}

				break;
			case 17:

				int opcion2 = 0;

				if (secretKey != null) {

					System.out.println("1. Crear fitxer i encriptar-lo.");
					System.out.println("2. Encriptar fitxer ja existent.");
					opcion2 = input.nextInt();

					switch (opcion2) {

					case 1:

						// CREAR FICHERO DE TEXTO CON EL CONTENIDO
						System.out.println("Indica el nom del fitxer: ");
						String nomFitxer = input.next();

						File fitxer = new File("src\\data\\" + nomFitxer + ".txt");
						BufferedWriter bw = null;
						PrintWriter escribir = null;
						if (fitxer.exists()) {
							System.out.println("El fitxer ja existeix.");
						} else {

							System.out.println("Escriu el que vols introduir al fitxer:");
							String contingut = input.nextLine();
							contingut = input.nextLine();

							try {
								bw = new BufferedWriter(new FileWriter(fitxer));

								escribir = new PrintWriter(bw);
								escribir.println(contingut);
								escribir.flush();
								escribir.close();
								bw.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							System.out.println("Fichero creado");
						}

						// ENCRIPTAR EL FICHERO CREADO ANTERIORMENTE CON LA CLAVE SIMETRICA

						try {
							byte[] bytesFitxer = Files.readAllBytes(Paths.get("src\\data\\" + nomFitxer + ".txt"));

							try {

								try {
									cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
								} catch (NoSuchAlgorithmException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (NoSuchPaddingException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								cipher.init(Cipher.ENCRYPT_MODE, secretKey);

								try {

									byte[] byteFinal = cipher.doFinal(bytesFitxer);

									OutputStream out = new FileOutputStream("src\\data\\" + nomFitxer + ".esm");
									out.write(byteFinal);
									out.close();

								} catch (IllegalBlockSizeException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (BadPaddingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							} catch (InvalidKeyException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						break;

					case 2:

						String data = "src\\data";

						File fdata = new File(data);
						File[] ficherosData = fdata.listFiles();

						for (File file : ficherosData) {

							if (file.getName().contains(".txt")) {
								System.out.println(file.getName());
							}

						}

						System.out.println("Sel·lecciona el fitxer a encriptar: ");
						String seleccio = input.next();

						File fileEncrypt = null;

						if (seleccio.contains(".txt")) {

							for (File file : ficherosData) {

								if (file.getName().equals(seleccio)) {
									fileEncrypt = file;
								}

							}

							if (fileEncrypt != null) {

								encriptarFicheroSimetrico(fileEncrypt, seleccio);

							}
						} else {

							seleccio = seleccio + ".txt";

							for (File file : ficherosData) {

								if (file.getName().equals(seleccio)) {
									fileEncrypt = file;
								}

							}

							if (fileEncrypt != null) {

								encriptarFicheroSimetrico(fileEncrypt, seleccio);

							}

						}

						break;

					default:
						System.out.println("Opció incorrecta.");
						break;

					}

				} else {
					System.out.println("Tens que carregar la clau simétrica.");
				}

				break;
			case 18:

				System.out.println("Sel·lecciona una opció: ");
				System.out.println("1. Desencriptar una clau simetrica i carregar-la.");
				System.out.println("2. Desencriptar un fitxer.txt.epv.");
				int o = input.nextInt();

				switch (o) {

				case 1:
					// DESENCRIPTAR CLAU SIMETRICA I CARREGARLA
					if (publicKey != null) {

						// LISTAR CLAVES SIMETRICAS ENCRIPTADAS
						String d = "src\\keys";
						File fi = new File(d);
						File[] keys = fi.listFiles();

						for (File file : keys) {

							if (file.getName().contains(".epv")) {
								System.out.println(file.getName());
							}

						}

						System.out.println("Sel·lecciona la clau a desencriptar: ");
						String decryptKey = input.next();

						File fDecrypt = null;

						if (decryptKey.contains(".epv")) {

							for (File file : keys) {

								if (file.getName().equals(decryptKey)) {
									fDecrypt = file;
								}

							}

							// DESENCRIPTAR CLAU SIMETRICA AMB LA CLAU PUBLICA
							if (fDecrypt != null) {

								byte[] byteSimetrica = null;
								try {
									byteSimetrica = Files.readAllBytes(Paths.get(fDecrypt.getAbsolutePath()));
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								try {
									try {
										// cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
										cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
									} catch (NoSuchAlgorithmException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (NoSuchPaddingException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									cipher.init(Cipher.DECRYPT_MODE, publicKey);
								} catch (InvalidKeyException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								// DESENCRIPTAMOS LOS BYTES DE LA CLAVE SIMETRICA
								byte[] simetrica2 = null;
								try {
									simetrica2 = cipher.doFinal(byteSimetrica);
								} catch (IllegalBlockSizeException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (BadPaddingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								// GENERAMOS UNA NUEVA CLAVE SIMETRICA A TRAVÉS DE LOS BYTES DESENCRIPTADOS Y
								// DECIMOS SI QUIERE CARGARLA COMO ACTIVA
								SecretKey secretKey2 = new SecretKeySpec(simetrica2, "AES");
								System.out.println("Nueva clave simetrica generada.");

								System.out.println();
								System.out.println("Vols carregar la nova clau simetrica? (1. per a sí, 2. per a no)");
								int n = input.nextInt();

								switch (n) {
								case 1:
									System.out.println("Carregant clau simetrica...");
									secretKey = secretKey2;
									break;
								default:
									System.out.println("No s'ha carregat la clau simetrica.");
									break;
								}

							} else {
								System.out.println("No s'ha pogut recuperar la clau.");
							}

						} else {

							decryptKey = decryptKey + ".epv";
							for (File file : keys) {

								if (file.getName().equals(decryptKey)) {
									fDecrypt = file;
								}

							}

							// DESENCRIPTAR CLAU SIMETRICA AMB LA CLAU PUBLICA
							if (fDecrypt != null) {

								byte[] byteSimetrica = null;
								try {
									byteSimetrica = Files.readAllBytes(Paths.get(fDecrypt.getAbsolutePath()));
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								try {
									try {
										// cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
										cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
									} catch (NoSuchAlgorithmException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (NoSuchPaddingException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									cipher.init(Cipher.DECRYPT_MODE, publicKey);
								} catch (InvalidKeyException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								// DESENCRIPTAMOS LOS BYTES DE LA CLAVE SIMETRICA
								byte[] simetrica2 = null;
								try {
									simetrica2 = cipher.doFinal(byteSimetrica);
								} catch (IllegalBlockSizeException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (BadPaddingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								// GENERAMOS UNA NUEVA CLAVE SIMETRICA A TRAVÉS DE LOS BYTES DESENCRIPTADOS Y
								// DECIMOS SI QUIERE CARGARLA COMO ACTIVA
								SecretKey secretKey2 = new SecretKeySpec(simetrica2, "AES");
								System.out.println("Nueva clave simetrica generada.");

								System.out.println();
								System.out.println("Vols carregar la nova clau simetrica? (1. per a sí, 2. per a no)");
								int n = input.nextInt();

								switch (n) {
								case 1:
									System.out.println("Carregant clau simetrica...");
									secretKey = secretKey2;
									break;
								default:
									System.out.println("No s'ha carregat la clau simetrica.");
									break;
								}

							} else {
								System.out.println("No s'ha pogut recuperar la clau.");
							}

						}

					} else {
						System.out.println("Carrega una clau publica.");
					}

					break;

				case 2:
					// DESENCRIPTAR FITXER AMB LA CLAU PÚBLICA
					if (publicKey != null) {

						System.out.println("Sel·lecciona el fitxer a desencriptar:");

						// LISTAR CLAVES SIMETRICAS ENCRIPTADAS
						String d = "src\\data";
						File fi = new File(d);
						File[] keys = fi.listFiles();

						for (File file : keys) {

							if (file.getName().contains(".epv")) {
								System.out.println(file.getName());
							}

						}

						System.out.println("Introdueix el nom del fitxer a desencriptar: ");
						String nom = input.next();

						File fd = null;

						if (nom.contains(".epv")) {

							for (File file : keys) {

								if (file.getName().equals(nom)) {
									fd = file;
								}

							}

							if (fd != null) {

								byte[] byteficherito = null;
								try {
									byteficherito = Files.readAllBytes(Paths.get(fd.getAbsolutePath()));
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								try {
									try {
										// cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
										cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
									} catch (NoSuchAlgorithmException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (NoSuchPaddingException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									cipher.init(Cipher.DECRYPT_MODE, publicKey);
								} catch (InvalidKeyException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								// DESENCRIPTAMOS LOS BYTES DEL FICHERO
								byte[] ficherito = null;
								try {
									ficherito = cipher.doFinal(byteficherito);
								} catch (IllegalBlockSizeException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (BadPaddingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								try {
									String prueba = new String(ficherito, "UTF-8");
									missatge = prueba;
									System.out.println(
											"Missatge generat. Comproba l' opció 7 per a mostrar el missatge.");
								} catch (UnsupportedEncodingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							} else {
								System.out.println("No s'ha pogut recuperar el fitxer.");
							}

						} else {

							nom = nom + ".epv";

							for (File file : keys) {

								if (file.getName().equals(nom)) {
									fd = file;
								}

							}

							if (fd != null) {

								byte[] byteficherito = null;
								try {
									byteficherito = Files.readAllBytes(Paths.get(fd.getAbsolutePath()));
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								try {
									try {

										cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
									} catch (NoSuchAlgorithmException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (NoSuchPaddingException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									cipher.init(Cipher.DECRYPT_MODE, publicKey);
								} catch (InvalidKeyException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								// DESENCRIPTAMOS LOS BYTES DEL FICHERO
								byte[] ficherito = null;
								try {
									ficherito = cipher.doFinal(byteficherito);
								} catch (IllegalBlockSizeException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (BadPaddingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								try {
									String prueba = new String(ficherito, "UTF-8");
									missatge = prueba;
									System.out.println(
											"Missatge generat. Comproba l' opció 7 per a mostrar el missatge.");
								} catch (UnsupportedEncodingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							} else {
								System.out.println("No s'ha pogut recuperar el fitxer.");
							}

						}

					} else {
						System.out.println("Carrega una clau publica.");
					}

					break;

				default:
					System.out.println("Opció incorrecta.");
					break;

				}

				break;
			case 19:

				if (privateKey != null) {

					String d = "src\\data";
					File fi = new File(d);
					File[] keys = fi.listFiles();

					for (File file : keys) {

						if (file.getName().contains(".epb")) {
							System.out.println(file.getName());
						}

					}

					System.out.println("Sel·lecciona el fitxer a desencriptar: ");
					String decrypt = input.next();

					File fDecrypt = null;

					if (decrypt.contains(".epb")) {

						for (File file : keys) {

							if (file.getName().equals(decrypt)) {
								fDecrypt = file;
							}

						}

						if (fDecrypt != null) {

							// AGAFAR BYTES DEL FITXER
							byte[] bytesDecrypt = null;

							try {
								bytesDecrypt = Files.readAllBytes(Paths.get(fDecrypt.getAbsolutePath()));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							try {
								cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
								cipher.init(Cipher.DECRYPT_MODE, privateKey);
							} catch (NoSuchAlgorithmException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (NoSuchPaddingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InvalidKeyException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							byte[] ficheritoDecrypt = null;
							try {
								ficheritoDecrypt = cipher.doFinal(bytesDecrypt);
							} catch (IllegalBlockSizeException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (BadPaddingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							System.out.println(
									"Vols generar un ficher .dat o carregar-lo al missatge? (1. generar fitxer, 2. carregar-lo al missatge)");
							int opcion3 = input.nextInt();

							switch (opcion3) {

							case 1:
								// GENERAR EL FITXER
								String nomFitxer = fDecrypt.getName();
								String[] split = nomFitxer.split("\\.");
								nomFitxer = split[0];
								FileOutputStream fileOuputStream;
								try {
									fileOuputStream = new FileOutputStream("src\\data\\" + nomFitxer + ".dat");
									fileOuputStream.write(ficheritoDecrypt);
									fileOuputStream.close();
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								break;

							case 2:
								String s = new String(ficheritoDecrypt);
								missatge = s;

								break;

							default:
								System.out.println("Opció incorrecta.");
								break;

							}

						} else {
							System.out.println("No s'ha pogut recuperar el fitxer o no s'ha trobat.");
						}

					} else {

						decrypt = decrypt + ".epb";

						for (File file : keys) {

							if (file.getName().equals(decrypt)) {
								fDecrypt = file;
							}

						}

						if (fDecrypt != null) {

							// AGAFAR BYTES DEL FITXER
							byte[] bytesDecrypt = null;

							try {
								bytesDecrypt = Files.readAllBytes(Paths.get(fDecrypt.getAbsolutePath()));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							try {
								cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
								cipher.init(Cipher.DECRYPT_MODE, privateKey);
							} catch (NoSuchAlgorithmException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (NoSuchPaddingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InvalidKeyException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							byte[] ficheritoDecrypt = null;
							try {
								ficheritoDecrypt = cipher.doFinal(bytesDecrypt);
							} catch (IllegalBlockSizeException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (BadPaddingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							System.out.println(
									"Vols generar un ficher .dat o carregar-lo al missatge? (1. generar fitxer, 2. carregar-lo al missatge)");
							int opcion3 = input.nextInt();

							switch (opcion3) {

							case 1:
								// GENERAR EL FITXER
								String nomFitxer = fDecrypt.getName();
								String[] split = nomFitxer.split("\\.");
								nomFitxer = split[0];
								FileOutputStream fileOuputStream;
								try {
									fileOuputStream = new FileOutputStream("src\\data\\" + nomFitxer + ".dat");
									fileOuputStream.write(ficheritoDecrypt);
									fileOuputStream.close();
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								break;

							case 2:
								String s = new String(ficheritoDecrypt);
								missatge = s;

								break;

							default:
								System.out.println("Opció incorrecta.");
								break;

							}

						} else {
							System.out.println("No s'ha pogut recuperar el fitxer o no s'ha trobat.");
						}

					}

				} else {
					System.out.println("Primer tens que carregar la clau privada.");
				}

				break;
			case 20:

				if (secretKey != null) {

					String d = "src\\data";
					File fi = new File(d);
					File[] keys = fi.listFiles();

					for (File file : keys) {

						if (file.getName().contains(".esm")) {
							System.out.println(file.getName());
						}

					}

					System.out.println("Sel·lecciona el fitxer a desencriptar: ");
					String dF = input.next();

					File fD = null;

					if (dF.contains(".esm")) {

						for (File file : keys) {

							if (file.getName().equals(dF)) {

								fD = file;

							}

						}

						if (fD != null) {

							// AGAFAR BYTES DEL FITXER
							byte[] fitxerSimetric = null;
							try {
								fitxerSimetric = Files.readAllBytes(Paths.get(fD.getAbsolutePath()));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							try {
								cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

								try {
									cipher.init(Cipher.DECRYPT_MODE, secretKey);
								} catch (InvalidKeyException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							} catch (NoSuchAlgorithmException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (NoSuchPaddingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							// DESENCRIPTAR BYTES
							byte[] simetrica2 = null;
							try {
								simetrica2 = cipher.doFinal(fitxerSimetric);
							} catch (IllegalBlockSizeException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (BadPaddingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							// CREAR EL NOU FITXER

							String nom = fD.getName();

							String[] partes = nom.split("\\.");
							nom = partes[0];

							FileOutputStream fileOuputStream;
							try {
								fileOuputStream = new FileOutputStream("src\\data\\" + nom + ".dat");
								try {
									fileOuputStream.write(simetrica2);
									fileOuputStream.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} else {
							System.out.println("No s'ha pogut carregar el fitxer.");
						}

					} else {

						dF = dF + ".esm";

						for (File file : keys) {

							if (file.getName().equals(dF)) {

								fD = file;

							}

						}

						if (fD != null) {

							// AGAFAR BYTES DEL FITXER
							byte[] fitxerSimetric = null;
							try {
								fitxerSimetric = Files.readAllBytes(Paths.get(fD.getAbsolutePath()));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							try {
								cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

								try {
									cipher.init(Cipher.DECRYPT_MODE, secretKey);
								} catch (InvalidKeyException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							} catch (NoSuchAlgorithmException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (NoSuchPaddingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							// DESENCRIPTAR BYTES
							byte[] simetrica2 = null;
							try {
								simetrica2 = cipher.doFinal(fitxerSimetric);
							} catch (IllegalBlockSizeException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (BadPaddingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							// CREAR EL NOU FITXER
							String nom = fD.getName();

							String[] partes = nom.split("\\.");
							nom = partes[0];

							FileOutputStream fileOuputStream;
							try {
								fileOuputStream = new FileOutputStream("src\\data\\" + nom + ".dat");
								try {
									fileOuputStream.write(simetrica2);
									fileOuputStream.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} else {
							System.out.println("No s'ha pogut carregar el fitxer.");
						}

					}

				} else {
					System.out.println("Primer tens que carregar la clau simètrica.");
				}

				break;
			case 21:

				if (generat && publicKey != null && privateKey != null) {

					try {
						System.out.println("Guardando datos del objeto en un fichero...");
						ObjectOutputStream guardarObjeto = new ObjectOutputStream(
								new FileOutputStream("src\\data\\comunitat.txt"));
						guardarObjeto.writeObject(sbd);
						guardarObjeto.close();

						MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

						InputStream arxiu = new FileInputStream("src\\data\\comunitat.txt");
						byte[] buffer = new byte[1];
						int caracter = arxiu.read(buffer);

						while (caracter != -1) {

							messageDigest.update(buffer);
							caracter = arxiu.read(buffer);
						}
						arxiu.close();

						byte[] resumen = messageDigest.digest(); // Genera el resumen SHA-1

						// GENERAR EL ARCHIVO CON EL RESUMEN SHA-1 Y CIFRADO CON LA CLAVE PRIVADA

						encriptarBytesPrivado(resumen);

					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					System.out.println("Primer tens que generar la comunitat, la clau pública i privada.");
				}

				break;
			case 22:

				if (publicKey != null) {

					File resumFile = new File("src\\data\\resum.epv");

					desencriptarBytesPrivada(resumFile);

				} else {
					System.out.println("Primer tens que carregar la clau publica.");
				}

			case 0:
				System.out.println("Bye");
				break;
			default:
				break;
			}
		} while (seleccionado != 0);
	}

	private static void desencriptarBytesPrivada(File resumFile) {

		byte[] bytesEncrypt = null;

		try {
			bytesEncrypt = Files.readAllBytes(Paths.get(resumFile.getAbsolutePath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// DESENCRIPTAR LOS BYTES

		try {
			cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, publicKey);

			// RESUM DESENCRIPTAT
			byte[] resum = cipher.doFinal(bytesEncrypt);

			// CALCULAR RESUM (COMPROBAR resumenA con un nuevo resumenB que se conseguira
			// del fichero creado)
			calcularResum(resum);

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void calcularResum(byte[] resumA) {

		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");

			InputStream arxiu = new FileInputStream("src\\data\\comunitat.txt");
			byte[] buffer = new byte[1];
			int caracter = arxiu.read(buffer);

			while (caracter != -1) {

				messageDigest.update(buffer);
				caracter = arxiu.read(buffer);
			}
			arxiu.close();

			byte[] resumB = messageDigest.digest(); // Genera el resumen SHA-1

			// COMPROBAR COINCIDENCIA HASH ENTRE resumA Y resumB
			boolean iguales = Arrays.equals(resumA, resumB);

			if (iguales) {
				System.out.println("Els dos resums són iguals.");
			} else {
				System.out.println("Error, no son iguals.");
			}

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void encriptarBytesPrivado(byte[] resumen) {

		System.out.println("Encriptant fitxer...");

		try {
			byte[] bytesFitxer = resumen;

			try {

				try {
					cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchPaddingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				cipher.init(Cipher.ENCRYPT_MODE, privateKey);

				try {

					byte[] byteFinal = cipher.doFinal(bytesFitxer);

					OutputStream out = new FileOutputStream("src\\data\\resum.epv");
					out.write(byteFinal);
					out.close();

				} catch (IllegalBlockSizeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BadPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void encriptarFicheroSimetrico(File fileEncrypt, String seleccio) {

		System.out.println("Encriptant fitxer...");

		try {
			byte[] bytesFitxer = Files.readAllBytes(Paths.get(fileEncrypt.getAbsolutePath()));

			try {

				try {
					cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchPaddingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				cipher.init(Cipher.ENCRYPT_MODE, secretKey);

				try {

					byte[] byteFinal = cipher.doFinal(bytesFitxer);

					String[] split = seleccio.split("\\.");
					seleccio = split[0];

					OutputStream out = new FileOutputStream("src\\data\\" + seleccio + ".esm");
					out.write(byteFinal);
					out.close();

				} catch (IllegalBlockSizeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BadPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void encriptarClaveSimetrica(File fileEncrypt, String seleccio) {
		System.out.println("Encriptant fitxer...");

		try {
			byte[] bytesFitxer = Files.readAllBytes(Paths.get(fileEncrypt.getAbsolutePath()));

			try {

				try {
					cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchPaddingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				cipher.init(Cipher.ENCRYPT_MODE, privateKey);

				try {

					byte[] byteFinal = cipher.doFinal(bytesFitxer);

					String[] split = seleccio.split("\\.");
					seleccio = split[0];

					OutputStream out = new FileOutputStream("src\\keys\\" + seleccio + ".epv");
					out.write(byteFinal);
					out.close();

				} catch (IllegalBlockSizeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BadPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void encriptarFicheroPrivado(File fileEncrypt, String seleccio) {
		System.out.println("Encriptant fitxer...");

		try {
			byte[] bytesFitxer = Files.readAllBytes(Paths.get(fileEncrypt.getAbsolutePath()));

			try {

				try {
					cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchPaddingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				cipher.init(Cipher.ENCRYPT_MODE, privateKey);

				try {

					byte[] byteFinal = cipher.doFinal(bytesFitxer);

					String[] split = seleccio.split("\\.");
					seleccio = split[0];

					OutputStream out = new FileOutputStream("src\\data\\" + seleccio + ".epv");
					out.write(byteFinal);
					out.close();

				} catch (IllegalBlockSizeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BadPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void encriptarFicheroPublico(File fileEncrypt, String seleccio) {
		System.out.println("Encriptant fitxer...");

		try {
			byte[] bytesFitxer = Files.readAllBytes(Paths.get(fileEncrypt.getAbsolutePath()));

			try {

				try {
					cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchPaddingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				cipher.init(Cipher.ENCRYPT_MODE, publicKey);

				try {

					byte[] byteFinal = cipher.doFinal(bytesFitxer);

					String[] split = seleccio.split("\\.");
					seleccio = split[0];

					OutputStream out = new FileOutputStream("src\\data\\" + seleccio + ".epb");
					out.write(byteFinal);
					out.close();

				} catch (IllegalBlockSizeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BadPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static int displayMenu() {
		System.out.println("1.- Generar Comunitat");
		System.out.println("2.- Veure Comunitat");
		System.out.println("3.- Afegir Alumne Extra");
		System.out.println("4.- Generar Fitxer de Comunitat per enviar en Clar");
		System.out.println("5.- Rebre Fitxer de Comunitat en Clar");
		System.out.println("6.- Generar Missatge");
		System.out.println("7.- Veure Missatge");
		System.out.println("8.- Gravar Missatge pla");
		System.out.println("9.- Carregar Missatge pla");

		System.out.println("10.- Generar parell de Claus RSA i gravar-les");
		System.out.println("11.- Generar Clau Simètrica AES i gravar-la");
		System.out.println("12.- Recuperar clau pública de fitxer");
		System.out.println("13.- Recuperar clau privada de fitxer");
		System.out.println("14.- Recuperar simètrica de Fitxer");
		System.out.println("15.- Encriptar fitxer amb clau pública");
		System.out.println("16.- Encriptar fitxer amb clau privada");
		System.out.println("17.- Encriptar fitxer amb clau simètrica");
		System.out.println("18.- Desencriptar fitxer amb clau pública");
		System.out.println("19.- Desencriptar fitxer amb clau privada");
		System.out.println("20.- Desencriptar fitxer amb clau simètrica");

		System.out.println("21.- Generar Fitxer de Comunitat per enviar Signat");
		System.out.println("22.- Rebre Fitxer de Comunitat Signat");

		System.out.println("\n\n0.- Sortir");
		System.out.print("Opció: ");

		int seleccionado = -1;
		try {
			seleccionado = input.nextInt();
		} catch (Exception e) {

		}
		input.nextLine();
		return seleccionado;
	}

	private static void saveKey(Key key, String fileName) throws Exception {
		byte[] publicKeyBytes = key.getEncoded();

		FileOutputStream fos = new FileOutputStream(fileName);
		fos.write(publicKeyBytes);
		fos.close();
	}

	private static SecretKey loadSimetricKey(String fileName) {
		SecretKey secretKey = null;
		try {
			FileInputStream fis = new FileInputStream(fileName);
			int numBtyes = fis.available();
			byte[] bytes = new byte[numBtyes];
			fis.read(bytes);
			fis.close();

			secretKey = new SecretKeySpec(bytes, "AES");

		} catch (IOException e) {
			System.out.println("No s'ha trobat la clau simètrica.");
		}
		return secretKey;
	}

	private static PrivateKey loadPrivateKey(String fileName) throws Exception {
		PrivateKey keyFromBytes = null;
		try {
			FileInputStream fis = new FileInputStream(fileName);
			int numBtyes = fis.available();
			byte[] bytes = new byte[numBtyes];
			fis.read(bytes);
			fis.close();

			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			KeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
			keyFromBytes = keyFactory.generatePrivate(keySpec);
		} catch (IOException e) {
			System.out.println("No s'ha trobat la clau privada.");
		}
		return keyFromBytes;
	}

	private static PublicKey loadPublicKey(String filename) throws Exception {
		PublicKey clavePublica = null;
		try {
			FileInputStream fis = new FileInputStream(filename);
			int numBytes = fis.available();
			byte[] bytes = new byte[numBytes];
			fis.read(bytes);
			fis.close();

			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			KeySpec keySpec = new X509EncodedKeySpec(bytes);
			clavePublica = keyFactory.generatePublic(keySpec);
		} catch (IOException e) {
			System.out.println("No s'ha trobat la clau publica.");
		}

		return clavePublica;

	}
}
