package com.biat.Interfacage_ov.model;

import lombok.*;

import javax.persistence.Column;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OV_TICKET2 {
    @Column(name = "ID_ANOMALIE")
    private String idanomalie;
    @Column(name = "ID_LIVRAISON")
    private String idlivraison;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "INSTANCE_TRAC")
    private String instancetrac;
    @Column(name = "PRIORITY")
    private String priority;
}
