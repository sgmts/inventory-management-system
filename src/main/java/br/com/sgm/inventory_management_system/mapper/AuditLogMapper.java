package br.com.sgm.inventory_management_system.mapper;

import br.com.sgm.inventory_management_system.dto.audit.AuditLogResponse;
import br.com.sgm.inventory_management_system.model.audit.AuditLog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditLogMapper {

    AuditLog toEntity(AuditLogResponse auditLogResponse);

    AuditLogResponse toDto(AuditLog auditLog);

}