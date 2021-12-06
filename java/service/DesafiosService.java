package service;

import dao.DesafiosDAO;
import model.Desafios;
import spark.Request;
import spark.Response;

public class DesafiosService {
private DesafiosDAO desafiosDAO = new DesafiosDAO();
	
	public Object add(Request request, Response response) {
		int topico = desafiosDAO.getMaxId() + 1;
		String quiz = request.queryParams("quiz");
		int pontuacao = Integer.parseInt(request.queryParams("pontuacao"));
		int videos_ID = Integer.parseInt(request.queryParams("videos_ID"));
		
		Desafios desafio = new Desafios(topico, quiz, pontuacao, videos_ID);
		
		desafiosDAO.inserirDesafios(desafio);
		
		response.status(201);
		return topico;
	}
	
	public Object get(Request request, Response response) {
		int topico = Integer.parseInt(request.params(":topico"));
		
		Desafios desafio = (Desafios) desafiosDAO.get(topico);
		if(desafio != null) {
			response.header("Content-Type", "application/xml");
    	    response.header("Content-Encoding", "UTF-8");

    	    return "\n<desafio>\n" + 
    		"\t<topico>" + desafio.getId() + "</modulo>\n" +
    		"\t<sgila>" + desafio.getQuiz() + "</sigla>\n" +
    		"\t<pontuacao>" + desafio.getPontuacao() + "</pontuacao>\n" +
    		"\t<topicos_modulo" + desafio.getVideoId() + "</topicos_modulo>\n" +
    		"</desafio>\n";
        } else {
            response.status(404); // 404 Not found
            return "Desafio " + topico + " não encontrado.";
    	   
		}
	}
	
	public Object update(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		Desafios desafio = (Desafios) desafiosDAO.get(id);
		
		if(desafio != null) {
	    	desafio.setQuiz(request.queryParams("quiz"));
	    	desafio.setPontuacao(Integer.parseInt(request.queryParams("pontuacao")));
	    	
	    	desafiosDAO.atualizarDesafios(desafio);
			return desafio;
        } else {
            response.status(404); 
            return "Topico " + id + " não encontrado.";
    	   
		}
	}
	
	public Object remove(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));

        Desafios desafio = (Desafios) desafiosDAO.get(id);

        if (desafio != null) {

            desafiosDAO.excluirDesafios(id);

            response.status(200); // success
        	return id;
        } else {
            response.status(404); // 404 Not found
            return "Produto não encontrado.";
        }
	}
	
	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<topicos type=\"array\">");
		for (Desafios desafio : desafiosDAO.getDesafios()) {
			returnValue.append(  "\n<desafios>\n" + 
					"\t<topico>" + desafio.getId() + "</modulo>\n" +
		    		"\t<sgila>" + desafio.getQuiz() + "</sigla>\n" +
		    		"\t<pontuacao>" + desafio.getPontuacao() + "</pontuacao>\n" +
		    		"\t<topicos_modulo" + desafio.getVideoId() + "</topicos_modulo>\n" +
		    		"</desafios>\n");
		}
		returnValue.append("</topicos>");
	    response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
}
