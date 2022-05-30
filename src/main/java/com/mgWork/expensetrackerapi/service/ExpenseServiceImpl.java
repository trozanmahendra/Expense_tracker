package com.mgWork.expensetrackerapi.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mgWork.expensetrackerapi.entity.Expense;
import com.mgWork.expensetrackerapi.exception.ResourceNotFoundException;
import com.mgWork.expensetrackerapi.repository.ExpenseRepository;

@Service
public class ExpenseServiceImpl implements ExpenseService {
	
	
//	@Bean
//	public Date dateBean() {
//		return new Date();
//		
//	}

	private ExpenseRepository expenseRepo;
	private UserService userService;

	@Autowired
	public ExpenseServiceImpl(ExpenseRepository expenseRepo, UserService userService) {
		super();
		this.expenseRepo = expenseRepo;
		this.userService = userService;
	}

	@Override
	public Expense getExpenseById(Long id) throws Exception {
		Optional<Expense> exp = expenseRepo.findByUserIdAndId(userService.getLoggedInUser().getId(),id);
		if (exp.isPresent()) 
			return exp.get();
		else
			throw new ResourceNotFoundException("expense id not present" + id);
	}

	@Override
	public Expense saveExpenseDetails(Expense expense) {
		expense.setUser(userService.getLoggedInUser());
		return expenseRepo.save(expense);
	}

	@Override
	public Expense updateExpenseDetails(Expense expense, long id) throws Exception {
		Expense oldvalue = getExpenseById(id);
		oldvalue.setName(expense.getName() != null ? expense.getName() : oldvalue.getName());
		oldvalue.setAmount(expense.getAmount() != null ? expense.getAmount() : oldvalue.getAmount());
		oldvalue.setCategory(expense.getCategory() != null ? expense.getCategory() : oldvalue.getCategory());
		oldvalue.setDate(expense.getDate() != null ? expense.getDate() : oldvalue.getDate());
		oldvalue.setDescription(
				expense.getDescription() != null ? expense.getDescription() : oldvalue.getDescription());

		return expenseRepo.save(oldvalue);
	}

	@Override
	public void DeleteExpense(long id) throws ResourceNotFoundException {
		Optional<Expense> exp = expenseRepo.findById(id);
		if (exp.isPresent()) 
			expenseRepo.deleteById(id);
		else
		throw new ResourceNotFoundException("expense id not present to delete " + id);

	}

	@Override
	public Page<Expense> getAllExpenses(Pageable pageable) {
		
		
		return expenseRepo.findByUserId(userService.getLoggedInUser().getId(), pageable);
	}

	@Override
	public List<Expense> getExpenseByCategory(String category, Pageable pageable) {

		return expenseRepo.findByUserIdAndCategory(userService.getLoggedInUser().getId(), category, pageable);
	}

	@Override
	public List<Expense> getByNameContaining(String name, Pageable pageable) {

		return expenseRepo.findByUserIdAndNameContaining(userService.getLoggedInUser().getId(),name, pageable);
	}

	@Override
	public List<Expense> getByDatebetween(Date start, Date end, Pageable page) {
		if (start == null)
			start = new Date(0);

		if (end == null)
			end = new Date(System.currentTimeMillis());

		return expenseRepo.findByuserIdAndDateBetween(userService.getLoggedInUser().getId(),start, end, page);
	}

}
