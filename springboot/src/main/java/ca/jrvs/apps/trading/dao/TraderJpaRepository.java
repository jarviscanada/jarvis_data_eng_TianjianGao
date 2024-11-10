package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.dom.Trader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TraderJpaRepository extends JpaRepository<Trader, Integer> {
}
