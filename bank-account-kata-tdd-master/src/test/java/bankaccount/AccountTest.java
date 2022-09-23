package bankaccount;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import bankaccount.domain.entities.Account;
import bankaccount.domain.entities.Operation;
import bankaccount.domain.exception.InsufficientBalanceException;
import bankaccount.domain.valueobject.OperationCommand;
import bankaccount.domain.valueobject.TypeOperation;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author CISSE Abdoulaye 2022-09-20
 */
class AccountTest {

  @Test
  public void deposit_withPositiveAmount() {

    //Given
    Account account = new Account("AC001");
    BigDecimal amount = BigDecimal.valueOf(10_000);
    TypeOperation typeOperation = TypeOperation.DEPOSIT;
    LocalDate dateOperation = LocalDate.of(2020,9,19);
    OperationCommand operationCommand = new OperationCommand(dateOperation,amount, typeOperation);

    //when
    account.executeOperation(operationCommand);

    //then
    BigDecimal expectedBalance = BigDecimal.valueOf(10_000);
    BigDecimal actualBalance = account.getBalance();
    Assertions.assertEquals(expectedBalance, actualBalance);
  }


  @Test
  public void deposit_withNegativeAmount() {

    //Given
    Account account = new Account("AC002");
    BigDecimal amount = BigDecimal.valueOf(-10_000);
    TypeOperation typeOperation = TypeOperation.DEPOSIT;
    LocalDate dateOperation = LocalDate.of(2020,9,19);
    OperationCommand operationCommand = new OperationCommand(dateOperation,amount, typeOperation);

    //then
    assertThrows(NumberFormatException.class,
        () -> {
          account.executeOperation(operationCommand);
        });
  }

  @Test
  public void withdrawal_withPositiveAmount() {

    //Given
    BigDecimal initialBalance = BigDecimal.valueOf(20_000);
    Account account = new Account("AC001", initialBalance);
    BigDecimal amount = BigDecimal.valueOf(10_000);
    TypeOperation typeOperation = TypeOperation.WITHDRAWAL;
    LocalDate dateOperation = LocalDate.of(2020,9,19);
    OperationCommand operationCommand = new OperationCommand(dateOperation,amount, typeOperation);

    //when
    account.executeOperation(operationCommand);

    //then
    BigDecimal expectedBalance = BigDecimal.valueOf(10_000);
    BigDecimal actualBalance = account.getBalance();
    Assertions.assertEquals(expectedBalance, actualBalance);
  }


  @Test
  public void withdrawal_withOperationAmountEqualToActualAccountBalance() {

    //Given
    BigDecimal initialBalance = BigDecimal.valueOf(20_000);
    Account account = new Account("AC001", initialBalance);
    BigDecimal amount = BigDecimal.valueOf(20_000);
    TypeOperation typeOperation = TypeOperation.WITHDRAWAL;
    LocalDate dateOperation = LocalDate.of(2020,9,19);
    OperationCommand operationCommand = new OperationCommand(dateOperation,amount, typeOperation);

    //when
    account.executeOperation(operationCommand);

    //then
    BigDecimal expectedBalance = BigDecimal.ZERO;
    BigDecimal actualBalance = account.getBalance();
    Assertions.assertEquals(expectedBalance, actualBalance);
  }


  @Test
  public void withdrawal_withNegativeAmount() {

    //Given
    BigDecimal initialBalance = BigDecimal.valueOf(20_000);
    Account account = new Account("AC001", initialBalance);
    BigDecimal amount = BigDecimal.valueOf(-10_000);
    TypeOperation typeOperation = TypeOperation.WITHDRAWAL;
    LocalDate dateOperation = LocalDate.of(2020,9,19);
    OperationCommand operationCommand = new OperationCommand(dateOperation,amount, typeOperation);

    //then
    assertThrows(NumberFormatException.class,
        () -> {
          account.executeOperation(operationCommand);
        });
  }

  @Test
  public void whihdraw_withInsufficientBalance() {

    //Given
    BigDecimal initialBalance = BigDecimal.valueOf(20_000);
    Account account = new Account("AC001", initialBalance);
    BigDecimal amount = BigDecimal.valueOf(40_000);
    TypeOperation typeOperation = TypeOperation.WITHDRAWAL;
    LocalDate dateOperation = LocalDate.of(2020,9,19);
    OperationCommand operationCommand = new OperationCommand(dateOperation,amount, typeOperation);

    //then
    assertThrows(InsufficientBalanceException.class,
        () -> {
          account.executeOperation(operationCommand);
        });
  }

  @Test
  public void checkTransactions_withNoTransactionDo() {

    //Given
    BigDecimal initialBalance = BigDecimal.valueOf(20_000);
    Account account = new Account("AC001", initialBalance);


    //then
    List<Operation> operations = account.getOperations();
    assertTrue(operations.isEmpty());
  }

  @Test
  public void checkTransactions_withAtLeastOneTransaction() {

    //Given

    BigDecimal initialBalance = BigDecimal.valueOf(20_000);
    Account account = new Account("AC001", initialBalance);
    LocalDate firstDateOperation = LocalDate.of(2020,9,19);
    OperationCommand firstOperationCommand = new OperationCommand(firstDateOperation,
        BigDecimal.valueOf(30_000),
        TypeOperation.DEPOSIT);
    account.executeOperation(firstOperationCommand);

    LocalDate secondDateOperation = LocalDate.of(2020,9,20);
    OperationCommand SecondOperationCommand = new OperationCommand(secondDateOperation,
        BigDecimal.valueOf(5_000),
        TypeOperation.WITHDRAWAL);
    account.executeOperation(SecondOperationCommand);


    Operation firstOperation = new Operation(firstDateOperation,BigDecimal.valueOf(30_000),
        BigDecimal.valueOf(50_000), TypeOperation.DEPOSIT);
    Operation secondOperation = new Operation(secondDateOperation,BigDecimal.valueOf(5_000),
        BigDecimal.valueOf(45_000), TypeOperation.WITHDRAWAL);
    List<Operation> operationsExpected =new ArrayList<>();
    operationsExpected.add(firstOperation);
    operationsExpected.add(secondOperation);

    //then
    List<Operation> operationsActual = account.getOperations();
    Assertions.assertEquals(operationsExpected,operationsActual);
  }

}
