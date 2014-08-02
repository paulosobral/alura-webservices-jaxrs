package br.com.alura.loja.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.alura.loja.dao.ProjetoDAO;
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

}
