package com.dental.lab.controller;

import com.dental.lab.dto.ApiResponse;
import com.dental.lab.dto.EntryRequest;
import com.dental.lab.dto.FilterRequest;
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
            System.out.println(entryRequest.getEntryDate());
            ApiResponse<Entry> response = new ApiResponse<>(200,false, "Data fetched successfully", entryService.addEntry(entryRequest.getLabId(), entryRequest.getDoctorId(), entryRequest.getMaterialId(), entryRequest.getEntryDate(),entryRequest.getAmount(),entryRequest.getGraph(),entryRequest.getUnit(),entryRequest.getPatient()));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<Entry> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Entry>> getEntryById(@PathVariable String id) {
        try {
            ApiResponse<Entry> response = new ApiResponse<>(200,false, "Data fetched successfully", entryService.getEntryById(id));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<Entry> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PostMapping("/get-entries-by-lab")
    public ResponseEntity<ApiResponse<PagedResponse<Entry>>> getEntry(@RequestBody FilterRequest filterRequest) {
        try {
            ApiResponse<PagedResponse<Entry>> response = new ApiResponse<>(200,false, "Data fetched successfully", entryService.getEntriesByFilter(filterRequest.getStartDate(),filterRequest.getEndDate(),filterRequest.getDoctorId(),filterRequest.getLabId(),filterRequest.getPage(),filterRequest.getSize()));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<PagedResponse<Entry>> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Entry>> updateEntry(@PathVariable String id, @RequestBody EntryRequest entryRequest) {
        try {
            ApiResponse<Entry> response = new ApiResponse<>(200,false, "Data fetched successfully", entryService.updateEntry(id, entryRequest.getDoctorId(),entryRequest.getMaterialId(), entryRequest.getEntryDate(),entryRequest.getAmount(),entryRequest.getGraph(),entryRequest.getUnit(),entryRequest.getPatient()));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<Entry> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteEntry(@PathVariable String id) {
        try {
            ApiResponse<String> response = new ApiResponse<>(200,false, "Data fetched successfully", entryService.deleteEntries(id));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<String> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }
}
