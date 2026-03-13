package com.cts.grantserve.dao;

import com.cts.grantserve.entity.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProposalDao extends JpaRepository<Proposal, Long> {
}
