package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.dom.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountJpaRepository extends JpaRepository<Account, Integer> {

  /**
   * Find an account by trader ID using method query derivation.
   * Spring Data JPA will automatically implement this method
   * @param traderId the trader's ID
   * @return the account associated with the provided trader ID
   */
  Account getAccountByTraderId(Integer traderId);

  /**
   * Find an account by trader ID using a custom JPQL query.
   * @param traderId the trader's ID
   * @return the account associated with the provided trader ID
   */
  @Query("SELECT a FROM Account WHERE a.trader_id = ?1")
  Account getAccountByTraderUsingJpql(Integer traderId);
}
