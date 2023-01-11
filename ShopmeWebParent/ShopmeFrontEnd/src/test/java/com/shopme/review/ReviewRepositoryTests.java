package com.shopme.review;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Review;
import com.shopme.common.entity.product.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ReviewRepositoryTests {
	
	@Autowired private ReviewRepository repo;
	
	@Test
	public void testFindByCustomerNoKeyword() {
		Integer customerId = 5;
		Pageable pageable = PageRequest.of(1, 5);
		
		Page<Review> page = repo.findByCustomer(customerId, pageable);
		long totalElements = page.getTotalElements();
		
		assertThat(totalElements).isGreaterThan(1);		
	}
	
	@Test
	public void testFindByCustomerWithKeyword() {
		Integer customerId = 5;
		String keyword = "charger";
		Pageable pageable = PageRequest.of(1, 5);
		
		Page<Review> page = repo.findByCustomer(customerId, keyword, pageable);
		long totalElements = page.getTotalElements();
		
		assertThat(totalElements).isGreaterThan(0);		
	}
	
	@Test
	public void testFindByCustomerAndId() {
		Integer customerId = 5;
		Integer reviewId = 4;
		
		Review review = repo.findByCustomerAndId(customerId, reviewId);
		assertThat(review).isNotNull();
	}
	
	@Test
	public void testFindByProduct() {
		Product product = new Product(23);
		Pageable pageable = PageRequest.of(0, 3);
		Page<Review> page = repo.findByProduct(product, pageable);
		
		assertThat(page.getTotalElements()).isGreaterThan(1);
		
		List<Review> content = page.getContent();
		content.forEach(System.out::println);
	}
	
	@Test
	public void testCountByCustomerAndProduct() {
		Integer customerId = 5;
		Integer productId = 1;
		Long count = repo.countByCustomerAndProduct(customerId, productId);
		
		assertThat(count).isEqualTo(1);
	}
	
	@Test
	public void testUpdateVoteCount() {
		Integer reviewId = 5;
		repo.updateVoteCount(reviewId);
		Review review = repo.findById(reviewId).get();
		
		assertThat(review.getVotes()).isEqualTo(2);
	}
	
	@Test
	public void testGetVoteCount() {
		Integer reviewId = 5;
		Integer voteCount = repo.getVoteCount(reviewId);
		
		assertThat(voteCount).isEqualTo(2);
	}
}
