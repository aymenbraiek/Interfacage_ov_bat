package com.biat.Interfacage_ov.controller;

import com.biat.Interfacage_ov.services.InterfaceserviceImpl;
import com.biat.Interfacage_ov.services.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@EnableScheduling
@EnableAsync
@Component
@Slf4j
public class InterfacageOvBatController {
    @Autowired
    @Qualifier("JdbcTemplatracanomalie")
    JdbcTemplate jdbcTemplatetracanomalie;

    @Autowired
    @Qualifier("JdbcTemplateoracle")
    JdbcTemplate jdbcTemplateoracle;
    @Autowired
    public InterfaceserviceImpl interfaceserviceImpl;
    private static final String queryinsert = "INSERT INTO  OV_TICKET2 VALUES (?,?,?,?,?)";
    private static final String queryupdate = "UPDATE `new_ticket_bat` SET `status`='OK' ";
    private static final String queryupdateanomalie = "UPDATE `ticket` SET `priority`='RETOURNEE'  WHERE `id`=?";
    private static final String queryupdateanomalieQualifierOrCertifier = "UPDATE `ticket` SET `priority`=?  WHERE `id`=?";
    private static final String queryflag = "update OV_TICKET2 set STATUS='0' where ID_ANOMALIE=?";
    private static final String queryticketchange = "INSERT INTO ticket_change VALUES (?,?,?,?,?,?)";


    @Scheduled(fixedRate = 2000)
    public void insertdatatoBat() {
        List<Map<String, Object>> list = interfaceserviceImpl.getAllTicketTrac();
        List<Map<String, Object>> listbat = interfaceserviceImpl.getAllTicketBat();
        if (list.size() > 0) {
            for (Map<String, Object> map : list) {
                try {
                    jdbcTemplateoracle.update(queryinsert, map.get("id"), "", "0", map.get("instance_trac"), "");
                    jdbcTemplatetracanomalie.update(queryupdate);
                } catch (InvalidResultSetAccessException e) {
                    throw new NotFoundException();
                } catch (DataAccessException e) {
                    throw new RuntimeException(e);


                }

            }
        }
    }

    @Scheduled(fixedRate = 10000)
    public void updatepriorityRetournee() {
        List<Map<String, Object>> listupdate = interfaceserviceImpl.getAlllistupdateRetournee();
        if (listupdate.size() > 0) {
            for (Map<String, Object> mapupdate : listupdate) {
                try {
                    List<Map<String, Object>> listticketchange = interfaceserviceImpl.getLastticketchange((String) mapupdate.get("ID_ANOMALIE"));
                    Long time = interfaceserviceImpl.generateTracDateNow();


                    if (listticketchange.size() > 0) {
                        log.info("liste >0");
                        if (!("RETOURNEE".equals(listticketchange.get(0).get("newvalue")))) {
                            log.info("old value is retournee");
                            jdbcTemplatetracanomalie.update(queryupdateanomalie, mapupdate.get("ID_ANOMALIE"));
                            jdbcTemplatetracanomalie.update(queryticketchange, mapupdate.get("ID_ANOMALIE"), time, mapupdate.get("RESPONSABLE"), "comment", 1, "");
                            jdbcTemplatetracanomalie.update(queryticketchange, mapupdate.get("ID_ANOMALIE"), time, mapupdate.get("RESPONSABLE"), "priority", listticketchange.get(0).get("newvalue"), "RETOURNEE");
                            jdbcTemplateoracle.update(queryflag, mapupdate.get("ID_ANOMALIE"));
                        }
                    } else {
                        log.info("liste change <0");
                        jdbcTemplatetracanomalie.update(queryupdateanomalie, mapupdate.get("ID_ANOMALIE"));
                        jdbcTemplatetracanomalie.update(queryticketchange, mapupdate.get("ID_ANOMALIE"), time, mapupdate.get("RESPONSABLE"), "comment", 1, "");
                        jdbcTemplatetracanomalie.update(queryticketchange, mapupdate.get("ID_ANOMALIE"), time, mapupdate.get("RESPONSABLE"), "priority", "A DEFINIR", "RETOURNEE");
                        jdbcTemplateoracle.update(queryflag, mapupdate.get("ID_ANOMALIE"));
                    }

                } catch (InvalidResultSetAccessException e) {
                    throw new NotFoundException();
                }

            }
        }
    }

    @Scheduled(fixedRate = 2000)
    public void updatepriorityQualifieeorcertifiee() {
        List<Map<String, Object>> listupdateQualifierOrCertifier = interfaceserviceImpl.getAllListUpdateQualifieeOrCertifiee();
        if (listupdateQualifierOrCertifier.size() > 0) {
            for (Map<String, Object> mapupdateQualifierOrCertifier : listupdateQualifierOrCertifier) {
                try {
                    List<Map<String, Object>> listtickeChange = interfaceserviceImpl.getLastticketchange((String) mapupdateQualifierOrCertifier.get("ID_ANOMALIE"));
                    Long time = interfaceserviceImpl.generateTracDateNow();
                    List<Map<String, Object>> listmargeResolution = interfaceserviceImpl.getMargeResolution((String) mapupdateQualifierOrCertifier.get("ID_ANOMALIE"));
                    String margeResolution = (String) listmargeResolution.get(0).get("value");
                    log.info("margeResolution"+margeResolution);
                    switch (margeResolution) {
                        case "RELEASE":
                            if (listtickeChange.size() > 0) {
                                log.info("liste de release >");
                                if (!("CERTIFIEE".equals(listtickeChange.get(0).get("newvalue")))) {
                                    log.info("ticket release");
                                    jdbcTemplatetracanomalie.update(queryupdateanomalie, mapupdateQualifierOrCertifier.get("ID_ANOMALIE"));
                                    jdbcTemplatetracanomalie.update(queryticketchange, mapupdateQualifierOrCertifier.get("ID_ANOMALIE"), time, mapupdateQualifierOrCertifier.get("RESPONSABLE"), "comment", 1, "");
                                    jdbcTemplatetracanomalie.update(queryticketchange, mapupdateQualifierOrCertifier.get("ID_ANOMALIE"), time, mapupdateQualifierOrCertifier.get("RESPONSABLE"), "priority", listtickeChange.get(0).get("newvalue"), "CERTIFIEE");
                                    jdbcTemplateoracle.update(queryflag, mapupdateQualifierOrCertifier.get("ID_ANOMALIE"));
                                }
                            } else {
                                log.info("ticket release else ");
                                jdbcTemplatetracanomalie.update(queryupdateanomalie, mapupdateQualifierOrCertifier.get("ID_ANOMALIE"));
                                jdbcTemplatetracanomalie.update(queryticketchange, mapupdateQualifierOrCertifier.get("ID_ANOMALIE"), time, mapupdateQualifierOrCertifier.get("RESPONSABLE"), "comment", 1, "");
                                jdbcTemplatetracanomalie.update(queryticketchange, mapupdateQualifierOrCertifier.get("ID_ANOMALIE"), time, mapupdateQualifierOrCertifier.get("RESPONSABLE"), "priority", "A DEFINIR", "CERTIFIEE");
                                jdbcTemplateoracle.update(queryflag, mapupdateQualifierOrCertifier.get("ID_ANOMALIE"));
                            }

                            break;
                        case "PROJET":
                            if (listtickeChange.size() > 0) {
                                if (!("QUALIFIEE".equals(listtickeChange.get(0).get("newvalue")))) {
                                    log.info("ticket projet ");
                                    jdbcTemplatetracanomalie.update(queryupdateanomalie, mapupdateQualifierOrCertifier.get("ID_ANOMALIE"));
                                    jdbcTemplatetracanomalie.update(queryticketchange, mapupdateQualifierOrCertifier.get("ID_ANOMALIE"), time, mapupdateQualifierOrCertifier.get("RESPONSABLE"), "comment", 1, "");
                                    jdbcTemplatetracanomalie.update(queryticketchange, mapupdateQualifierOrCertifier.get("ID_ANOMALIE"), time, mapupdateQualifierOrCertifier.get("RESPONSABLE"), "priority", listtickeChange.get(0).get("newvalue"), "QUALIFIEE");
                                    jdbcTemplateoracle.update(queryflag, mapupdateQualifierOrCertifier.get("ID_ANOMALIE"));
                                }
                            } else {
                                log.info("ticket projet else ");
                                jdbcTemplatetracanomalie.update(queryupdateanomalie, mapupdateQualifierOrCertifier.get("ID_ANOMALIE"));
                                jdbcTemplatetracanomalie.update(queryticketchange, mapupdateQualifierOrCertifier.get("ID_ANOMALIE"), time, mapupdateQualifierOrCertifier.get("RESPONSABLE"), "comment", 1, "");
                                jdbcTemplatetracanomalie.update(queryticketchange, mapupdateQualifierOrCertifier.get("ID_ANOMALIE"), time, mapupdateQualifierOrCertifier.get("RESPONSABLE"), "priority", "A DEFINIR", "QUALIFIEE");
                                jdbcTemplateoracle.update(queryflag, mapupdateQualifierOrCertifier.get("ID_ANOMALIE"));
                            }

                        default:
                            log.info("une autre instance trac");
                            break;
                    }
                } catch (InvalidResultSetAccessException e) {
                    throw new NotFoundException();
                }

            }
        }


    }
}
