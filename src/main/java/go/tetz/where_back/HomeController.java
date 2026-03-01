package go.tetz.where_back;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "홈", description = "메인 페이지")
@RestController
@RequestMapping("/")
public class HomeController {

    @Operation(summary = "메인 페이지", description = "스웨거 및 카카오 로그인 링크")
    @GetMapping(produces = "text/html")
    public String home() {
        return """
            <!DOCTYPE html>
            <html lang="ko">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Where📍</title>
            </head>
            <body>
                <a href="./swagger-ui/index.html" target="_blank">
                    <h1>🚀 스웨거 바로가기</h1>
                </a>
                <a href="./api/auth/kakao/backend" target="_blank">
                    <h1>🍪 카카오 로그인 임시 테스트 - 백엔드 응답</h1>
                </a>
            </body>
            </html>
            """;
    }
}
