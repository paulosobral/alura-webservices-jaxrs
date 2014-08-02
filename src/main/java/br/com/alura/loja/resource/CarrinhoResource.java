package br.com.alura.loja.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;

// ATENDE PELA URI carinhos:
@Path("carrinhos")
public class CarrinhoResource {
	
	// PRODUZ ATRAVÉZ DO MÉTODO GET HTTP, UM MEDIATYPE DO TIPO XML:
	@Path("{id}")
	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	public String busca(@PathParam("id") long id)
	{
		CarrinhoDAO carrinhoDao = new CarrinhoDAO();
		
		Carrinho carrinho = carrinhoDao.busca(id);
		
		return carrinho.toXML();
	}
	
}
