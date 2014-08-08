package br.com.alura.loja.resource;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;

// ATENDE PELA URI carinhos:
@Path("carrinhos")
public class CarrinhoResource {

	// PRODUZ ATRAVÉZ DO MÉTODO GET HTTP, UM MEDIATYPE DO TIPO XML:
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String busca(@PathParam("id") long id) {
		CarrinhoDAO carrinhoDao = new CarrinhoDAO();

		Carrinho carrinho = carrinhoDao.busca(id);

		return carrinho.toXML();
	}

	// ADICIONA UM CARRINHO CONVERTENDO XML PARA OBJETO JAVA ATRAVÉS DO MÉTODO
	// POST NA URL /carrinhos (SEM PATH DEFINIDO)
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response adiciona(String conteudo) {
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		new CarrinhoDAO().adiciona(carrinho);

		// DEVOLVE O STATUS CODE PARA O CLIENTE CREATED (201), E INFORMA A URI
		// ONDE PODEMOS ACESSAR O RECURSO CRIADO (URI DO WEBSERVICE QUE
		// VISUALIZA O CARRINHO COM O ID CRIADO):
		URI uri = URI.create("/carrinhos/" + carrinho.getId());
		return Response.created(uri).build();
	}

	// SUBRECURSO (RECURSO DENTRO DE RECURSO) QUE EXCLUÍ (MÉTODO DELETE DO HTTP)
	// PRODUTO COM ID DEFINIDO DENTRO DO CARRINHO COM ID DEFINIDO:
	@Path("{id}/produto/{produtoId}")
	@DELETE
	public Response removeProduto(@PathParam("id") long id,
			@PathParam("produtoId") long produtoId) {
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		carrinho.remove(produtoId);

		// RESPONDE STATUS CODE 200 OK:
		return Response.ok().build();
	}

	// SUBRECURSO (RECURSO DENTRO DE RECURSO) QUE ATUALIZA (MÉTODO PUT DO HTTP)
	// COM CONTEÚDO XML
	// PRODUTO COM ID DEFINIDO DENTRO DO CARRINHO COM ID DEFINIDO:
	@Path("{id}/produto/{produtoId}")
	@Consumes(MediaType.APPLICATION_XML)
	@PUT
	public Response alteraProduto(String conteudo, @PathParam("id") long id,
			@PathParam("produtoId") long produtoId) {

		Carrinho carrinho = new CarrinhoDAO().busca(id);

		// CONVERTE XML PASSADO PARA UM CARRINHO:
		Produto produto = (Produto) new XStream().fromXML(conteudo);

		// ATUALIZA CARRINHO:
		carrinho.troca(produto);

		// RESPONDE STATUS CODE 200 OK:
		return Response.ok().build();
	}

	// SUBRECURSO (RECURSO DENTRO DE RECURSO) QUE ATUALIZA (MÉTODO PUT DO HTTP)
	// COM CONTEÚDO XML
	// QUANTIDADE DO PRODUTO COM ID DEFINIDO DENTRO DO CARRINHO COM ID DEFINIDO:
	@Path("{id}/produto/{produtoId}/quantidade")
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response alteraProduto(@PathParam("id") long id,
			@PathParam("produtoId") long produtoId, String conteudo) {
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		Produto produto = (Produto) new XStream().fromXML(conteudo);
		carrinho.trocaQuantidade(produto);
		return Response.ok().build();
	}
}
