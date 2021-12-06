package service;

import dao.UsuarioDAO;
import model.Usuario;
import spark.Request;
import spark.Response;

public class UsuarioService {
	private UsuarioDAO usuarioDAO = new UsuarioDAO();


	public Object add(Request request, Response response) {
		int id = usuarioDAO.getMaxId() + 1;
		String email = request.queryParams("email").trim();
		String senha = request.queryParams("senha");
		String nome = request.queryParams("nome");
		String sobrenome = request.queryParams("sobrenome");
		String telefone = request.queryParams("telefone");
		String is_prime = request.queryParams("is_prime");
		String usuarioNome = request.queryParams("usuario");
		String foto_perfil = request.queryParams("foto_perfil");
		String descricao = request.queryParams("descricao");


		Usuario usuario = new Usuario(id, email, senha, nome, sobrenome, telefone, 
				is_prime, usuarioNome, foto_perfil, descricao, 1);

		usuarioDAO.inserirUsuario(usuario);

		response.status(201); // 201 Created
		return id;
	}

	public String login(Request request, Response response) {
		String body = request.body().toString();
		body = body.replaceAll("email=", "");
		body = body.replaceAll("senha=", "");
		body = body.replaceAll("%40", "@");
		String login[] = body.split("&");
		
		Usuario user = usuarioDAO.login(login[0], login[1]);
		if(user != null) {
			response.header("Access-Control-Allow-Origin", "*");
		    response.header("Content-Type", "application/");
		    response.header("Content-Encoding", "UTF-8");
		    String resultado = "{\"id\":" + user.getId() + ",\"email\":\"" + user.getEmail() + "\",\"nome\":\"" + user.getNome() + "\",\"sobrenome\":\"" + user.getSobrenome() + "\"}";
		    return resultado.toString();
		}
		else {
			response.header("Access-Control-Allow-Origin", "*");
		    response.header("Content-Type", "application/");
		    response.header("Content-Encoding", "UTF-8");
		    String res = "null";
		    return res;
		}
	}
	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        
		Usuario usuario = (Usuario) usuarioDAO.get(id);

        if (usuario != null) {
        	
        	usuario.setEmail(request.queryParams("email"));
        	usuario.setSenha(request.queryParams("senha"));
        	usuario.setNome(request.queryParams("nome"));
        	usuario.setSobrenome(request.queryParams("sobrenome"));
        	usuario.setTelefone(request.queryParams("telefone"));
        	usuario.setIsPrime(request.queryParams("is_prime"));
        	usuario.setUsuario(request.queryParams("usuario"));
        	usuario.setFotoPerfil(request.queryParams("foto_perfil"));
        	usuario.setDescricao(request.queryParams("descricao"));
        	usuario.setCursosId(Integer.parseInt(request.queryParams("cursos_area")));

        	usuarioDAO.atualizarUsuario(usuario);
            return id;
        } else {
            response.status(404); // 404 Not found
            return "Produto não encontrado.";
        }
        
	}

	public Object remove(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));

        Usuario usuario = (Usuario) usuarioDAO.get(id);

        if (usuario != null) {

            usuarioDAO.excluirUsuario(id);

            response.status(200); // success
        	return id;
        } else {
            response.status(404); // 404 Not found
            return "Produto não encontrado.";
        }
	}

	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<usuarios type=\"array\">");
		for (Usuario usuario : usuarioDAO.getUsuarios()) {
			returnValue.append("\n<usuario>\n" + 
            		"\t<id>" + usuario.getId() + "</id>\n" +
            		"\t<email>" + usuario.getDescricao() + "</email>\n" +
            		"\t<senha>" + usuario.getSenha() + "</senha>\n" +
            		"\t<nome>" + usuario.getNome() + "</nome>\n" +
            		"\t<sobrenome>" + usuario.getSobrenome() + "</sobrenome>\n" +
            		"\t<telefone>" + usuario.getTelefone() + "</telefone>\n" +
            		"\t<is_prime>" + usuario.getIsPrime() + "</is_prime>\n" +
            		"\t<usuario>" + usuario.getUsuario() + "</usuario>\n" +
            		"\t<fotoPerfil>" + usuario.getFotoPerfil() + "</fotoPerfil>\n" +
            		"\t<descricao>" + usuario.getDescricao() + "</descricao>\n" +
            		"\t<cursosArea>" + usuario.getCursosId() + "</cursosArea>\n" +
            		"</usuario>\n");
		}
		returnValue.append("</usuarios>");
	    response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
}
	
