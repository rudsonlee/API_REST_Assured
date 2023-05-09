package exemplos;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(DataProviderRunner.class)
public class Cap3Test {

    @DataProvider
    //é uma anotação que indica que o método é um provedor de dados.
    public static Object[][] zipCodesAndPlaces () {
        // método "zipCodesAndPlaces", que é o nome escolhido para este provedor de dados
        // Ele é público (public), estático (static) e retorna um array bidimensional de objetos (Object[][])
        return new Object[][] {
                //declaração dos dados que serão fornecidos. É criado um novo array bidimensional de objetos (Object[][]) que contém três linhas e três colunas.
                { "us", "90210", "Beverly Hills" },
                { "us", "12345", "Schenectady" },
                { "ca", "B2R", "Waverley"},
                {"nl", "1001", "Amsterdam"}

                //As três linhas correspondem a três conjuntos de dados para os testes, cada um com três elementos
                //O primeiro elemento é uma string que representa o país
                //segundo elemento é uma string que representa o código postal
                // terceiro elemento é uma string que representa o nome da cidade.
        };
    }

    @Test
    @UseDataProvider("zipCodesAndPlaces")
    //método irá utilizar um provedor de dados com o nome "zipCodesAndPlaces" para fornecer os argumentos de entrada para cada execução do método.

    public void requestZipCodesFromCollection_checkPlaceNameInResponseBody_expectSpecifiedPlaceName(String countryCode, String zipCode, String expectedPlaceName) {
         //método recebe três argumentos: countryCode, zipCode e expectedPlaceName
        //O corpo do método usa a biblioteca Rest Assured para realizar uma solicitação HTTP GET à API Zippopotam usando os parâmetros countryCode e zipCode no caminho da URL
        //A resposta é então validada para garantir que o nome do local esperado seja retornado.

        given(). //é uma construção de linguagem natural que inicia a preparação para uma solicitação HTTP usando o Rest Assured.
                pathParam("countryCode", countryCode).pathParam("zipCode", zipCode).
                //define os valores dos parâmetros da URL.
                when().
                get("http://zippopotam.us/{countryCode}/{zipCode}").
                //envia uma solicitação GET HTTP para a API Zippopotam, utilizando os valores countryCode e zipCode fornecidos como parâmetros da URL
                then().
                assertThat().
                body("places[0].'place name'", equalTo(expectedPlaceName));
        //método "assertThat()" do Rest Assured para validar a resposta. Aqui, está sendo verificado se o nome do local retornado na resposta corresponde ao nome do local esperado (expectedPlaceName)
        //A sintaxe "places[0].'place name'" é usada para acessar o primeiro item na matriz "places" retornada pela API e verificar o nome do local específico retornado
        //A função "equalTo()" é usada para verificar se o valor do nome do local retornado é igual ao valor do nome do local esperado

    }

    @Test
    public void requestUsZipCode90210_checkPlaceNameInResponseBody_expectBeverlyHills() {

        given().
                when().
                get("http://zippopotam.us/us/90210").
                then().
                assertThat().
                body("places[0].'place name'", equalTo("Beverly Hills"));
    }

    @Test
    public void requestUsZipCode12345_checkPlaceNameInResponseBody_expectSchenectady() {

        given().
                when().
                get("http://zippopotam.us/us/12345").
                then().
                assertThat().
                body("places[0].'place name'", equalTo("Schenectady"));
    }

    @Test
    public void requestCaZipCodeB2R_checkPlaceNameInResponseBody_expectWaverley() {

        given().
                when().
                get("http://zippopotam.us/ca/B2R").
                then().
                assertThat().
                body("places[0].'place name'", equalTo("Waverley"));
    }
}
