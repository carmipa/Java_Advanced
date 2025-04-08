package br.com.fiap.money_flow_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Entity
@Data
public class Transaction {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "campo origatório")
    @Size(min = 5, max = 255, message = "deve ter entre 5 e 255 caracteres")
    private String description;

    @Positive(message = "deve ser positivo")
    private BigDecimal amount;

    @PastOrPresent(message = "deve no passado") // lançamento de hoje e antes de hoje
    private LocalDate date;

    @NotNull(message = "campo obrigatório")
    @Enumerated(EnumType.STRING) // em caso de enum se tem que usar esse caso
    private TransactionType type;

    @NotNull(message = "campo obrigatório")
    @ManyToOne// cardinalidade do relacionamento M : 1
    private Category category;

}
