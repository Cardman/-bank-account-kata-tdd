package bankaccount.domain.service;

import java.math.BigDecimal;

/**
 * <p>Describe method to execute an operation on account./p>
 *
 * @author CISSE Abdoulaye 2022-09-21
 */
public interface DoOperation {

  BigDecimal execute(BigDecimal operationAmount, BigDecimal actualAccountBalance);
}
