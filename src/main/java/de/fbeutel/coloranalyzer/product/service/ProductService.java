package de.fbeutel.coloranalyzer.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import de.fbeutel.coloranalyzer.product.domain.Product;
import de.fbeutel.coloranalyzer.product.domain.ProductRepository;

@Slf4j
@Service
public class ProductService {

  private final ProductRepository productRepository;

  public ProductService(final ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public List<Product> findAllSortedByColor(final String selectedColor) {
    return productRepository.findAllSortedByColor(selectedColor);
  }
}
