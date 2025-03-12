package br.com.fiap.money_flow_api.controller;

import br.com.fiap.money_flow_api.model.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class CategoryController {

    private Logger log = LoggerFactory.getLogger(getClass());

    private List<Category> repository = new ArrayList<>();

    @GetMapping("/categories")
    public List<Category> index(){

        return repository;
    }

    //Cadastrar categorias

    @PostMapping("/categories")
    public ResponseEntity<Category> create(@RequestBody Category category){

        log.info("Cadastrando categoria...:" + category.getName());

        repository.add(category);

        return ResponseEntity.status(201).body(category);

    }

    @GetMapping("/categories/{id}")
    public Category get(@PathVariable Long id){ // 200 sucesso

        log.info("Buscando categoria...:" + id);

        return getCategory(id);
    }

    // Apagar categorias
    @DeleteMapping("/categories/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // avisa que o retorno é 204
    public void destroy(@PathVariable Long id){

        log.info("Apagando Categoria...:" + id);

        repository.remove(getCategory(id));

    }

    // Editar categorias
    @PutMapping("/categories/{id}")
    public Category update(@PathVariable Long id, @RequestBody Category category){

        log.info("Atualizando Categoria...:" + id + " " + category);

        repository.remove(getCategory(id));
        category.setId(id);
        repository.add(category);

        return category;
    }

    private Category getCategory(Long id){
        return repository
                .stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                ); // retira o if do metodo
    }

}
