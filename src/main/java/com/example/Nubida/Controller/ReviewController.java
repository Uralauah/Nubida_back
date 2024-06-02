package com.example.Nubida.Controller;

import com.example.Nubida.DTO.ReviewDTO;
import com.example.Nubida.Service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@ResponseBody
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/getAll")
    public List<ReviewDTO> getAll(Principal principal){
        return reviewService.getAll(principal);
    }

    @PostMapping("/addReview")
    public ResponseEntity<?> addReview(@RequestParam(name = "id") int id, @RequestBody ReviewDTO reviewDTO,Principal principal) {
        int result = reviewService.addReview(id, reviewDTO,principal);
        switch (result) {
            case -1:
                return ResponseEntity.badRequest().body("해당 국가를 찾을 수 없습니다.");
            case 200:
                return ResponseEntity.ok().body("성공적으로 추가되었습니다.");
            default:
                return ResponseEntity.internalServerError().body("알 수 없는 오류 발생");
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody ReviewDTO reviewDTO){
        int result = reviewService.delete(reviewDTO.getId());
        switch (result){
            case -1:
                return ResponseEntity.badRequest().body("리뷰 정보를 확인할 수 없습니다.");
            case 200:
                return ResponseEntity.ok().body("성공적으로 삭제되었습니다.");
            default:
                return ResponseEntity.internalServerError().body("알 수 없는 오류 발생");
        }
    }

    @PostMapping("/change")
    public ResponseEntity<?> change(@RequestBody ReviewDTO reviewDTO){
        int result = reviewService.change(reviewDTO);
        switch (result){
            case -1:
                return ResponseEntity.badRequest().body("리뷰 정보를 확인할 수 없습니다.");
            case -2:
                return ResponseEntity.badRequest().body("리뷰 정보를 확인할 수 없습니다.");
            case 200:
                return ResponseEntity.ok().body("성공적으로 수정되었습니다.");
            default:
                return ResponseEntity.internalServerError().body("알 수 없는 오류 발생");
        }
    }


}
