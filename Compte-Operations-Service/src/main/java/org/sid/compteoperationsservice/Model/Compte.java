package org.sid.compteoperationsservice.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Compte {
    @Id
    @GeneratedValue
    private Long id;
    private Double solde;
    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private LocalDateTime date_creation;
    private String type;
    private String etat;
    @OneToMany(mappedBy = "compte", fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Operation> operations = new ArrayList<>();
    private Long ClientId;
    @Transient
    private Client client;
}
