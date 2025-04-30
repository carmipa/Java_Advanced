package br.com.fiap.money_flow_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.money_flow_api.model.Transaction;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {

    Page<Transaction> findByDescriptionContaining(String description, Pageable pageable);


    Page<Transaction> findByDescriptionContainingIgnoreCase(Pageable pageable);
}
