package com.br.userserver.dto;

import com.br.userserver.model.TypeUser;
import com.br.userserver.model.UserModel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserDto {

    private UUID id;
    @NotEmpty @NotNull
    private String name;
    @NotEmpty @NotNull
    private String email;
    private TypeUser typeUser;
    private List<UUID> books;


    @Builder
    public UserDto(UUID id, String name, String email, List<UUID> books) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.books = books;
    }

    public UserDto buildUserToUserDto(UserModel user){
        return new UserDto().builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                //.books(user.getBooks())
                .build();
    }
}
