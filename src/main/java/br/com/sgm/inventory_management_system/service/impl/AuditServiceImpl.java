package br.com.sgm.inventory_management_system.service.impl;

import br.com.sgm.inventory_management_system.dto.audit.AuditLogResponse;
import br.com.sgm.inventory_management_system.mapper.AuditLogMapper;
import br.com.sgm.inventory_management_system.model.audit.AuditEnum;
import br.com.sgm.inventory_management_system.model.audit.AuditLog;
import br.com.sgm.inventory_management_system.repository.AuditLogRepository;
import br.com.sgm.inventory_management_system.service.AuditService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditLogRepository auditLogRepository;

    private final ObjectMapper objectMapper;

    private final AuditLogMapper auditLogMapper;

    public static final String OLD_VALUE = "oldValue";
    public static final String NEW_VALUE = "newValue";
    public static final String SUMARY_TEXT = "Alterações em %s: %s";

    public  void logEntityChanges(AuditEnum action, String entityName, Object oldObject, Object newObject, String performedBy) {
        try{
            Map<String,Object> changes = new HashMap<>();

            //Obtendo todas as propriedades da classe
            Field[] fields = oldObject.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true); // Permite acessar valores privados

                Object oldValue = field.get(oldObject);
                Object newValue = field.get(newObject);

                // Somente registra alterações
                if (oldValue != null && newValue != null && !oldValue.equals(newValue)) {
                    changes.put(field.getName(),Map.of(OLD_VALUE, oldValue, NEW_VALUE, newValue));
                }
            }

            if (!changes.isEmpty()) {
                String detailJson = objectMapper.writeValueAsString(changes);
                String sumary = String.format(SUMARY_TEXT, entityName,changes.keySet());

                AuditLog log = auditLogFactory();
                log.setTag(action.name());
                log.setEntityName(entityName);
                log.setPerformedBy(performedBy);
                log.setSumary(sumary);
                log.setDetailJson(detailJson);
                auditLogRepository.save(log);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private AuditLog auditLogFactory() {
        return new AuditLog();
    }

    public List<AuditLogResponse>getAllAuditLogs() {
        List<AuditLog> logList = auditLogRepository.findAll();

        return logList.stream()
                .map(auditLogMapper::toDto)
                .toList();
    }
}
