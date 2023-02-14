package com.example.fitxplore.controller;

import com.example.fitxplore.dao.ClientRepository;
import com.example.fitxplore.dao.PaymentRepository;
import com.example.fitxplore.dao.SubscriberRepository;
import com.example.fitxplore.dao.TrainerRepository;
import com.example.fitxplore.entity.Client;
import com.example.fitxplore.entity.Payment;
import com.example.fitxplore.entity.Trainer;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Map;


@Controller

public class HomeController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private TrainerRepository trainerRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private SubscriberRepository subscriberRepository;


    @PostMapping("/create_order")
    @ResponseBody
    public String createOrder(@RequestBody Map<String, Object> data, Principal principle) throws RazorpayException {

        System.out.println(data);
        int amt = Integer.parseInt(data.get("amount").toString());
        var client = new RazorpayClient("rzp_test_KJlRFeNbThTgLB", "BhxuVyDAESlfMfE4FIx5ZKDi");
        JSONObject ob = new JSONObject();
        ob.put("amount", amt * 100);
        ob.put("currency", "INR");
        ob.put("receipt", "txn_235425");

        // Creating new Order
        Order order = client.Orders.create(ob);
        System.out.println(order);

        // save order in database
        Payment payment = new Payment();
        payment.setAmount(order.get("amount") + "");
        payment.setOrderId(order.get("id"));
        payment.setPaymentId(null);
        payment.setStatus("created");

        this.paymentRepository.save(payment);

        return order.toString();
    }

    @PostMapping("/update_order")
    public ResponseEntity<?> updateOrder(@RequestBody Map<String, Object> data) {
        Payment payment = this.paymentRepository.findByOrderId(data.get("order_id").toString());
        payment.setPaymentId(data.get("payment_id").toString());
        payment.setStatus(data.get("id_status").toString());
        paymentRepository.save(payment);

        System.out.println(data);
        return ResponseEntity.ok(Map.of("msg", "updated"));
    }

    @PostMapping("/update_entries_trainer")
    @ResponseBody
    public String updateEntriesTrainer(@RequestBody Map<String, Object> data) {
        System.out.println(data);
        Trainer trainer = this.trainerRepository.getTrainerByUserName(data.get("username").toString());
        trainer.setRole("ROLE_TRAINER");
        trainerRepository.save(trainer);
        System.out.println(trainer);
        return "Role Trainer Saved";
    }

    @PostMapping("/update_entries_client")
    @ResponseBody
    public String updateEntriesClient(@RequestBody Map<String, Object> data) {
        System.out.println(data);
        Client client = this.clientRepository.getClientByUserName(data.get("username").toString());
        client.setRole("ROLE_CLIENT");
        clientRepository.save(client);
        System.out.println(client);
        return "Role Client Saved";
    }

    @GetMapping("/subscribeTrainer")
    public String subscribeTrainer() {
        return "subscribeTrainer";
    }

    @GetMapping("/subscribeClient")
    public String subscribeClient() {
        return "subscribeClient";
    }


    @GetMapping("/start")
    public String Main() {
        return "home";
    }

    @GetMapping("/liveWell")
    public String Main1() {
        return "LiveWell";
    }

    @GetMapping("/getFit")
    public String Main2() {
        return "getFit";
    }

    @GetMapping("/eatHealthy")
    public String Main3() {
        return "eatHealthy";
    }

    @GetMapping("/login1")
    public String Main4() {
        return "login";
    }

    @GetMapping("/signup")
    public String Main5() {
        return "signup";
    }

    @PostMapping("/trainerRegister")
    @ResponseBody
    public String registerTrainer(@RequestBody Map<String, Object> data) {
        Trainer trainer = new Trainer();
        trainer.setName(data.get("name").toString());
        trainer.setUserName(data.get("username").toString());
        trainer.setPassword(passwordEncoder.encode(data.get("password").toString()));
        trainer.setEmail(data.get("email").toString());
        trainerRepository.save(trainer);
        System.out.println(trainer);
        return "Trainer has been Registered";
    }

    @PostMapping("/clientRegister")
    @ResponseBody
    public String registerClient(@RequestBody Map<String, Object> data) {
        Client client = new Client();
        client.setName(data.get("name").toString());
        client.setUserName(data.get("username").toString());
        client.setPassword(passwordEncoder.encode(data.get("password").toString()));
        client.setEmail(data.get("email").toString());
        clientRepository.save(client);
        System.out.println(client);
        return "Client has been Registered";
    }


}