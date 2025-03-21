package com.challenge.api.account.management.domain;

import com.challenge.api.account.management.service.models.AccountStatusEnum;
import com.challenge.api.account.management.service.models.AccountTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "S_CHALLENGE.T_ACCOUNTS")
public class Account {

    @Id
    @Min(value = 100000, message = "El número debe ser un valor secuencial de 6 dígitos")
    @Max(value = 999999, message = "El número debe ser un valor secuencial de 6 dígitos")
    @Column("account_number")
    @JsonProperty("accountNumber")
    private Long accountNumber;

    private String customerId;

    @NotBlank(message = "El tipo no puede estar vacío")
    @Size(max = 50, message = "El tipo no puede tener más de 50 caracteres")
    private AccountTypeEnum type;

    @Min(value = 0, message = "El saldo inicial no puede ser negativo")
    private BigDecimal initialBalance;

    @NotBlank(message = "El estado no puede estar vacío")
    @Size(max = 20, message = "El estado no puede tener más de 20 caracteres")
    private AccountStatusEnum status;

}