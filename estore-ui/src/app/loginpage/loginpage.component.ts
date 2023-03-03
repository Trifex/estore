import { Component, Input, OnInit } from '@angular/core';
import { User } from '../user';

import { Router } from '@angular/router';
import { Location } from '@angular/common';

import { UserService } from '../user.service';

@Component({
  selector: 'app-loginpage',
  templateUrl: './loginpage.component.html',
  styleUrls: ['./loginpage.component.css']
})
export class LoginpageComponent implements OnInit {
  @Input() users?: User[];

  constructor(
    private router: Router,
    private userService: UserService,
    private location: Location
  ) { }

  ngOnInit(): void {
    this.getUsers();
  }
  
  getUsers(): void {
    this.userService.getUsers()
      .subscribe(users => this.users = users);
  }

  // check if input username exists within users fetched on initialization
  checkIfExists(name: string): boolean{
    if(this.users){
      for (let index = 0; index < this.users.length; index++) {
        if(this.users[index].name == name) {return true}
      }
    }
    return false
  }

  // check if user already exists, if not then user needs to be created
  // also check if existing user is the admin user and navigate as necessary 
  checkUser(name: string): void {
    if(name.length != 0) {
      if(this.checkIfExists(name)){
        if(name == "admin") {this.router.navigate(['/products/admin/edit'])}
        else {this.router.navigate(['home/' + name])}
      }
      else{
        this.userService.createUser(name).subscribe()
        let url = 'home/' + name;
        this.router.navigate([url])
      }
    }
  }
}
