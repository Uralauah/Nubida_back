package com.example.Nubida.Service;

import com.example.Nubida.Entity.Supply;
import com.example.Nubida.Entity.Travel;
import com.example.Nubida.Entity.TravelSupply;
import com.example.Nubida.Repository.TravelSupplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TravelSupplyService {
    private final TravelSupplyRepository travelSupplyRepository;

    public void create(Travel travel, Supply supply){
        TravelSupply travelSupply = new TravelSupply();
        travelSupply.setTravel(travel);
        travelSupply.setSupply(supply);
        travelSupplyRepository.save(travelSupply);
    }
}
