package com.example.expense_tracker.services;

import com.example.expense_tracker.DTO.TransactionCategoryResponse;
import com.example.expense_tracker.DTO.UserResponse;
import com.example.expense_tracker.DTO.UserTransactionResponse;
import com.example.expense_tracker.exceptions.TransactionCategoryAlreadyExist;
import com.example.expense_tracker.models.TransactionCategory;
import com.example.expense_tracker.models.User;
import com.example.expense_tracker.repositories.TransactionCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class TransactionCategoryService {

    private static final Logger logger = Logger.getLogger(TransactionCategoryService.class.getName());

    private final TransactionCategoryRepository transactionCategoryRepository;
    private final UserService userService;

    public TransactionCategoryService(TransactionCategoryRepository transactionCategoryRepository, UserService userService) {
        this.transactionCategoryRepository = transactionCategoryRepository;
        this.userService = userService;
    }


    //get
    public UserTransactionResponse getTransactionCategoriesByUserId(Long userId){
        User user = userService.getUserById(userId);
        UserResponse userResponse = new UserResponse(user.getId(), user.getName(), user.getEmail());

        List<TransactionCategory> categories =transactionCategoryRepository.getTransactionCategoryByUserId(userId);

        List<TransactionCategoryResponse> categoryResponses = categories.stream().map(
                cat -> new TransactionCategoryResponse(cat.getId(), cat.getCategoryName(), cat.getCategoryColor())
        ).toList();

        UserTransactionResponse userTransactionResponse = new UserTransactionResponse(
                userResponse,
                categoryResponses
        );

        return userTransactionResponse;
    }

    public TransactionCategory getTransactionCategoryByCategoryName(String name){
        return transactionCategoryRepository.getTransactionCategoryByCategoryName(name);
    }


    // post
    public UserTransactionResponse createTransactionCategory(Long userId, String categoryName, String categoryColor) {
        logger.info(String.format("Creating TransactionCategory for User ID: {}", userId));

        // 1. Validate user
        User user = userService.getUserById(userId);


        // 2. Validate if category already exists for this user
        UserTransactionResponse existingCategoriesResponse = getTransactionCategoriesByUserId(userId);
        List<TransactionCategoryResponse> categoryResponseList = existingCategoriesResponse.getCategories();

        boolean alreadyExists = categoryResponseList.stream()
                .anyMatch(category -> category.getName().equalsIgnoreCase(categoryName));

        if (alreadyExists) {
            throw new TransactionCategoryAlreadyExist(categoryName);
        }

        // 3. Create and save new category
        TransactionCategory transactionCategory = new TransactionCategory();
        transactionCategory.setUser(user);
        transactionCategory.setCategoryName(categoryName);
        transactionCategory.setCategoryColor(categoryColor);

        TransactionCategory savedCategory = transactionCategoryRepository.save(transactionCategory);

        // 4. Build response
        UserResponse userResponse = new UserResponse(user.getId(), user.getName(), user.getEmail());
        TransactionCategoryResponse categoryResponse = new TransactionCategoryResponse(
                savedCategory.getId(),
                savedCategory.getCategoryName(),
                savedCategory.getCategoryColor()
        );

        return new UserTransactionResponse(userResponse, List.of(categoryResponse));
    }
    
}
