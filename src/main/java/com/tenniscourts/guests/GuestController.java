package com.tenniscourts.guests;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tenniscourts.config.BaseRestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "guest", produces = "application/json", consumes = "application/json")
public class GuestController extends BaseRestController{

    @Autowired
    private GuestService guestService;


    @GetMapping("/find")
    @ApiOperation(value = "Search for guests using ID or name")
    public ResponseEntity<GuestDTO> findGuest(@RequestParam(required = false) Long guestid, @RequestParam(required = false) String guestName){
        return ResponseEntity.ok(guestService.findGuest(guestid, guestName));

    }

    @ApiOperation(value = "Search for all guests")
    @GetMapping("/findall")
    public ResponseEntity<List<GuestDTO>> findAllGuest(){
        return ResponseEntity.ok(guestService.findAllGuest());

    }

    @ApiOperation(value = "Create a new guest")
    @PostMapping("/create")
    public ResponseEntity<Void> guestCreate(@RequestBody @ApiParam(required = true) @Valid GuestDTO guestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.create(guestDTO).getId())).build();
    }

    @ApiOperation(value = "Delete a guest record by ID")
    @DeleteMapping("/delete/{guestId}")
    public ResponseEntity<GuestDTO> cancelReservation(@PathVariable @ApiParam(required = true) Long guestId) {
        return ResponseEntity.ok(guestService.deleteGuest(guestId));
    }

    @ApiOperation(value = "Update guest records")
    @PatchMapping("/update/{guestId}")
    public ResponseEntity<GuestDTO> updateGuest(@PathVariable @ApiParam(required = true) Long guestId, @RequestBody @Valid @ApiParam(required = true) GuestDTO guestDTO) {
        return ResponseEntity.ok(guestService.updateGuest(guestId , guestDTO));
    }

}

