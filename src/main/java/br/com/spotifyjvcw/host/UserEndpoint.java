package br.com.spotifyjvcw.host;

import br.com.spotifyjvcw.usecase.ValidateAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserEndpoint {

    private final ValidateAuthentication validateAuthentication;

    @GetMapping("/new")
    public ResponseEntity<Void> redirectToAuthSpotify() {
        String url = validateAuthentication.createSpotifyUrlNewUser();
        return ResponseEntity.status(302).header("Location", url).build();
    }

    @GetMapping("/generate-token")
    public ResponseEntity<String> getToken(@RequestParam String code) {
        validateAuthentication.generateToken(code);
        return ResponseEntity.ok("Sucesso!");
    }
}
