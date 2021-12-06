package app;
import static spark.Spark.*;
import dao.UsuarioDAO;
import service.UsuarioService;
import service.VideosService;


public class Aplicacao {

	private static UsuarioService usuarioService = new UsuarioService();
	private static VideosService videosService = new VideosService();


	public static void main(String[] args) {
		
	    	port(5433);

	        post("/usuario", (request, response) -> usuarioService.add(request, response));

			post("/login", (request, response) -> usuarioService.login(request,response));
			
			post("/video", (request, response) -> videosService.add(request, response));
			
			post("/video/get", (request, response) -> videosService.getAll(request, response));
	}
}
