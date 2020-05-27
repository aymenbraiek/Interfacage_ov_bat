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
public class TicketChange implements Serializable {


    private Integer ticket;
    private Ticket ticketPointer;
    private Long time;
    private String author;
    private String field;
    private String oldvalue;
    private String newvalue;
}
