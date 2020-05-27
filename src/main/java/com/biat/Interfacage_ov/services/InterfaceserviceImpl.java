package com.biat.Interfacage_ov.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class InterfaceserviceImpl implements InterfacgeServiceOv {


    @Autowired
    @Qualifier("JdbcTemplatracanomalie")
    JdbcTemplate jdbcTemplatetracanomalie;
    @Autowired
    @Qualifier("JdbcTemplateoracle")
    JdbcTemplate jdbcTemplateoracle;
    private static final String query = "SELECT  MAX(time) FROM ticket_change  WHERE ticket =?  AND field='priority'";
    private static final String queryForListchange = "SELECT  * FROM ticket_change t WHERE ticket=?  AND field='priority' AND time=?";
    private static final String queryalltickettrac = "SELECT * FROM `new_ticket_bat` WHERE `status`='NOTOK'";
    private static final String querybat = "select * from OV_TICKET2";
    private static final String listticketupdate = "SELECT * FROM OV_TICKET2 WHERE STATUS='1' AND ID_ANOMALIE is NOT NULL AND RESPONSABLE IS NOT NULL ";
    private static final String listticketupdateQC = "SELECT * FROM OV_TICKET2 WHERE STATUS='2' AND ID_ANOMALIE is NOT NULL AND RESPONSABLE IS NOT NULL";
    private static final String queryMargeResolution = "SELECT * FROM `ticket_custom` WHERE `name` LIKE 'marge_resolution' and `ticket`=?";

    public Long generateTracDateNow() {
        String currentTimeMillis = String.valueOf(System.currentTimeMillis()) + "000";
        return Long.parseLong(currentTimeMillis);

    }

    public List<Map<String, Object>> getLastticketchange(String id) {
        try {
            List<Map<String, Object>> listticketchange =new ArrayList<>();
            String maxtime = jdbcTemplatetracanomalie.queryForObject(query, String.class, id);
             listticketchange = jdbcTemplatetracanomalie.queryForList(queryForListchange, id, maxtime);
            log.info(listticketchange.toString());
            return listticketchange;
        } catch (InvalidResultSetAccessException e) {
            throw new NotFoundException();

        }
    }

    @Override
    public List<Map<String, Object>> getAllTicketTrac() {
        try {
            List<Map<String, Object>> list = jdbcTemplatetracanomalie.queryForList(queryalltickettrac);
            log.info("nombre de ticket trac a traité" + String.valueOf(list.size()));
            return list;
        } catch (InvalidResultSetAccessException e) {
            throw new NotFoundException();
        }
    }

    @Override
    public List<Map<String, Object>> getAllTicketBat() {
        try {
            List<Map<String, Object>> listbat = jdbcTemplateoracle.queryForList(querybat);
            log.info("Nombre de ticket bat a traits" + String.valueOf(listbat.size()));
            return listbat;
        } catch (InvalidResultSetAccessException e) {
            throw new NotFoundException();
        }
    }

    @Override
    public List<Map<String, Object>> getAlllistupdateRetournee() {
        try {
            List<Map<String, Object>> listupdateRetournee = jdbcTemplateoracle.queryForList(listticketupdate);
            log.info("list ticket update Retournee" + listupdateRetournee.size());
            return listupdateRetournee;
        } catch (InvalidResultSetAccessException e) {
            throw new NotFoundException();
        }
    }

    @Override
    public List<Map<String, Object>> getAllListUpdateQualifieeOrCertifiee() {
        try {
            List<Map<String, Object>> listUpdateQualifierOrCertifier = jdbcTemplateoracle.queryForList(listticketupdateQC);
            log.info("list ticket update qualifier or certifier" + listUpdateQualifierOrCertifier.size());
            return listUpdateQualifierOrCertifier;
        } catch (InvalidResultSetAccessException e) {
            throw new NotFoundException();
        }
    }

    @Override
    public List<Map<String, Object>> getMargeResolution(String id) {
        try {

            List<Map<String, Object>> listMargeResolution = jdbcTemplatetracanomalie.queryForList(queryMargeResolution, id);
            log.info("listMargeResolution"+listMargeResolution);
            return listMargeResolution;
        } catch (InvalidResultSetAccessException e) {
            throw new NotFoundException();

        }
    }


}
