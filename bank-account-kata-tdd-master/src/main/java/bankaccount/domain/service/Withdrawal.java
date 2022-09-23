package bankaccount.domain.service;

import bankaccount.domain.exception.InsufficientBalanceException;
import java.math.BigDecimal;

/**
 * <p>Implementation to make a withdrawal</p>
 *
 * @author CISSE Abdoulaye 2022-09-21
 */
public class Withdrawal implements DoOperation {

  @Override
  public BigDecimal execute(BigDecimal operationAmount, BigDecimal actualAccountBalance) {
    boolean isAccountBalanceInsufficient =
        actualAccountBalance.compareTo(operationAmount) < 0;
    if (isAccountBalanceInsufficient) {
      throw new InsufficientBalanceException();
    }
    return actualAccountBalance.subtract(operationAmount);
  }
}
