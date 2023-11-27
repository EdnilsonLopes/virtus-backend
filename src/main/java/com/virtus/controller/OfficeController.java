package com.virtus.controller;

import com.virtus.common.BaseController;
import com.virtus.common.annotation.LoggedUser;
import com.virtus.domain.dto.request.OfficeRequestDTO;
import com.virtus.domain.dto.response.JurisdictionResponseDTO;
import com.virtus.domain.dto.response.MemberResponseDTO;
import com.virtus.domain.dto.response.OfficeResponseDTO;
import com.virtus.domain.dto.response.PageableResponseDTO;
import com.virtus.domain.entity.Office;
import com.virtus.domain.model.CurrentUser;
import com.virtus.service.OfficeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/offices")
public class OfficeController extends BaseController<Office, OfficeService, OfficeRequestDTO, OfficeResponseDTO> {

    public OfficeController(OfficeService service) {
        super(service);
    }

    @GetMapping("/jurisdictions")
    public ResponseEntity<PageableResponseDTO<JurisdictionResponseDTO>> getJurisdictionByOffice(
            @LoggedUser CurrentUser currentUser,
            @RequestParam(required = false, defaultValue = "") String filter,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam Integer officeId) {
        return ResponseEntity.ok(getService().findJurisdictionByOfficeId(currentUser, filter, page, size, officeId));
    }

    @GetMapping("/members")
    public ResponseEntity<PageableResponseDTO<MemberResponseDTO>> getMemberByOffice(
            @LoggedUser CurrentUser currentUser,
            @RequestParam(required = false, defaultValue = "") String filter,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam Integer officeId) {
        return ResponseEntity.ok(getService().findMemberByOfficeId(currentUser, filter, page, size, officeId));
    }

    @PutMapping("/jurisdictions")
    public ResponseEntity<OfficeResponseDTO> updateJurisdictions(@LoggedUser CurrentUser currentUser, @RequestBody OfficeRequestDTO body){
        return ResponseEntity.ok(getService().updateJurisdictions(currentUser, body));
    }

    @PutMapping("/members")
    public ResponseEntity<OfficeResponseDTO> updateMembers(@LoggedUser CurrentUser currentUser, @RequestBody OfficeRequestDTO body){
        return ResponseEntity.ok(getService().updateMembers(currentUser, body));
    }

}
