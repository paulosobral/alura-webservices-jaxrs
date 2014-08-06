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
import br.com.alura.loja.dao.ProjetoDAO;
import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Projeto;

//ATENDE PELA URI projetos:
@Path("projetos")
public class ProjetoResource {

	// PRODUZ ATRAVÉZ DO MÉTODO GET HTTP, UM MEDIATYPE DO TIPO XML:
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String busca(@PathParam("id") long id) {
		ProjetoDAO projetoDao = new ProjetoDAO();
		Projeto projeto = projetoDao.busca(id);

		return projeto.toXML();
	}

	// ADICIONA UM PROJETO CONVERTENDO XML PARA OBJETO JAVA ATRAVÉS DO MÉTODO
	// POST NA URL /projetos (SEM PATH DEFINIDO)
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response adiciona(String conteudo) {
		Projeto projeto = (Projeto) new XStream().fromXML(conteudo);
		new ProjetoDAO().adiciona(projeto);

		// DEVOLVE O STATUS CODE PARA O CLIENTE CREATED (201), E INFORMA A URI
		// ONDE PODEMOS ACESSAR O RECURSO CRIADO (URI DO WEBSERVICE QUE
		// VISUALIZA O PROJETO COM O ID CRIADO):
		URI uri = URI.create("/projetos/" + projeto.getId());
		return Response.created(uri).build();
	}

}
