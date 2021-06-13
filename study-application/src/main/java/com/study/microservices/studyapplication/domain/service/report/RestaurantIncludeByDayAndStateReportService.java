package com.study.microservices.studyapplication.domain.service.report;

import com.study.microservices.studyapplication.api.controller.model.request.restaurant.RestaurantIncludeByDayAndStateFilter;
import com.study.microservices.studyapplication.domain.service.RestaurantQueryService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RestaurantIncludeByDayAndStateReportService {

    private final RestaurantQueryService repository;

    public byte[] buildReportPdf(RestaurantIncludeByDayAndStateFilter filter, String timeOffset) {
        try {
            /* Searching for the .jasper */
            InputStream fileReport = this.getClass().getResourceAsStream("/reports/RestaurantIncludeByDayAndState.jasper");

            Map<String, Object> parameters = new HashMap<>();
            /* Indicating our localization */
            parameters.put("REPORT_LOCALE", new Locale("pt", "BR"));

            /* The object that receive the information from database */
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(repository
                    .searchRestaurantIncludeByDayAndState(filter, timeOffset));

            /* Executing the report */
            JasperPrint jasperPrint = JasperFillManager.fillReport(fileReport, parameters, dataSource);

            /* Exporting to pdf */
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new ReportException("Error during generating report of RestaurantIncludeByDayAndState", e);
        }
    }

}
