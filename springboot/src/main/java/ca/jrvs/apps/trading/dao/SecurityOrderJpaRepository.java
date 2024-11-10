package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.dom.SecurityOrder;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SecurityOrderJpaRepository extends JpaRepository<SecurityOrder, Integer> {
  /**
   * Find all security orders by account ID and status
   * @param accountId the account ID
   * @param status the status of the order (e.g. "FILLED", "CANCELLED")
   * @return a list of security orders matching the criteria
   */
  List<SecurityOrder> findByAccountIdAndStatus(Integer accountId, String status);

  /**
   * TODO
   * Example of a JPQL query to find security orders by ticker and status.
   * This is just an example in case you need more complex queries later.
   */
  @Query("SELECT s FROM SecurityOrder s WHERE s.ticker = ?1 AND s.status = ?2")
  SecurityOrder findByTickerAndStatus(String ticker, String status);

}
