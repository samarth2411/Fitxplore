package com.example.fitxplore.controller;

import com.example.fitxplore.dao.ClientRepository;
import com.example.fitxplore.dao.TrainerRepository;
import com.example.fitxplore.entity.Client;
import com.example.fitxplore.entity.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TrainerRepository trainerRepository;

    @RequestMapping("/dashboard")
    public String dashboard() {
        return "client_dashboard";
    }

    @GetMapping("/{Trainer_username}")
    public String setChoosedTrainer(@PathVariable("Trainer_username") String Trainer_username, Principal principal) {
        System.out.println("-------------" + Trainer_username + "--------------------");
        String client = principal.getName();
        System.out.println("The Logged in Client is :- " + client);
        Client clientObject = this.clientRepository.getClientByUserName(client);
        Trainer trainerObject = this.trainerRepository.getTrainerByUserName(Trainer_username);
        clientObject.setTrainer(trainerObject);
        clientRepository.save(clientObject);
        return "client_dashboard";
    }

    @GetMapping("/chooseTrainer")
    public String a(Model model, Principal principal) {
        List<Trainer> list = trainerRepository.getTrainersByRole("ROLE_TRAINER");
        model.addAttribute("List", list);
        return "chooseTrainer";
    }
}
