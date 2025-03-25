package br.com.fiap.money_flow_api.controller;

import br.com.fiap.money_flow_api.model.Category;
import br.com.fiap.money_flow_api.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private Logger log = LoggerFactory.getLogger(getClass());

    // private List<Category> repository = new ArrayList<>();

    // deixa de ser um arraylist para ser um category
    @Autowired // spring injeta a dependência
    private CategoryRepository repository;

    // busca todas as categorias do BD
    @GetMapping("/categories")
    public List<Category> index(){

        return repository.findAll();
    }

    //Cadastrar categorias

    @PostMapping("/categories")
    public ResponseEntity<Category> create(@RequestBody Category category){

        log.info("Cadastrando categoria...:" + category.getName());

        // cadastrar uma categoria
        //repository.add(category);

        // salva no repository
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

        // antes apagava o arraylist
        // repository.remove(getCategory(id));

        // deleta
        repository.delete(getCategory(id));

    }

    // Editar categorias
    @PutMapping("{id}")
    public Category update(@PathVariable Long id, @RequestBody Category category){

        log.info("Atualizando Categoria...:" + id + " " + category);


        // repository.remove(getCategory(id));
        // category.setId(id);
        // repository.add(category);

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
