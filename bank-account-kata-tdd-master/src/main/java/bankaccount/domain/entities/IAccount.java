package bankaccount.domain.entities;

import bankaccount.domain.valueobject.OperationCommand;

/**
 * <p> Describing the contracts that an account must respect</p>
 *
 * @author CISSE Abdoulaye 2022-09-21
 */
public interface IAccount {

  void executeOperation(OperationCommand operationCommand);

}
