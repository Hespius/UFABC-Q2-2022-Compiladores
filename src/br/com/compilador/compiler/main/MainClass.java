package br.com.compilador.compiler.main;

import br.com.compilador.compiler.exceptions.LexicalException;
import br.com.compilador.compiler.lexico.Scanner;
import br.com.compilador.compiler.lexico.Token;

public class MainClass {
	public static void main(String[] args) {
		try {
			Scanner sc = new Scanner("input.ufabc");
			Token token = null;
			
			do {
				token = sc.proximoToken();
				if (token != null) {
					System.out.println(token);
				}
			} while (token != null);
		} catch (LexicalException ex) {
			System.out.println("ERRO LEXICO "+ex.getMessage());

		}
		catch (Exception ex) {
			System.out.println("ERRO GENÉRICO!!");
			System.out.println(ex.getClass().getName());
		}
	}
}