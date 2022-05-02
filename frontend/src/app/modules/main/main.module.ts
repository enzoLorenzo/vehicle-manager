import {NgModule} from '@angular/core';
import {MainComponent} from './main.component';
import {NavComponent} from "./components/nav/nav.component";
import { VehiclesComponent} from './pages/vehicles/vehicles.component';
import {CoreModule} from "../../core/core.module";
import { VehicleCardComponent } from './pages/vehicles/vehicle-card/vehicle-card.component';
import { DialogDogComponent } from './pages/vehicles/dialog-dog/dialog-dog.component';
import { ReactiveFormsModule } from '@angular/forms';

const COMPONENTS = [
  MainComponent,
  NavComponent,
  VehiclesComponent
]


@NgModule({
  declarations: [COMPONENTS, VehicleCardComponent, DialogDogComponent],
  imports: [CoreModule, ReactiveFormsModule],
  exports: [COMPONENTS]
})
export class MainModule {
}
