package com.example.Nubida.Service;

import com.example.Nubida.DTO.GetTransportDTO;
import com.example.Nubida.DTO.TransportationDTO;
import com.example.Nubida.Entity.Transportation;
import com.example.Nubida.Repository.TransportationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransportationService {
    private final TransportationRepository transportationRepository;

//    public void create(TransportationDTO transportationDTO){
//        Transportation transportation = new Transportation();
//        transportation.setName(transportationDTO.getName());
//        transportationRepository.save(transportation);
//    }

    public List<GetTransportDTO> getAll(){
        List<Transportation> transportationList = transportationRepository.findAll();
        List<GetTransportDTO> result = new ArrayList<>();
        for(Transportation transportation : transportationList){
            GetTransportDTO getTransportDTO = new GetTransportDTO();
            getTransportDTO.setId(transportation.getId());
            getTransportDTO.setName(transportation.getName());
            result.add(getTransportDTO);
        }
        return result;
    }
}
