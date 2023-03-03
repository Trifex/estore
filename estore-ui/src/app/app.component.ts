import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Go Green';

  // check if current url contain string "login" to check if currently on login page
  // if so then no need to display nav or log out buttons
  checkLogin(): boolean{
    if(String(location.toString()).indexOf("login") != -1) {return false}
    else {return true}
  }

  // check if admin is logged in, if so then no need to display nav buttons
  checkIfAdmin(): boolean{
    if(String(location.toString()).indexOf("admin") != -1) {return true}
    else {return false}
  }

  // check if current page is product details by seeing if the name acquired from getName has a /
  // if so then no need to display nav or log out buttons
  checkDetail(): boolean{
    if(this.getName().includes("/")) {return false}
    else if(this.getName().includes("/carts/")) {return false}
    else {return true}
  }

  // check if current page is product details editor by seeing if id comes after edit
  // if so then no need to display nav or log out buttons
  checkAdminDetail(): boolean{
    const curUrl = String(location.toString())
    let end_loc = curUrl.indexOf("edit") + 4 // index + 4 should be end of url if url ends in edit
    if(end_loc != curUrl.length) {return false}
    return true
  }

  // get the name from the url without using ActivatedRoute
  // works based on knowing where name should be within the string representation of url
  getName(): string{
    const curURL = String(location.toString())
    let name = "error";
    if(curURL.indexOf("home") != -1) {name = curURL.substring(curURL.indexOf("home")+5)}
    else{
      if(curURL.indexOf("products") != -1) {name = curURL.substring(curURL.indexOf("products")+9)}
      else {name = curURL.substring(curURL.indexOf("carts")+6)}
    }
    return name
  }

}
