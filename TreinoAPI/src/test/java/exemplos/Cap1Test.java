package exemplos;

import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Cap1Test {


    @Test   //Esta anotação é usada para indicar que o método é um teste. Isso permite que a estrutura de teste execute o método como um teste automatizado.
    public void requestUsZipCode90210_checkPlaceNameInResponseBody_expectBeverlyHills() {

        given().
                //é usado para especificar a preparação para o teste. Aqui, estamos configurando a solicitação HTTP que será enviada à API.
                when().
                //O método when() é usado para especificar a ação que será realizada no teste. Aqui, estamos enviando uma solicitação GET para a URL
                get("http://zippopotam.us/us/90210").
                // Este é o método HTTP GET que é usado para enviar a solicitação para a URL especificada.
                then().
                //O método then() é usado para especificar o que o teste deve verificar. Aqui, estamos verificando a resposta da API para garantir que o nome da cidade seja "Beverly Hills"
                assertThat().
                //Este é o método usado para especificar a asserção que será feita no teste.
                body("places[0].'place name'", equalTo("Beverly Hills"));
        //Este é o corpo da asserção. Ele especifica que estamos verificando o valor do elemento "place name" do primeiro objeto na matriz "places" retornada pela API e que esse valor deve ser igual a "Beverly Hills". O uso de aspas simples em torno do nome do campo place name é necessário devido ao espaço em branco no nome do campo
        //O equalTo é um método que compara o valor recuperado do campo place name com o valor esperado "Beverly Hills"
    }
}

