/**
 * Klasse Fee.java
 * Stellt Methoden zur Verfügung, um das Honorar inklusive Reisekosten zu berechnen.
 * Für vier verschiedene Szenarien gibt es vier überladene Methoden. 
 */
package com.linuxmaker.calculator;

import javax.swing.text.StyledEditorKit.BoldAction;

/**
 * @author Andreas Günther, IT-LINUXMAKER
 *
 */
public class Fee {
	private Double drivingTime = Double.parseDouble(new Settings().readSettings("drivingTime"));
	private Double maxDistance = Double.parseDouble(new Settings().readSettings("maxdistance"));
	private Double minFee = Double.parseDouble(new Settings().readSettings("minFee"));
	private Double workingHours = Double.parseDouble(new Settings().readSettings("workinghours"));
	private Double railBonus = Double.parseDouble(new Settings().readSettings("railCard"));
	private Double consumption = Double.parseDouble(new Settings().readSettings("consumption"));
	private Double fuelConsumption = Double.parseDouble(new Settings().readSettings("fuelConsumption"));
	private Double fuelprice = Double.parseDouble(new Settings().readSettings("fuelprice"));
	
	/*
	 * Main method, used by the graphical user interface
	 */
	public Double feeCalculator (String city, 
								 Double travelDistance, 
								 Double fee, 
								 Double hoursPerDay, 
								 Double sconto, 
								 int projektdays, 
								 int overnightStay, 
								 Boolean drive) 
	{
		Double distance = Double.parseDouble(new XMLCreator().readXML(city).get(1));
		Double monthlyTicket = Double.parseDouble(new XMLCreator().readXML(city).get(3));
		Double roundTripTicket = Double.parseDouble(new XMLCreator().readXML(city).get(4));
		Double hotelCosts = Double.parseDouble(new XMLCreator().readXML(city).get(5));	
		Boolean publictransport = Boolean.parseBoolean(new XMLCreator().readXML(city).get(6));
		Double account = null;
		
		if (drive) {
			if (overnightStay == 0) { // Tägliches Pendeln
				if (fee < minFee) { // Stundensatz
						account = (fee*hoursPerDay + (consumption/100 + fuelConsumption/100 * fuelprice) * distance * 2) * sconto;
				} else { // Tagessatz
						account = (fee*factorWorkingHours(workingHours, hoursPerDay) + (consumption/100 + fuelConsumption/100 * fuelprice) * distance * 2) * sconto;
				}
			} else { // Wöchentliches Pendeln
				if (fee < minFee) { // Stundensatz
					account = (fee*hoursPerDay + (consumption/100 + fuelConsumption/100 * fuelprice) * distance * 2 + overnightStay * hotelCosts) * sconto;
				} else { // Tagessatz
					account = (fee*factorWorkingHours(workingHours, hoursPerDay) + (consumption/100 + fuelConsumption/100 * fuelprice) * distance * 2 + overnightStay * hotelCosts) * sconto;
				}
			}
		} else {
			if (travelDistance <= maxDistance) { // Monatsticket
				if (overnightStay == 0) { // Tägliches Pendeln
					if (fee < minFee) { // Stundensatz
						if (roundTripTicket > monthlyTicket/projektdays || publictransport) { // Verwendung des Monatstickets(3)
							account = calculateFee(fee*hoursPerDay, monthlyTicket, sconto, projektdays);
						} else { // Verwendung des Normaltickets(4)
							account = calculateFee(fee*hoursPerDay, roundTripTicket, sconto, railBonus);
						}	
					} else { // Tagessatz
						if (roundTripTicket > monthlyTicket/projektdays || publictransport) { // Verwendung des Monatstickets(3)
							account = calculateFee(fee*factorWorkingHours(workingHours, hoursPerDay), monthlyTicket, sconto, projektdays);
						} else { // Verwendung des Normaltickets(4)
							account = calculateFee(fee*factorWorkingHours(workingHours, hoursPerDay), roundTripTicket, sconto, railBonus);
						}
					}
				} else { // Wöchentliches Pendeln
					if (fee < minFee) { // Stundensatz
						if (roundTripTicket > monthlyTicket/projektdays) { // Verwendung des Monatstickets(3)
							account = calculateFee(fee*hoursPerDay, monthlyTicket, sconto, hotelCosts, projektdays);
						} else { // Verwendung des Normaltickets(4)
							account = calculateFee(fee*hoursPerDay, roundTripTicket, sconto, railBonus, hotelCosts, overnightStay);
						}
					} else { // Tagessatz
						if (roundTripTicket > monthlyTicket/projektdays) { // Verwendung des Monatstickets(3)
							account = calculateFee(fee*factorWorkingHours(workingHours, hoursPerDay), monthlyTicket, sconto, hotelCosts, projektdays);
						} else { // Verwendung des Normaltickets(4)
							account = calculateFee(fee*factorWorkingHours(workingHours, hoursPerDay), roundTripTicket, sconto, railBonus, hotelCosts, overnightStay);
						}
					}
				}		
			} else { // Normalticket kommt zum Einsatz
				if (fee < minFee) { // Stundensatz
					account = calculateFee(fee*hoursPerDay, roundTripTicket, sconto, railBonus, hotelCosts, overnightStay);
				} else { // Tagessatz
					account = calculateFee(fee*factorWorkingHours(workingHours, hoursPerDay), roundTripTicket, sconto, railBonus, hotelCosts, overnightStay);
				}
			}
		}		
		return account;
	}
	
	public Double netFeeCalculator(String city, 
								   Double travelDistance, 
								   Double fee, 
								   Double hoursPerDay, 
								   Double sconto, 
								   int projektdays, 
								   int overnightStay, 
								   Boolean drive) 
	{
		Double distance = Double.parseDouble(new XMLCreator().readXML(city).get(1));
		Double monthlyTicket = Double.parseDouble(new XMLCreator().readXML(city).get(3));
		Double roundTripTicket = Double.parseDouble(new XMLCreator().readXML(city).get(4));
		Double hotelCosts = Double.parseDouble(new XMLCreator().readXML(city).get(5));
		Boolean publictransport = Boolean.parseBoolean(new XMLCreator().readXML(city).get(6));
		Double account = null;
		if (drive) {
			if (overnightStay == 0) { // Tägliches Pendeln
				if (fee < minFee) { // Stundensatz
					account = fee*hoursPerDay/sconto - (consumption/100 + fuelConsumption/100 * fuelprice) * distance * 2;
				} else { // Tagessatz
					account = fee*factorWorkingHours(workingHours, hoursPerDay)/sconto - (consumption/100 + fuelConsumption/100 * fuelprice) * distance * 2;
				}
			} else { // Wöchentliches Pendeln
				if (fee < minFee) { // Stundensatz
					account = fee*hoursPerDay/sconto - (consumption/100 + fuelConsumption/100 * fuelprice) * distance * 2 + overnightStay * hotelCosts;
				} else { // Tagessatz
					account = fee*factorWorkingHours(workingHours, hoursPerDay)/sconto - (consumption/100 + fuelConsumption/100 * fuelprice) * distance * 2 + overnightStay * hotelCosts;
				}
			}
		} else {
			if (travelDistance <= maxDistance) { // Monatsticket
				if (overnightStay == 0) { // Tägliches Pendeln
					if (fee < minFee) { // Stundensatz
						if (roundTripTicket > monthlyTicket/projektdays || publictransport) { // Verwendung des Monatstickets(3)
							account = reCalculateFee(fee*hoursPerDay, monthlyTicket, projektdays, sconto);
						} else { // Verwendung des Normaltickets(4)
							account = reCalculateFee(fee*hoursPerDay, roundTripTicket, railBonus, sconto);
						}	
					} else { // Tagessatz
						if (roundTripTicket > monthlyTicket/projektdays || publictransport) { // Verwendung des Monatstickets(3)
							account = reCalculateFee(fee*factorWorkingHours(workingHours, hoursPerDay), monthlyTicket, projektdays, sconto);
						} else { // Verwendung des Normaltickets(4)
							account = reCalculateFee(fee*factorWorkingHours(workingHours, hoursPerDay), roundTripTicket, railBonus, sconto);
						}
					}
				} else { // Wöchentliches Pendeln
					if (fee < minFee) { // Stundensatz
						if (roundTripTicket > monthlyTicket/projektdays) { // Verwendung des Monatstickets(3)
							account = reCalculateFee(fee*hoursPerDay, monthlyTicket, hotelCosts, projektdays, sconto);
						} else { // Verwendung des Normaltickets(4)
							account = reCalculateFee(fee*hoursPerDay, roundTripTicket, hotelCosts, railBonus, overnightStay, sconto);
						}
					} else { // Tagessatz
						if (roundTripTicket > monthlyTicket/projektdays) { // Verwendung des Monatstickets(3)
							account = reCalculateFee(fee*factorWorkingHours(workingHours, hoursPerDay), monthlyTicket, hotelCosts, projektdays, sconto);
						} else { // Verwendung des Normaltickets(4)
							account = reCalculateFee(fee*factorWorkingHours(workingHours, hoursPerDay), roundTripTicket, hotelCosts, railBonus, overnightStay, sconto);
						}
					}
				}		
			} else { // Normalticket kommt zum Einsatz
				if (fee < minFee) { // Stundensatz
					account = reCalculateFee(fee*hoursPerDay, roundTripTicket, hotelCosts, railBonus, overnightStay, sconto);
				} else { // Tagessatz
					account = reCalculateFee(fee*factorWorkingHours(workingHours, hoursPerDay), roundTripTicket, hotelCosts, railBonus, overnightStay, sconto);
				}
			}
		}
		return account;
	}
	
	/*
	 * Methode um Faktor zu erzeugen, wenn Default-Tagesarbeitszeit von Tagesarbeitszeit beim Kunden abweicht
	 */
	public Double factorWorkingHours(Double defaultWorkingHours, 
									 Double dayWorkingHours) 
	{
		return dayWorkingHours/defaultWorkingHours;
	}
	
	/*
	 * Methode für den Fall Monatsticket und tägliches Pendeln
	 */
	public Double calculateFee(Double fee, 
							   Double monthlyTicket, 
							   Double sconto, 
							   int projektdays) 
	{
		return (fee + monthlyTicket/projektdays)*sconto;				
	}
	
	public Double reCalculateFee(Double fee, 
								 Double monthlyTicket, 
								 int projektdays, 
								 Double sconto) 
	{
		return (fee/sconto) - monthlyTicket/projektdays;				
	}
	
	/*
	 * Methode für den Fall Normalticket und tägliches Pendeln
	 */
	public Double calculateFee(Double fee, 
					 		   Double roundTripTicket, 
					 		   Double sconto, 
					 		   Double railBonus) 
	{
		return (fee + roundTripTicket*railBonus)*sconto;				
	}
	
	public Double reCalculateFee(Double fee, 
								 Double roundTripTicket, 
								 Double railBonus, 
								 Double sconto) 
	{
		return (fee/sconto) - roundTripTicket*railBonus;	
	}
	
	/*
	 * Methode für den Fall Monatsticket, wöchentliches Pendeln und Übernachtung
	 */
	public Double calculateFee(Double fee, 
							   Double monthlyTicket, 
							   Double sconto, 
							   Double hotel, 
							   int projektdays) 
	{
		return (fee + monthlyTicket/projektdays + hotel)*sconto;		
	}
	
	public Double reCalculateFee(Double fee, 
								 Double monthlyTicket, 
								 Double hotel, 
								 int projektdays, 
								 Double sconto) 
	{
		return (fee/sconto) - (monthlyTicket/projektdays + hotel);				
	}
	
	
	/*
	 * Methode für die Fälle Normalticket, wöchentliches Pendeln und Übernachtung
	 */
	public Double calculateFee(Double fee, 
							   Double ticket, 
							   Double sconto, 
							   Double railBonus, 
							   Double hotel, 
							   int  overnightStay) 
	{
		return (fee*(overnightStay + 1) + ticket*railBonus + hotel*overnightStay)/(overnightStay + 1)*sconto;				
	}
	
	public Double reCalculateFee(Double fee, 
								 Double ticket, 
								 Double hotel, 
								 Double railBonus, 
								 int  overnightStay, 
								 Double sconto) 
	{
		return ((fee/sconto)*(overnightStay + 1) - (ticket*railBonus + hotel*overnightStay))/(overnightStay + 1);			
	}
}
