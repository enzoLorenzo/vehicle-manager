import {NgModule} from '@angular/core';
import {MainComponent} from './main.component';
import {NavComponent} from "./components/nav/nav.component";
import {VehiclesComponent} from './pages/vehicles/vehicles.component';
import {CoreModule} from "../../core/core.module";
import {VehicleCardComponent} from './pages/vehicles/vehicle-card/vehicle-card.component';
import {DialogAddVehicleComponent} from './pages/vehicles/dialog-add-vehicle/dialog-add-vehicle.component';
import {ReactiveFormsModule} from '@angular/forms';
import {RepairsComponent} from "./pages/repairs/repairs.component";

const COMPONENTS = [
  MainComponent,
  NavComponent,
  VehiclesComponent,
  VehicleCardComponent,
  RepairsComponent,
  DialogAddVehicleComponent
]


@NgModule({
  declarations: [COMPONENTS],
  imports: [CoreModule, ReactiveFormsModule],
  exports: [COMPONENTS]
})
export class MainModule {
}
