import {NgModule} from '@angular/core';
import {LoginModule} from "./login/login.module";
import {MainModule} from "./main/main.module";

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
