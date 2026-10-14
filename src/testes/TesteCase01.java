package testes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import br.edu.puc.currencyconverter.CurrencyServices;
import br.edu.puc.currencyconverter.MoedaISO4217;
import java.util.Calendar;

public class TesteCompleto {

    @Test
    public void testConversaoValida_CaminhoFeliz() {
        CurrencyServices services = new CurrencyServices();
        Calendar dataCotacao = Calendar.getInstance();
        double resultado = services.converter("BRL", "USD", dataCotacao, 100.0);
        assertTrue(resultado > 0.0, "O resultado da conversao deve ser maior que zero para entradas validas.");
    }

    @Test
    public void testMesmaMoeda() {
        CurrencyServices api = new CurrencyServices();
        String mb = "BRL";
        String mc = "BRL";
        double quantia = 100.0d;
        Calendar dataCal = Calendar.getInstance();

        assertTrue(api.isValidCurrency(mb), "A moeda de origem deve ser valida no sistema");
        assertTrue(api.isValidCurrency(mc), "A moeda de destino deve ser valida no sistema");

        MoedaISO4217 base = api.getCurrency(mb);
        MoedaISO4217 convers = api.getCurrency(mc);

        assertNotNull(base, "A moeda base nao pode ser nula");
        assertNotNull(convers, "A moeda de destino nao pode ser nula");

        double resultado = api.converter(mb, mc, dataCal, quantia);
        assertEquals(quantia, resultado, "Se as moedas sao iguais, o valor retornado deve ser igual ao valor inserido.");
    }

    @Test
    public void testValorZeroOuNegativo() {
        CurrencyServices api = new CurrencyServices();
        Calendar dataCotacao = Calendar.getInstance();

        double resultadoZero = api.converter("BRL", "USD", dataCotacao, 0.0);
        assertEquals(0.0, resultadoZero, "Para quantia zero, o resultado retornado deve ser zero.");

        double resultadoNegativo = api.converter("BRL", "USD", dataCotacao, -50.0);
        assertTrue(resultadoNegativo <= 0.0, "Para quantia negativa, a conversao deve ser bloqueada ou retornar <= 0.");
    }

    @Test
    public void testMoedaInvalida() {
        CurrencyServices api = new CurrencyServices();
        String moedaInvalida = "XYZ";

        boolean ehValida = api.isValidCurrency(moedaInvalida);
        assertFalse(ehValida, "Uma moeda inexistente (XYZ) deve retornar false na validacao.");

        MoedaISO4217 moeda = api.getCurrency(moedaInvalida);
        assertNull(moeda, "A busca por uma moeda inexistente deve retornar null.");
    }

    @Test
    public void testDataFutura() {
        CurrencyServices api = new CurrencyServices();
        Calendar dataFutura = Calendar.getInstance();
        dataFutura.add(Calendar.YEAR, 1);

        double resultado = api.converter("BRL", "USD", dataFutura, 100.0);
        assertEquals(0.0, resultado, "Para uma data futura, nao deve haver taxa de conversao (resultado 0.0).");
    }
}
