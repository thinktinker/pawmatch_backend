package com.example.pawmatch.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(
        uniqueConstraints = {                                                   // user cannot apply for the same pet again
                @UniqueConstraint(name = "unique_user_pet_application", columnNames = {"user_id", "pet_id"})
        }
)
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;                                                         // Integer Id (Auto-increment)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties("applications")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    @JsonIgnoreProperties("applications")
    private Pet pet;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime applicationDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EnumApprovalStatus approvalStatus = EnumApprovalStatus.PENDING;

    @Column
    @Lob
    private String reason;

}
