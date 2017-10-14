package mainPackage.gameEngine.jour;

public class Date {

	private String date;

	public Date() {
		date = "00/01/01";
	}

	public Date(int j, int m, int a) {
		if (j < 10) {
			date = "0" + j + "/";
		} else {
			date = j + "/";
		}

		if (m < 10) {
			date += "0" + m + "/";
		} else {
			date += m + "/";
		}

		if (a < 10) {
			date += "0" + a;
		} else {
			date += a;
		}
	}

	public Date(Date date) {
		this.date = date.date.toString();
	}
	
	public Date(String date) {
		this.date = date;
	}
	
	public String toString() {
		return this.date;
	}

	/*
	 * Voila les 6 seules m�thodes qui vont être utilis�es en dehors de cette
	 * classe.
	 */

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Date dateJourSuivant() {
		// Va cr�er une Date a partir de la pr�c�dente.
		Date d = new Date(this);
		d.addJour(1);
		return d;
	}

	public void addJour(int i) {
		// Va changer la date pour que ce soit i jours apr�s l'actuel
		// i >= 0
		if (this.getJour() + i <= 28) {
			this.setJour(this.getJour() + i);
		} else {
			this.addMois((getJour() + i) / 28);
			i = (this.getJour() + i) % 28;
			this.setJour(i);
		}
	}

	public void addMois(int i) {
		// Va changer la date pour que ce soit i mois apr�s l'actuel
		// i >= 0
		if (this.getMois() + i <= 10) {
			this.setMois(this.getMois() + i);
		} else {
			this.addAnnee((getMois() + i) / 10);
			i = (this.getMois() + i) % 10;
			this.setMois(i);
		}
	}

	public void addAnnee(int i) {
		// Va changer la date pour que ce soit i ann�es apr�s l'actuelle
		// i >= 0
		this.setAnnee(this.getAnnee() + i);
	}
	
	
	public boolean superieurDate(Date date)
	// V�rifie que la date objet soit superieur a la date parametre
	{
		if(this.getAnnee() > date.getAnnee())
		{
			return true;
		}
		else if(this.getMois() > date.getMois())
		{
			return true;
		}
		else if(this.getJour() > date.getJour())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public int getJour() {
		String[] temp = date.split("/");
		return Integer.parseInt(temp[0]);
	}

	public int getMois() {
		String[] temp = date.split("/");
		return Integer.parseInt(temp[1]);
	}

	public int getAnnee() {
		String[] temp = date.split("/");
		return Integer.parseInt(temp[2]);
	}

	/*
	 * A priori il ne faudra pas utiliser les m�thodes qui suivent.
	 */
	private void setJour(int i) {
		String[] temp = date.split("/");
		if (i < 0) {
			this.setDate("0" + i + "/" + temp[1] + "/" + temp[2]);
		} else {
			this.setDate(i + "/" + temp[1] + "/" + temp[2]);
		}
	}

	private void setMois(int i) {
		String[] temp = date.split("/");
		if (i < 0) {
			this.setDate(temp[0] + "/" + "0" + i + "/" + temp[2]);
		} else {
			this.setDate(temp[0] + "/" + i + "/" + temp[2]);
		}
	}

	private void setAnnee(int i) {
		String[] temp = date.split("/");
		if (i < 0) {
			this.setDate(temp[0] + "/" + temp[1] + "/" + "0" + i);
		} else {
			this.setDate(temp[0] + "/" + temp[1] + "/" + i);
		}
	}
	
	public boolean equals(Date date) {
		return this.getDate().equals(date.getDate());
	}

}
