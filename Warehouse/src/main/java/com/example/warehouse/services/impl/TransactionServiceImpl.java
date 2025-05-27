package com.example.warehouse.services.impl;

import com.example.warehouse.domain.Transaction;
import com.example.warehouse.repositories.TransactionRepository;
import com.example.warehouse.services.TransactionService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction getTransactionById(Integer transactionId) {
        return transactionRepository.findById(transactionId).
                orElseThrow(() -> new NoSuchElementException("Transaction not found with ID: " + transactionId));
    }

    @Override
    public Page<Transaction> getAllTransactions(Date fromDate, Date toDate, Transaction.TransactionType type, Integer employeeId, Pageable pageable) {
        Specification<Transaction> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (fromDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("date"), startOfDay(fromDate)));
            }
            if (toDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("date"), fromDate));
            }
            if (type != null) {
                predicates.add(cb.equal(root.get("transactionType"), type));
            }
            if (employeeId != null) {
                predicates.add(cb.equal(root.get("employee").get("id"), employeeId));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return transactionRepository.findAll(spec, pageable);
    }

    private Date startOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
