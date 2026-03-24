package com.cts.grantserve.specification;

import com.cts.grantserve.entity.GrantApplication;
import com.cts.grantserve.entity.Program;
import org.springframework.data.jpa.domain.Specification;

public class ProgramSpecification {
    public static Specification<Program> hasName(String title) {
        return (root, query, cb) -> {
            if (title == null || title.isEmpty()) return null;
            return cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
        };
    }
    public static Specification<Program> hasId(Long id) {
        return (root, query, cb) -> {
            if (id == null) return null;
            return cb.equal(root.get("id"), id);
        };
    }
}
