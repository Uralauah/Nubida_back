package com.example.Nubida.Controller;

import com.example.Nubida.DTO.*;
import com.example.Nubida.Entity.Supply;
import com.example.Nubida.Entity.Travel;
import com.example.Nubida.DTO.TravelDTO;
import com.example.Nubida.Repository.TravelRepository;
import com.example.Nubida.Service.TravelService;
import com.example.Nubida.Entity.Traveler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@ResponseBody
@RequestMapping("/travel")
public class TravelController {
    private final TravelService travelService;
    private final TravelRepository travelRepository;

    @PostMapping("/create")
    public void createTravel(@RequestBody TravelDTO travelDTO, Principal principal) {
        travelService.createTravel(travelDTO, principal);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteTravel(@RequestParam(name = "id") int id) {
        int result = travelService.deleteTravel(id);
        return switch (result) {
            case -1 -> ResponseEntity.badRequest().body("해당 여행이 존재하지 않습니다.");
            case 200 -> ResponseEntity.ok().body("성공적으로 삭제되었습니다.");
            default -> ResponseEntity.internalServerError().body("알 수 없는 오류 발생");
        };
    }

    @GetMapping("/getMyTravel")
    public List<Travel> getMyTravel(Principal principal) {
        return travelService.getMyTravel(principal);
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinTravel(@RequestBody Code code, Principal principal) {
        int result = travelService.joinTravel(code.getCode(), principal);
        return switch (result) {
            case -1 -> ResponseEntity.badRequest().body("존재하지 않는 코드입니다.");
            case -2 -> ResponseEntity.badRequest().body("사용자 정보를 확인할 수 없습니다.");
            case -3 -> ResponseEntity.badRequest().body("이미 참가 중인 여행입니다.");
            case 200 -> ResponseEntity.ok().body("여행 참가 성공");
            default -> ResponseEntity.internalServerError().body("알 수 없는 오류 발생");
        };
    }

    @GetMapping("/getAllTravel")
    public List<Travel> getAllTravel(Principal principal) {
        return travelService.getAllTravel(principal);
    }

    @GetMapping("/getTravelInfo")
    public Travel getTravelInfo(@RequestParam(name = "id") long id) {
        return travelService.getTravelInfo(id);
    }

    @GetMapping("/getTravelTraveler")
    public List<Traveler> getTravelTraveler(@RequestParam(name = "id") long id) {
        return travelService.getTravelTraveler(id);
    }

    @PostMapping("/deleteTraveler")
    public ResponseEntity<?> deleteTravelTraveler(Principal principal, @RequestBody DeleteTravelerDTO deleteTravelerDTO) {
        int result = travelService.deleteTravelTraveler(principal, deleteTravelerDTO.getTravel_id(), deleteTravelerDTO.getTravel_traveler_id());
        return switch (result) {
            case -1 -> ResponseEntity.badRequest().body("존재하지 않는 여행입니다.");
            case -2 -> ResponseEntity.badRequest().body("사용자를 확인할 수 없습니다.");
            case -3 -> ResponseEntity.badRequest().body("권한을 확인할 수 없습니다.");
            case -4 -> ResponseEntity.badRequest().body("대상자를 확인할 수 없습니다.");
            case 200 -> ResponseEntity.ok().body("정상적으로 삭제되었습니다.");
            default -> ResponseEntity.internalServerError().body("알 수 없는 오류 발생");
        };
    }


    @GetMapping("/viewReview")
    public ReviewDTO viewTravelReview(@RequestParam(name="id")long travel_id,Principal principal){
        return travelService.viewTravelReview(travel_id,principal);
    }

    @PostMapping("/addBudget")
    public ResponseEntity<?> addBudget(@RequestParam(name="id")long travel_id,@RequestBody BudgetDTO budget){
        int result = travelService.addBudget(travel_id, budget);
        switch (result){
            case -1:
                return ResponseEntity.badRequest().body("여행을 찾을 수 없습니다.");
            case 200:
                return ResponseEntity.ok().body("성공적으로 수정되었습니다.");
            default:
                return ResponseEntity.internalServerError().body("알 수 없는 오류 발생");
        }
    }

    @PostMapping("/addTransport")
    public ResponseEntity<?> addTransport(@RequestParam(name="id")long travel_id, @RequestBody TransportationDTO transportationDTO){
        int result = travelService.addTransportation(travel_id, transportationDTO);
        return switch (result) {
            case -1 -> ResponseEntity.badRequest().body("여행을 찾을 수 없습니다.");
            case -2 -> ResponseEntity.badRequest().body("이동수단을 찾을 수 없습니다.");
            case 200 -> ResponseEntity.ok().body("성공적으로 추가되었습니다.");
            default -> ResponseEntity.internalServerError().body("알 수 없는 오류 발생");
        };
    }

    @PostMapping("/quitTravel")
    public ResponseEntity<?> quitTravel(@RequestParam(name="id")long travel_id, Principal principal){
        int result = travelService.quitTravel(principal,travel_id);
        return switch (result) {
            case -1 -> ResponseEntity.badRequest().body("여행을 확인할 수 없습니다.");
            case -2 -> ResponseEntity.badRequest().body("사용자를 확인할 수 없습니다.");
            case -3 -> ResponseEntity.badRequest().body("알 수 없는 오류 발생");
            case 200 -> ResponseEntity.ok().body("성공적으로 탈퇴하였습니다.");
            default -> ResponseEntity.internalServerError().body("알 수 없는 오류 발생");
        };
    }
}
