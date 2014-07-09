/**
 * Klasse Fee.java
 * Stellt Methoden zur Verfügung, um das Honorar inklusive Reisekosten zu berechnen.
 * Für vier verschiedene Szenarien gibt es vier überladene Methoden. 
 */
package com.linuxmaker.calculator;

/**
 * @author Andreas Günther, IT-LINUXMAKER
 *
 */
public class Fee {
	private String drivingTime = new Settings().readSettings("drivingTime");
	private Double maxDistance = Double.parseDouble(new Settings().readSettings("maxdistance"));
	private Double minFee = Double.parseDouble(new Settings().readSettings("minFee"));
	private Double workingHours = Double.parseDouble(new Settings().readSettings("workinghours"));
	private Double railBonus = Double.parseDouble(new Settings().readSettings("railCard"));
	
	/*
	 * Main methode, used by the graphical user interface
	 */
	public Double feeCalculator (String city, Double travelDistance, Double fee, Double hoursPerDay, Double sconto, int projektdays, int overnightStay) {
		XMLCreator xmlelement = new XMLCreator();
		Double monthlyTicket = Double.parseDouble(xmlelement.readXML(city).get(3));
		Double roundTripTicket = Double.parseDouble(xmlelement.readXML(city).get(4));
		Double hotelCosts = Double.parseDouble(xmlelement.readXML(city).get(5));
		Double account = null;
		
		if (travelDistance <= maxDistance) { // Monatsticket
			if (fee < minFee) { // Stundensatz
				if (roundTripTicket > monthlyTicket/projektdays) { // Verwendung des Monatstickets(3)
					account = calculateHonorar(fee*hoursPerDay, monthlyTicket, sconto, projektdays);
				} else { // Verwendung des Normaltickets(4)
					account = calculateHonorar(fee*hoursPerDay, roundTripTicket, sconto, railBonus);
				}
			} else { // Tagessatz
				if (roundTripTicket > monthlyTicket/projektdays) { // Verwendung des Monatstickets(3)
					account = calculateHonorar(fee*factorWorkingHours(workingHours, hoursPerDay), monthlyTicket, sconto, projektdays);
				} else { // Verwendung des Normaltickets(4)
					account = calculateHonorar(fee*factorWorkingHours(workingHours, hoursPerDay), roundTripTicket, sconto, railBonus);
				}
			}
		} else { // Normalticket kommt zum Einsatz
			if (fee < minFee) { // Stundensatz
				account = calculateHonorar(fee*hoursPerDay, roundTripTicket, sconto, railBonus, hotelCosts, overnightStay);
			} else { // Tagessatz
				account = calculateHonorar(fee*factorWorkingHours(workingHours, hoursPerDay), roundTripTicket, sconto, railBonus, hotelCosts, overnightStay);
			}
		}
		return account;
	}
	
	/*
	 * Methode um Faktor zu erzeugen, wenn Default-Tagesarbeitszeit von Tagesarbeitszeit beim Kunden abweicht
	 */
	public Double factorWorkingHours(Double defaultWorkingHours, Double dayWorkingHours) {
		return dayWorkingHours/defaultWorkingHours;
	}
	
	/*
	 * Methode für den Fall Monatsticket und tägliches Pendeln
	 */
	public Double calculateHonorar(Double fee, Double monthlyTicket, Double sconto, int projektdays) {
		return (fee + monthlyTicket/projektdays)*sconto;				
	}
	
	public Double reCalculateHonorar(Double fee, Double monthlyTicket, int projektdays, Double sconto) {
		return (fee/sconto) - monthlyTicket/projektdays;				
	}
	
	/*
	 * Methode für den Fall Normalticket und tägliches Pendeln
	 */
	public Double calculateHonorar(Double fee, Double roundTripTicket, Double sconto, Double railBonus) {
		return (fee + roundTripTicket*railBonus)*sconto;				
	}
	
	public Double reCalculateHonorar(Double fee, Double roundTripTicket, Double railBonus, Double sconto) {
		return (fee/sconto) - roundTripTicket*railBonus;	
	}
	
	/*
	 * Methode für den Fall Monatsticket, wöchentliches Pendeln und Übernachtung
	 */
	public Double calculateHonorar(Double fee, Double monthlyTicket, Double sconto, Double hotel, int projektdays) {
		return (fee + monthlyTicket/projektdays + hotel)*sconto;		
	}
	
	public Double reCalculateHonorar(Double fee, Double monthlyTicket, Double hotel, int projektdays, Double sconto) {
		return (fee/sconto) - (monthlyTicket/projektdays + hotel);				
	}
	
	
	/*
	 * Methode für die Fälle Normalticket, wöchentliches Pendeln und Übernachtung
	 */
	public Double calculateHonorar(Double fee, Double ticket, Double sconto, Double railBonus, Double hotel, int  overnightStay) {
		return (fee*(overnightStay + 1) + ticket*railBonus + hotel*overnightStay)/(overnightStay + 1)*sconto;				
	}
	
	public Double reCalculateHonorar(Double fee, Double ticket, Double hotel, Double railBonus, int  overnightStay, Double sconto) {
		return ((fee/sconto)*(overnightStay + 1) - (ticket*railBonus + hotel*overnightStay))/(overnightStay + 1);			
	}
}
