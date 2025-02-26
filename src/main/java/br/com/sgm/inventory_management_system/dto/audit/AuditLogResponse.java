package br.com.sgm.inventory_management_system.dto.audit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogResponse {

    private String tag;
    private String entityName;
    private String performedBy;
    private String sumary;
    private String detailJson;

    private LocalDateTime lastModifiedAt;
}