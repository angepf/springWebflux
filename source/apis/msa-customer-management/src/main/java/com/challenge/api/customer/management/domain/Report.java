package com.challenge.api.customer.management.domain;

import com.challenge.api.customer.management.service.models.AccountStatusEnum;
import com.challenge.api.customer.management.service.models.AccountTypeEnum;
import com.challenge.api.customer.management.service.models.MovementTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "T_REPORTS", schema = "S_CHALLENGE")
public class Report {

    @Id
    private Long id;

    private LocalDate date;

    @Column("customer_id")
    private String customerId;

    @Column("account_number")
    private Long accountNumber;

    @Column("type_account")
    private AccountTypeEnum typeAccount;

    @Column("initial_balance")
    private BigDecimal initialBalance;

    private AccountStatusEnum status;

    private BigDecimal value;

    @Column("type_movement")
    private MovementTypeEnum typeMovement;

    private BigDecimal balance;

}