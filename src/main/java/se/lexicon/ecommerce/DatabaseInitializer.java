package se.lexicon.ecommerce;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import se.lexicon.ecommerce.model.Category;
import se.lexicon.ecommerce.model.Product;
import se.lexicon.ecommerce.model.Promotion;
import se.lexicon.ecommerce.repository.CategoryRepository;
import se.lexicon.ecommerce.repository.ProductRepository;
import se.lexicon.ecommerce.repository.PromotionRepository;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;

    @Override
    public void run(String... args) {
        Category electronics = categoryRepository.findByNameIgnoreCase("Electronics");
        if (electronics == null) {
            electronics = new Category();
            electronics.setName("Electronics");
            electronics = categoryRepository.save(electronics);
        }

        Category books = categoryRepository.findByNameIgnoreCase("Books");
        if (books == null) {
            books = new Category();
            books.setName("Books");
            books = categoryRepository.save(books);
        }

        if (productRepository.count() == 0) {
            Product laptop = new Product();
            laptop.setName("Laptop Pro 14");
            laptop.setPrice(new BigDecimal("14999.00"));
            laptop.setCategory(electronics);

            Product smartphone = new Product();
            smartphone.setName("Smartphone X");
            smartphone.setPrice(new BigDecimal("8999.00"));
            smartphone.setCategory(electronics);

            Product novel = new Product();
            novel.setName("Spring Boot Basics");
            novel.setPrice(new BigDecimal("299.00"));
            novel.setCategory(books);

            productRepository.saveAll(List.of(laptop, smartphone, novel));
        }

        if (promotionRepository.count() == 0) {
            Promotion springSale = new Promotion();
            springSale.setCode("SPRING10");
            springSale.setStartDate(LocalDate.now().minusDays(1));
            springSale.setEndDate(LocalDate.now().plusDays(30));

            Promotion clearance = new Promotion();
            clearance.setCode("CLEAR20");
            clearance.setStartDate(LocalDate.now().minusDays(7));
            clearance.setEndDate(null);

            promotionRepository.saveAll(List.of(springSale, clearance));
        }
    }
}
