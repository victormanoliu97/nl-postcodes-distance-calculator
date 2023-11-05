package com.projects.nlpostcodesdistancecalculator.service.populator;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.projects.nlpostcodesdistancecalculator.persistance.entity.Postcode;
import com.projects.nlpostcodesdistancecalculator.persistance.repository.PostcodeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CSVToDatabasePostcodesImporterImpl implements PostcodesImporter {
    private final Logger logger = LoggerFactory.getLogger(CSVToDatabasePostcodesImporterImpl.class);

    private final PostcodeRepository postcodeRepository;

    @Value("${postcodes.import.run}")
    private boolean shouldRunImporting;

    @PostConstruct
    private void startImport() {
        if(shouldRunImporting) {
            this.importPostCodes();
        }
        else {
            logger.info("Not importing postcodes due to postcodes.import.run set to false");
        }
    }

    @Override
    public void importPostCodes() {
        try (FileReader postCodesFileReader = new FileReader("src/main/resources/postcodes-coordinates-NL.csv");
             CSVReader csvReader = new CSVReaderBuilder(postCodesFileReader).withSkipLines(1).build()) {

            ColumnPositionMappingStrategy<Postcode> beanStrategy = new ColumnPositionMappingStrategy<>();
            beanStrategy.setType(Postcode.class);
            beanStrategy.setColumnMapping("postCodeID","postCode","latitude","longitude");

            CsvToBean<Postcode> csvToBean = new CsvToBean<>();
            csvToBean.setMappingStrategy(beanStrategy);
            csvToBean.setCsvReader(csvReader);

            List<Postcode> postCodes = csvToBean.parse();
            postcodeRepository.saveAll(postCodes);
        }
        catch (Exception e) {
            logger.warn("Could not populate the postcodes database due to: {}", e.getMessage());
        }
    }
}

