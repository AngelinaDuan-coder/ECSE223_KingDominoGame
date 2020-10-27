package ca.mcgill.ecse223.kingdomino.controller;

public class InvalidInputException extends Exception {
	
	public InvalidInputException(String errorMessage) {
		super(errorMessage);
	}
}
