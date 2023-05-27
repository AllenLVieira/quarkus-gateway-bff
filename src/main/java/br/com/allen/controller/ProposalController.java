package br.com.allen.controller;

import br.com.allen.dto.ProposalDetailsDTO;
import br.com.allen.service.ProposalService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
@Path("/api/trade")
public class ProposalController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProposalController.class);

    @Inject
    ProposalService proposalService;

    @GET
    @Path("/{id}")
    @RolesAllowed({"user", "manager"})
    public Response getProposalDetailsById(@PathParam("id") long id) {
        try {
            LOGGER.info("Requisição recebida para proposta com ID: {}", id);
            ProposalDetailsDTO proposalDetailsDTO = proposalService.getProposalDetailsById(id);
            LOGGER.info("Requisição com ID {}, devolvida com sucesso: {}", id, proposalDetailsDTO);
            return Response.ok(proposalDetailsDTO,
                    MediaType.APPLICATION_JSON).build();
        } catch (ServerErrorException e) {
            LOGGER.error("Requisição com ID {} falhou em ser executada. {}", id, e.getLocalizedMessage());
            return Response.serverError().build();
        }
    }

    @POST
    @RolesAllowed("proposal-customer")
    public Response createNewProposal(ProposalDetailsDTO proposalDetails) {
        try {
            LOGGER.info("Requisição recebida para criação proposta: {}", proposalDetails);
            int proposalRequestStatus = proposalService.createProposal(proposalDetails).getStatus();

            if (proposalRequestStatus > 199 || proposalRequestStatus < 205) {
                LOGGER.info("Proposta criada com sucesso: {}", proposalDetails);
                return Response.ok().build();
            } else {
                LOGGER.error("Falha ao criar proposta: {}", proposalDetails);
                return Response.status(proposalRequestStatus).build();
            }
        } catch (Exception e) {
            LOGGER.error("Falha ao criar proposta {}. {}", proposalDetails, e.getLocalizedMessage());
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("/remove/{id}")
    @RolesAllowed("manager")
    public Response removeProposal(@PathParam("id") long id) {
        try {
            LOGGER.info("Requisição para deleção de proposta com ID: {}", id);
            int proposalRequestStatus = proposalService.removeProposal(id).getStatus();

            if (proposalRequestStatus > 199 || proposalRequestStatus < 205) {
                LOGGER.info("Proposta deletada com sucesso: {}", id);
                return Response.ok().build();
            } else {
                LOGGER.error("Erro ao deletar proposta com ID: {}", id);
                return Response.status(proposalRequestStatus).build();
            }
        } catch (Exception e) {
            LOGGER.error("Erro ao deletar proposta com ID: {}. {}", id, e.getLocalizedMessage());
            return Response.serverError().build();
        }
    }
}
