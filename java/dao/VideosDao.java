package dao;

import java.sql.*;
import model.Videos;


public class VideosDao {
	private Connection conexao;
	private int maxId;
	
	public VideosDao(){
		conexao = null;
		maxId = 0;
	}
	
	public boolean conectar() {
		String driverName = "org.postgresql.Driver";                    
		String serverName = "localhost";
		String mydatabase = "simplecode";
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
	
	public boolean inserirVideos(Videos video) {
		conectar();
		boolean status = false;
		this.maxId = (video.getId() > this.maxId) ? video.getId() : this.maxId;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO simplecode.videos (ID, nome, duracao, conteudo, area) "
					       + "VALUES ("+video.getId()+ ", '" + video.getNome() + "', " + video.getDuracao() + ", '"  
					       + video.getConteudo() + "', '" + video.getArea() +"');");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean atualizarVideos(Videos videos) {
		conectar();
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE simplecode.videos SET nome = '" + videos.getNome() + "',  duracao = '"  
				       + videos.getDuracao() + "', area = '" + videos.getArea();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean excluirVideos(int id) {
		conectar();
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM simplecode.video WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public Videos get(int id) {
		Videos[] desafio = getVideos();
		Videos desafioFinal = null;
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
	
	public Videos[] getVideos() {
		conectar();
		Videos[] desafios = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM simplecode.video");		
	         if(rs.next()){
	             rs.last();
	             desafios = new Videos[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                desafios[i] = new Videos(rs.getInt("id"), rs.getString("nome"), rs.getInt("duracao"), rs.getString("conteudo"), rs.getString("area"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return desafios;
	}
}
