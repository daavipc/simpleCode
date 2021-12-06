package service;

import dao.VideosDao;
import model.Videos;
import spark.Request;
import spark.Response;

public class VideosService {
	private VideosDao videosDAO = new VideosDao();
	
	public Object add(Request request, Response response) {
		int id = videosDAO.getMaxId() + 1;
		String nome = request.queryParams("nome");
		int duracao = 0;
		String descricao = request.queryParams("conteudo");
		String area = request.queryParams("area");
		Videos Videos = new Videos(id, nome, duracao, descricao, area);
		
		videosDAO.inserirVideos(Videos);
		
		response.status(201);
		return id;
	}
	
	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		
		Videos curso = (Videos) videosDAO.get(id);
		if( curso != null) {
			response.header("Content-Type", "application/xml");
    	    response.header("Content-Encoding", "UTF-8");

    	    return "\n<curso>\n" + 
    		"\t<modulo>" + curso.getId() + "</modulo>\n" +
    		"\t<nome>" + curso.getNome() + "</nome>\n" +
    		"\t<horas>" + curso.getDuracao() + "</horas>\n" +
			"\t<horas>" + curso.getConteudo() + "</horas>\n" +
			"\t<horas>" + curso.getArea() + "</horas>\n" +
    		"</curso>\n";
        } else {
            response.status(404); // 404 Not found
            return "Topico " + id + " não encontrado.";
    	   
		}
	}
	
	public Object update(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		Videos curso = (Videos) videosDAO.get(id);
		
		if(curso != null) {
	    	curso.setNome(request.queryParams("nome"));
	    	curso.setDuracao(Integer.parseInt(request.queryParams("duracao")));
	    	videosDAO.atualizarVideos(curso);
			return id;
        } else {
            response.status(404); // 404 Not found
            return "Curso " + id + " não encontrado.";
    	   
		}
	}
	
	public Object remove(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));

        Videos video = (Videos) videosDAO.get(id);

        if (video != null) {

            videosDAO.excluirVideos(id);

            response.status(200); // success
        	return id;
        } else {
            response.status(404); // 404 Not found
            return "Produto não encontrado.";
        }
	}
	
	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer();
		for (Videos videos : videosDAO.getVideos()) {
			returnValue.append("{\"id\":" + videos.getId() + 
					",\"nome\":\"" + videos.getNome() + "\",\"duracao\":\"" 
					+ videos.getDuracao() + "\",\"conteudo\":\"" + videos.getConteudo() + "\",\"area\":\""
					+ videos.getArea() + "\"}" );
		}
		response.header("Access-Control-Allow-Origin", "*");
	    response.header("Content-Type", "application/");
	    response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
}