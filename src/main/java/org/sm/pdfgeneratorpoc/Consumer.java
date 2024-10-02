package org.sm.pdfgeneratorpoc;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "consumer")
public class Consumer {
    @Id
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String ipAddress;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
