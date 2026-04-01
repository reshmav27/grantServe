package com.cts.grantserve.service;

import com.cts.grantserve.dto.ReviewDto;
import com.cts.grantserve.entity.Proposal;
import com.cts.grantserve.entity.Review;
import com.cts.grantserve.entity.User;
import com.cts.grantserve.enums.ReviewStatus;
import com.cts.grantserve.exception.ReviewNotFoundException;
import com.cts.grantserve.repository.IProposalRepository;
import com.cts.grantserve.repository.ReviewRepository;
import com.cts.grantserve.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private IProposalRepository proposalRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private User reviewer;
    private Proposal proposal;
    private Review review;
    private ReviewDto reviewDto;

    @BeforeEach
    void setUp() {
        reviewer = new User();
        reviewer.setUserID(2L);
        reviewer.setRole("REVIEWER");

        proposal = new Proposal();
        proposal.setProposalID(10L);
        proposal.setStatus("UNDER_REVIEW");

        review = new Review();
        review.setReviewID(1L);
        review.setProposal(proposal);
        review.setReviewer(reviewer);
        review.setStatus(ReviewStatus.PENDING);

        reviewDto = new ReviewDto(10L, 2L, 8, "Great proposal", LocalDate.now(), ReviewStatus.REVIEWED);
    }

    @Test
    public void testAssignReviewer_Success() {
        // Arrange
        when(proposalRepository.findById(10L)).thenReturn(Optional.of(proposal));
        when(userRepository.findById(2L)).thenReturn(Optional.of(reviewer));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        // Act
        String result = reviewService.assignReviewer(reviewDto);

        // Assert
        assertEquals("Review assigned successfully", result);
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    public void testGetReviewsByReviewer_Success() {
        // Arrange
        when(reviewRepository.findByReviewer_UserID(2L)).thenReturn(List.of(review));

        // Act
        List<Review> result = reviewService.getReviewsByReviewer(2L);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    public void testGetReviewsByReviewer_NotFound() {
        // Arrange
        when(reviewRepository.findByReviewer_UserID(2L)).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(ReviewNotFoundException.class, () -> reviewService.getReviewsByReviewer(2L));
    }

    @Test
    public void testGetReviewById_Success() {
        // Arrange
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        // Act
        Review result = reviewService.getReviewById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getReviewID());
    }

    @Test
    public void testUpdateReview_WithProposalSync() {
        // Arrange
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        // Status is REVIEWED in reviewDto, so it should trigger proposal update

        // Act
        String result = reviewService.updateReview(1L, reviewDto);

        // Assert
        assertEquals("Review updated successfully", result);
        assertEquals("REVIEWED", proposal.getStatus()); // Verify status sync
        verify(proposalRepository, times(1)).save(proposal);
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    public void testDeleteReview_Success() {
        // Arrange
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        // Act
        String result = reviewService.deleteReview(1L);

        // Assert
        assertEquals("Review deleted successfully", result);
        verify(reviewRepository, times(1)).delete(review);
    }
}