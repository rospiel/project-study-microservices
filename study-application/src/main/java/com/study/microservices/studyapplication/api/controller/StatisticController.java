package com.study.microservices.studyapplication.api.controller;

import com.study.microservices.studyapplication.api.controller.model.request.restaurant.RestaurantIncludeByDayAndStateFilter;
import com.study.microservices.studyapplication.domain.service.RestaurantQueryService;
import com.study.microservices.studyapplication.domain.service.report.RestaurantIncludeByDayAndStateReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_PDF;
import static org.springframework.http.MediaType.APPLICATION_PDF_VALUE;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticController {

    private final RestaurantIncludeByDayAndStateReportService restaurantIncludeByDayAndStateReportService;

    @GetMapping(value = "/include-daily-state-restaurant", produces = APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> searchRestaurantIncludeByDayAndState(RestaurantIncludeByDayAndStateFilter filter,
                                                                       @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {

        byte[] pdf = restaurantIncludeByDayAndStateReportService.buildReportPdf(filter, timeOffset);
        return ResponseEntity.ok()
                .contentType(APPLICATION_PDF)
                .headers(buildHeadersPdf())
                .body(pdf);
    }

    private HttpHeaders buildHeadersPdf() {
        HttpHeaders headers = new HttpHeaders();
        /* Can ensure that the file isn't open in the browser but downloaded from it */
        headers.add(CONTENT_DISPOSITION, "attachment; filename=include-daily-state-restaurant.pdf");
        return headers;
    }
}
