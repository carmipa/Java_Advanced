package br.com.fiap.money_flow_api.controller;

import br.com.fiap.money_flow_api.model.Transaction;
import br.com.fiap.money_flow_api.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("transactions")
public class TransactionJava {

    @Autowired // injeta a dependencia
    private TransactionRepository repository;

    @GetMapping
    public List<Transaction> index(){

        return repository.findAll();
    }

}
