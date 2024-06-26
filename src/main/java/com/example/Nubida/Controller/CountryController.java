package com.example.Nubida.Controller;

import com.example.Nubida.DTO.CountryDTO;
import com.example.Nubida.DTO.RecommendCountryDTO;
import com.example.Nubida.Service.CountryService;
import com.example.Nubida.DTO.ReviewDTO;
import com.example.Nubida.Entity.Country;
import com.example.Nubida.Service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/country")
@RequiredArgsConstructor
@ResponseBody
public class CountryController {
    private final CountryService countryService;

    @PostMapping("/admin/create")
    public ResponseEntity<?> createCountry(@RequestBody CountryDTO countryDTO) {
        int result = countryService.createCountry(countryDTO);
        switch (result) {
            case -1:
                return ResponseEntity.badRequest().body("이미 존재하는 국가입니다.");
            case 200:
                return ResponseEntity.ok().body("국가 생성 성공");
            default:
                return ResponseEntity.badRequest().body("알 수 없는 오류 발생");
        }
    }

    @GetMapping("/getAllCountry")
    public List<Country> getAllCountry() {
        return countryService.getAllCountry();
    }

    @GetMapping("/getCountry")
    public Country getCountry(@RequestParam(name = "id") int id) {
        return countryService.getCountry(id);
    }



    @GetMapping("/getRecommend")
    public  List<RecommendCountryDTO> getRecommendCountry(){
        return countryService.getRecommendCountry();
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteCountry(@RequestBody CountryDTO countryDTO){
        int result = countryService.deleteCountry(countryDTO);
        switch (result){
            case -1:
                return ResponseEntity.badRequest().body("국가 정보를 확인할 수 없습니다.");
            case 200:
                return ResponseEntity.ok().body("성공적으로 삭제되었습니다.");
            default:
                return ResponseEntity.internalServerError().body("알 수 없는 오류 발생");
        }
    }
}
