package br.com.space.forum.controller;

import br.com.space.forum.controller.dto.TopicoDto;
import br.com.space.forum.modelo.Curso;
import br.com.space.forum.modelo.Topico;
import br.com.space.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController //assume o comportamento padrao de todos os metodos(ResponseBody)
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository; //injecao de dependencia
    @RequestMapping("/topicos")
    public List<TopicoDto> lista() {
        List<Topico> topicos = topicoRepository.findAll();
        return TopicoDto.converter(topicos);
    }

}
