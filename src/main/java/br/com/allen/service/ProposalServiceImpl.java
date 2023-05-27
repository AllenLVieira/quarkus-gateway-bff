package br.com.allen.service;

import br.com.allen.client.ProposalRestClient;
import br.com.allen.dto.ProposalDetailsDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class ProposalServiceImpl implements ProposalService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProposalServiceImpl.class);

    @Inject
    @RestClient
    ProposalRestClient proposalRestClient;

    @Override
    public ProposalDetailsDTO getProposalDetailsById(long proposalId) {
        LOGGER.info("Recebendo detalhes da proposta de ID: {}", proposalId);
        ProposalDetailsDTO proposalDetailsById = proposalRestClient.getProposalDetailsById(proposalId);
        LOGGER.info("Dados de proposta recebidos: {}", proposalDetailsById);
        return proposalDetailsById;
    }

    @Override
    public Response createProposal(ProposalDetailsDTO proposalDetails) {
        LOGGER.info("Criando proposta: {}", proposalDetails);
        Response response = proposalRestClient.createProposal(proposalDetails);
        LOGGER.info("Resposta da criação de proposta: {}", response);
        return response;
    }

    @Override
    public Response removeProposal(long proposalId) {
        LOGGER.info("Deletando proposta de ID: {}", proposalId);
        Response response = proposalRestClient.removeProposal(proposalId);
        LOGGER.info("Proposta de ID: {} removida. {}", proposalId, response);
        return response;
    }
}
