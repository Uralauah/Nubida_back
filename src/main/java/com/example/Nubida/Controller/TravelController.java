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
    public void create(@RequestBody TravelDTO travelDTO, Principal principal) {
        travelService.create(travelDTO, principal);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam(name = "id") int id) {
        int result = travelService.delete(id);
        switch (result) {
            case -1:
                return ResponseEntity.badRequest().body("해당 여행이 존재하지 않습니다.");
            case 200:
                return ResponseEntity.ok().body("성공적으로 삭제되었습니다.");
            default:
                return ResponseEntity.internalServerError().body("알 수 없는 오류 발생");
        }
    }

    @GetMapping("/see")
    public Traveler info(Principal principal) {
        return travelService.info(principal);
    }

    @GetMapping("/allTravel")
    public List<Travel> allTravel(Principal principal) {
        return travelService.allTravel(principal);
    }

    @GetMapping("/leaders")
    public List<Travel> leaders(Principal principal) {
        return travelService.leaders(principal);
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody Code code, Principal principal) {
        System.out.println(code.getCode());
        int result = travelService.join(code.getCode(), principal);
        switch (result) {
            case -1:
                return ResponseEntity.badRequest().body("존재하지 않는 코드입니다.");
            case -2:
                return ResponseEntity.badRequest().body("이미 참가 중인 여행입니다.");
            case 200:
                return ResponseEntity.ok().body("여행 참가 성공");
            default:
                return ResponseEntity.internalServerError().body("알 수 없는 오류 발생");
        }
    }

    @GetMapping("/adminTravel")
    public List<Travel> adminTravel(Principal principal) {

        return travelService.adminTravel(principal);
    }

    @GetMapping("/allInfo")
    public Travel allInfo(@RequestParam(name = "id") long id) {

        return travelService.allInfo(id);
    }

    @GetMapping("/traveler")
    public List<Traveler> viewTravelTravler(@RequestParam(name = "id") long id) {
        return travelService.viewTravelTraveler(id);
    }

    @PostMapping("/deleteTraveler")
    public ResponseEntity<?> deleteTraveler(Principal principal, @RequestBody DeleteTravelerDTO deleteTravelerDTO) {
        int result = travelService.deleteTraveler(principal, deleteTravelerDTO.getTravel_id(), deleteTravelerDTO.getTravel_traveler_id());
        switch (result) {
            case -1:
                return ResponseEntity.badRequest().body("존재하지 않는 여행입니다.");
            case -2:
                return ResponseEntity.badRequest().body("사용자를 확인할 수 없습니다.");
            case -3:
                return ResponseEntity.badRequest().body("권한을 확인할 수 없습니다.");
            case -4:
                return ResponseEntity.badRequest().body("대상자를 확인할 수 없습니다.");
            case 200:
                return ResponseEntity.ok().body("정상적으로 삭제되었습니다.");
            default:
                return ResponseEntity.internalServerError().body("알 수 없는 오류 발생");
        }
    }

    @PostMapping("/setNum")
    public void setNum(@RequestParam(name = "id") long id, @RequestParam(name = "num") int num) {
        travelService.setNum(id, num);

    }

    @PostMapping("/supply/create")
    public ResponseEntity<?> createSupply(@RequestParam(name = "id") int travel_id, @RequestBody SupplyDTO supplyDTO) {
        int result = travelService.createSupply(travel_id, supplyDTO);
        switch (result) {
            case -1:
                return ResponseEntity.badRequest().body("여행 정보를 확인할 수 없습니다.");
            case 200:
                return ResponseEntity.ok().body("성공적으로 추가되었습니다.");
            default:
                return ResponseEntity.internalServerError().body("알 수 없는 오류가 발생했습니다.");

        }
    }

    @GetMapping("/supply/getAll")
    public List<Supply> getAllSupply(@RequestParam(name = "id") long travel_id) {
        return travelService.getAllSupply(travel_id);
    }

    @PostMapping("/supply/check")
    public void check(@RequestParam(name = "id") long travel_id, @RequestBody SupplyDTO supplyDTO) {
        travelService.supplyCheck(travel_id, supplyDTO);
    }

    @PostMapping("/supply/count")
    public void count(@RequestParam(name = "id") long travel_id, @RequestBody SupplyDTO supplyDTO) {
        travelService.supplyCount(travel_id, supplyDTO);
    }

    @PostMapping("/supply/delete")
    public ResponseEntity<?> delete(@RequestParam(name="id")long travel_id,@RequestBody SupplyDTO supplyDTO){
        int result = travelService.supplyDelete(travel_id,supplyDTO);
        switch(result){
            case -1:
                return ResponseEntity.badRequest().body("여행을 확인할 수 없습니다.");
            case -2:
                return ResponseEntity.badRequest().body("삭제에 오류가 생겼습니다.");
            case 200:
                return ResponseEntity.ok().body("성공적으로 삭제되었습니다.");
            default:
                return ResponseEntity.internalServerError().body("알 수 없는 오류 발생");
        }
    }

    @GetMapping("/viewReview")
    public ReviewDTO viewReview(@RequestParam(name="id")long travel_id,Principal principal){
        return travelService.viewReview(travel_id,principal);
    }

    @PostMapping("/budget")
    public ResponseEntity<?> budget(@RequestParam(name="id")long travel_id,@RequestBody BudgetDTO budget){
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
        switch (result){
            case -1:
                return ResponseEntity.badRequest().body("여행을 찾을 수 없습니다.");
            case -2:
                return ResponseEntity.badRequest().body("이동수단을 찾을 수 없습니다.");
            case 200:
                return ResponseEntity.ok().body("성공적으로 추가되었습니다.");
            default:
                return ResponseEntity.internalServerError().body("알 수 없는 오류 발생");
        }
    }

    @PostMapping("/quit")
    public ResponseEntity<?> quit(@RequestParam(name="id")long travel_id, Principal principal){
        int result = travelService.quit(principal,travel_id);
        switch (result){
            case -1:
                return ResponseEntity.badRequest().body("여행을 확인할 수 없습니다.");
            case -2:
                return ResponseEntity.badRequest().body("사용자를 확인할 수 없습니다.");
            case -3:
                return ResponseEntity.badRequest().body("알 수 없는 오류 발생");
            case 200:
                return ResponseEntity.ok().body("성공적으로 탈퇴하였습니다.");
            default:
                return ResponseEntity.internalServerError().body("알 수 없는 오류 발생");
        }
    }
}
