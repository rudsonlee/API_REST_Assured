package exemplos;

import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

public class Cap2Test {

    @Test
    public void requestUsZipCode90210_checkStatusCode_expectHttp200() { //método de teste com um nome descritivo que indica o que está sendo testado. O código a seguir será executado quando o teste for executado.

        given().                                                                //método para definir as condições da solicitação
                when().                                                        //ação é fazer uma solicitação HTTP GET
                get("http://zippopotam.us/us/90210").                      //Ele retorna informações de código postal dos EUA para o código postal 90210
                then().                                                      //método para definir as condições de resposta
                assertThat().                                               //método para definir as condições de resposta esperadas
                statusCode(200);                                         //Se a resposta tiver um código de status diferente de 200, o teste falhará
    }

    @Test
    public void requestUsZipCode90210_checkContentType_expectApplicationJson() {

        given().
                when().
                get("http://zippopotam.us/us/90210").
                then().
                assertThat().
                contentType(ContentType.JSON);                                //Verifica se o tipo de conteúdo da resposta HTTP é JSON.
    }

    @Test
    public void requestUsZipCode90210_logRequestAndResponseDetails() {

        given().
                log().all().            //para registrar todos os detalhes da requisição no log
                when().
                get("http://zippopotam.us/us/90210").
                then().
                log().body();          //para registrar o corpo (body) da resposta no log. Isso é útil para verificar se a resposta da API está correta.
    }

    @Test
    public void requestUsZipCode90210_checkPlaceNameInResponseBody_expectBeverlyHills() {

        given().
                when().
                get("http://zippopotam.us/us/90210").
                then().
                assertThat().
                body("places[0].'place name'", equalTo("Beverly Hills"));
        // é uma afirmação que verifica se o primeiro elemento na matriz "places" na resposta tem um atributo chamado "place name" e se seu valor é "Beverly Hills"
        // O método equalTo é um método estático de RestAssured que compara o valor atual com o valor esperado
    }

    @Test
    public void requestUsZipCode90210_checkStateNameInResponseBody_expectCalifornia() {

        given().
                when().
                get("http://zippopotam.us/us/90210").
                then().
                assertThat().
                body("places[0].state", equalTo("California"));
        //O método body() é outro método estático do Rest-Assured que permite que você especifique qual parte da resposta HTTP deseja verificar. Aqui, estamos verificando se o estado ("state") retornado na resposta é "California"
        // A asserção equalTo() verifica se o valor da resposta é exatamente "California"
    }

    @Test
    public void requestUsZipCode90210_checkListOfPlaceNamesInResponseBody_expectContainsBeverlyHills() {

        given().
                when().
                get("http://zippopotam.us/us/90210").
                then().
                assertThat().
                body("places.'place name'", hasItem("Beverly Hills"));
        //método que define a localização do valor a ser avaliado na resposta HTTP. O método body() especifica o caminho para o elemento JSON que contém o valor que queremos testar. Nesse caso, o valor é "Beverly Hills" e é esperado que esteja presente na lista de lugares no corpo da resposta HTTP
        //O método hasItem() do Rest Assured verifica se um item específico está presente em uma coleção
    }

    @Test
    public void requestUsZipCode90210_checkNumberOfPlaceNamesInResponseBody_expectOne() {

        given().
                when().
                get("http://zippopotam.us/us/90210").
                then().
                assertThat().
                body("places.'place name'", hasSize(1));
        // Este é um método de Rest-Assured que verifica o corpo da resposta HTTP. Ele usa a sintaxe do JSONPath para verificar se a resposta contém um campo "place name" dentro de um objeto "places" com um tamanho igual a 1
    }
}