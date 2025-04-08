package br.com.fiap.money_flow_api.controller;

import br.com.fiap.money_flow_api.model.Category;
import br.com.fiap.money_flow_api.repository.CategoryRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired // spring injeta a dependência
    private CategoryRepository repository;

    // busca todas as categorias do BD
    @GetMapping("/categories")
    public List<Category> index(){

        return repository.findAll();
    }

    //Cadastrar categorias

    @PostMapping("{id}}")
    public ResponseEntity<Category> create(@RequestBody @Valid Category category){

        log.info("Cadastrando categoria...:" + category.getName());
        repository.save(category);
        return ResponseEntity.status(201).body(category);
    }

    @GetMapping("/{id}")
    public Category get(@PathVariable Long id){ // 200 sucesso

        log.info("Buscando categoria...:" + id);
        return getCategory(id);
    }

    // Apagar categorias
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // avisa que o retorno é 204
    public void destroy(@PathVariable Long id){

        log.info("Apagando Categoria...:" + id);
        repository.delete(getCategory(id));

    }

    // Editar categorias
    @PutMapping("{id}")
    public Category update(@PathVariable Long id, @RequestBody @Valid Category category){

        log.info("Atualizando Categoria...:" + id + " " + category);
        getCategory(id);
        category.setId(id);
        repository.save(category);
        return category;
    }

    private Category getCategory(Long id){
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                );
    }

}
