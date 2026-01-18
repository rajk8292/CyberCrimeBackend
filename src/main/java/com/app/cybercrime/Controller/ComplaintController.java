package com.app.cybercrime.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.app.cybercrime.entity.Complaint;
import com.app.cybercrime.repository.ComplaintRepository;
import com.app.cybercrime.service.EmailService;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin("*")
public class ComplaintController {

    @Autowired
    private ComplaintRepository repo;

    @Autowired
    private EmailService emailService;

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

        Complaint saved = repo.save(c);
        

        // Email send
        try {
        	String emailSubject = "✅ Complaint Registered Successfully";

        	String emailBody = "<div style=\"font-family: Arial, sans-serif; max-width: 600px; margin: auto; border: 1px solid #ddd; padding: 20px; border-radius: 10px;\">"
        	        + "<div style=\"background: #0d6efd; color: white; padding: 15px; border-radius: 10px 10px 0 0;\">"
        	        + "<h2 style=\"margin: 0;\">Cybercrime Complaint Registered</h2>"
        	        + "</div>"
        	        + "<div style=\"padding: 20px;\">"
        	        + "<p>Dear <b>" + saved.getName() + "</b>,</p>"
        	        + "<p style=\"font-size: 16px;\">Your complaint has been <b style=\"color: #0d6efd;\">registered successfully</b>.</p>"
        	        + "<table style=\"width: 100%; border-collapse: collapse; margin-top: 15px;\">"
        	        + "<tr><td style=\"padding: 10px; border: 1px solid #ddd;\"><b>Tracking ID</b></td><td style=\"padding: 10px; border: 1px solid #ddd;\">"
        	        + saved.getTrackingId() + "</td></tr>"
        	        + "<tr><td style=\"padding: 10px; border: 1px solid #ddd;\"><b>Status</b></td><td style=\"padding: 10px; border: 1px solid #ddd;\">"
        	        + saved.getStatus() + "</td></tr>"
        	        + "<tr><td style=\"padding: 10px; border: 1px solid #ddd;\"><b>Date</b></td><td style=\"padding: 10px; border: 1px solid #ddd;\">"
        	        + saved.getCreatedDate() + "</td></tr>"
        	        + "</table>"
        	        + "<p style=\"margin-top: 20px;\">You can track your complaint using the above Tracking ID.</p>"
        	        + "<p style=\"margin-top: 30px;\"><b>Thank you,</b><br>Cybercrime Team</p>"
        	        + "</div></div>";


            emailService.sendSimpleMail(saved.getEmail(), emailSubject, emailBody);
        } catch(Exception e) {
            System.out.println("Email send failed: " + e.getMessage());
        }

        return saved;
    }

    @PutMapping("/{id}/status")
    public Complaint updateStatus(@PathVariable Long id, @RequestParam String status) {
        Complaint c = repo.findById(id).orElseThrow(() -> new RuntimeException("Complaint not found"));
        c.setStatus(status);
        Complaint saved = repo.save(c);

        try {
            String subject = "🔔 Complaint Status Updated";

            String htmlBody = "<div style=\"font-family: Arial, sans-serif; max-width: 600px; margin: auto; border: 1px solid #ddd; padding: 20px; border-radius: 10px;\">"
                    + "<div style=\"background: #0d6efd; color: white; padding: 15px; border-radius: 10px 10px 0 0;\">"
                    + "<h2 style=\"margin: 0;\">Cybercrime Complaint Update</h2>"
                    + "</div>"
                    + "<div style=\"padding: 20px;\">"
                    + "<p>Dear <b>" + saved.getName() + "</b>,</p>"
                    + "<p style=\"font-size: 16px;\">Your complaint status has been <b style=\"color: #0d6efd;\">updated successfully</b>.</p>"
                    + "<table style=\"width: 100%; border-collapse: collapse; margin-top: 15px;\">"
                    + "<tr><td style=\"padding: 10px; border: 1px solid #ddd;\"><b>Tracking ID</b></td><td style=\"padding: 10px; border: 1px solid #ddd;\">"
                    + saved.getTrackingId() + "</td></tr>"
                    + "<tr><td style=\"padding: 10px; border: 1px solid #ddd;\"><b>New Status</b></td><td style=\"padding: 10px; border: 1px solid #ddd;\">"
                    + saved.getStatus() + "</td></tr>"
                    + "<tr><td style=\"padding: 10px; border: 1px solid #ddd;\"><b>Date</b></td><td style=\"padding: 10px; border: 1px solid #ddd;\">"
                    + saved.getCreatedDate() + "</td></tr>"
                    + "</table>"
                    + "<p style=\"margin-top: 20px;\">If you have any questions, reply to this email.</p>"
                    + "<p style=\"margin-top: 30px;\"><b>Thank you,</b><br>Cybercrime Team</p>"
                    + "</div></div>";

            emailService.sendHtmlMail(saved.getEmail(), subject, htmlBody);

        } catch (Exception e) {
            System.out.println("Email send failed: " + e.getMessage());
        }

        return saved;
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
