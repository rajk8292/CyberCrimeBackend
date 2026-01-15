package com.app.cybercrime.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.cybercrime.entity.Evidence;
import com.app.cybercrime.repository.EvidenceRepository;

@RestController
@RequestMapping("/api/evidence")
@CrossOrigin("*")
public class EvidenceController {

    @Autowired
    private EvidenceRepository repo;

    @PostMapping("/upload")
    public Evidence upload(@RequestParam Long caseId, @RequestParam MultipartFile file) throws IOException {
        Files.createDirectories(Paths.get("uploads"));
        String path = "uploads/" + file.getOriginalFilename();
        Files.copy(file.getInputStream(), Paths.get(path), StandardCopyOption.REPLACE_EXISTING);

        Evidence e = new Evidence();
        e.setCaseId(caseId);
        e.setFileName(file.getOriginalFilename());
        e.setFilePath(path);
        e.setUploadTime(LocalDateTime.now());
        return repo.save(e);
    }

    @GetMapping("/{caseId}")
    public List<Evidence> getByCase(@PathVariable Long caseId){
        return repo.findByCaseId(caseId);
    }
    @DeleteMapping("/{id}")
    public String deleteEvidence(@PathVariable Long id) throws IOException {
        Evidence e = repo.findById(id).orElseThrow();
        Files.deleteIfExists(Paths.get(e.getFilePath()));
        repo.deleteById(id);
        return "Evidence deleted successfully";
    }
 // Agar chahte ho alag mapping:
    @GetMapping("/list/{caseId}")
    public List<Evidence> getByCaseList(@PathVariable Long caseId){
        return repo.findByCaseId(caseId);
    }


}
