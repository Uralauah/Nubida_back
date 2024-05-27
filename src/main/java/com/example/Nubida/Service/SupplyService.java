package com.example.Nubida.Service;

import com.example.Nubida.DTO.SupplyDTO;
import com.example.Nubida.Entity.Supply;
import com.example.Nubida.Repository.SupplyRepository;
import com.example.Nubida.Entity.TravelSupply;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplyService {
    private final SupplyRepository supplyRepository;
    public Supply createSupply(SupplyDTO supplyDTO){
        Supply supply = new Supply();
        supply.setName(supplyDTO.getName());
        supply.setCheck(supplyDTO.isCheck());
        supply.setCount(supplyDTO.getCount());
        supplyRepository.save(supply);
        return supply;
    }

    public List<Supply> getAllSupply(List<TravelSupply> travelSupplies){
        List<Supply> supplies = new ArrayList<>();
        for(TravelSupply travelSupply : travelSupplies){
            supplies.add(travelSupply.getSupply());
        }
        return supplies;
    }
}
