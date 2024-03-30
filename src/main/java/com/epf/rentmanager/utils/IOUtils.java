package com.epf.rentmanager.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class IOUtils {
	public static void print(String message) {
		System.out.println(message); 
	}
	public static String readString(String message, boolean mandatory) {
		print(message);
		
		String input = null;
		int attempt = 0;
		
		do {
			if (attempt >= 1) {
				print("Cette valeur est obligatoire");
			}
			
			input = readString();
			attempt++;
		} while (mandatory && (input.isEmpty() || input == null));
		
		return input;
	}
	public static String readString() {
		Scanner scanner = new Scanner(System.in);
		String value = scanner.nextLine();
		
		return value;
	}
	public static int readInt(String message) {
		print(message);
		
		String input = null;
		int output = 0;
		boolean error = false;
		
		do {
			input = readString();
			error = false;
			
			try {
				output = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				error = true;
				print("Veuillez saisir un nombre");
			}
		} while (error);
		
		return output;
	}
	public static LocalDate readDate(String message, boolean mandatory) {
		print(message);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
		LocalDate output = null;
		boolean error = false;
		
		do {
			try {
				error = false;
				String stringDate = readString();
	        	output = LocalDate.parse(stringDate, formatter);
	        } catch (DateTimeParseException e) {
	        	error = true;
	        	print("Veuillez saisir une date valide (dd/MM/yyyy)");
	        } 
		} while (error && mandatory);
        
		return output;
	}
}
