package de.fbeutel.coloranalyzer.product.domain;

import static org.springframework.data.domain.Sort.Order.asc;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String>, ProductRepositoryCustom {

}

interface ProductRepositoryCustom {

  List<Product> findAllSortedByColor(String selectedColor);
}

class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

  private final MongoTemplate mongoTemplate;

  public ProductRepositoryCustomImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public List<Product> findAllSortedByColor(final String selectedColor) {
    final String fieldName = "productImage.imageScores." + selectedColor;
    final Query query = new Query();
    query.addCriteria(new Criteria().andOperator(Criteria.where(fieldName).exists(true), Criteria.where(fieldName).lt(10)));
    query.with(Sort.by(asc(fieldName)));

    return mongoTemplate.find(query, Product.class);
  }
}