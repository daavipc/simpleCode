package model;
import java.io.Serializable;

public class Desafios {
public static final String TOPICO_PADRAO = "Novo Topico";
public static final String NOME_PADRAO = "ABC";

int id;
String quiz;
int pontuacao;
int video_id;

public Desafios() {
    id = -1;
    quiz = NOME_PADRAO;
    pontuacao = 0;
}

public Desafios(int id, String quiz, int pontuacao, int video_id) {
    setQuiz(quiz);
    setId(id);
    setPontuacao(pontuacao);
    setVideoId(video_id);
}

public int getId() {
    return id;
}

public void setId(int id) {
    this.id = id;

}
public String getQuiz(){
    return quiz;
}

public void setQuiz(String quiz) {
    this.quiz = quiz;
}

public int getPontuacao(){
    return pontuacao;
}

public void setPontuacao(int pontuacao){
    this.pontuacao = pontuacao;
}

public void setVideoId(int videoId){
    this.video_id = videoId;
}

public int getVideoId(){
    return video_id;
}

public String toString() {
    return "Id" + id + "quiz: " + quiz + "Pontução" + pontuacao;	
}

}

