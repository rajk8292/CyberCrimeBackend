package com.app.cybercrime.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.app.cybercrime.entity.Complaint;
import com.app.cybercrime.repository.ComplaintRepository;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin("*")
public class ComplaintController {

    @Autowired
    private ComplaintRepository repo;

    // Register complaint with optional document
    @PostMapping("/with-file")
    public Complaint createComplaintWithFile(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String subject,
            @RequestParam String description,
            @RequestParam(required = false) MultipartFile document
    ) throws IOException {
        Complaint c = new Complaint();
        c.setName(name);
        c.setEmail(email);
        c.setPhone(phone);
        c.setSubject(subject);
        c.setDescription(description);
        c.setStatus("Pending");
        c.setTrackingId("CYB-" + System.currentTimeMillis());
        c.setCreatedDate(LocalDate.now());

        if(document != null && !document.isEmpty()){
            c.setDocumentName(document.getOriginalFilename());
            c.setDocumentContent(document.getBytes());
        }

        return repo.save(c);
    }
    @PutMapping("/{id}/status")
    public Complaint updateStatus(@PathVariable Long id, @RequestParam String status) {
        Complaint c = repo.findById(id).orElseThrow(() -> new RuntimeException("Complaint not found"));
        c.setStatus(status);
        return repo.save(c);
    }
    // Get all complaints
    @GetMapping
    public List<Complaint> getAllComplaints() {
        return repo.findAll();
    }
 // Get complaint by trackingId
    @GetMapping("/{trackingId}")
    public Complaint getComplaintByTrackingId(@PathVariable String trackingId) {
        return repo.findByTrackingId(trackingId)
                   .orElseThrow(() -> new RuntimeException("Invalid Tracking ID"));
    }

}
