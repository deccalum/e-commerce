package se.lexicon.ecommerce;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.jspecify.annotations.NonNull;
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
/**
 * Seeds initial category, product, and promotion data at application startup.
 */
public class DatabaseInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;

    /**
     * Executes startup data seeding in an idempotent way.
     *
     * @param args startup arguments
     */
    @Override
    public void run(String @NonNull... args) {
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

        if (!productRepository.existsByNameIgnoreCaseAndCategoryId("Laptop Pro 14", electronics.getId())) {
            Product laptop = new Product();
            laptop.setName("Laptop Pro 14");
            laptop.setPrice(new BigDecimal("14999.00"));
            laptop.setCategory(electronics);
            productRepository.save(laptop);
        }

        if (!productRepository.existsByNameIgnoreCaseAndCategoryId("Smartphone X", electronics.getId())) {
            Product smartphone = new Product();
            smartphone.setName("Smartphone X");
            smartphone.setPrice(new BigDecimal("8999.00"));
            smartphone.setCategory(electronics);
            productRepository.save(smartphone);
        }

        if (!productRepository.existsByNameIgnoreCaseAndCategoryId("Spring Boot Basics", books.getId())) {
            Product novel = new Product();
            novel.setName("Spring Boot Basics");
            novel.setPrice(new BigDecimal("299.00"));
            novel.setCategory(books);
            productRepository.save(novel);
        }

        if (promotionRepository.findByCode("SPRING10") == null) {
            Promotion springSale = new Promotion();
            springSale.setCode("SPRING10");
            springSale.setStartDate(LocalDate.now().minusDays(1));
            springSale.setEndDate(LocalDate.now().plusDays(30));
            promotionRepository.save(springSale);
        }

        if (promotionRepository.findByCode("CLEAR20") == null) {
            Promotion clearance = new Promotion();
            clearance.setCode("CLEAR20");
            clearance.setStartDate(LocalDate.now().minusDays(7));
            clearance.setEndDate(null);
            promotionRepository.save(clearance);
        }
    }
}