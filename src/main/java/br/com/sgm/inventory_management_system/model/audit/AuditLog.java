package br.com.sgm.inventory_management_system.model.audit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "audit_log")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String tag;

    @Column
    private String entityName;

    @Column
    private String performedBy;

    @Column
    private String sumary;

    @Column
    private String detailJson;

    @Column
    private LocalDateTime lastModifiedAt;

    @PrePersist
    private void prePersist() {
        lastModifiedAt = LocalDateTime.now();
    }
}