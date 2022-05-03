import {Component, EventEmitter, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService, RegisterCommand, UserType} from "../../../../core/services/auth/auth.service";
import {EMPTY, tap} from "rxjs";
import {catchError} from "rxjs/operators";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {

  @Output() public registerSuccess = new EventEmitter<string>();

  registerFG: FormGroup = new FormGroup({
    userTypeFC: new FormControl(UserType.CLIENT, Validators.required),
    usernameFC: new FormControl('', Validators.required),
    passwordFC: new FormControl('', Validators.required),
    nicknameFC: new FormControl('', Validators.required),
  });

  keys = Object.keys;
  USER_TYPE = UserType;
  isError = false;
  errorMessage = "";

  constructor(private router: Router,
              private authService: AuthService) {
  }

  register() {
    const userType: UserType = this.registerFG.get("userTypeFC")!.value

    const command = this.getRegisterCommand();
    this.authService.register(command, userType).pipe(
      tap(() => {
        this.isError = false;
        this.moveToLoginTab(command.username);
      }),
      catchError(err => {
        this.isError = true;
        this.errorMessage = err.error[0];
        return EMPTY;
      })
    ).subscribe()
  }

  moveToLoginTab(username: string) {
    this.registerSuccess.emit(username);
  }

  private getRegisterCommand() {
    const command: RegisterCommand = {
      username: this.registerFG.get("usernameFC")!.value,
      password: this.registerFG.get("passwordFC")!.value,
      nickname: this.registerFG.get("nicknameFC")!.value
    }
    return command;
  }
}
