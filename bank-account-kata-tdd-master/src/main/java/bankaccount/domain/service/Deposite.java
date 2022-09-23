package bankaccount.domain.service;

import java.math.BigDecimal;

/**
 * <p>Implementation to make a deposit</p>
 *
 * @author CISSE Abdoulaye 2022-09-21
 */
public class Deposite implements DoOperation {

  @Override
  public BigDecimal execute(BigDecimal operationAmount, BigDecimal actualAccountBalance) {
    return actualAccountBalance.add(operationAmount);
  }
}
