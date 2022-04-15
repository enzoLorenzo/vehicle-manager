import {Component, OnDestroy} from '@angular/core';
import {Router} from "@angular/router";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../../../core/services/auth/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnDestroy {

  isError = false;

  loginFG: FormGroup = new FormGroup({
    usernameFC: new FormControl('', Validators.required),
    passwordFC: new FormControl('', Validators.required),
  });

  constructor(private router: Router,
              private authService: AuthService) {
  }


  submit() {
    this.authService.login(
      this.loginFG.get('usernameFC')?.value,
      this.loginFG.get('passwordFC')?.value
    ).subscribe((loginResponse: boolean) => {
      if (loginResponse) {
        this.router.navigate(['./main']);
        this.isError = false;
      } else {
        this.isError = true;
      }
    });

  }

  ngOnDestroy(): void {
  }
}
