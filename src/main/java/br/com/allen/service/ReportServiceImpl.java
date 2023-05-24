package br.com.allen.service;

import br.com.allen.client.ReportRestClient;
import br.com.allen.dto.OpportunityDTO;
import br.com.allen.utils.CSVUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.io.ByteArrayInputStream;
import java.util.List;

@ApplicationScoped
public class ReportServiceImpl implements ReportService {

    @Inject
    @RestClient
    ReportRestClient reportRestClient;

    @Override
    public ByteArrayInputStream generateCSVOpportunityReport() {
        List<OpportunityDTO> opportunityData = reportRestClient.requestOpportunitiesData();
        return CSVUtils.opportunitiesToCSV(opportunityData);
    }

    @Override
    public List<OpportunityDTO> getOpportunitiesData() {
        return reportRestClient.requestOpportunitiesData();
    }
}
