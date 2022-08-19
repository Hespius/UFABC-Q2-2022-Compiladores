package br.com.compilador.compiler.lexico;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import br.com.compilador.compiler.exceptions.LexicalException;

public class Scanner {

	private char[] content;
	private int    estado;
	private int    pos;

	public Scanner(String filename) {
		try {
			String txtConteudo;
			txtConteudo = new String(Files.readAllBytes(Paths.get(filename)),StandardCharsets.UTF_8);
			
			System.out.println("-------  Conteudo input  -----");
			System.out.println(txtConteudo);
			System.out.println("------------------------------");
			
			content = txtConteudo.toCharArray();
			pos=0;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public Token proximoToken() {
		char caracterAtual;
		Token token;
		String termo="";
		
		if (ehEOF()) {
			return null;
		}
		
		estado = 0;
		
		while (true) {
			caracterAtual = proximoCaracter();

			switch(estado) {
			case 0:
				if (ehCaracter(caracterAtual)) {
					termo += caracterAtual;
					estado = 1;
				}
				else if (ehDigito(caracterAtual)) {
					estado = 2;
					termo += caracterAtual;
				}
				else if (ehEspaco(caracterAtual)) {
					estado = 0;
				}
				else if (ehOperador(caracterAtual)) {
					termo += caracterAtual;
					token = new Token();
					token.setType(Token.TK_OPERADOR);
					token.setText(termo);
					return token;
				}
				else {
					throw new LexicalException("SÍMBOLO não reconhecido");
				}
				break;
			case 1:
				if (ehCaracter(caracterAtual) || ehDigito(caracterAtual)) {
					estado = 1;
					termo += caracterAtual;
				}
				else if (ehEspaco(caracterAtual) || ehOperador(caracterAtual) || ehEOF(caracterAtual)){
					if (!ehEOF(caracterAtual))
						back();
					token = new Token();
					token.setType(Token.TK_IDENTIFICADOR);
					token.setText(termo);
					return token;
				}
				else {
					throw new LexicalException("Identificador mal formado!");
				}
				break;
			case 2:
				if (ehDigito(caracterAtual) || caracterAtual == '.') {
					estado = 2;
					termo += caracterAtual;
				}
				else if (!ehCaracter(caracterAtual) || ehEOF(caracterAtual)) {
					if (!ehEOF(caracterAtual))
						back();
					token = new Token();
					token.setType(Token.TK_NUMERICO);
					token.setText(termo);
					return token;
				}
				else {
					throw new LexicalException("NUMERO não reconhecido");
				}
				break;
		    }
		}
	}

	private boolean ehDigito(char c) {
		return c >= '0' && c <= '9';
	}

	private boolean ehCaracter(char c) {
		return (c>='A' && c <= 'Z') || (c >= 'a' && c <= 'z') ;
	}

	private boolean ehOperador(char c) {
		return c == '>' || c == '<' || c == '=' || c == '!' || c == '+' || c == '-' || c == '*' || c == '/';
	}
	
	private boolean ehEspaco(char c) {
		return c == ' ' || c == '\t' || c == '\n' || c == '\r'; 
	}

	private char proximoCaracter() {
		if (ehEOF()) {
			return '\0';
		}
		return content[pos++];
	}
	
	private boolean ehEOF() {
		return pos >= content.length;
	}

    private void back() {
    	pos--;
    }
    
    private boolean ehEOF(char c) {
    	return c == '\0';
    }





}