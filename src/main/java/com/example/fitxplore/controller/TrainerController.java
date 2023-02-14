package com.example.fitxplore.controller;

import com.example.fitxplore.dao.TrainerRepository;
import com.example.fitxplore.entity.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

@Controller
@RequestMapping("/trainer")
public class TrainerController {

    @Autowired
    private TrainerRepository trainerRepository;

    @RequestMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        String trainer = principal.getName();
        System.out.println("The Logged in Trainer is :- " + trainer);
        Trainer obj = trainerRepository.getTrainerByUserName(trainer);
        model.addAttribute("trainerObject", obj);
        return "trainer_dashboard";
    }

    @GetMapping("/editProfile")
    public String editProfile(Model model, Principal principal) {
        String name = principal.getName();
        Trainer trainer = this.trainerRepository.getTrainerByUserName(name);
        model.addAttribute("trainer", trainer);
        return "editProfileTrainer";
    }

    @PostMapping("/edit")
    public String submitEditForm(@RequestParam("workExperience") int workExperience,
                                 @RequestParam("about") String about,
                                 @RequestParam("image") MultipartFile file,
                                 Principal principal) throws IOException {

        String name = principal.getName();
        Trainer trainer = this.trainerRepository.getTrainerByUserName(name);
        if (file.isEmpty()) {
            System.out.println("File is Empty");
        } else {
            // upload the file to folder
            trainer.setImage(file.getOriginalFilename());
            File saveFile = new ClassPathResource("static/Images").getFile();
            Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        }
        trainer.setWorkExperience(workExperience);
        trainer.setAbout(about);
        trainerRepository.save(trainer);

        return "trainer_dashboard";
    }
}
