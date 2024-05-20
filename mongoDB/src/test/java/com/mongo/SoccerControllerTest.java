package com.mongo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongo.controller.SoccerController;
import com.mongo.dtos.CreatePlayerDto;
import com.mongo.dtos.CreateTeamDto;
import com.mongo.enums.PlayerPosition;
import com.mongo.model.Address;
import com.mongo.model.Player;
import com.mongo.repository.PlayerRepository;
import com.mongo.repository.TeamRepository;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class SoccerControllerTest {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    @InjectMocks
    private SoccerController soccerController;

//    @Autowired
//    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(soccerController).build();

        objectMapper = new ObjectMapper();
    }

    @Test
    void createPlayerTest() throws Exception {
        CreatePlayerDto createPlayerDto1 = new CreatePlayerDto();
        createPlayerDto1.setName("John Doe");
        createPlayerDto1.setBirthDate(new Date());
        createPlayerDto1.setPosition(PlayerPosition.LEFT_WINGER);
        createPlayerDto1.setAvailable(true);

        CreatePlayerDto createPlayerDto2 = new CreatePlayerDto();
        createPlayerDto2.setName("Ronaldo");
        createPlayerDto2.setBirthDate(new Date());
        createPlayerDto2.setPosition(PlayerPosition.LEFT_WINGER);
        createPlayerDto2.setAvailable(true);

        List<CreatePlayerDto> createPlayerDtoList = List.of(createPlayerDto1, createPlayerDto2);

        List<Player> players = createPlayerDtoList
                .stream()
                .map(CreatePlayerDto::toPlayer)
                .collect(Collectors.toList());

        List<Player> playersCreated = playerRepository.saveAll(players);

//        when(playerRepository.save(any(Player.class))).thenReturn(player);

//        mockMvc.perform(post("/players")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(createPlayerDto)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.name").value(player.getName()))
//                .andExpect(jsonPath("$.birthDate").value(player.getBirthDate().getTime()))
//                .andExpect(jsonPath("$.position").value(player.getPosition().toString()))
//                .andExpect(jsonPath("$.available").value(player.isAvailable()));
    }

    @Test
    void createTeamTest() {
        CreateTeamDto createTeamDto = new CreateTeamDto();
        createTeamDto.setName("Manchester United");
        createTeamDto.setAcronym("MU");
        createTeamDto.setAddress(Address.builder()
                .city("England")
                .postalCode("11")
                .street("street")
                .build());

        val team = teamRepository.save(createTeamDto.toTeam());
        val players = new HashSet<>(playerRepository.findAll());
        team.setPlayers(players);
        teamRepository.save(team);
    }
}
