package com.example.Nubida.Service;

import com.example.Nubida.DTO.PlanDTO;
import com.example.Nubida.Entity.Plan;
import com.example.Nubida.Repository.PlanRepository;
import com.example.Nubida.Entity.Travel;
import com.example.Nubida.Repository.TravelRepository;
import com.example.Nubida.Entity.TravelPlan;
import com.example.Nubida.Repository.TravelPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;
    private final TravelRepository travelRepository;
    private final TravelPlanRepository travelPlanRepository;
    public int addPlan(long travel_id, PlanDTO planDTO){
        Optional<Travel> ot = travelRepository.findById(travel_id);
        if(ot.isEmpty())
            return -1;
        Travel travel = ot.get();
        Plan plan = new Plan();
        plan.setPlan_name(planDTO.getPlan_name());
        plan.setPlan_cost(planDTO.getPlan_cost());
        plan.setStart_date(planDTO.getStart_date());
        plan.setFinish_date(planDTO.getFinish_date());
        planRepository.save(plan);
        TravelPlan travelPlan = new TravelPlan();
        travelPlan.setTravel(travel);
        travelPlan.setPlan(plan);
        travel.setRemain_budget(travel.getRemain_budget()-planDTO.getPlan_cost());
        travelRepository.save(travel);
        travelPlanRepository.save(travelPlan);
        return 200;
    }

    public List<Plan> getPlans(long travel_id){
        Optional<Travel> ot = travelRepository.findById(travel_id);
        if(ot.isEmpty())
            return null;
        Travel travel = ot.get();
        List<TravelPlan> travelPlans = travelPlanRepository.findAllByTravel(travel);
        List<Plan> plans = new ArrayList<>();
        for (TravelPlan travelPlan : travelPlans){
            plans.add(travelPlan.getPlan());
        }
        return plans;
    }

    public int deletePlan(long plan_id){
        Optional<Plan> op = planRepository.findById(plan_id);
        if(op.isEmpty())
            return -1;
        Plan plan = op.get();
        Optional<TravelPlan> otp = travelPlanRepository.findByPlan(plan);
        if(otp.isEmpty())
            return -2;
        TravelPlan travelPlan = otp.get();
        Travel travel = travelPlan.getTravel();
        travel.setRemain_budget(travel.getRemain_budget()+plan.getPlan_cost());
        travelRepository.save(travel);
        travelPlanRepository.delete(travelPlan);
        planRepository.delete(plan);
        return 200;
    }
}
