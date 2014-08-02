package br.com.alura.loja;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Servidor {

	public static HttpServer startaServidor() {
		// CRIA A URI QUE VAI SER UTILIZADA PELO SERVIDOR:
		URI uri = URI.create("http://localhost:8080/");

		// DEFINE A CONFIGRUAÇÃO BASEADA EM TODAS AS CLASSES DO PACOTE LOJA:
		ResourceConfig config = new ResourceConfig()
				.packages("br.com.alura.loja");

		// INSTÂNCIA UM SERVIDOR GRIZZLY:
		return GrizzlyHttpServerFactory.createHttpServer(uri, config);
	}

	public static void main(String[] args) throws IOException {

		HttpServer server = Servidor.startaServidor();

		// IMPRIME UMA MENSAGEM DE RODANDO E AGUARDE AO TÉRMINO DA EXECUÇÃO
		// QUANDO O USUÁRIO APERTAR ENTER:
		System.out.println("Servidor rodando");
		System.in.read();
		server.stop();
		System.out.println("Servidor parado");

	}

}
