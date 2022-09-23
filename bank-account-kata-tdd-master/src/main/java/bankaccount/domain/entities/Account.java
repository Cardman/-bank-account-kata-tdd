package bankaccount.domain.entities;

import bankaccount.domain.service.Deposite;
import bankaccount.domain.service.DoOperation;
import bankaccount.domain.service.Withdrawal;
import bankaccount.domain.valueobject.OperationCommand;
import bankaccount.domain.valueobject.TypeOperation;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CISSE Abdoulaye 2022-09-20
 */
public class Account implements IAccount {

  private final String accountNumber;
  private final List<Operation> operations;
  private BigDecimal balance;

  public Account(String accountNumber) {
    this(accountNumber,BigDecimal.ZERO);
  }

  public Account(String accountNumber, BigDecimal initialBalance) {
    this.accountNumber = accountNumber;
    this.balance = initialBalance;
    operations = new ArrayList<>();
  }

  public BigDecimal getBalance() {
    return balance;
  }


  public List<Operation> getOperations() {
    return operations;
  }

  @Override
  public void executeOperation(OperationCommand operationCommand) {
    BigDecimal operationAmount = operationCommand.amount();
    checkAmount(operationAmount);
    TypeOperation typeOperation = operationCommand.typeOperation();
    DoOperation operation = switch (typeOperation) {
      case DEPOSIT -> new Deposite();
      case WITHDRAWAL -> new Withdrawal();
    };

    balance = operation.execute(operationAmount, balance);
    saveOperation(balance, operationCommand);
  }

  private void checkAmount(BigDecimal operationAmount) {
    boolean isAccountNegative =
        operationAmount.compareTo(BigDecimal.ZERO) < 0;
    if (isAccountNegative) {
      throw new NumberFormatException("Amount can't be negative");
    }
  }

  private void saveOperation(BigDecimal balance, OperationCommand operationCommand) {
    TypeOperation typeOperation = operationCommand.typeOperation();
    BigDecimal amount = operationCommand.amount();
    LocalDate date = operationCommand.date();
    Operation operation = new Operation(date, amount, balance, typeOperation);
    this.operations.add(operation);
  }
}
