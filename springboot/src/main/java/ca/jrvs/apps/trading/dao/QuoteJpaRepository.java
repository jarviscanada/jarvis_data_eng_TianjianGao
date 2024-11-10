package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.dom.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteJpaRepository extends JpaRepository<Quote, Integer> {

}
