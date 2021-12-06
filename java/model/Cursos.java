package model;

public class Cursos {
	public static final String NOME_PADRAO = "Curso";
	int id;
	String nome;
	int horas;
	
	public Cursos() {
		id = -1;
		nome = NOME_PADRAO;
		horas = 0;
	}
	
	public Cursos(int id, String nome, int horas) {
		setId(id);
		setNome(nome);
		setHoras(horas);
	}
	
	public int getId(){
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
	
	public int getHoras() {
		return horas;
	}
	
	public void setHoras(int horas) {
		this.horas = horas;
	}
	
	public String toString() {
		return "Nome: " +nome + "   Horas: " + horas;
	}
}
