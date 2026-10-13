package testes;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Calendar;

import br.edu.puc.currencyconverter.*;

import org.junit.jupiter.api.Test;

class TesteCase01 { //simula os vários cenários de uso da tela

	@Test
	void testMesmaMoeda() {
		//teste msg de não conversao por moedas iguais
		
		//Inicio do Arrange - Preparando os teste e asserts intemediários
		//instancia a API
		CurrencyServices api = new CurrencyServices();
					
		//simula os dados oriundos da tela - entradas
		String mb = "BRL";
		String mc = "BRL";
		double quantia = 100.00d;
		Calendar dateCal = Calendar.getInstance();//pega data de hoje
		
		//dados de saída
		double valorConvertidoDeSaida = 0.0; //neste teste, será gerado erro
		
		// testa se as moedas selecionadas na COMBO são válidas
		assertTrue (api.isValidCurrency(mb));		
		assertTrue (api.isValidCurrency(mc));
		
		MoedaISO4217 base, convers;
		//Atribui as moedas do tipo da ENUM MoedaISO4217
		assertNotNull(base = api.getCurrency(mb));
		assertNotNull(convers = api.getCurrency(mc));
		
		//verifica se o construtor atirou a exceção
		//de argumento ilgeal, com mensagem de "mesma moeda"
	    IllegalArgumentException exception = assertThrows(
	              IllegalArgumentException.class, 
	              () -> api.converter (mb,mc,dateCal,100),  // Chamada do construtor aqui
	            "Moedas de base e convesao não podem ser as mesmas"
	          );						
	} 
	
	@Test
	void testConversaoOK() {
		//teste msg de não conversao por moedas iguais
		
		//Inicio do Arrange - Preparando os teste e asserts intemediários
		//instancia a API
		CurrencyServices api = new CurrencyServices();
					
		//simula os dados oriundos da tela - entradas
		String mb = "BRL";
		String mc = "EUR";
		double quantia = 100.00d; 
		Calendar dateCal = Calendar.getInstance();//pega data de hoje
		
		//dados de saída
		double valorConvertidoDeSaida = 17.0d;
	
		// testa se as moedas selecionadas na COMBO são válidas
		assertTrue (api.isValidCurrency(mb));		
		assertTrue (api.isValidCurrency(mc));
		
		MoedaISO4217 base, convers;
		//Atribui as moedas do tipo da ENUM MoedaISO4217
		assertNotNull(base = api.getCurrency(mb));
		assertNotNull(convers = api.getCurrency(mc));
		assertEquals(valorConvertidoDeSaida,api.converter (mb,mc,dateCal,quantia));
		
	}
	

}
