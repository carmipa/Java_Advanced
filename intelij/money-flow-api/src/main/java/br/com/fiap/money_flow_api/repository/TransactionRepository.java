package br.com.fiap.money_flow_api.repository;

import br.com.fiap.money_flow_api.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
