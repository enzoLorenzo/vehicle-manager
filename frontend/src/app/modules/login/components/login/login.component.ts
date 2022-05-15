import {Component, Input} from '@angular/core';
import {Router} from "@angular/router";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService, UserType} from "../../../../core/services/auth/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  isError = false;
  loginFG: FormGroup = new FormGroup({
    usernameFC: new FormControl('', Validators.required),
    passwordFC: new FormControl('', Validators.required),
  });

  constructor(private router: Router,
              private authService: AuthService) {
  }

  @Input()
  set username(value: string) {
    this.loginFG.get('usernameFC')?.setValue(value);
  }

  submit() {
    this.authService.login(
      this.loginFG.get('usernameFC')?.value,
      this.loginFG.get('passwordFC')?.value
    ).subscribe((loginResponse: boolean) => {
      if (loginResponse) {
        const path = this.authService.getUserType() === UserType.CLIENT
          ? "./main/vehicles"
          : "./main/workshops";
        this.router.navigate([path]);
        this.isError = false;
      } else {
        this.isError = true;
      }
    });
  }
}
