package com.biat.Interfacage_ov.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket implements Serializable {


    private Integer id;
    private String type;
    private Long time;
    private Long changetime;
    private String component;
    private String severity;
    private String priority;
    private String owner;
    private String reporter;
    private String cc;
    private String version;
    private String milestone;
    private String status;
    private String resolution;
    private String summary;
    private String description;
    private String keywords;
}
