package br.com.space.forum.controller;

import br.com.space.forum.controller.dto.DetalhesDoTopicoDto;
import br.com.space.forum.controller.dto.TopicoDto;
import br.com.space.forum.controller.form.AtualizacaoTopicoForm;
import br.com.space.forum.controller.form.TopicoForm;
import br.com.space.forum.modelo.Topico;
import br.com.space.forum.repository.CursoRepository;
import br.com.space.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import org.springframework.data.domain.Page;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController //assume o comportamento padrao de todos os metodos(ResponseBody)
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository; //injecao de dependencia
    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public Page<TopicoDto> lista(@RequestParam(required = false) String nomeCurso,
                                 @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page=0, size=10)
                                 Pageable paginacao) {

        if(nomeCurso == null) {
            Page<Topico> topicos = topicoRepository.findAll(paginacao);
            return TopicoDto.converter(topicos);
        } else {
            Page<Topico> topicos = topicoRepository.findAByCurso_Nome(nomeCurso, paginacao);
            return TopicoDto.converter(topicos);
        }

    }

    @PostMapping
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
        Topico topico = form.converter(cursoRepository);
        topicoRepository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }
    @GetMapping("/{id}") //@pathVariable para o method entender que eh o valor dps da barra
    public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        if(topico.isPresent()) {
            return ResponseEntity.ok(new DetalhesDoTopicoDto(topico.get()));
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/{id}")
    @Transactional //dizendo para commitar as transações no fim deste metodo
    public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form) {
        Optional<Topico> optional = topicoRepository.findById(id);
        if(optional.isPresent()) {
            Topico topico = form.atualizar(id, topicoRepository);
            return  ResponseEntity.ok(new TopicoDto(topico));
        }

        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity remover(@PathVariable Long id) {
        Optional<Topico> optional = topicoRepository.findById(id);
        if(optional.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
