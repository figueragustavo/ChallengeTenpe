package com.challenge.tenpe.repository;

import com.challenge.tenpe.entity.TransactionEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TenpeRepository extends PagingAndSortingRepository<TransactionEntity, Integer> {
    void save(TransactionEntity entity);
}
