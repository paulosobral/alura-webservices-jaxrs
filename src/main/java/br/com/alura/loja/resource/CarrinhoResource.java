package br.com.alura.loja.resource;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;

// ATENDE PELA URI carinhos:
@Path("carrinhos")
public class CarrinhoResource {
	
	// PRODUZ ATRAVÉZ DO MÉTODO GET HTTP, UM MEDIATYPE DO TIPO XML:
	@Path("{id}")
	@GET 
	@Produces(MediaType.APPLICATION_XML)
	public String busca(@PathParam("id") long id)
	{
		CarrinhoDAO carrinhoDao = new CarrinhoDAO();
		
		Carrinho carrinho = carrinhoDao.busca(id);
		
		return carrinho.toXML();
	}
	
	// ADICIONA UM CARRINHO CONVERTENDO XML PARA OBJETO JAVA ATRAVÉS DO MÉTODO POST NA URL /carrinhos (SEM PATH DEFINIDO)
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response adiciona(String conteudo)
	{	
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		new CarrinhoDAO().adiciona(carrinho);
		
		// DEVOLVE O STATUS CODE PARA O CLIENTE CREATED (201), E INFORMA A URI ONDE PODEMOS ACESSAR O RECURSO CRIADO (URI DO WEBSERVICE QUE VISUALIZA O CARRINHO COM O ID CRIADO):
		URI uri = URI.create("/carrinhos/" + carrinho.getId());
		return Response.created(uri).build();
	}
	
}
