package com.example.pawmatch.dto;

import com.example.pawmatch.model.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ApplicationDTO {
    private Integer application_id;
    private Integer user_id;
    private Pet pet;
    private EnumApprovalStatus approvalStatus;
    private String applicationDate;
    private String reason;
    private String message;
}
