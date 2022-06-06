package br.com.space.forum.controller;

import br.com.space.forum.controller.dto.TopicoDto;
import br.com.space.forum.modelo.Curso;
import br.com.space.forum.modelo.Topico;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController //assume o comportamento padrao de todos os metodos(ResponseBody)
public class TopicosController {

    @RequestMapping("/topicos")
    public List<TopicoDto> lista() {
        Topico topico = new Topico("Dúvidaaa", "Duvida sobre Spring", new Curso("Spring", "Programação"));
        return TopicoDto.converter(Arrays.asList(topico, topico, topico)); //cria uma lista com os elementos
    }

}
