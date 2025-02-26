package br.com.sgm.inventory_management_system.repository;

import br.com.sgm.inventory_management_system.model.audit.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}