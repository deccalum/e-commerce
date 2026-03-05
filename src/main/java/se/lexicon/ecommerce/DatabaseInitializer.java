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
        System.out.println("[DatabaseInitializer] Starting data seeding...");
        Category electronics = categoryRepository.findByNameIgnoreCase("Electronics");
        if (electronics == null) {
            electronics = new Category();
            electronics.setName("Electronics");
            electronics = categoryRepository.save(electronics);
            System.out.println("[DatabaseInitializer] Created Electronics category");
        }
        System.out.println("[DatabaseInitializer] Electronics category ready");

        Category books = categoryRepository.findByNameIgnoreCase("Books");
        if (books == null) {
            books = new Category();
            books.setName("Books");
            books = categoryRepository.save(books);
            System.out.println("[DatabaseInitializer] Created Books category");
        }
        System.out.println("[DatabaseInitializer] Books category ready");

        if (!productRepository.existsByNameIgnoreCaseAndCategoryId("Laptop Pro 14", electronics.getId())) {
            Product laptop = new Product();
            laptop.setName("Laptop Pro 14");
            laptop.setPrice(new BigDecimal("14999.00"));
            laptop.setCategory(electronics);
            laptop.setImageUrls(java.util.List.of());
            productRepository.save(laptop);
            System.out.println("[DatabaseInitializer] Created Laptop Pro 14");
        }
        System.out.println("[DatabaseInitializer] Laptop Pro 14 ready");

        if (!productRepository.existsByNameIgnoreCaseAndCategoryId("Smartphone X", electronics.getId())) {
            Product smartphone = new Product();
            smartphone.setName("Smartphone X");
            smartphone.setPrice(new BigDecimal("8999.00"));
            smartphone.setCategory(electronics);
            smartphone.setImageUrls(java.util.List.of());
            productRepository.save(smartphone);
            System.out.println("[DatabaseInitializer] Created Smartphone X");
        }
        System.out.println("[DatabaseInitializer] Smartphone X ready");

        if (!productRepository.existsByNameIgnoreCaseAndCategoryId("Spring Boot Basics", books.getId())) {
            Product novel = new Product();
            novel.setName("Spring Boot Basics");
            novel.setPrice(new BigDecimal("299.00"));
            novel.setCategory(books);
            novel.setImageUrls(java.util.List.of());
            productRepository.save(novel);
            System.out.println("[DatabaseInitializer] Created Spring Boot Basics");
        }
        System.out.println("[DatabaseInitializer] Spring Boot Basics ready");

        System.out.println("[DatabaseInitializer] Data seeding complete.");

        // Print application.properties contents
        try {
            java.io.InputStream in = getClass().getClassLoader().getResourceAsStream("application.properties");
            if (in != null) {
                System.out.println("[DatabaseInitializer] application.properties content:");
                java.util.Properties props = new java.util.Properties();
                props.load(in);
                props.forEach((k, v) -> System.out.println(k + "=" + v));
                in.close();
            } else {
                System.out.println("[DatabaseInitializer] application.properties not found in classpath.");
            }
        } catch (Exception e) {
            System.out.println("[DatabaseInitializer] Could not print application.properties: " + e.getMessage());
        }

        /*
         * if (promotionRepository.findByCode("SPRING10") == null) {
         * Promotion springSale = new Promotion();
         * springSale.setCode("SPRING10");
         * springSale.setDiscountType("PERCENTAGE"); // Set a non-null discount type
         * springSale.setDiscountValue(new BigDecimal("10.00"));
         * springSale.setStartDate(LocalDate.now().minusDays(1));
         * springSale.setEndDate(LocalDate.now().plusDays(30));
         * promotionRepository.save(springSale);
         * }
         * 
         * if (promotionRepository.findByCode("CLEAR20") == null) {
         * Promotion clearance = new Promotion();
         * clearance.setCode("CLEAR20");
         * clearance.setDiscountType("PERCENTAGE"); // Set a non-null discount type
         * clearance.setDiscountValue(new BigDecimal("20.00"));
         * clearance.setStartDate(LocalDate.now().minusDays(7));
         * clearance.setEndDate(null);
         * promotionRepository.save(clearance);
         * 
         * }
         */
    }
}