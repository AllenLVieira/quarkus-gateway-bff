package br.com.allen.service;

import br.com.allen.client.ReportRestClient;
import br.com.allen.dto.OpportunityDTO;
import br.com.allen.utils.CSVUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.util.List;

@ApplicationScoped
public class ReportServiceImpl implements ReportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Inject
    @RestClient
    ReportRestClient reportRestClient;

    @Override
    public ByteArrayInputStream generateCSVOpportunityReport() {
        LOGGER.info("Gerando CSV de oportunidades");
        List<OpportunityDTO> opportunityData = reportRestClient.requestOpportunitiesData();
        LOGGER.info("Dados de oportunidade recebidos: {}", opportunityData);
        ByteArrayInputStream csvData = CSVUtils.opportunitiesToCSV(opportunityData);
        LOGGER.info("Relatório CSV gerado com sucesso");
        return csvData;
    }

    @Override
    public List<OpportunityDTO> getOpportunitiesData() {
        LOGGER.info("Recebida requisição para consulta de oportunidades");
        List<OpportunityDTO> opportunityData = reportRestClient.requestOpportunitiesData();
        LOGGER.info("Dados recebidos: {}", opportunityData);
        return opportunityData;
    }
}
