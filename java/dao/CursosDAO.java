package dao;
import java.sql.*;
import model.Cursos;

public class CursosDAO {
	private Connection conexao;
	private int maxId;
	
	public CursosDAO(){
		conexao = null;
		maxId = 0;
	}
	
	public boolean conectar() {
		String driverName = "org.postgresql.Driver";                    
		String serverName = "localhost";
		String mydatabase = "simpleCode";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
		String username = "ti2cc";
		String password = "ti@cc";
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conexão efetuada com o postgres!");
		} catch (ClassNotFoundException e) { 
			System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
		}

		return status;
	}

	public boolean close() {
		boolean status = false;
		
		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}
	
	public boolean inserirCursos(Cursos curso) {
		conectar();
		boolean status = false;
		this.maxId = (curso.getId() > this.maxId) ? curso.getId() : this.maxId;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO simplecode.curso (nome, horas) "
					       + "VALUES ("+curso.getNome()+ ", '" + curso.getHoras() + "');");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean atualizarCursos(Cursos curso) {
		conectar();
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE simplecode.curso SET nome = '" + curso.getNome() + "', horas = '"  
				       + curso.getHoras();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean excluirCursos(int codigo) {
		conectar();
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM simplecode.curso WHERE modulo = " + codigo);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public Cursos get(int id) {
		Cursos[] curso = getCursos();
		Cursos cursoFinal = null;
		for(int i = 0; i < curso.length; i++) {
			if(curso[i].getId() == id) {
				cursoFinal = curso[i];
			}
		}
		return cursoFinal;
	}
	
	public int getMaxId() {
		return maxId;
	}
	
	public Cursos[] getCursos() {
		conectar();
		Cursos[] usuarios = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM simplecode.curso");		
	         if(rs.next()){
	             rs.last();
	             usuarios = new Cursos[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                usuarios[i] = new Cursos(rs.getInt("id"), rs.getString("nome"), rs.getInt("horas"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return usuarios;
	}
	
}
