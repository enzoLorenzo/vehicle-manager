import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {LoginPageComponent} from "../modules/login/login-page.component";
import {MainComponent} from "../modules/main/main.component";
import {AuthGuard} from "./guards/auth.guard";
import {VehiclesComponent} from "../modules/main/pages/vehicles/vehicles.component";
import {RepairsComponent} from "../modules/main/pages/repairs/repairs.component";
import {WorkshopsComponent} from "../modules/main/pages/workshops/workshops.component";

const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'main'},
  {path: 'login', component: LoginPageComponent},
  {
    path: 'main',
    component: MainComponent,
    children: [
      {path: '', redirectTo: 'vehicles', pathMatch: 'full'},
      {path: 'vehicles', component: VehiclesComponent},
      {path: 'repairs', component: RepairsComponent},
      {path: 'workshops', component: WorkshopsComponent}
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
