package xml;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Controlador implements Initializable {

	// TEXTO
	@FXML
	private Text TResultado1;
	@FXML
	private Text TResultado2;
	@FXML
	private Text TResultado3;
	@FXML
	private Text TResultado4;
	@FXML
	private Text TResultado5;
	@FXML
	private Text TObjectiu;
	@FXML
	private Text NumeroUtilitzar;
	@FXML
	private Text pista;

	// BOTONES

	@FXML
	private Button solucion;

	@FXML
	private Button btnEnigme;

	@FXML
	private Button btnNeteja;

	@FXML
	private Button btnN1;

	@FXML
	private Button btnN2;

	@FXML
	private Button btnN3;

	@FXML
	private Button btnN4;

	@FXML
	private Button btnN5;

	@FXML
	private Button btnN6;

	@FXML
	private Button btnR1;

	@FXML
	private Button btnR2;

	@FXML
	private Button btnR3;

	@FXML
	private Button btnR4;

	@FXML
	private Button btnR5;

	@FXML
	private Button btnSuma;

	@FXML
	private Button btnResta;

	@FXML
	private Button btnMultiplicacion;

	@FXML
	private Button btnDivision;

	private ArrayList<String> lista = new ArrayList<String>();

	private ArrayList<Button> botones = new ArrayList<Button>();

	private int resultado;

	private int n1;

	private int n2;

	private String signo;

	private String objetivo;

	private String[] particion;

	private boolean valido = true;

	// METODO SUMAR CON SUS CONTROLES

	private int sumar(int numero1, int numero2) {

		if (numero1 + numero2 <= 0) {

			valido = false;
			System.out.println("error suma");

		}

		n1 = numero1;
		n2 = numero2;
		signo = "+";

		return numero1 + numero2;

	}

	// METODO RESTAR CON SUS CONTROLES

	private int restar(int numero1, int numero2) {

		if (numero1 - numero2 <= 0) {

			valido = false;
			System.out.println("error resta");

		}

		n1 = numero1;
		n2 = numero2;
		signo = "-";

		return numero1 - numero2;

	}

	// METODO DIVIDIR CON SUS CONTROLES

	private int dividir(int numero1, int numero2) {

		if (numero1 / numero2 > 0) {

			n1 = numero1;
			n2 = numero2;
			signo = "/";

		} else {

			valido = false;
			System.out.println("error al dividir");

		}

		return numero1 / numero2;

	}

	// METODO MULTIPLICAR CON SUS CONTROLES

	private int multiplicar(int numero1, int numero2) {

		if (numero1 * numero2 <= 0) {

			valido = false;
			System.out.println("error multiplicar");

		}

		n1 = numero1;
		n2 = numero2;
		signo = "*";

		return numero1 * numero2;

	}

	@FXML
	private void coger(ActionEvent event) {

		Button boton = (Button) event.getSource();

		String valor = boton.getText();

		particion = TObjectiu.getText().split(" ");

		objetivo = particion[1];

		System.out.println(objetivo);

		// CONTROLAMOS QUE LOS BOTONES QUE SE DESACTIVEN NO SEAN LOS SIGNOS DE LAS
		// OPERACIONES

		if (boton.getText().equals("?")) {

			solucion(event);

		}

		if (!boton.getText().equals("*") && !boton.getText().equals("+") && !boton.getText().equals("-")
				&& !boton.getText().equals("/")) {

			botones.add(boton);

			boton.setDisable(true);

			btnN1.setDisable(true);

			btnN2.setDisable(true);

			btnN3.setDisable(true);

			btnN4.setDisable(true);

			btnN5.setDisable(true);

			btnN6.setDisable(true);

			btnSuma.setDisable(false);

			btnResta.setDisable(false);

			btnMultiplicacion.setDisable(false);

			btnDivision.setDisable(false);

		} else {

			btnSuma.setDisable(true);

			btnResta.setDisable(true);

			btnMultiplicacion.setDisable(true);

			btnDivision.setDisable(true);

			btnN1.setDisable(false);

			btnN2.setDisable(false);

			btnN3.setDisable(false);

			btnN4.setDisable(false);

			btnN5.setDisable(false);

			btnN6.setDisable(false);

		}

		for (Button button : botones) {
			button.setDisable(true);
		}

		lista.add(valor);

		if (lista.contains("*") && lista.size() == 3) {

			resultado = multiplicar(Integer.parseInt(lista.get(0)), Integer.parseInt(lista.get(2)));

			lista.clear();

			insertar(resultado);

			lista.add(String.valueOf(resultado));
		}

		if (lista.contains("+") && lista.size() == 3) {

			resultado = sumar(Integer.parseInt(lista.get(0)), Integer.parseInt(lista.get(2)));

			lista.clear();

			insertar(resultado);

			lista.add(String.valueOf(resultado));

		}

		if (lista.contains("-") && lista.size() == 3) {

			resultado = restar(Integer.parseInt(lista.get(0)), Integer.parseInt(lista.get(2)));

			lista.clear();

			insertar(resultado);

			lista.add(String.valueOf(resultado));
		}

		if (lista.contains("/") && lista.size() == 3) {

			resultado = dividir(Integer.parseInt(lista.get(0)), Integer.parseInt(lista.get(2)));

			lista.clear();

			insertar(resultado);

			lista.add(String.valueOf(resultado));

		}

		if (objetivo.equals(String.valueOf(resultado))) {

			TObjectiu.setText("HAS GANADO MAQUINON");

			btnN1.setDisable(true);

			btnN2.setDisable(true);

			btnN3.setDisable(true);

			btnN4.setDisable(true);

			btnN5.setDisable(true);

			btnN6.setDisable(true);

			btnR1.setDisable(true);

			btnR2.setDisable(true);

			btnR3.setDisable(true);

			btnR4.setDisable(true);

			btnR5.setDisable(true);

			btnSuma.setDisable(true);

			btnResta.setDisable(true);

			btnMultiplicacion.setDisable(true);

			btnDivision.setDisable(true);

			btnNeteja.setDisable(true);

			valido = true;

		}

		if (!valido) {

			TObjectiu.setText("HAS PERDIDO MAQUINON");

			btnN1.setDisable(true);

			btnN2.setDisable(true);

			btnN3.setDisable(true);

			btnN4.setDisable(true);

			btnN5.setDisable(true);

			btnN6.setDisable(true);

			btnR1.setDisable(true);

			btnR2.setDisable(true);

			btnR3.setDisable(true);

			btnR4.setDisable(true);

			btnR5.setDisable(true);

			btnSuma.setDisable(true);

			btnResta.setDisable(true);

			btnMultiplicacion.setDisable(true);

			btnDivision.setDisable(true);

			btnNeteja.setDisable(true);

		}

		System.out.println("numeros: " + valor);

	}

	@FXML
	private void nuevo(ActionEvent event) {
		vaciar();
	}

	@FXML
	private void vaciar() {

		btnN1.setDisable(false);

		btnN2.setDisable(false);

		btnN3.setDisable(false);

		btnN4.setDisable(false);

		btnN5.setDisable(false);

		btnN6.setDisable(false);

		btnR1.setDisable(false);
		btnR1.setText("?");

		btnR2.setDisable(false);
		btnR2.setText("?");

		btnR3.setDisable(false);
		btnR3.setText("?");

		btnR4.setDisable(false);
		btnR4.setText("?");

		btnR5.setDisable(false);
		btnR5.setText("?");

		TResultado1.setText("-");

		TResultado2.setText("-");

		TResultado3.setText("-");

		TResultado4.setText("-");

		TResultado5.setText("-");

		TObjectiu.setText("Objectiu: 560");

		btnNeteja.setDisable(false);

		resultado = 0;

		lista.clear();

		botones.clear();

		valido = true;

		NumeroUtilitzar.setText("");

	}

	private void insertar(int resultado) {

		if (btnR1.getText().equals("?")) {

			System.out.println("BOTON 1");

			btnR1.setText(String.valueOf(resultado));

			if (valido) {

				TResultado1.setText(String.valueOf(n1 + " " + signo + " " + n2 + " = " + resultado));
				NumeroUtilitzar.setText(String.valueOf(resultado));

			}

			else {

				TResultado1.setText("Operación no válida");
				NumeroUtilitzar.setText("");

			}

		}

		else {

			if (btnR2.getText().equals("?")) {

				System.out.println("BOTON 2");

				btnR2.setText(String.valueOf(resultado));

				if (valido) {

					TResultado2.setText(n1 + " " + signo + " " + n2 + " = " + resultado);
					NumeroUtilitzar.setText(String.valueOf(resultado));

				}

				else {

					TResultado2.setText("Operación no válida");
					NumeroUtilitzar.setText("");

				}

			}

			else {

				if (btnR3.getText().equals("?")) {

					System.out.println("BOTON 3");

					btnR3.setText(String.valueOf(resultado));

					if (valido) {

						TResultado3.setText(n1 + " " + signo + " " + n2 + " = " + resultado);
						NumeroUtilitzar.setText(String.valueOf(resultado));

					}

					else {

						TResultado3.setText("Operación no válida");
						NumeroUtilitzar.setText("");

					}

				}

				else {

					if (btnR4.getText().equals("?")) {

						System.out.println("BOTON 4");

						btnR4.setText(String.valueOf(resultado));

						if (valido) {

							TResultado4.setText(n1 + " " + signo + " " + n2 + " = " + resultado);
							NumeroUtilitzar.setText(String.valueOf(resultado));

						}

						else {

							TResultado4.setText("Operación no válida");
							NumeroUtilitzar.setText("");

						}

					}

					else {

						if (btnR5.getText().equals("?")) {

							System.out.println("BOTON 5");

							btnR5.setText(String.valueOf(resultado));

							if (valido) {

								TResultado5.setText(n1 + " " + signo + " " + n2 + " = " + resultado);
								NumeroUtilitzar.setText(String.valueOf(resultado));

							}

							else {

								TResultado5.setText("Operación no válida");
								NumeroUtilitzar.setText("");

							}

						}

					}

				}
			}

		}

	}

	@FXML
	private void solucion(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Controlador.class.getResource("solucion.fxml"));
			Scene scene = new Scene(loader.load());
			Stage stage = new Stage();
			stage.setTitle("Xifres Enigmatiques");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void pistes(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Controlador.class.getResource("pistas.fxml"));
			Scene scene = new Scene(loader.load());
			Stage stage = new Stage();
			stage.setTitle("Xifres Enigmatiques");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}
