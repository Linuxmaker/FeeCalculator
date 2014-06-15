/**
 * Klasse Honorar.java
 * Stellt Methoden zur Verfügung, um das Honorar inklusive Reisekosten zu berechnen.
 * Für vier verschiedene Szenarien gibt es vier überladene Methoden. 
 */
package com.linuxmaker.calculator;

/**
 * @author Andreas Günther, IT-LINUXMAKER
 *
 */
public class Fee {
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
	 * Methode für die Fälle Normalticket oder Flugticket, wöchentliches Pendeln und Übernachtung
	 */
	public Double calculateHonorar(Double fee, Double ticket, Double sconto, Double railBonus, Double hotel, int  overnightStay) {
		return (fee*(overnightStay + 1) + ticket + hotel*overnightStay)/(overnightStay + 1)*sconto;				
	}
	
	public Double reCalculateHonorar(Double fee, Double ticket, Double hotel, Double railBonus, int  overnightStay, Double sconto) {
		return ((fee/sconto)*(overnightStay + 1) - (ticket*railBonus + hotel*overnightStay))/(overnightStay + 1);			
	}
}
