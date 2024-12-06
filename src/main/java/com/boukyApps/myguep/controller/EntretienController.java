package com.boukyApps.myguep.controller;

import com.boukyApps.myguep.model.Entretien;
import com.boukyApps.myguep.repository.EntretienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController()
@RequestMapping("/entretien")
@CrossOrigin(origins = "http://localhost:4200")
public class EntretienController {

    private EntretienRepository entretienRepository;
    private Logger logger = Logger.getLogger(this.getClass().getName());


    /**
     * Constuctor
     * @param entretienRepository
     */
    public EntretienController(EntretienRepository entretienRepository) {
        this.entretienRepository = entretienRepository;
    }

    @GetMapping
    public List<Entretien> getAllEntretiens(){
        System.out.println("sout - entré dasn get");
        logger.info("info 2 - entré dasn get");
        logger.warning("warning 2 - entré dasn get");
        logger.severe("severe 2 - entré dasn get");
        return entretienRepository.findAll();
    }

    @PostMapping
    public Entretien saveEntretien(@RequestBody Entretien entretien){

        System.out.println("entré saveEntretien");
        return entretienRepository.save(entretien);
    }

}
