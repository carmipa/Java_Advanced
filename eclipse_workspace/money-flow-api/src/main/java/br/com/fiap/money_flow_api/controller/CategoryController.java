package br.com.fiap.money_flow_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

    // Listar todas as categorias

    // Get : 8080/categories -> 200 ok -> json

    @RequestMapping(method = {RequestMethod.GET}, path = "/categories")
    public String index(){

        return "Lista de categorias";
    }

    //Cadastrar categorias

    // Apagar categorias
}
