package dao;

import java.sql.*;
import model.Desafios;


public class DesafiosDAO {
	private Connection conexao;
	private int maxId;
	
	public DesafiosDAO(){
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
	
	public boolean inserirDesafios(Desafios desafio) {
		conectar();
		boolean status = false;
		this.maxId = (desafio.getId() > this.maxId) ? desafio.getId() : this.maxId;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO simplecode.desafio (quiz, pontuacao, videos_id) "
					       + "VALUES ("+desafio.getQuiz()+ ", '" + desafio.getPontuacao() + "', '"  
					       + desafio.getVideoId() + "');");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean atualizarDesafios(Desafios desafio) {
		conectar();
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE simplecode.desafio SET nuiz = '" + desafio.getQuiz() + "',  pontuacao = '"  
				       + desafio.getPontuacao() + "', videos_id = '" + desafio.getVideoId();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean excluirDesafios(int codigo) {
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
	
	public Desafios get(int id) {
		Desafios[] desafio = getDesafios();
		Desafios desafioFinal = null;
		for(int i = 0; i < desafio.length; i++) {
			if(desafio[i].getId() == id) {
				desafioFinal = desafio[i];
			}
		}
		return desafioFinal;
	}
	
	public int getMaxId() {
		return maxId;
	}
	
	public Desafios[] getDesafios() {
		conectar();
		Desafios[] desafios = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM simplecode.curso");		
	         if(rs.next()){
	             rs.last();
	             desafios = new Desafios[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                desafios[i] = new Desafios(rs.getInt("id"), rs.getString("quiz"), rs.getInt("pontuacao"), rs.getInt("videos_id"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return desafios;
	}
}
