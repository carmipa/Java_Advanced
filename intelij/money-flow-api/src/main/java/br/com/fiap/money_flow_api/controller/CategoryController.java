package br.com.fiap.money_flow_api.controller;

import br.com.fiap.money_flow_api.model.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {

    private List<Category> repository = new ArrayList<>();

    @GetMapping("/categories")
    public List<Category> index(){

        return repository;
    }

    //Cadastrar categorias

    @PostMapping("/categories")
    public ResponseEntity<Category> create(@RequestBody Category category){

        System.out.println("Cadastrando categoria...:" + category.getName());

        repository.add(category);

        return ResponseEntity.status(201).body(category);

    }

    @GetMapping("/categories/{id}")
    public ResponseEntity <Category> get(@PathVariable Long id){

        System.out.println("Buscando categoria...:" + id);

        var category = repository
                .stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();

        if(category.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(category.get());
    }

    // Apagar categorias
}
