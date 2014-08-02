package br.com.alura.loja.resource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
	@Produces(MediaType.APPLICATION_XML)
	public String adiciona(String conteudo) {
		Projeto projeto = (Projeto) new XStream().fromXML(conteudo);
		new ProjetoDAO().adiciona(projeto);

		return "<status>sucesso</status>";
	}

}
