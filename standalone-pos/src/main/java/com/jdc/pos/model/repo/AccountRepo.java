package com.jdc.pos.model.repo;

import com.jdc.pos.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account, String> {
}
