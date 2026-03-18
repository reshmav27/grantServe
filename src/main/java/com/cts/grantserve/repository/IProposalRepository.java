package com.cts.grantserve.repository;

import com.cts.grantserve.entity.Proposal;
import com.cts.grantserve.projection.IProposalProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface IProposalRepository extends JpaRepository<Proposal, Long> {

    @Query("SELECT p.proposalID as proposalID, p.fileURI as fileURI, " +
            "p.submittedDate as submittedDate, p.status as status, " +
            "p.grantApplication.applicationID as applicationID " +
            "FROM Proposal p WHERE p.proposalID = :id")
    List<IProposalProjection> findProjectedById(@Param("id") Long id);

   }