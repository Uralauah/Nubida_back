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
    public List<ReviewDTO> getAllReviews(Principal principal){
        return reviewService.getAllReviews(principal);
    }

    @PostMapping("/addReview")
    public ResponseEntity<?> addReview(@RequestParam(name = "id") int id, @RequestBody ReviewDTO reviewDTO,Principal principal) {
        int result = reviewService.addReview(id, reviewDTO,principal);
        return switch (result) {
            case -1 -> ResponseEntity.badRequest().body("해당 국가를 찾을 수 없습니다.");
            case 200 -> ResponseEntity.ok().body("성공적으로 추가되었습니다.");
            default -> ResponseEntity.internalServerError().body("알 수 없는 오류 발생");
        };
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteReview(@RequestBody ReviewDTO reviewDTO){
        int result = reviewService.deleteReview(reviewDTO.getId());
        return switch (result) {
            case -1 -> ResponseEntity.badRequest().body("리뷰 정보를 확인할 수 없습니다.");
            case 200 -> ResponseEntity.ok().body("성공적으로 삭제되었습니다.");
            default -> ResponseEntity.internalServerError().body("알 수 없는 오류 발생");
        };
    }

    @PostMapping("/modify")
    public ResponseEntity<?> modifyReview(@RequestBody ReviewDTO reviewDTO){
        int result = reviewService.modifyReview(reviewDTO);
        return switch (result) {
            case -1, -2 -> ResponseEntity.badRequest().body("리뷰 정보를 확인할 수 없습니다.");
            case 200 -> ResponseEntity.ok().body("성공적으로 수정되었습니다.");
            default -> ResponseEntity.internalServerError().body("알 수 없는 오류 발생");
        };
    }


}
