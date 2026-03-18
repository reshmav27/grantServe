package com.cts.grantserve.specification;

import com.cts.grantserve.entity.GrantApplication;
import org.springframework.data.jpa.domain.Specification;

public class GrantApplicationSpecification {
    public static Specification<GrantApplication> hasName(String title) {
        return (root, query, cb) -> {
            if (title == null || title.isEmpty()) return null;
            return cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
        };
    }


    public static Specification<GrantApplication> hasId(Long id) {
        return (root, query, cb) -> {
            if (id == null) return null;
            return cb.equal(root.get("id"), id);
        };
    }
}
