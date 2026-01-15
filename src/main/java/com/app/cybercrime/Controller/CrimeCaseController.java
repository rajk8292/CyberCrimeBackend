package com.app.cybercrime.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.cybercrime.entity.CrimeCase;
import com.app.cybercrime.repository.CrimeCaseRepository;

@RestController
@RequestMapping("/api/cases")
@CrossOrigin("*")
public class CrimeCaseController {

    @Autowired
    private CrimeCaseRepository repo;

    @PostMapping
    public CrimeCase createCase(@RequestBody CrimeCase c){
        c.setStatus("Registered");
        c.setCreatedDate(LocalDate.now());
        return repo.save(c);
    }

    @GetMapping
    public List<CrimeCase> getAllCases(){
        return repo.findAll();
    }

    @PutMapping("/{id}/status")
    public CrimeCase updateStatus(@PathVariable Long id, @RequestParam String status){
        CrimeCase c = repo.findById(id).orElseThrow();
        c.setStatus(status);
        return repo.save(c);
    }
    @DeleteMapping("/{id}")
    public String deleteCase(@PathVariable Long id){
        repo.deleteById(id);
        return "Case deleted successfully";
    }
    @GetMapping("/{id}")
    public CrimeCase getCase(@PathVariable Long id){
        return repo.findById(id).orElseThrow();
    }
    @PutMapping("/{id}")
    public CrimeCase updateCase(@PathVariable Long id, @RequestBody CrimeCase updated) {
        CrimeCase c = repo.findById(id).orElseThrow();
        c.setCaseTitle(updated.getCaseTitle());
        c.setCrimeType(updated.getCrimeType());
        c.setAssignedOfficer(updated.getAssignedOfficer());
        c.setDescription(updated.getDescription());
        c.setStatus(updated.getStatus());
        return repo.save(c);
    }

    @GetMapping("/search")
    public List<CrimeCase> searchCases(
            @RequestParam(required = false) String crimeType,
            @RequestParam(required = false) String assignedOfficer,
            @RequestParam(required = false) String status) {

        List<CrimeCase> cases = repo.findAll();
        if (crimeType != null)
            cases = cases.stream().filter(c -> c.getCrimeType().equalsIgnoreCase(crimeType)).toList();
        if (assignedOfficer != null)
            cases = cases.stream().filter(c -> c.getAssignedOfficer().equalsIgnoreCase(assignedOfficer)).toList();
        if (status != null)
            cases = cases.stream().filter(c -> c.getStatus().equalsIgnoreCase(status)).toList();
        return cases;
    }


}