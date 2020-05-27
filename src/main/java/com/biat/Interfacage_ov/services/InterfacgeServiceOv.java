package com.biat.Interfacage_ov.services;

import java.util.List;
import java.util.Map;

public interface InterfacgeServiceOv {
    Long generateTracDateNow();
    List<Map<String, Object>> getLastticketchange(String id);
    List<Map<String, Object>> getAllTicketTrac();
    List<Map<String, Object>> getAllTicketBat();
    List<Map<String, Object>> getAlllistupdateRetournee();
    List<Map<String, Object>> getAllListUpdateQualifieeOrCertifiee();
    List<Map<String, Object>> getMargeResolution(String id);







}
