package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dom.Account;
import ca.jrvs.apps.trading.dao.AccountJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

  private final AccountJpaRepository accountJpaRepository;
  private final SecurityOrderJpaRepository securityOrderJpaRepository;

  @Autowired
  public AccountService(AccountJpaRepository accountJpaRepository) {
    this.accountJpaRepository = accountJpaRepository;
    this.securityOrderJpaRepository = securityOrderJpaRepository;
  }

  /**
   * Deletes the account if the balance is 0 and there are no open positions.
   * @param traderId cannot be null
   * @throws IllegalArgumentException if unable to delete (e.g. balance not 0 or open positions)
   */
  @Transactional
  public void deleteAccountByTraderId(Integer traderId) {
    Account account = accountJpaRepository.getAccountByTraderId(traderId);

    if (account != null) {
      throw new IllegalArgumentException("Trader ID not found: " + traderId);
    }

    if (account.getAmount() != 0) {
      throw new IllegalArgumentException("Account balance not 0.");
    }

    // Check if there are any open positions
    List<SecurityOrder> openOrders = securityOrderJpaRepository.findByAccountIdAndStatus(account.getId(), "OPEN");
    if (!openOrders.isEmpty()) {
      throw new IllegalArgumentException("Cannot delete account with open positions.");
    }

    // Delete the account
    accountJpaRepository.deleteById(account.getId());
  }

  /**
   * Deposit funds into an account.
   * @param traderId the trader's ID
   * @param amount the amount to deposit (must be positive)
   * @return updated Account
   * @throws IllegalArgumentException if input is valid
   */
  @Transactional
  public Account deposit(Integer traderId, Double amount) {
    if (amount == null || amount <= 0) {
      throw new IllegalArgumentException("Amount must be greater than 0.");
    }

    Account account = accountJpaRepository.getAccountByTraderId(traderId);
    if (account == null) {
      throw new IllegalArgumentException("Trader ID not found: " + traderId);
    }

    // Update account balance
    account.setAmount(account.getAmount() + amount);
    return accountJpaRepository.save(account); // Save the updated account
  }

  /**
   * Withdraw funds from an account.
   * @param traderId the trader's ID
   * @param amount the amount to withdraw (must be positive and <= account balance)
   * @return updated Account
   * @throws IllegalArgumentException if input is invalid or insufficient funds
   */
  @Transactional
  public Account withdraw(Integer traderId, Double amount) {
    if (amount == null || amount <= 0) {
      throw new IllegalArgumentException("Withdrawal amount must be greater than 0.");
    }

    Account account = accountJpaRepository.getAccountByTraderId(traderId);
    if (account == null) {
      throw new IllegalArgumentException("Trader ID not found: " + traderId);
    }

    // Update account balance
    account.setAmount(account.getAmount() - amount);
    return accountJpaRepository.save(account); // Save the updated account
  }

  /**
   * Find an account by trader ID.
   * @param traderId the trader's ID
   * @return Account
   * @throws IllegalArgumentException if trader ID is not found
   */
  public Account findByAccountByTraderId(Integer traderId) {
    Account account = accountJpaRepository.getAccountByTraderId(traderId);
    if (account == null) {
      throw new IllegalArgumentException("Trader ID not found: " + traderId);
    }
    return account;
  }

  /**
   * Create a new account for a trader.
   * @param traderId the trader's ID
   * @param initialAmount the initial deposit amount
   * @return the created Account
   * @throws IllegalArgumentException if input is invalid
   */
  @Transactional
  public Account creatAccount(Integer traderId, Double initialAmount) {
    if (initialAmount == null || initialAmount <= 0) {
      throw new IllegalArgumentException("Initial amount must be greater than 0.");
    }

    Account account = new Account();
    account.setTraderId(traderId);
    account.setAmount(initialAmount);
    return accountJpaRepository.save(account);
  }
}
