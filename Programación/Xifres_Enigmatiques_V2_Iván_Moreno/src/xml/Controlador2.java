package xml;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Controlador2 implements Initializable {

	@FXML
	Text objetivo;

	@FXML
	Text numeroUsar;

	@FXML
	Text numerito;

	@FXML
	Text operacion1;

	@FXML
	Text operacion2;

	@FXML
	Text operacion3;

	@FXML
	Text operacion4;

	@FXML
	Text operacion5;

	@FXML
	Button ventanaInicial;

	@FXML
	Button sumar;

	@FXML
	Button restar;

	@FXML
	Button multiplicar;

	@FXML
	Button dividir;

	@FXML
	Button limpiar;

	@FXML
	Button numero1;

	@FXML
	Button numero2;

	@FXML
	Button numero3;

	@FXML
	Button numero4;

	@FXML
	Button numero5;

	@FXML
	Button numero6;

	@FXML
	Button interrogante1;

	@FXML
	Button interrogante2;

	@FXML
	Button interrogante3;

	@FXML
	Button interrogante4;

	@FXML
	Button interrogante5;

	private String signo;

	private int resultOperacion;

	private boolean valido = true;

	private String[] particion;

	private ArrayList<Button> botones = new ArrayList<Button>();

	private ArrayList<Integer> numerosCalcular = new ArrayList<Integer>();

	private int resultado = 0;

	@FXML
	private void random(ActionEvent event) {

		empezar();

		ArrayList<Integer> numeros = new ArrayList<Integer>();

		boolean salida = false;

		// GENERAR LOS NUMEROS RANDOM
		int random1 = (int) (Math.random() * 100 + 1);
		int random2 = (int) (Math.random() * 75 + 1);
		int random3 = (int) (Math.random() * 50 + 1);
		int random4 = (int) (Math.random() * 25 + 1);
		int random5 = (int) (Math.random() * 10 + 1);
		int random6 = (int) (Math.random() * 10 + 1);

		numero1.setText(String.valueOf(random1));
		numero2.setText(String.valueOf(random2));
		numero3.setText(String.valueOf(random3));
		numero4.setText(String.valueOf(random4));
		numero5.setText(String.valueOf(random5));
		numero6.setText(String.valueOf(random6));

		numeros.add(random1);
		numeros.add(random2);
		numeros.add(random3);
		numeros.add(random4);
		numeros.add(random5);
		numeros.add(random6);

		while (!salida) {

			for (Iterator iterator = numeros.iterator(); iterator.hasNext();) {
				Integer integer = (Integer) iterator.next();

				int signoRandom = (int) (Math.random() * 4);

				// SUMA
				if (signoRandom == 0) {

					if (resultado == 0) {

						System.out.println("SUMA");

						System.out.println(resultado + " = " + resultado + " + " + integer);

						resultado = resultado + integer;

						iterator.remove();

					}

					else {

						System.out.println("SUMA");

						System.out.println(resultado + " = " + resultado + " + " + integer);

						resultado = resultado + integer;

						iterator.remove();

					}

				}

				// RESTA
				if (signoRandom == 1) {

					if (resultado == 0) {

						System.out.println("RESTA");

						System.out.println(resultado + " = " + resultado + " + " + integer);

						resultado = resultado + integer;

						iterator.remove();

					}

					else {

						if (resultado - integer > 0) {

							System.out.println("RESTA");

							System.out.println(resultado + " = " + resultado + " - " + integer);

							resultado = resultado - integer;

							iterator.remove();

						}

					}

				}

				// MULTIPLICAR
				if (signoRandom == 2) {

					if (resultado == 0) {

						System.out.println("MULTIPLICAR");

						System.out.println(resultado + " = " + resultado + " + " + integer);

						resultado = resultado + integer;

						iterator.remove();

					}

					else {

						System.out.println("MULTIPLICAR");

						System.out.println(resultado + " = " + resultado + " * " + integer);

						resultado = resultado * integer;

						iterator.remove();

					}

				}

				// DIVIDIR
				if (signoRandom == 3) {

					if (resultado == 0) {

						System.out.println("DIVIDIR");

						System.out.println(resultado + " = " + resultado + " + " + integer);

						resultado = resultado + integer;

						iterator.remove();

					}

					else {

						if (resultado / integer > 0) {

							System.out.println("DIVIDIR");

							System.out.println(resultado + " = " + resultado + " / " + integer);

							resultado = resultado / integer;

							iterator.remove();

						}

					}

				}

			}

			if (resultado >= 100 && resultado <= 999) {

				salida = true;

			}

			else {

				salida = false;

				numeros.clear();

				numeros.add(random1);
				numeros.add(random2);
				numeros.add(random3);
				numeros.add(random4);
				numeros.add(random5);
				numeros.add(random6);

				resultado = 0;

			}

		}

		System.out.println("RESULTADO A ADQUIRIR: " + resultado);

		objetivo.setText("Objectiu: " + String.valueOf(resultado));

	}

	@FXML
	private void limpiar(ActionEvent event) {

		empezar();

	}

	@FXML
	private void numeros(ActionEvent event) {

		int numero = 0;

		Button boton = (Button) event.getSource();

		numero = Integer.parseInt(boton.getText());

		boton.setDisable(true);

		botones.add(boton);

		numerosCalcular.add(numero);

		if (!boton.getText().equals("+") || !boton.getText().equals("*") || !boton.getText().equals("-")
				|| !boton.getText().equals("/")) {

			numero1.setDisable(true);

			numero2.setDisable(true);

			numero3.setDisable(true);

			numero4.setDisable(true);

			numero5.setDisable(true);

			numero6.setDisable(true);

		}

		if (numerosCalcular.size() == 2) {

			if (signo.equals("+")) {

				resultOperacion = suma(numerosCalcular.get(0), numerosCalcular.get(1));

				System.out.println("RESULTADO DE LA SUMA " + resultOperacion);

				// ENTRA

				insertar();

			}

			if (signo.equals("*")) {

				resultOperacion = multiplicacion(numerosCalcular.get(0), numerosCalcular.get(1));

				System.out.println("RESULTADO DE LA MULTIPLICACION " + resultOperacion);

				// ENTRA

				insertar();

			}

			if (signo.equals("/")) {

				resultOperacion = division(numerosCalcular.get(0), numerosCalcular.get(1));

				System.out.println("RESULTADO DE LA DIVISION " + resultOperacion);

				// ENTRA

				insertar();

			}

			if (signo.equals("-")) {

				resultOperacion = resta(numerosCalcular.get(0), numerosCalcular.get(1));

				System.out.println("RESULTADO DE LA RESTA " + resultOperacion);

				// ENTRA

				insertar();

			}

			numerosCalcular.clear();
			numerosCalcular.add(resultOperacion);

		}

		System.out.println(numero);

	}

	@FXML
	private void operaciones(ActionEvent event) {

		Button boton = (Button) event.getSource();

		signo = boton.getText();

		System.out.println(signo);

		numero1.setDisable(false);

		numero2.setDisable(false);

		numero3.setDisable(false);

		numero4.setDisable(false);

		numero5.setDisable(false);

		numero6.setDisable(false);

		for (Button button : botones) {
			button.setDisable(true);
		}

	}

	@FXML
	private void ventanaInicial(ActionEvent event) {

		try {

			Stage stage = (Stage) this.ventanaInicial.getScene().getWindow();
			stage.close();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Controlador.class.getResource("ventanaprincipal.fxml"));
			Scene scene = new Scene(loader.load());
			Stage stage2 = new Stage();
			stage2.setTitle("Xifres Enigmatiques");
			stage2.setScene(scene);
			stage2.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		random(null);

	}

	public int suma(int numero1, int numero2) {

		if (numero1 + numero2 <= 0) {

			valido = false;
			System.out.println("error al sumar");

		}

		else {

			valido = true;

		}

		return numero1 + numero2;

	}

	public int multiplicacion(int numero1, int numero2) {

		if (numero1 * numero2 <= 0) {

			valido = false;
			System.out.println("error al multiplicar");

		}

		else {

			valido = true;

		}

		return numero1 * numero2;

	}

	public int division(int numero1, int numero2) {

		if (numero1 / numero2 <= 0) {

			valido = false;
			System.out.println("error al dividir");

		}

		else {

			valido = true;

		}

		return numero1 / numero2;

	}

	public int resta(int numero1, int numero2) {

		if (numero1 - numero2 <= 0) {

			valido = false;
			System.out.println("error al restar");

		} else {

			valido = true;

		}

		return numero1 - numero2;

	}

	public void insertar() {

		System.out.println("insertar");

		if (valido) {

			if (operacion1.getText().equals("-")) {

				if (resultOperacion == resultado) {

					bloquear();

				}

				operacion1.setText(numerosCalcular.get(0) + " " + signo + " " + numerosCalcular.get(1));

				interrogante1.setText(String.valueOf(resultOperacion));

				numeroUsar.setText(String.valueOf(resultOperacion));

			}

			else {

				if (operacion2.getText().equals("-")) {

					if (resultOperacion == resultado) {

						bloquear();

					}

					operacion2.setText(numerosCalcular.get(0) + " " + signo + " " + numerosCalcular.get(1));

					interrogante2.setText(String.valueOf(resultOperacion));

					numeroUsar.setText(String.valueOf(resultOperacion));

				}

				else {

					if (operacion3.getText().equals("-")) {

						if (resultOperacion == resultado) {

							bloquear();

						}

						operacion3.setText(numerosCalcular.get(0) + " " + signo + " " + numerosCalcular.get(1));

						interrogante3.setText(String.valueOf(resultOperacion));

						numeroUsar.setText(String.valueOf(resultOperacion));

					}

					else {

						if (operacion4.getText().equals("-")) {

							if (resultOperacion == resultado) {

								bloquear();

							}

							operacion4.setText(numerosCalcular.get(0) + " " + signo + " " + numerosCalcular.get(1));

							interrogante4.setText(String.valueOf(resultOperacion));

							numeroUsar.setText(String.valueOf(resultOperacion));

						}

						else {

							if (operacion5.getText().equals("-")) {

								if (resultOperacion == resultado) {

									bloquear();

								}

								operacion5.setText(numerosCalcular.get(0) + " " + signo + " " + numerosCalcular.get(1));

								interrogante5.setText(String.valueOf(resultOperacion));

								numeroUsar.setText(String.valueOf(resultOperacion));

							}

						}

					}

				}

			}

		}

		else {

			if (operacion1.getText().equals("-")) {

				operacion1.setText("Operación no valida");
				bloquear();
			} else {

				if (operacion2.getText().equals("-")) {

					operacion2.setText("Operación no valida");
					bloquear();
				} else {
					if (operacion3.getText().equals("-")) {

						operacion3.setText("Operación no valida");
						bloquear();
					} else {
						if (operacion4.getText().equals("-")) {

							operacion4.setText("Operación no valida");
							bloquear();
						} else {

							if (operacion5.getText().equals("-")) {

								operacion5.setText("Operación no valida");
								bloquear();
							}

						}

					}

				}

			}

		}

	}

	public void bloquear() {

		if (valido) {

			objetivo.setText("HAS GANADO");

		}

		else {

			objetivo.setText("HAS PERDIDO");

		}

		limpiar.setDisable(true);

		numero1.setDisable(true);

		numero2.setDisable(true);

		numero3.setDisable(true);

		numero4.setDisable(true);

		numero5.setDisable(true);

		numero6.setDisable(true);

		sumar.setDisable(true);

		restar.setDisable(true);

		dividir.setDisable(true);

		multiplicar.setDisable(true);

		interrogante1.setDisable(true);

		interrogante2.setDisable(true);

		interrogante3.setDisable(true);

		interrogante4.setDisable(true);

		interrogante5.setDisable(true);

	}

	public void empezar() {

		limpiar.setDisable(false);

		numero1.setDisable(false);

		numero2.setDisable(false);

		numero3.setDisable(false);

		numero4.setDisable(false);

		numero5.setDisable(false);

		numero6.setDisable(false);

		sumar.setDisable(false);

		restar.setDisable(false);

		dividir.setDisable(false);

		multiplicar.setDisable(false);

		operacion1.setText("-");
		interrogante1.setText("?");
		interrogante1.setDisable(false);

		operacion2.setText("-");
		interrogante2.setText("?");
		interrogante2.setDisable(false);

		operacion3.setText("-");
		interrogante3.setText("?");
		interrogante3.setDisable(false);

		operacion4.setText("-");
		interrogante4.setText("?");
		interrogante4.setDisable(false);

		operacion5.setText("-");
		interrogante5.setText("?");
		interrogante5.setDisable(false);

		botones.clear();

		numerosCalcular.clear();

		// resultado = 0;

		// resultOperacion = 0;

		numeroUsar.setText("");

	}

}
