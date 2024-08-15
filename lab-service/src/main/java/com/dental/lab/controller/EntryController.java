package com.dental.lab.controller;

import com.dental.lab.dto.ApiResponse;
import com.dental.lab.dto.EntryRequest;
import com.dental.lab.dto.PagedResponse;
import com.dental.lab.model.Entry;
import com.dental.lab.services.EntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/lab/entry")
@RequiredArgsConstructor
public class EntryController {
    private final EntryService entryService;

    @PostMapping
    public ResponseEntity<ApiResponse<Entry>> addEntry(@RequestBody EntryRequest entryRequest) {
        try {
            ApiResponse<Entry> response = new ApiResponse<>(200,false, "Data fetched successfully", entryService.addEntry(entryRequest.getLabId(), entryRequest.getDoctorId(), entryRequest.getMaterialId(), entryRequest.getEntryDate()));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<Entry> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping("/getEntriesByLab")
    public ResponseEntity<ApiResponse<PagedResponse<Entry>>> addEntry(@RequestParam String labId, @RequestParam(defaultValue = "0",required = false) int page, @RequestParam(defaultValue = "10",required = false) int size) {
        try {
            ApiResponse<PagedResponse<Entry>> response = new ApiResponse<>(200,false, "Data fetched successfully", entryService.getEntriesByLab(labId,page,size));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<PagedResponse<Entry>> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }
}
