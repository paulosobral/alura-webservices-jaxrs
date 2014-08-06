package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.modelo.Projeto;

public class ClienteTest {

	private HttpServer server;
	private Client client;
	
	@Before
	public void startaServidor() {
		
		this.server = Servidor.startaServidor();
		
		// CRIA UM CLIENTE HTTP:
		this.client = ClientBuilder.newClient();
	}

	@After
	public void mataServidor() {
		this.server.stop();
	}

	@Test
	public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {

		// DEFINE A URL QUE O CLIENTE VAI CONSUMIR:
		WebTarget target = client.target("http://localhost:8080");

		// REALIZA UMA REQUISIÇÃO AO SERVIDOR EM UMA DETERMINADA URI PELO MÉTODO
		// GET:
		// O RETORNO CONVERTE EM STRING:
		String conteudo = target.path("/carrinhos/1").request().get(String.class);

		// CONVERTE O XML CARRINHO PARA OBJETO CARRINHO:
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);

		// VERIFICA SE A RUA ESTA CORRETA:
		Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());

	}

	@Test
	public void testaQueBuscarUmProjetoTrazOProjetoEsperado() {

		// DEFINE A URL QUE O CLIENTE VAI CONSUMIR:
		WebTarget target = client.target("http://localhost:8080");

		// REALIZA UMA REQUISIÇÃO AO SERVIDOR EM UMA DETERMINADA URI PELO MÉTODO
		// GET:
		// O RETORNO CONVERTE EM STRING:
		String conteudo = target.path("/projetos/1").request().get(String.class);

		Projeto projeto = (Projeto) new XStream().fromXML(conteudo);

		// VERIFICA SE A STRING RETORNADA POSSUÍ O TREIXO DE TEXTO ABAIXO:
		Assert.assertEquals("Minha loja", projeto.getNome());
	}
	
	@Test
	public void testaAdicionarCarrinhoPostXml()
	{
        WebTarget target = client.target("http://localhost:8080");
        
        // CRIA UM CARRINHO, E O CONVERTE PARA XML:
        Carrinho carrinho = new Carrinho();
        carrinho.adiciona(new Produto(314L, "Tablet", 999, 1));
        carrinho.setRua("Rua Vergueiro");
        carrinho.setCidade("Sao Paulo");
        String xml = carrinho.toXML();
        
        // O ENTITY DO JAX-RS ADICIONA O MEDIA TYPE DE XML, ENTÃO ENVIAMOS O POST:
        Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);
        Response response = target.path("/carrinhos").request().post(entity);
        
        // VERIFICA O RETORNO SE É SUCESSO (STATUS CODE 201, CREATED):
        Assert.assertEquals(201, response.getStatus());
        
        // PEGA O CONTEÚDO DO HEADER LOCATION (201 CREATED) DE RESPOSTA:
        String location = response.getHeaderString("Location");
        
        // O LINK DO NOVO CONTEÚDO CRIADO, TENTAMOS ACESSA-LO E VERIFICAR SE O CONTEÚDO ENVIADO ESTÁ lÁ
        String conteudoCriado = client.target(location).request().get(String.class);
        
        Assert.assertTrue(conteudoCriado.contains("São Paulo"));
	}

}
