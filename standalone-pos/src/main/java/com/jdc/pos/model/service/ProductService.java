package com.jdc.pos.model.service;

import com.jdc.pos.model.PosException;
import com.jdc.pos.model.entity.Category;
import com.jdc.pos.model.entity.Product;
import com.jdc.pos.model.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public List<Product> search(Category category, String name) {

        StringBuffer sb = new StringBuffer("select p from Product p where 1 = 1");
        Map<String, Object> params = new HashMap<>();

        if(null != category) {
            sb.append(" and p.category = :category");
            params.put("category", category);
        }

        if(!StringUtils.isEmpty(name)) {
            sb.append(" and lower(p.name) like lower(:name)");
            params.put("name", name.concat("%"));
        }

        return productRepo.findByQuery(sb.toString(), params);
    }

    public void save(Product product) {

        if(null == product.getCategory()) {
            throw new PosException("Please select category.");
        }

        if(StringUtils.isEmpty(product.getName())) {
            throw new PosException("Please enter Product Name.");
        }

        if(product.getPrice() == 0) {
            throw new PosException("Please enter Product Price.");
        }

        productRepo.save(product);
    }
}
