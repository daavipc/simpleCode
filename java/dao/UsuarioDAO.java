package dao;

import java.sql.*;
import model.Usuario;

public class UsuarioDAO {
	private Connection conexao;
	private int maxId;
	
	
	public UsuarioDAO() {
		conexao = null;
		maxId =0;
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
	
	public boolean inserirUsuario(Usuario usuario) {
		conectar();
		boolean status = false;
		this.maxId = (usuario.getId() > this.maxId) ? usuario.getId() : this.maxId;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO simplecode.usuario (id, usuario, nome, email, senha) "
					       + "VALUES ("+usuario.getId()+ ", '" + usuario.getUsuario() + "', '" + usuario.getNome() + "', '" + usuario.getEmail().trim() +"', '"  
					       + usuario.getSenha() + "');");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean atualizarUsuario(Usuario usuario) {
		conectar();
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE usuario SET email = '" + usuario.getEmail() + "', senha = '"  
				       + usuario.getSenha() + "', nome = '" + usuario.getNome() + "', nome = '" 
				       + usuario.getNome() + "', sobrenome = '" + usuario.getSobrenome() +  
				       "', telefone = '" + usuario.getTelefone() + "', usuario = '" + usuario.getUsuario() 
				       + "', descricao = '" + usuario.getDescricao() + "'" + " WHERE codigo = " + usuario.getId();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean excluirUsuario(int codigo) {
		conectar();
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM usuario WHERE id = " + codigo);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public Usuario[] getUsuarios() {
		conectar();
		Usuario[] usuarios = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM simplecode.usuario");		
	         if(rs.next()){
	             rs.last();
	             usuarios = new Usuario[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                usuarios[i] = new Usuario(rs.getInt("id"), rs.getString("email"), rs.getString("senha"), rs.getString("nome"), 
	                		rs.getString("sobrenome"), rs.getString("telefone"), rs.getString("is_prime"), rs.getString("usuario"), 
	                		rs.getString("foto_perfil"), rs.getString("descricao"), rs.getInt("cursos_ID"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return usuarios;
	}
	
	public Usuario get(int id) {
		Usuario[] usuarios = getUsuarios();
		Usuario usuarioFinal = null;
		for(int i = 0; i < usuarios.length; i++) {
			if(usuarios[i].getId() == id) {
				usuarioFinal = usuarios[i];
			}
		}
		return usuarioFinal;
	}
	
	public Usuario login(String email, String senha) {
		Usuario[] usuarios = getUsuarios();
		Usuario usuarioFinal = null;
		int flag = 0;
		for(int i = 0; i < usuarios.length; i++) {
			if(usuarios[i].getEmail().trim().equals(email) == true && usuarios[i].getSenha().equals(senha) == true) {
				usuarioFinal = usuarios[i];
				flag++;
			}
		}
		if(flag == 1) {
			return usuarioFinal;
		}
		else {
			return null;	
		}
	}
	
	public int getMaxId() {
		return maxId;
	}
	
	
	public void listarUsuarios() {
		Usuario[] usuarios = getUsuarios();
		for(int i = 0; i < usuarios.length; i++) {
			System.out.println(usuarios[i].toString());
		}
	}
	
	
}

