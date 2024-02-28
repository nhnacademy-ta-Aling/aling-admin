package kr.aling.admin.common.feign;

import kr.aling.admin.user.dto.response.IsExistsUserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("aling-user")
public interface UserFeignClient {

    @GetMapping("/check/{userNo}")
    ResponseEntity<IsExistsUserResponseDto> isExistsUser(@PathVariable Long userNo);
}
