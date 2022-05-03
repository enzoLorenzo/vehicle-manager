import {NgModule} from '@angular/core';
import {LoginPageComponent} from './login-page.component';
import {LoginComponent} from "./components/login/login.component";
import {RegisterComponent} from './components/register/register.component';
import {CoreModule} from "../../core/core.module";

const COMPONENTS = [
  LoginPageComponent,
  LoginComponent,
  RegisterComponent
]

const MODULES = [
  CoreModule
]

@NgModule({
  declarations: [COMPONENTS],
  imports: [MODULES]
})
export class LoginModule {
}
