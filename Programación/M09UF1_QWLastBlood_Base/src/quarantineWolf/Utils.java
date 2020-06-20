package quarantineWolf;

public class Utils {
	public static void afegirAlumnes(Poble p) {
		p.afegirPersonatge(new Alumne("AlvaroBarba"));
		p.afegirPersonatge(new Alumne("OscarBorrego"));
		p.afegirPersonatge(new Alumne("OscarBravo"));
		p.afegirPersonatge(new Alumne("AlbertCimas"));
		p.afegirPersonatge(new Alumne("DavidClemente"));
		p.afegirPersonatge(new Alumne("RaulErencia"));
		p.afegirPersonatge(new Alumne("EthanEscriba"));
		p.afegirPersonatge(new Alumne("RubenEsteban"));
		p.afegirPersonatge(new Alumne("DanielGarcia"));
		p.afegirPersonatge(new Alumne("DavidGil"));
		p.afegirPersonatge(new Alumne("RafaelGonzalez"));
		p.afegirPersonatge(new Alumne("AdrianGragera"));
		p.afegirPersonatge(new Alumne("AlejandroHernandez"));
		p.afegirPersonatge(new Alumne("DavidHernandez"));
		p.afegirPersonatge(new Alumne("AndreuInvernon"));
		p.afegirPersonatge(new Alumne("JoanMedina"));
		p.afegirPersonatge(new Alumne("CarlosNieto"));
		p.afegirPersonatge(new Alumne("MarcNuño"));
		p.afegirPersonatge(new Alumne("RogerPuchol"));
		p.afegirPersonatge(new Alumne("JaumePuig"));
		p.afegirPersonatge(new Alumne("AndresQuiros"));
	}

	public static void afegirAlumneExtra(Poble p) {
		p.afegirPersonatge(new Alumne("AaronAbellon_" + p.getHabitants().size()));	
	}
	public static void afegirProfessors(Poble p) {
		p.afegirPersonatge(new Professor("MarcAlbareda"));
		p.afegirPersonatge(new Professor("EloiVazquez"));
		p.afegirPersonatge(new Professor("GregorioSantamaria"));
		p.afegirPersonatge(new Professor("IsabeldeAndres"));
		p.afegirPersonatge(new Professor("NicolasTorrubiano"));
		p.afegirPersonatge(new Professor("PereTomas"));
		p.afegirPersonatge(new Professor("EmparMestre"));
	}
	
	public static void afegirCapEstudis(Poble p) {
		p.afegirPersonatge(new CapEstudis("AlexGuerrero"));
	}

	
	public static void afegirInfiltrats(Poble p) {
		p.afegirPersonatge(new Infiltrat("JoseBermudez"));
		p.afegirPersonatge(new Infiltrat("JonCampaña"));
		p.afegirPersonatge(new Infiltrat("EmilioCasas"));
		p.afegirPersonatge(new Infiltrat("JaredEsparza"));
		p.afegirPersonatge(new Infiltrat("PolLopez"));
		p.afegirPersonatge(new Infiltrat("EmilioMaso"));
		p.afegirPersonatge(new Infiltrat("IvanMoreno"));
		p.afegirPersonatge(new Infiltrat("RoggerOrdoñez"));
		p.afegirPersonatge(new Infiltrat("DimaPanus"));
		p.afegirPersonatge(new Infiltrat("RaulPerez"));
		
	}
}
