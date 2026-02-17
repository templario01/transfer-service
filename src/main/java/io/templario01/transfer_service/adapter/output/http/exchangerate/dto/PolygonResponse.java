package io.templario01.transfer_service.adapter.output.http.exchangerate.dto;

import lombok.Data;
import java.util.List;

@Data
public class PolygonResponse {
    private String ticker;
    private String status;
    private List<Result> results;

    @Data
    public static class Result {
        private double c; // close
    }
}