package bankaccount.domain.entities;

import bankaccount.domain.valueobject.TypeOperation;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author CISSE Abdoulaye 2022-09-20
 */
public record Operation(
    LocalDate date, BigDecimal amount, BigDecimal balance, TypeOperation typeOperation) {

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Operation operation = (Operation) o;
    return Objects.equals(date, operation.date) &&
        Objects.equals(amount, operation.amount) &&
        Objects.equals(balance, operation.balance) &&
        typeOperation == operation.typeOperation;
  }

  @Override
  public int hashCode() {
    return Objects.hash(date, amount, balance, typeOperation);
  }

  @Override
  public String toString() {
    return date +
        " | " + typeOperation +
        " | " + amount +
        " | " + balance;
  }
}
