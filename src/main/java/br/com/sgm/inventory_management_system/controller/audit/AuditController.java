package br.com.sgm.inventory_management_system.controller.audit;

import br.com.sgm.inventory_management_system.dto.audit.AuditLogResponse;
import br.com.sgm.inventory_management_system.service.AuditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/audit")
@AllArgsConstructor
public class AuditController {

    private final AuditService auditService;

    @GetMapping
    public List<AuditLogResponse> getAllAuditsLogs(
            @RequestHeader(name = "Authorization", required = true) String token) {
        return auditService.getAllAuditLogs();
    }
}