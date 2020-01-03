package com.jdc.pos.model.repo;

import com.jdc.pos.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
}
