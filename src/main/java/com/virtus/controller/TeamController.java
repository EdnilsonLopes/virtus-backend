package com.virtus.controller;

import com.virtus.common.annotation.LoggedUser;
import com.virtus.domain.dto.request.TeamRequestDTO;
import com.virtus.domain.dto.response.*;
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
                                           @RequestBody TeamRequestDTO body) {
        service.updateTeam(currentUser, body);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all-members-by-current-user")
    public ResponseEntity<List<MemberResponseDTO>> getAllTeamMembersByCurrentUser(@LoggedUser CurrentUser currentUser) {
        return ResponseEntity.ok(service.findAllTeamMembersByBoss(currentUser));
    }

    @GetMapping("/all-supervisors-by-current-user")
    public ResponseEntity<List<SupervisorResponseDTO>> getAllSupervisorsByCurrentUser(@LoggedUser CurrentUser currentUser) {
        return ResponseEntity.ok(service.findAllSupervisorsByBoss(currentUser));
    }

    @PostMapping("/assign-team")
    public ResponseEntity<TeamResponseDTO> assignTeam(@LoggedUser CurrentUser currentUser, @RequestBody TeamRequestDTO body) {
        service.assignTeam(currentUser, body);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/supervisor")
    public ResponseEntity<SupervisorResponseDTO> getSupervisor(@LoggedUser CurrentUser currentUser,
                                                               @RequestParam Integer entityId,
                                                               @RequestParam Integer cycleId) {
        return ResponseEntity.ok(service.getSupervisorByEntityIdAndCycleId(entityId, cycleId));
    }

    @GetMapping("/team-members")
    public ResponseEntity<List<TeamMemberDTO>> getTeamMembers(@LoggedUser CurrentUser currentUser,
                                                              @RequestParam Integer entityId,
                                                              @RequestParam Integer cycleId) {
        return ResponseEntity.ok(service.getTeamMembersByEntityIdAndCycleId(entityId, cycleId));
    }

    @GetMapping("/validate/team-member")
    public ResponseEntity<Void> validateUserSelectedAsTeamMember(@LoggedUser CurrentUser currentUser,
                                                                 @RequestParam Integer cycleId,
                                                                 @RequestParam Integer userTeamMemberId,
                                                                 @RequestParam Integer supervisorId){
        service.validateUserSelectedAsTeamMember(cycleId, userTeamMemberId, supervisorId);
        return ResponseEntity.ok().build();
    }

}
