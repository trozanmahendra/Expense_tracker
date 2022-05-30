package com.mgWork.expensetrackerapi.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mgWork.expensetrackerapi.entity.Expense;
import com.mgWork.expensetrackerapi.exception.ResourceNotFoundException;

public interface ExpenseService {
	
	Page<Expense> getAllExpenses(Pageable pageable);
	
	Expense getExpenseById(Long id) throws Exception;
	Expense saveExpenseDetails(Expense expense);
	Expense updateExpenseDetails(Expense expense, long id) throws Exception;
	void DeleteExpense(long id) throws ResourceNotFoundException;
	List<Expense> getExpenseByCategory(String category,Pageable pageable);
	List<Expense> getByNameContaining(String name,Pageable pageable);
	List<Expense> getByDatebetween(Date start , Date end,Pageable page);
}
