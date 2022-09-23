package bankaccount.domain.valueobject;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>Describing  data necessary to  perform an operation on a account</p>
 *
 * @author CISSE Abdoulaye 2022-09-21
 */
public record OperationCommand (LocalDate date,BigDecimal amount,TypeOperation typeOperation){ }



