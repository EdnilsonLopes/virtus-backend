package com.virtus.controller;

import com.virtus.common.annotation.LoggedUser;
import com.virtus.domain.dto.request.TeamRequestDTO;
import com.virtus.domain.dto.response.PageableResponseDTO;
import com.virtus.domain.dto.response.SupervisorResponseDTO;
import com.virtus.domain.dto.response.TeamMemberResponseDTO;
import com.virtus.domain.dto.response.TeamResponseDTO;
import com.virtus.domain.model.CurrentUser;
import com.virtus.service.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService service;

    public TeamController(TeamService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<PageableResponseDTO<TeamResponseDTO>> getAllByCurrentUser(
            @LoggedUser CurrentUser currentUser,
            @RequestParam(required = false, defaultValue = "") String filter,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(service.findTeamsByCurrentUser(currentUser, page, size));
    }

    public ResponseEntity<Void> updateTeam(@LoggedUser CurrentUser currentUser,
                                                      @RequestBody TeamRequestDTO body){
        service.updateTeam(currentUser, body);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all-members-by-current-user")
    public ResponseEntity<List<TeamMemberResponseDTO>> getAllTeamMembersByCurrentUser(@LoggedUser CurrentUser currentUser){
        return ResponseEntity.ok(service.findAllTeamMembersByBoss(currentUser));
    }

    @GetMapping("/all-supervisors-by-current-user")
    public ResponseEntity<List<SupervisorResponseDTO>> getAllSupervisorsByCurrentUser(@LoggedUser CurrentUser currentUser){
        return ResponseEntity.ok(service.findAllSupervisorsByBoss(currentUser));
    }

}
