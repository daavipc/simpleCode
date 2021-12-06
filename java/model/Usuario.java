package model;

public class Usuario {
	public static final String EMAIL_PADRAO = "davi@gmail.com";
	public static final String SENHA_PADRAO = "1234";
	public static final String NOME_PADRAO = "Davi";
	public static final String SOBRENOME_PADRAO = "Pierrer";
	public static final String TELEFONE_PADRAO = "666666";
	public static final String PRIME_PADRAO = "FALSE";
	public static final String USUARIO_PADRAO = "davizao01";
	public static final String FOTO_PADRAO = "";
	public static final String DESCRICAO_PADRAO = "Eu gosto de pudim";
	
	int id;
	String email;
	String senha;
	String nome;
	String sobrenome;
	String telefone;
	String is_prime;
	String usuario;
	String foto_perfil;
	String descricao;
	int cursos_id;
	
	public Usuario() {
		id = -1;
		email = EMAIL_PADRAO;
		senha = SENHA_PADRAO;
		nome = NOME_PADRAO;
		sobrenome = SOBRENOME_PADRAO;
		telefone = TELEFONE_PADRAO;
		is_prime = PRIME_PADRAO;
		usuario = USUARIO_PADRAO;
		foto_perfil = FOTO_PADRAO;
		descricao = DESCRICAO_PADRAO;
		cursos_id = -1;
	}
	
	public Usuario(int id, String email, String senha, String nome, String sobrenome, String telefone, 
			String is_prime, String usuario, String foto_perfil, String descricao, int cursos_id) {
		setId(id);
		setEmail(email);
		setSenha(senha);
		setNome(nome);
		setSobrenome(sobrenome);
		setTelefone(telefone);
		setIsPrime(is_prime);
		setUsuario(usuario);
		setFotoPerfil(foto_perfil);
		setDescricao(descricao);
		setCursosId(cursos_id);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getSobrenome() {
		return sobrenome;
	}
	
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public String getIsPrime() {
		return is_prime;
	}
	
	public void setIsPrime(String is_prime) {
		this.is_prime = is_prime;
	}
	
	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getFotoPerfil() {
		return foto_perfil;
	}
	
	public void setFotoPerfil(String foto_perfil) {
		this.foto_perfil = foto_perfil;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public int getCursosId() {
		return cursos_id;
	}
	
	public void setCursosId(int cursos_id) {
		this.cursos_id = cursos_id;
	}
	
	public String toString() {
		return "Email: " + email + "   Senha: " +senha + "   Nome: " + nome + "   Sobrenome: " + sobrenome + "   Telefone: " + 
	((String) telefone) + "   Is Prime: " +((String) is_prime) + "   Usuario: " + ((String) usuario) + "   Descricao: " +((String) descricao);
	}
}
