package com.example.Nubida.Controller;

import com.example.Nubida.DTO.GetTransportDTO;
import com.example.Nubida.Service.TransportationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/transportation")
@RequiredArgsConstructor
@ResponseBody
public class TransportationController {
    private final TransportationService transportationService;

    @GetMapping("/getAll")
    public List<GetTransportDTO> getAllTransportation(){
        return transportationService.getAllTransportation();
    }
}
