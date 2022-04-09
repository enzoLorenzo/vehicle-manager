import {NgModule} from '@angular/core';
import {LoginPageComponent} from './login-page.component';
import {LoginComponent} from "./components/login/login.component";
import {MaterialModule} from "../../core/material.module";

const COMPONENTS = [
  LoginPageComponent,
  LoginComponent
]

const MODULES = [
  MaterialModule
]

@NgModule({
  declarations: [COMPONENTS],
  imports: [MODULES]
})
export class LoginModule {
}
