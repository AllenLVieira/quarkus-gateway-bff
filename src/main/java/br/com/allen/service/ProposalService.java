package br.com.allen.service;

import br.com.allen.dto.ProposalDetailsDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public interface ProposalService {

    ProposalDetailsDTO getProposalDetailsById(@PathParam("id") long proposalId);

    Response createProposal(ProposalDetailsDTO proposalDetails);

    Response removeProposal(long id);
}
