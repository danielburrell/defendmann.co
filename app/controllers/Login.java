package controllers;

import org.springframework.stereotype.Component;

import play.mvc.Controller;
import play.mvc.Result;

@Component
public class Login extends Controller {

  public static Result login() { 
    session("level", "199");
    session("steamId", "2047");
    return ok("Level set");
  }
  
  public static Result logout() {
    session().clear();
    return ok("Logged Out");
  }
}
