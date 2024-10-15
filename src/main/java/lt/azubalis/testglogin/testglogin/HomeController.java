package lt.azubalis.testglogin.testglogin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class HomeController {

    @GetMapping("/")
    public String showPublicPage(Authentication authentication) {
        // Check if the user is authenticated
        if (authentication != null && authentication.isAuthenticated()) {
            // Redirect to the home page if authenticated
            return "redirect:/home";
        }
        // Return the public index page if not authenticated
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {
        // Cast authentication principal to OidcUser to get user info from Google
        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();

        String firstName = oidcUser.getGivenName(); // Retrieve the user's first name
        String lastName = oidcUser.getFamilyName(); // Retrieve the user's last name
        String profilePicture = oidcUser.getPicture();
        String email = oidcUser.getEmail(); // Retrieve the user's email

        // Add the first name, last name, and email to the model
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        model.addAttribute("email", email);
        model.addAttribute("profilePictureUrl", profilePicture);

        return "home"; // Return the view 'home.html'
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/"; // Redirect to the public homepage after logout
    }
}
