import {NgModule} from '@angular/core';
import {MainComponent} from './main.component';
import {NavComponent} from "./components/nav/nav.component";
import { VehiclesComponent} from './pages/vehicles/vehicles.component';
import {CoreModule} from "../../core/core.module";
import { VehicleCardComponent } from './pages/vehicles/vehicle-card/vehicle-card.component';
import { DialogAddVehicleComponent } from './pages/vehicles/dialog-add-vehicle/dialog-add-vehicle.component';
import { ReactiveFormsModule } from '@angular/forms';

const COMPONENTS = [
  MainComponent,
  NavComponent,
  VehiclesComponent
]


@NgModule({
  declarations: [COMPONENTS, VehicleCardComponent, DialogAddVehicleComponent],
  imports: [CoreModule, ReactiveFormsModule],
  exports: [COMPONENTS]
})
export class MainModule {
}
