import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {Vehicle, VehicleImage} from "../../../models/vehicle";
import {MatDialog} from "@angular/material/dialog";
import {DialogEditVehicleComponent} from "../dialog-edit-vehicle/dialog-edit-vehicle.component";
import {VehicleApiService} from "../../../services/vehicle-api.service";
import {FileService} from "../../../services/file.service";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";

@Component({
  selector: 'app-vehicle-card',
  templateUrl: './vehicle-card.component.html',
  styleUrls: ['./vehicle-card.component.scss']
})
export class VehicleCardComponent implements OnInit{

  _vehicle!: Vehicle;
  image?: VehicleImage;
  imageSrc?: SafeUrl;

  @Input() set vehicle(value: Vehicle) {
    this._vehicle = value;
    this.initImage();
  }

  @Output()
  delete: EventEmitter<any> = new EventEmitter<any>();

  constructor(private vehicleApiService: VehicleApiService,
              public dialog: MatDialog,
              public fileService: FileService,
              private sanitizer: DomSanitizer) {
  }

  ngOnInit(): void {
    // this.initImage();
  }

  private initImage() {
    if (!!this._vehicle.imageId) {
      this.fileService.getImage(this._vehicle.imageId).subscribe(image => {
        const objectURL = 'data:image/jpeg;base64,' + image.image;
        this.image = image;
        this.imageSrc = this.sanitizer.bypassSecurityTrustUrl(objectURL);
      })
    }
  }

  editVehicleDialog(): void {
    const dialogRef = this.dialog.open(DialogEditVehicleComponent, {
      width: '300px',
      data: {
        vehicle: this._vehicle,
        image: this.image
      }
    })
      .afterClosed()
      .subscribe(() => this.getVehicle());
  }

  deleteVehicle() {
    this.delete.emit()
  }

  private getVehicle() {
    this.vehicleApiService.getClientSingleVehicle(this._vehicle.id)
      .subscribe((vehicle: Vehicle) => {
        this._vehicle = vehicle;
        this.initImage();
      });

  }
}
