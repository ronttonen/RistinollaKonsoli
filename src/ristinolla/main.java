package ristinolla;

import java.util.Scanner;
import java.io.*;
import java.util.InputMismatchException;
import java.util.Random;


/**
 * Main class for the main game
 * 
 * 
 * @author Roni
 * 
 *
 */

public class main{
	private static Scanner lu = new Scanner(System.in);
	
	/**
	 * 
	 * Main function game runs inside main function
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		for (String s : args) {
			System.out.println(s);
		} // Voi antaa argumentteja ennen ajoa, tulostaa viestin.
		final Scanner lokilukija = new Scanner(new File("loki.txt"));
		String rivi = "";
		int numberOfGames = 0;
		while (lokilukija.hasNext()) {
			rivi = lokilukija.nextLine();
			if (rivi.contains("Uusi peli")) {
				numberOfGames++;
			}
		} // lukee lokin läpi, kuinka monta peliä on pelattu ja osaa numeroida pelattavan pelin
		lokilukija.close();
		// define variables
		int gametype = 0;
		String p1 = null;
		String p2 = null;
		PrintWriter loki = new PrintWriter(new FileWriter("loki.txt", true)); // avaa lokiin kirjoittajan
		loki.println("");
		loki.println("-----------------------------------------------------------------");
		loki.println("");
		loki.println("Uusi peli");
		loki.println("Peli numero: " + numberOfGames);
		loki.println(" ");
		//gametype must be defined 
		do{
			try { // single or multiplayer
				p("Yksin(1) vai kaksinpeli(2), syötä 1 tai 2");
				gametype = lu.nextInt();
				if (gametype == 1){
					p1 = "bot";
				}
			}
			catch(InputMismatchException e){
				break;
			}
		}
		while(gametype == 1 && gametype == 2);
		
		//Player 1 name always needed
		do{
			try { //p1 name
				p("Pelaajan 1 nimi");
				p1 = lu.next();
				loki.println("Ensimmäisen pelaajan nimi on: " + p1);
			}
			catch(InputMismatchException e){
				break;
			}
		}
		while (p1 == null);
		//get player 2 name if needed
		if (gametype == 2 && p1 != null) {
			
		
		if (gametype == 2 && p1 != null){
			do {
				try { //p2 name
					p("Pelaajan 2 nimi");
					p2 = lu.next();
					loki.println("Toisen pelaajan nimi on: " + p2);
				}
				catch(InputMismatchException e){
					break;
				}
			}
			while (p2 == null);
		}
		
		loki.println("");
		loki.println("Kaksinpeli");
		loki.println("");
		int turn = 0;
		int win = 0;
		int a = 0;
		botDance(1, 250); // LOOPS, WAIT
		char[] taulu = new char[9]; 
		for (int i=0;i<taulu.length;i++){
				a = i+1;
				taulu[i] = Character.forDigit(a, 10);			
		}
		int turn_count = 0;
		do {
			clear();
			tulostaTaulu(taulu);
			win = checkWin(taulu);
			p(win + " ");
			if (win == 1){
				p(p1 + " VOITTI");
				victoryDance(p1);
				turn = 2;
				
			}
			else if(win == 2){
				p(p2 + " VOITTI");
				victoryDance(p2);
				turn = 2;
				
			}
			else if(win == 3){
				p("TASAPELI");
				turn = 2;
				
			}
			else if (win == 0){
				if (turn == 0 && win == 0){
					do{
						
						p("Pelaajan " + p1 + " vuoro syötä 1-9");
						int sy;
						try{
							sy = lu.nextInt(); // TRY CATCH
						}
						catch(InputMismatchException e){
							break;
						}					
						if (sy > 9 || sy < 1){
							break;
						}
						if (taulu[sy-1] != 'X' && taulu[sy-1] != 'O'){
							taulu[sy-1] = 'X';
							turn = 1;
							loki.println(p1 + " laittoi X:n ruutuun: " + sy);
						}
					}
					while (turn == 0);
				}
				else {
					if (gametype == 2 && win == 0){
						do{
							p("Pelaajan " + p2 + " vuoro syötä 1-9");
							int sy;
							try{
								sy = lu.nextInt(); // TRY CATCH
							}
							catch(InputMismatchException e){
								break;
							}
							if (sy > 9 || sy < 1){
								break;
							}
							if (taulu[sy-1] != 'X' && taulu[sy-1] != 'O'){
								taulu[sy-1] = 'O';
								turn = 0;
								loki.println(p2 + " laittoi O:n ruutuun: " + sy);
							}
						}
						while(turn == 1);
					}
					//else if (gametype == 1 && turn == 1 && win == 0){
						//wait(750);
						//clear();
						//tulostaTaulu(taulu);
						//botPelaa(taulu);
						//turn = 0;
					//}
				}
				win = checkWin(taulu);
				if (win == 1){
					p(p1 + " VOITTI");
					victoryDance(p1);
					turn = 2;
					loki.println("");
					loki.println(p1 + " Voitti");
				}
				else if(win == 2){
					p(p2 + " VOITTI");
					victoryDance(p2);
					turn = 2;
					loki.println("");
					loki.println(p2 + " Voitti");
				}
				else if(win == 3){
					p("TASAPELI");
					turn = 2;
					loki.println("");
					loki.println("Tasapeli");
				}
			}
		}
		while(win == 0);
		clear();
		tulostaTaulu(taulu);
		if (win == 1){
			p(p1 + " VOITTI");
		}
		else if(win == 2){
			p(p2 + " VOITTI");
		}
		else if(win == 3){
			p("TASAPELI");
		}
		loki.close();
	}
		else { // yksinpeli
			loki.println("");
			loki.println("Yksinpeli");
			loki.println("");
			p2 = "Tietokone";
			int turn = 1;
			int target;
			int win = 0;
			int a = 0;
			botDance(1, 250); // LOOPS, WAIT
			char[] taulu = new char[9]; 
			for (int i=0;i<taulu.length;i++){
					a = i+1;
					taulu[i] = Character.forDigit(a, 10);			
			} // asettaa pelipöydän ja vuoron pelaajalle
			clear();
			tulostaTaulu(taulu);
			do { //pelin mainloop
				while(turn == 1) {
					
				p("Pelaajan " + p1 + " vuoro syötä 1-9");
				int sy;
				try{
					sy = lu.nextInt(); // TRY CATCH
				}
				catch(InputMismatchException e){
					break;
				}					
				if (sy > 9 || sy < 1){
					break;
				}
				if (taulu[sy-1] != 'X' && taulu[sy-1] != 'O'){
					taulu[sy-1] = 'X';
					loki.println(p1 + " laittoi X:n ruutuun: " + sy);
					turn = 2;
				} // pelaajan vuoro
				}			
				clear();
				tulostaTaulu(taulu);
				win = checkWin(taulu); // tarkistaa onko pelaaja voittanut ennen tietokoneen vuoroa
				if (win == 0) { // tietokoneen vuoro
				p(p2 + "en vuoro");
				wait(750);
				target = botPelaa(taulu, win);
				loki.println("Tietokone laittoi ruutuun: " + target);
				clear();
				tulostaTaulu(taulu);
				win = checkWin(taulu);
				turn = 1;
				}
			
	} while (win == 0); // mainloop jatkuu niin kauan kun kukaan ei ole voittanut, tai tasapeli
			if (win == 1){
				p(p1 + " VOITTI");
				victoryDance(p1);
				loki.println("");
				loki.println(p1 + " Voitti");
			}
			else if(win == 2){
				p(p2 + " VOITTI");
				victoryDance(p2);
				loki.println("");
				loki.println(p2 + " Voitti");
			}
			else if(win == 3){
				p("TASAPELI");
				loki.println("");
				loki.println("Tasapeli");
			}
			if (win == 1) {
				tulostaTaulu(taulu);
				p(p1 + " VOITTI");
			}
			else if (win == 2) {
				tulostaTaulu(taulu);
				p("Tietokone VOITTI");
			} // tulostaa lopputuloksen
			
			
			loki.close(); // sulkee loki kirjoittajan
	}
	}
	
	
	/*FUNCTIONS START*/
	/**
	 * 
	 * A function to stall program for a time. Time chosen is parameter mil.
	 * 
	 * @param mil
	 */
	public static void wait(int mil){ // wait function antaa kuvan, että tietokone miettii
		try{
			Thread.sleep(mil);
		}
		catch(InterruptedException ie){
			
		}
	}
	
	/**
	 * Clears board.
	 * 
	 * 
	 */
	public static void clear(){
		for(int i = 0; i < 25;i++){
		p("");	
		}
	}
	
	/**
	 * Faster system.out.println()
	 * 
	 * @param a
	 */
	public static void p(String a){
		System.out.println(a);
	} // nopeampi tapa printata
	
	/**
	 * An animation made by miska. Takes in a wait time and how many times animation is looped.
	 * 
	 * @param loops
	 * @param wait
	 */
	
	public static void botDance(int loops, int wait){
		for (int i=0;i<loops;i++){
			wait(wait);
			clear();
			p("\\[^.^]/");
			wait(wait);
			clear();
			p("_[^.^]|");
			wait(wait);
			clear();
			p("\\[^.^]/");
			wait(wait);
			clear();
			p("|[^.^]_");
		}
	} // joku ihme animaatio
	
	/**
	 * Prints gameboard. Parameter is board that is made in the beginning of the game
	 * 
	 * 
	 * @param t
	 */
	public static void tulostaTaulu(char[] t){
		
		p("-------------");
		p("| " + t[0] + " | " + t[1] + " | " + t[2] + " |");
		p("-------------");
		p("| " + t[3] + " | " + t[4] + " | " + t[5] + " |");
		p("-------------");
		p("| " + t[6] + " | " + t[7] + " | " + t[8] + " |");
		p("-------------");
		
	} // tulostaa peli taulun.
	
	/**
	 * Checks if anyone has won the game. Takes in game board.
	 * 
	 * 
	 * @param taulu
	 * @return
	 */
	public static int checkWin(char[] taulu){
		  if (	(taulu[0] == 'X' && taulu[1] == 'X' && taulu[2] == 'X') || 
				(taulu[3] == 'X' && taulu[4] == 'X' && taulu[5] == 'X') || 
				(taulu[6] == 'X' && taulu[7] == 'X' && taulu[8] == 'X') || 
				(taulu[0] == 'X' && taulu[4] == 'X' && taulu[8] == 'X') || 
				(taulu[6] == 'X' && taulu[4] == 'X' && taulu[2] == 'X') || 
				(taulu[0] == 'X' && taulu[3] == 'X' && taulu[6] == 'X') || 
				(taulu[1] == 'X' && taulu[4] == 'X' && taulu[7] == 'X') || 
				(taulu[2] == 'X' && taulu[5] == 'X' && taulu[8] == 'X')) {
			  		// X 
			  	return 1;

	        } else if ((taulu[0] == 'O' && taulu[1] == 'O' && taulu[2] == 'O') || 
					(taulu[3] == 'O' && taulu[4] == 'O' && taulu[5] == 'O') || 
					(taulu[6] == 'O' && taulu[7] == 'O' && taulu[8] == 'O') || 
					(taulu[0] == 'O' && taulu[4] == 'O' && taulu[8] == 'O') || 
					(taulu[6] == 'O' && taulu[4] == 'O' && taulu[2] == 'O') || 
					(taulu[0] == 'O' && taulu[3] == 'O' && taulu[6] == 'O') || 
					(taulu[1] == 'O' && taulu[4] == 'O' && taulu[7] == 'O') || 
					(taulu[2] == 'O' && taulu[5] == 'O' && taulu[8] == 'O')) {
	            // O WINS
	        	return 2;
	        } else if (taulu[0] != '1' && taulu[1] != '2' && taulu[2] != '3' && taulu[3] != '4' && taulu[4] != '5'
	                && taulu[5] != '6' && taulu[6] != '7' && taulu[7] != '8' && taulu[8] != '9') {
	        	// DRAW
	        	return 3;
	        }
	        else {
	        	return 0;
	        }

	} // tarkistaa onko kukaan voittanut, tai onko pelipöytä täynnä
	
	//botPelaa palauttaa numeron 0-9 mihin ruutuun se laittaa arvon
	
	/**
	 * Ai for bot. Takes in gameboard and has anyone won the game
	 * @param t
	 * @param win
	 * @return
	 * @throws IOException
	 */
	public static int botPelaa(char[] t, int win) throws IOException{
		// BOT always O
		int target;
		win = checkWin(t);
		if (win == 0) {
			
		do {
			Random rnd = new Random();
			target = rnd.nextInt(9);
			if (t[target] != 'X' && t[target] != 'O') {
				
				int ruutu = target + 1;
				t[target] = 'O';
				win = checkWin(t);
				return ruutu;
				
			}
		
			
		} while( t[target] == 'X' || t[target] == 'O' );
		}
		return win = 2;
			}
	
	/**
	 * Victory dance is great animation by Miska
	 * 
	 * 
	 * @param p
	 */
	
	public static void victoryDance(String p){
		for (int i=0;i<50;i++){
			for (int a=0;a<i;a++){
				System.out.print("-");
			}
			System.out.print(p + " Voitti!");
			for (int b=50;b>i;b--){
				System.out.print("-");
			}
			wait(100);
			clear();
		}
	} // hieno animaatio voittajalle
	
}
