package br.com.allen.utils;

import br.com.allen.dto.OpportunityDTO;
import br.com.allen.enums.HeaderEnum;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class CSVUtils {

    public static ByteArrayInputStream opportunitiesToCSV(List<OpportunityDTO> opportunities) {
        final CSVFormat format = CSVFormat.Builder.create().setHeader(HeaderEnum.class).build();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(output), format)) {
            for (OpportunityDTO opps : opportunities) {
                List<String> data = createCSVData(opps);
                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(output.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Falha ao importar dados para CSV: " + e.getMessage(), e);
        }
    }

    private static List<String> createCSVData(OpportunityDTO opps) {
        return Arrays.asList(String.valueOf(opps.getProposalId()),
                String.valueOf(opps.getCustomer()),
                String.valueOf(opps.getPriceTonne()),
                String.valueOf(opps.getLastDollarQuotation()));
    }
}
