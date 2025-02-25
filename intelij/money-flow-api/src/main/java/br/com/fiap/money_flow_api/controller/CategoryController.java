package br.com.fiap.money_flow_api.controller;

import br.com.fiap.money_flow_api.model.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    // Listar todas as categorias

    // Get : 8080/categories -> 200 ok -> json

    //@RequestMapping(method = {RequestMethod.GET}, path = "/categories", produces = "application/json")
    // public Category index(){

    @GetMapping("/categories")
    public List<Category> index(){

        return List.of(new Category(1, "Alimentação", "lanche"),
                new Category(2, "Transporte", "transport"),
                new Category(3, "Educação", "books"),
                new Category(4, "Saúde", "life"),
                new Category(5, "Lazer", "freedom"),
                new Category(6, "Salário", "money"),
                new Category(7, "Investimento", "investments"),
                new Category(8, "Outros", "other"));
    }

    //Cadastrar categorias

    // Apagar categorias
}
