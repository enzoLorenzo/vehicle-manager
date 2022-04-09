import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {LoginPageComponent} from "../modules/login/login-page.component";
import {MainComponent} from "../modules/main/main.component";
import {AuthGuard} from "./guards/auth.guard";

const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'main'},
  {path: 'login', component: LoginPageComponent},
  {
    path: 'main',
    component: MainComponent,
    children: [
      // {path: '', redirectTo: 'dashboard', pathMatch: 'full'},
      // {path: 'dashboard', component: DashboardComponent},
    ],
    canActivate:[AuthGuard]
  },
  {path: '**', component: LoginPageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    onSameUrlNavigation: 'reload'
  })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
