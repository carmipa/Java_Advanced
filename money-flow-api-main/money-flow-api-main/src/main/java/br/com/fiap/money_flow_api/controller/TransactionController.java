package br.com.fiap.money_flow_api.controller;


import br.com.fiap.money_flow_api.model.Transaction;
import br.com.fiap.money_flow_api.model.TransactionFilterDto;
import br.com.fiap.money_flow_api.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transactions")
public class TransactionController {

    @Autowired
    private TransactionRepository repository;

    // Paginação + filtro (a lógica do filtro ainda precisa ser aplicada dentro do repository)
    @GetMapping
    public Page<Transaction> index(
            TransactionFilterDto filter,
            @PageableDefault(size = 10, sort = "date", direction = Sort.Direction.DESC) Pageable pageable) {

        //if (filter.description() != null){
        //    return repository.findByDescriptionContaining((filter.description(), pageable);
        //}

       // var probe = Transaction.builder()
       //         .description(filter.description())
        //        .date(filter.startDate().build();
       // var matcher = ExampleMatcher.matching().withIgnoreCase()
        //        .withStringMatcher();
        //var example = Example.of(probe, matcher);

        // TODO: aplicar filtros usando Specification, Criteria, ou lógica customizada
        // return repository.findByDescriptionContainingIgnoreCase(pageable); // <-- Aqui é só paginação, ainda sem filtros aplicados
        //return repository.findAll(example, pageable);

        var specification = TransactionSpecification.withFilter(filter);
        return repository.findAll(specification, pageable);
    }

    // query methods

}
