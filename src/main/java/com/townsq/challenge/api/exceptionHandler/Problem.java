package com.townsq.challenge.api.exceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(Include.NON_NULL)
@Builder
@Getter
@Schema(name = "Problema")
public class Problem {

    private Integer status;
    private String title;
    private String message;
    private String userMessage;
    private OffsetDateTime timestamp;

    private List<Object> objects;

    @Builder
    @Getter
    @Schema(name = "ProblemObject")
    public static class Object {
        private String userMessage;
    }

}
