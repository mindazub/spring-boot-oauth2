package lt.azubalis.testglogin.testglogin;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class ProfileController {

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        // Cast authentication principal to OidcUser to get user info from Google
        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();

        String firstName = oidcUser.getGivenName(); // Retrieve the user's first name
        String lastName = oidcUser.getFamilyName(); // Retrieve the user's last name
        String profilePicture = oidcUser.getPicture();
        String email = oidcUser.getEmail(); // Retrieve the user's email

        // Add the first name, last name, email, and profile picture URL to the model
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        model.addAttribute("email", email);
        model.addAttribute("profilePictureUrl", profilePicture);

        return "profile"; // Return the view 'profile.html'
    }


}
