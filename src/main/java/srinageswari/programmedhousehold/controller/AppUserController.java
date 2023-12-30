package srinageswari.programmedhousehold.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author smanickavasagam
 *     <p>Note: Created this controller just to make sure that oauth2 gets redirected to welcome
 *     page
 */
@Controller
public class AppUserController {
  @GetMapping("/login")
  public String getLoginPage() {
    return "login";
  }

  @GetMapping("/")
  public String getWelcomePage() {
    return "index";
  }
}
