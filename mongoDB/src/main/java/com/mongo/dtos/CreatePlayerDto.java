package com.mongo.dtos;

import com.mongo.enums.PlayerPosition;
import com.mongo.model.Player;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class CreatePlayerDto {
    private String name;

    private Date birthDate;

    private PlayerPosition position;

    private boolean isAvailable;

    public Player toPlayer() {
        return new Player()
                .setName(name)
                .setBirthDate(birthDate)
                .setPosition(position)
                .setAvailable(isAvailable);
    }
}
