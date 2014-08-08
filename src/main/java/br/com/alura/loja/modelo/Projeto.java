package br.com.alura.loja.modelo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

//XmlRootElement DIZ QUE ESTE ELEMENTO É UM ELEMENTO VÁLIDO XML DO JAXB:
//XmlAccessType DIZ QUE TODOS OS CAMPOS SERAM SERIALIZADOS NO XML:
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Projeto {
	private long id;
	private String nome;
	private int anoDeInicio;
	
	// O JAXB SÓ FUNCIONA COM UM CONSTRUTOR PADRÃO VAZIO:
	public Projeto() {
		
	}

	public Projeto(long id, String nome, int anoDeInicio) {
		super();
		this.id = id;
		this.nome = nome;
		this.anoDeInicio = anoDeInicio;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public int getAnoDeInicio() {
		return anoDeInicio;
	}
	
	public void setAnoDeInicio(int anoDeInicio) {
		this.anoDeInicio = anoDeInicio;
	}
	
	public String toXML() {

		XStream xStream = new XStream();
		return xStream.toXML(this);

	}
	
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
}
