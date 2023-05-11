package exemplos;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Cap4Test {
    private static RequestSpecification requestSpec;
    //variável estática chamada requestSpec do tipo RequestSpecification. Ela é usada para armazenar as especificações da solicitação HTTP

    @BeforeClass
    public static void createRequestSpecification() {

        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://api.zippopotam.us").
                build();
        // instância de RequestSpecBuilder, que é uma classe usada para construir especificações de solicitação HTTP
        //Em seguida, é chamado o método setBaseUri("http://api.zippopotam.us") para definir a URI base da solicitação como "http://api.zippopotam.us"
        //Por fim, o método build() é chamado para construir o objeto RequestSpecification e atribuí-lo à variável requestSpec
    }

    @Test
    public void requestUsZipCode90210_checkPlaceNameInResponseBody_expectBeverlyHills_withRequestSpec() {

        given(). //marca o início de uma cadeia de chamadas para construir uma solicitação HTTP
                spec(requestSpec).
                //variável requestSpec é passada como especificação para a solicitação HTTP. Provavelmente
                //contém configurações pré-definidas para a solicitação, como cabeçalhos, parâmetros ou autenticação
                when().
                //Essa linha indica o início da seção "quando" da solicitação HTTP, onde são definidas as ações que serão executadas
                get("us/90210").
                //método HTTP GET com o caminho "us/90210" para a solicitação.
                then().
                // indica o início da seção "então" da solicitação HTTP, onde são definidas as asserções que serão verificadas
                assertThat().
                //asserção é iniciada usando o método assertThat()
                statusCode(200);
                //asserção que verifica se o código de status da resposta HTTP é igual a 200.
    }

    private static ResponseSpecification responseSpec;
//variável estática chamada responseSpec do tipo ResponseSpecification
    @BeforeClass
    public static void createResponseSpecification() {

        responseSpec = new ResponseSpecBuilder().
                //Cria uma nova instância da classe ResponseSpecBuilder
                //que é responsável por construir especificações de resposta para testes
                expectStatusCode(200).
                // Define a expectativa de que o código de status da resposta seja 200
                expectContentType(ContentType.JSON).
                // Define a expectativa de que o tipo de conteúdo da resposta seja JSON
                build();
    }

    @Test
    public void requestUsZipCode90210_checkPlaceNameInResponseBody_expectBeverlyHills_withResponseSpec() {

        given().
                spec(requestSpec).
                when().
                get("http://zippopotam.us/us/90210").
                then().
                spec(responseSpec).
                and().
                assertThat().
                body("places[0].'place name'", equalTo("Beverly Hills"));
    }

    @Test
    public void requestUsZipCode90210_extractPlaceNameFromResponseBody_assertEqualToBeverlyHills() {

        String placeName =

                given().
                        spec(requestSpec).
                        when().
                        get("http://zippopotam.us/us/90210").
                        then().
                        extract().
                        path("places[0].'place name'");

        Assert.assertEquals("Beverly Hills", placeName);
    }
}
