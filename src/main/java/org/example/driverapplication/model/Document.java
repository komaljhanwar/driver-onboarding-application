package org.example.driverapplication.model;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Document {
    private String documentUrl;
    private Long driverId;
}
