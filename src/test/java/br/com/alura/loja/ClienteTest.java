package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Projeto;

public class ClienteTest {

	private HttpServer server;

	@Before
	public void startaServidor() {
		this.server = Servidor.startaServidor();
	}

	@After
	public void mataServidor() {
		this.server.stop();
	}

	@Test
	public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {

		// CRIA UM CLIENTE HTTP:
		Client client = ClientBuilder.newClient();

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

		// CRIA UM CLIENTE HTTP:
		Client client = ClientBuilder.newClient();

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

}
