package br.com.allen.controller;

import br.com.allen.dto.OpportunityDTO;
import br.com.allen.service.ReportService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.ServerErrorException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;

@ApplicationScoped
@Path("/api/opportunity")
public class ReportController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class);

    @Inject
    ReportService reportService;

    @GET
    @Path("/report")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @RolesAllowed({"user", "manager"})
    public Response generateReport() {
        try {
            LOGGER.info("Requisição para geração de relatório recebida.");
            ByteArrayInputStream csvReport = reportService.generateCSVOpportunityReport();
            LOGGER.info("Relatório gerado com sucesso.");
            return Response.ok(csvReport,
                            MediaType.APPLICATION_OCTET_STREAM)
                    .header("content-disposition",
                            "attachment; filename = " + new Date() + "--oportunity.csv")
                    .build();
        } catch (ServerErrorException e) {
            LOGGER.error("Erro na geração do relatório");
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/data")
    @RolesAllowed({"user", "manager"})
    public Response generateOpportunitiesData() {
        try {
            LOGGER.info("Requisição para geração de dados de oportunidade recebida.");
            List<OpportunityDTO> opportunitiesData = reportService.getOpportunitiesData();
            LOGGER.info("Obtenção das oportunidades realizada com sucesso.");
            return Response.ok(opportunitiesData,
                            MediaType.APPLICATION_JSON)
                    .build();
        } catch (ServerErrorException e) {
            LOGGER.error("Erro na obtenção das oportunidades.");
            return Response.serverError().build();
        }
    }
}
