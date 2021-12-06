package service;

import dao.CursosDAO;
import model.Cursos;
import spark.Request;
import spark.Response;

public class CursosService {
	private CursosDAO cursosDAO = new CursosDAO();
	
	public Object add(Request request, Response response) {
		int id = cursosDAO.getMaxId() + 1;
		String nome = request.queryParams("nome");
		int horas = Integer.parseInt(request.queryParams("horas"));
		
		Cursos curso = new Cursos(id, nome, horas);
		
		cursosDAO.inserirCursos(curso);
		
		response.status(201);
		return id;
	}
	
	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		
		Cursos curso = (Cursos) cursosDAO.get(id);
		if( curso != null) {
			response.header("Content-Type", "application/xml");
    	    response.header("Content-Encoding", "UTF-8");

    	    return "\n<curso>\n" + 
    		"\t<modulo>" + curso.getId() + "</modulo>\n" +
    		"\t<nome>" + curso.getNome() + "</nome>\n" +
    		"\t<horas>" + curso.getHoras() + "</horas>\n" +
    		"</curso>\n";
        } else {
            response.status(404); 
            return "Topico " + id + " não encontrado.";
    	   
		}
	}
	
	public Object update(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		Cursos curso = (Cursos) cursosDAO.get(id);
		
		if(curso != null) {
	    	curso.setNome(request.queryParams("nome"));
	    	curso.setHoras(Integer.parseInt(request.queryParams("horas")));
	    	cursosDAO.atualizarCursos(curso);
			return id;
        } else {
            response.status(404); // 404 Not found
            return "Curso " + id + " não encontrado.";
    	   
		}
	}
	
	public Object remove(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));

        Cursos curso = (Cursos) cursosDAO.get(id);

        if (curso != null) {

            cursosDAO.excluirCursos(id);

            response.status(200); // success
        	return id;
        } else {
            response.status(404); // 404 Not found
            return "Produto não encontrado.";
        }
	}
	
	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<topicos type=\"array\">");
		for (Cursos curso : cursosDAO.getCursos()) {
			returnValue.append(  "\n<curso>\n" + 
					"\t<modulo>" + curso.getId() + "</modulo>\n" +
		    		"\t<nome>" + curso.getNome() + "</nome>\n" +
		    		"\t<horas>" + curso.getHoras() + "</horas>\n" +
		    		"</curso>\n");
		}
		returnValue.append("</topicos>");
	    response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
}
