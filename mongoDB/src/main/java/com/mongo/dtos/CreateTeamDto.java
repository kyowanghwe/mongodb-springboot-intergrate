package com.mongo.dtos;

import com.mongo.model.Address;
import com.mongo.model.Team;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

@Setter
@Getter
public class CreateTeamDto {
    private String name;

    private String acronym;

    private Address address;

    public Team toTeam() {
        return new Team().setName(name).setAcronym(acronym).setAddress(address);
    }
}
