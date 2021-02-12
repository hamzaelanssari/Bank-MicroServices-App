package org.sid.compteoperationsservice.entities;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.sid.compteoperationsservice.model.Client;
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
    private LocalDateTime dateCreation;
    private String type;
    private String etat;
    @OneToMany(mappedBy = "compte", fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Operation> operations = new ArrayList<>();
    private Long idClient;
    @Transient
    private Client client;
}

class CompteTypes{
    public static String COURANT = "COURANT";
    public static String EPARGNE = "EPARGNE";
}

