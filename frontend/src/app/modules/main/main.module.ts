import {NgModule} from '@angular/core';
import {MainComponent} from './main.component';
import {NavComponent} from "./components/nav/nav.component";
import {VehiclesComponent} from './pages/vehicles/vehicles.component';
import {CoreModule} from "../../core/core.module";
import {VehicleCardComponent} from './pages/vehicles/vehicle-card/vehicle-card.component';
import {DialogAddVehicleComponent} from './pages/vehicles/dialog-add-vehicle/dialog-add-vehicle.component';
import {ReactiveFormsModule} from '@angular/forms';
import {RepairsComponent} from "./pages/repairs/repairs.component";
import { DialogEditVehicleComponent } from './pages/vehicles/dialog-edit-vehicle/dialog-edit-vehicle.component';
import { WorkshopsComponent } from './pages/workshops/workshops.component';
import { WorkshopCardComponent } from './pages/workshops/workshop-card/workshop-card.component';
import { DialogAddWorkshopComponent } from './pages/workshops/dialog-add-workshop/dialog-add-workshop.component';
import { DialogEditWorkshopComponent } from './pages/workshops/dialog-edit-workshop/dialog-edit-workshop.component';
import { DialogAddRepairComponent } from './pages/repairs/dialog-add-repair/dialog-add-repair.component';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DialogEditRepairComponent } from './pages/repairs/dialog-edit-repair/dialog-edit-repair.component';
import { DialogRateRepairComponent } from './pages/repairs/dialog-rate-repair/dialog-rate-repair.component';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import { WorkshopRatingsComponent } from './pages/repairs/workshop-ratings/workshop-ratings.component';


const COMPONENTS = [
  MainComponent,
  NavComponent,
  VehiclesComponent,
  VehicleCardComponent,
  RepairsComponent,
  DialogAddVehicleComponent,
  DialogEditVehicleComponent,
  WorkshopsComponent,
  WorkshopCardComponent,
  DialogAddWorkshopComponent,
  DialogEditWorkshopComponent,
  DialogAddRepairComponent,
  DialogEditRepairComponent,
  DialogRateRepairComponent,
  WorkshopRatingsComponent
]


@NgModule({
  declarations: [COMPONENTS],
  imports: [CoreModule, ReactiveFormsModule, MatDatepickerModule, BrowserAnimationsModule, MatNativeDateModule, NgbModule],
  exports: [COMPONENTS]
})
export class MainModule {
}
