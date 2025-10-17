package com.example.expense_tracker.services;

import com.example.expense_tracker.models.TransactionCategory;
import com.example.expense_tracker.models.User;
import com.example.expense_tracker.repositories.TransactionCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    // post
    public TransactionCategory createTransactionCategory(Long userId, String categoryName, String categoryColor){
        logger.info("Create TransactionCategory with User: "+ userId);
        User user = userService.getUserById(userId);

        TransactionCategory transactionCategory = new TransactionCategory();
        transactionCategory.setUser(user);
        transactionCategory.setCategoryName(categoryName);
        transactionCategory.setCategoryColor(categoryColor);

        TransactionCategory savedCategory = transactionCategoryRepository.save(transactionCategory);
        return savedCategory;
    }


}
