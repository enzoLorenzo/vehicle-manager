import {NgModule, Optional, SkipSelf} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {AppRoutingModule} from "./app-routing.module";
import {FlexLayoutModule, FlexModule} from "@angular/flex-layout";
import {MaterialModule} from "./material.module";
import {MainInterceptor} from "./interceptors/main.interceptor";
import {ErrorInterceptor} from "./interceptors/error.interceptor";
import {environment} from "../../environments/environment";
import {AlreadyLoadedCoreModuleError} from "./errors/custom-errors";

const COMPONENTS = []

const SERVICES = []

const MODULES = [
  CommonModule,
  AppRoutingModule,
  BrowserAnimationsModule,
  MaterialModule,
  HttpClientModule,
  FlexModule,
  FlexLayoutModule
]

const INTERCEPTORS = [
  {provide: HTTP_INTERCEPTORS, useClass: MainInterceptor, multi: true},
  {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true},
]

@NgModule({
  declarations: [],
  providers: [
    INTERCEPTORS,
    {provide: "BASE_API_URL", useValue: environment.apiUrl}
  ],
  imports: [MODULES],
  exports: [MODULES]
})
export class CoreModule {
  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    if (parentModule) {
      throw new AlreadyLoadedCoreModuleError();
    }
  }
}
