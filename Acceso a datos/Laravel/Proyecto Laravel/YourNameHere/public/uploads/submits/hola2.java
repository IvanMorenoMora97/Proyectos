import java.util.Scanner;

public class hola2 {

	public static void main(String[] args) {
		
		
		//Programa que pide un numero por pantalla y lo multiplica x 5
		
		  Scanner reader = new Scanner(System.in); 
		//Pide casos de prueba
		  
		int casos=reader.nextInt();
		
		  
		for (int i = casos;i>0;i--) {
			
			
			int numero=reader.nextInt();
			System.out.println(numero*5);
			
			
		}
		
	}
	
}
