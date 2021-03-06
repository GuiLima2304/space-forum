package br.com.space.forum.controller.dto;

import br.com.space.forum.modelo.Topico;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TopicoDto {

    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;

    public TopicoDto(Topico topico) {
        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.mensagem = topico.getMensagem();
        this.dataCriacao = topico.getDataCriacao();
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public static List<TopicoDto> converter(List<Topico> topicos) {
        //ler sobre api de stream, antes era tudo manual
        //TopicoDto::new chama o construtor e pass o topico como parametro
        return topicos.stream().map(TopicoDto::new).collect(Collectors.toList());
    }
}
