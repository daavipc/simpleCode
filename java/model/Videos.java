package model;

public class Videos {
	public static final String NOME_PADRAO = "Nome do video";
	public static final String DESCRICAO_PADRAO = "bla bla bla bla";
	
	int id;
	String nome;
	int duracao;
	String conteudo;
	String area;
	
	public Videos() {
		id = -1;
		nome = NOME_PADRAO;
		conteudo = DESCRICAO_PADRAO;
		area = "AREA";
	}
	
	public Videos(int id, String nome, int duracao, String descricao, String area) {
		setId(id);
		setNome(nome);
		setDuracao(duracao);
		setConteudo(descricao);
		setArea(area);
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public int getDuracao() {
		return duracao;
	}
	
	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}
	
	public String getConteudo() {
		return conteudo;
	}
	
	public void setConteudo(String descricao) {
		this.conteudo = descricao;
	}
	
	public String getArea() {
		return area;
	}
	
	public void setArea(String area) {
		this.area = area;
	}
}
