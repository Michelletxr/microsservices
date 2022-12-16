package com.br.userserver.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name="table_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//id gerado de forma automatica
    @Column(unique = true, updatable = false)
    private UUID id;

    @Column(unique = false, updatable = false)
    private String name;

    @Column(unique = false, updatable = true)
    private String email;

    private TypeUser typeUser;

    //private List<UUID> books;
}
