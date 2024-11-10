package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.dto.Quote;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteDAO extends JpaRepository<Quote, Long> {
  Quote save(Quote quote);
  List<Quote> saveAll(List<Quote> quotes);
  List<Quote> findAll();
  Optional<Quote> findById(String ticker);
  boolean existsById(String ticker);
  void deleteById(String ticker);
  long count();
  void deleteAll();
}
