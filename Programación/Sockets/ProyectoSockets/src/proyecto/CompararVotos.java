package proyecto;

import java.util.Comparator;

public class CompararVotos implements Comparator<Cliente> {

	@Override
	public int compare(Cliente g1, Cliente g2) {

		if (g1.getVotos() != g2.getVotos())
			return (g1.getVotos() - g2.getVotos());
		else {
			return 0;
		}
	}
}

