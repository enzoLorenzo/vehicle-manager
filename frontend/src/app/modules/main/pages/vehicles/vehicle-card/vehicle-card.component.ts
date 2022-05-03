import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Vehicle} from "../../../models/vehicle";

@Component({
  selector: 'app-vehicle-card',
  templateUrl: './vehicle-card.component.html',
  styleUrls: ['./vehicle-card.component.scss']
})
export class VehicleCardComponent implements OnInit {

  @Input()
  vehicle!: Vehicle;

  @Output()
  delete: EventEmitter<any> = new EventEmitter<any>();

  constructor() { }

  ngOnInit(): void {
  }

  deleteVehicle() {
    this.delete.emit()
  }
}
