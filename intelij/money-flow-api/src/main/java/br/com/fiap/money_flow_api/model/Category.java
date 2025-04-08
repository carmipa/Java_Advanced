package br.com.fiap.money_flow_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
// @Getter
// @Setter
// @NoArgsConstructor
// @AllArgsConstructor
// @ToString
// @EqualsAndHashCode
@Data // cria tudo o que foi feito acima get, set hash, construtor, to string
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @NotNull - valida para não ser nulo
    @NotBlank(message = "campo obigatório")// -- não permite caractere mem branco
    // @NotEmpty validae para que não seja vazio
    @Size(min=3 , max=255, message = "deve ter pelo menos 3 caracteres")
    private String name;

    @NotBlank(message = "Campo obrigatório")
    @Pattern(regexp = "^[A-Z].*", message = "deve começar com letra maiuscula") // obrigando a começar com letra maiuscula
    private String icon;

}
