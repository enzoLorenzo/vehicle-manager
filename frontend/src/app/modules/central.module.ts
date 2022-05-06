
import {LoginModule} from "./login/login.module";
import {MainModule} from "./main/main.module";
import {NavComponent} from "./main/components/nav/nav.component";
import {MaterialModule} from "../core/material.module";
import {AppRoutingModule} from "../core/app-routing.module";
import {CoreModule} from "../core/core.module";
import {NgModule} from "@angular/core";

const MODULES = [
  LoginModule,
  MainModule
]

@NgModule({
  declarations: [],
  imports: [MODULES],
  exports: [MODULES]
})
export class CentralModule {
}
