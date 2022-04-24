import {NgModule} from '@angular/core';
import {MainComponent} from './main.component';
import {NavComponent} from "./components/nav/nav.component";
import {VehiclesComponent} from './pages/vehicles/vehicles.component';
import {CoreModule} from "../../core/core.module";

const COMPONENTS = [
  MainComponent,
  NavComponent,
  VehiclesComponent
]


@NgModule({
  declarations: [COMPONENTS],
  imports: [CoreModule],
  exports: [COMPONENTS]
})
export class MainModule {
}
