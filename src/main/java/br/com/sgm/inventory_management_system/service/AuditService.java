package br.com.sgm.inventory_management_system.service;

import br.com.sgm.inventory_management_system.dto.audit.AuditLogResponse;
import br.com.sgm.inventory_management_system.model.audit.AuditEnum;

import java.util.List;

public interface AuditService {

    void logEntityChanges(AuditEnum action, String entityName, Object oldObject, Object newObject, String performedBy);

    List<AuditLogResponse> getAllAuditLogs();
}