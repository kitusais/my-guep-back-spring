package com.boukyApps.myguep.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "entretien")
public class Entretien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

//    @Column
//    String interlocuteurNom;


    // 1 Favoris / Accueil
    @Column
    private LocalDateTime date;
    @Column
    private Integer avisNote;
    @Column
    private String commentaireFavori;

    // 2 Esn
    @Column
    private String nomEsn;
    @Column
    private String interlocuteurNom;
    @Column
    private String interlocuteurPrenom;
    @Column
    private String moyenCommunication;
    @Column
    private String taille;
    @Column
    private Integer salairePropose;
    @Column
    private String avantageFinancier;
    @Column
    private String avantageSorties;
    @Column
    private String avantageDivers;
    @Column
    private String commentaireEsn;

    // 3 Client
    @Column
    private String nomClient;
    @Column
    private String secteurActivite;
    // [secteurActivite: String]: any;
    @Column
    private String commentaireClient;

    //4 Mission
    @Column
    private String lieu;
    @Column
    private Integer distance;
    @Column
    private Integer tempsEnVoiture;
    @Column
    private Integer tempsEnCommun;
    @Column
    private String commentaireMission;

    //5 Résumé
    @Column
    private String resume;
    @Column
    private String commentaireResume;

//    // XX Commun
//    favoris:any[];
}
