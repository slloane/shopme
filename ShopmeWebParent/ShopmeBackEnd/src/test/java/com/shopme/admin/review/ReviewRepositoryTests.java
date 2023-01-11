package com.shopme.admin.review;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Customer;
import com.shopme.common.entity.Review;
import com.shopme.common.entity.product.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ReviewRepositoryTests {

	@Autowired private ReviewRepository repo;
	
	@Test
	public void testCreateReview() {
		Integer productId = 1;
		Product product = new Product(productId);
		
		Integer customerId = 5;
		Customer customer = new Customer(customerId);
		
		Review review = new Review();
		review.setProduct(product);
		review.setCustomer(customer);
		review.setReviewTime(new Date());
		review.setHeadline("Perfect for my needs. Loving it!");
		review.setComment("Nice to have: wireless remote, iOS app, GPS...");
		review.setRating(5);
		
		Review savedReview = repo.save(review);
		
		assertThat(savedReview).isNotNull();
		assertThat(savedReview.getId()).isGreaterThan(0);		
	}
	
	@Test
	public void testListReviews() {
		List<Review> listReviews = repo.findAll();
		
		assertThat(listReviews.size()).isGreaterThan(0);
		
		listReviews.forEach(System.out::println);
	}
	
	@Test
	public void testGetReview() {
		Integer id = 3;
		Review review = repo.findById(id).get();
		
		assertThat(review).isNotNull();
		
		System.out.println(review);
	}
	
	@Test
	public void testUpdateReview() {
		Integer id = 3;
		String headline = "An awesome camera at an awesome price";
		String comment = "Overall great camera and is highly capable...";
		
		Review review = repo.findById(id).get();
		review.setHeadline(headline);
		review.setComment(comment);
		
		Review updatedReview = repo.save(review);
		
		assertThat(updatedReview.getHeadline()).isEqualTo(headline);
		assertThat(updatedReview.getComment()).isEqualTo(comment);
	}
	
	@Test
	public void testDeleteReview() {
		Integer id = 3;
		repo.deleteById(id);
		
		Optional<Review> findById = repo.findById(id);
		
		assertThat(findById).isNotPresent();
	}
}
