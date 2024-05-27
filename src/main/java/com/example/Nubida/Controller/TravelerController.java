package com.example.Nubida.Controller;

import com.example.Nubida.Entity.Traveler;
import com.example.Nubida.DTO.TravelerDTO;
import com.example.Nubida.Service.TravelerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/traveler")
@ResponseBody
public class TravelerController {
    private final TravelerService travelerService;

    //    @PostMapping("/register")
//    public int create( TravelerDTO travelerDTO){
//        return travelerService.create(travelerDTO);
//    }
    @PostMapping("/register")
    public ResponseEntity<?> create(@RequestBody TravelerDTO travelerDTO) {
        System.out.println(travelerDTO.getUsername());
        int result = travelerService.create(travelerDTO);
        switch (result) {
            case -1:
                return ResponseEntity.badRequest().body("이미 존재하는 이메일입니다.");
            case -2:
                return ResponseEntity.badRequest().body("이미 존재하는 유저네임입니다.");
            case 200:
                return ResponseEntity.ok().body("회원가입 성공");
            default:
                return ResponseEntity.internalServerError().body("알 수 없는 오류 발생");
        }
    }

    @GetMapping("/getId")
    public Long getId(Principal principal){
        return travelerService.getId(principal);
    }

    @GetMapping("/getAllTraveler")
    public List<Traveler> getAllTraveler (Principal principal){
        return travelerService.getAllTraveler(principal);
    }
}