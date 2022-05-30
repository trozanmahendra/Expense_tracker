package com.mgWork.expensetrackerapi.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mgWork.expensetrackerapi.entity.Expense;
import com.mgWork.expensetrackerapi.exception.ResourceNotFoundException;
import com.mgWork.expensetrackerapi.service.ExpenseService;

@RestController
public class ExpenseController {

	private ExpenseService expenseService;

	@Autowired
	public ExpenseController(ExpenseService expenseService) {
		super();
		this.expenseService = expenseService;
	}

//	@ResponseStatus(code = HttpStatus.FOUND)
//	@GetMapping("/expenses")
//	public List<Expense> getAllExpenses() {
//		return expenseService.getAllExpenses();
//	}
	@ResponseStatus(code = HttpStatus.FOUND)
	@GetMapping("/expenses")
	public List<Expense> getAllExpenses(Pageable pageable) {

//		calculatefactorial(1);         //stackOverFlowError arises caught by generalised exceptionhandler
//		return expenseService.getAllExpenses(pageable);
		return expenseService.getAllExpenses(pageable).toList();

	}

	@GetMapping("/expenses/{user_id}/id/{id}")
	public String getExpenseById(@PathVariable("user_id") Long user_id, @PathVariable Long id) {
		return "The pathvariables are " + id + " " + user_id;

	}

	@GetMapping("/users/expenses")
	public String getExpenseById2(@RequestParam("user_id") Long user_id, @RequestParam Long id) {
		return "The pathvariables are " + id + " " + user_id;

	}

	@ResponseStatus(code = HttpStatus.FOUND)
	@GetMapping("/expense")
	public Expense getExpenseById3(@RequestParam Long id) throws Exception {
		return expenseService.getExpenseById(id);

	}

	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping("/expense")
	public Expense saveExpense(@Valid @RequestBody Expense expense) {
		return expenseService.saveExpenseDetails(expense);

	}

	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@DeleteMapping("/expense")
	public void deleteExpense(@RequestParam Long id) throws ResourceNotFoundException {
		expenseService.DeleteExpense(id);
	}

	@ResponseStatus(code = HttpStatus.CREATED)
	@PutMapping("/update/expense/{id}")
	public Expense updateExpenseDetails(@PathVariable long id, @RequestBody Expense expense) throws Exception {

		return expenseService.updateExpenseDetails(expense, id);
	}

	public int calculatefactorial(int n) {
		return n * calculatefactorial(n - 1);
	}

	@GetMapping("/expenses/category")
	public List<Expense> getExpenseByCategory(@RequestParam String category, Pageable pageable) {
		return expenseService.getExpenseByCategory(category, pageable);

	}

	@GetMapping("/expenses/name/{name}")
	public List<Expense> getByNameContaining(@PathVariable String name, Pageable pageable) {
		return expenseService.getByNameContaining(name, pageable);

	}

	@GetMapping("/expenses/dates")
	public List<Expense> getExpenseByDateBetween(
			@RequestParam(required = false) @DateTimeFormat(pattern = "MMddyyyy") Date start,
			@RequestParam(required = false) @DateTimeFormat(pattern = "MMddyyyy") Date end, Pageable page) {
		return expenseService.getByDatebetween(start, end, page);

	}

}
