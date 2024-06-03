package com.example.Nubida.Controller;

import com.example.Nubida.Entity.Plan;
import com.example.Nubida.DTO.PlanDTO;
import com.example.Nubida.Service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@ResponseBody
@RequestMapping("/plan")
public class PlanController {
    private final PlanService planService;
    @PostMapping("/addPlan")
    public ResponseEntity<?> addPlan(@RequestParam(name="travel_id") long id, @RequestBody PlanDTO planDTO){
        int result = planService.addPlan(id,planDTO);
        switch(result){
            case -1:
                return ResponseEntity.badRequest().body("여행을 찾을 수 없습니다.");
            case 200:
                return ResponseEntity.ok().body("성공적으로 추가되었습니다.");
            default:
                return ResponseEntity.internalServerError().body("알 수 없는 오류가 발생했습니다.");
        }
    }

    @GetMapping("/getPlans")
    public List<Plan> getPlans(@RequestParam(name="id")int id){
        return planService.getPlans(id);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deletePlan(@RequestParam(name="plan_id") long id){
        int result = planService.deletePlan(id);
        switch (result){
            case -1:return ResponseEntity.badRequest().body("계획을 찾을 수 없습니다.");
            case -2:
                return ResponseEntity.badRequest().body("여행을 찾을 수 없습니다.");
            case 200:
                return ResponseEntity.ok().body("성공적으로 삭제되었습니다.");
            default:
                return ResponseEntity.internalServerError().body("알 수 없는 오류 발생");
        }
    }
}
