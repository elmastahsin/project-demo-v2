package com.company.projectdemo.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "logs")
@Where(clause = "is_deleted=false")
@TypeDef(name = "json", typeClass = JsonBinaryType.class)
public class LogHistory {

    @Id
    @GeneratedValue
    private Long id;

    private String tableName;
    //JsonBinaryType
    @Type(type = "json")
    @Column(columnDefinition = "jsonb") // ensure this matches your DB column type
    private Map<String, Object> changedColumn = new HashMap<>();
    private String operation;
    private String changedBy;
    private LocalDateTime changedAt;


}
