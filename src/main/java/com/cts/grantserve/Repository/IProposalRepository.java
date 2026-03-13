package com.cts.grantserve.Repository;

import com.cts.grantserve.entity.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProposalRepository extends JpaRepository<Proposal, Long> {
}
