package com.mgWork.expensetrackerapi.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mgWork.expensetrackerapi.entity.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

	List<Expense> findByUserIdAndCategory(Long userId, String category,Pageable pageable);
	List<Expense> findByUserIdAndNameContaining(Long userId,String name,Pageable pageable);
	
	List<Expense> findByuserIdAndDateBetween(Long userId,Date start,Date end,Pageable page);
	
	Page<Expense> findByUserId(Long id,Pageable pageable);
	Optional<Expense> findByUserIdAndId(long id,Long expenseId);
}
