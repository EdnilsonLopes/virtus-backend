package com.virtus.common;

import com.virtus.common.annotation.LoggedUser;
import com.virtus.common.domain.dto.BaseRequestDTO;
import com.virtus.common.domain.dto.BaseResponseDTO;
import com.virtus.common.domain.entity.BaseEntity;
import com.virtus.domain.dto.response.PageableResponseDTO;
import com.virtus.domain.model.CurrentUser;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
public class BaseController<T extends BaseEntity,
        S extends BaseService<T, ? extends BaseRepository<T>, C, DTO>,
        C extends BaseRequestDTO,
        DTO extends BaseResponseDTO> {

    private final S service;

    public BaseController(S service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<PageableResponseDTO<DTO>> getAll(
            @LoggedUser CurrentUser currentUser,
            @RequestParam(required = false, defaultValue = "") String filter,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        if (Strings.isBlank(filter)) {
            return ResponseEntity.ok(getService().findAll(currentUser, page, size));
        }else {
            return ResponseEntity.ok(getService().findAllByFilter(currentUser, filter, page, size));
        }
    }

    @GetMapping("/by-id")
    public ResponseEntity<DTO> getById(@LoggedUser CurrentUser currentUser, @RequestParam Integer id) {
        return ResponseEntity.ok(getService().findById(currentUser, id));
    }

    @PostMapping
    public ResponseEntity<DTO> create(@LoggedUser CurrentUser currentUser, @RequestBody C body) {
        return ResponseEntity.ok(getService().create(currentUser, body));
    }

    @PutMapping
    public ResponseEntity<DTO> update(@LoggedUser CurrentUser currentUser, @RequestBody C body) {
        return ResponseEntity.ok(getService().update(currentUser, body));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@LoggedUser CurrentUser currentUser, @RequestParam Integer id) {
        getService().delete(id);
        return ResponseEntity.ok().build();
    }

    public S getService() {
        return service;
    }
}
