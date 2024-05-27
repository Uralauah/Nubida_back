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
    private final ReviewService reviewService;

    @PostMapping("/admin/create")
    public ResponseEntity<?> create(@RequestBody CountryDTO countryDTO) {
        int result = countryService.create(countryDTO);
        switch (result) {
            case -1:
                return ResponseEntity.badRequest().body("이미 존재하는 국가입니다.");
            case 200:
                return ResponseEntity.ok().body("국가 생성 성공");
            default:
                return ResponseEntity.badRequest().body("알 수 없는 오류 발생");
        }
    }

    @GetMapping("/allCountry")
    public List<Country> allCountry() {
        return countryService.allCountry();
    }

    @GetMapping("/getCountry")
    public Country getCountry(@RequestParam(name = "id") int id) {
        return countryService.getCountry(id);
    }

    @PostMapping("/addReview")
    public ResponseEntity<?> addReview(@RequestParam(name = "id") int id, @RequestBody ReviewDTO reviewDTO,Principal principal) {
        int result = countryService.addReview(id, reviewDTO,principal);
        switch (result) {
            case -1:
                return ResponseEntity.badRequest().body("해당 국가를 찾을 수 없습니다.");
            case 200:
                return ResponseEntity.ok().body("성공적으로 추가되었습니다.");
            default:
                return ResponseEntity.internalServerError().body("알 수 없는 오류 발생");
        }
    }

    @GetMapping("/getRecommend")
    public  List<RecommendCountryDTO> getRecommend(){
        return countryService.getRecommendCountry();
    }
}
