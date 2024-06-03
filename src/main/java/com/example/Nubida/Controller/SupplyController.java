package com.example.Nubida.Controller;

import com.example.Nubida.DTO.SupplyDTO;
import com.example.Nubida.Entity.Supply;
import com.example.Nubida.Service.SupplyService;
import com.example.Nubida.Service.TravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@ResponseBody
@RequestMapping("/supply")
public class SupplyController {
    private final SupplyService supplyService;
    private final TravelService travelService;
    @PostMapping("/create")
    public ResponseEntity<?> createSupply(@RequestParam(name = "id") long travel_id, @RequestBody SupplyDTO supplyDTO) {
        int result = travelService.createSupply(travel_id, supplyDTO);
        return switch (result) {
            case -1 -> ResponseEntity.badRequest().body("여행 정보를 확인할 수 없습니다.");
            case 200 -> ResponseEntity.ok().body("성공적으로 추가되었습니다.");
            default -> ResponseEntity.internalServerError().body("알 수 없는 오류가 발생했습니다.");
        };
    }

    @GetMapping("/getAll")
    public List<Supply> getAllTravelSupply(@RequestParam(name = "id") long travel_id) {
        return travelService.getAllTravelSupply(travel_id);
    }

    @PostMapping("/check")
    public void supplyChangeCheck(@RequestBody SupplyDTO supplyDTO) {
        supplyService.supplyChangeCheck(supplyDTO);
    }

    @PostMapping("/count")
    public void supplyChangeCount(@RequestBody SupplyDTO supplyDTO) {
        supplyService.supplyChangeCount(supplyDTO);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> supplyDelete(@RequestParam(name="id")long travel_id,@RequestBody SupplyDTO supplyDTO){
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

}
