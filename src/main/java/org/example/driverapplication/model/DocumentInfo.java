package org.example.driverapplication.model;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentInfo {
    private String documentUrl;
    private Long driverId;
}
