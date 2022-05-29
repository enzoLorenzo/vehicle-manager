import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PriceListPosition, Workshop} from "../../models/workshop";
import {WorkshopApiService} from "../../services/workshop-api.service";
import {MatSelectChange} from "@angular/material/select";
import {PriceListApiService} from "../../services/price-list-api.service";
import {AuthService, UserType} from "../../../../core/services/auth/auth.service";

@Component({
  selector: 'app-price-list',
  templateUrl: './price-list.component.html',
  styleUrls: ['./price-list.component.scss']
})
export class PriceListComponent implements OnInit {

  workshops: Workshop[] = [];

  selectedWorkshop?: Workshop;

  priceListFG = this.fb.group({
    priceList: this.fb.array([])
  });
  userType: UserType;
  USER_TYPE = UserType;

  constructor(private fb: FormBuilder,
              private workshopApiService: WorkshopApiService,
              private priceListApiService: PriceListApiService,
              private authService: AuthService) {
    this.userType = authService.getUserType();
  }

  get priceListFA(): FormArray {
    return this.priceListFG.controls["priceList"] as FormArray;
  }


  ngOnInit(): void {
    this.getWorkshops();
  }


  addPosition() {
    const priceListPositionForm: FormGroup = this.generatePositionFC();
    this.priceListFA.push(priceListPositionForm);
  }

  deletePosition(positionIndex: number) {
    this.priceListFA.removeAt(positionIndex);
  }

  savePriceList() {
    const priceList: PriceListPosition[] = this.priceListFA.getRawValue()
      .map(positionFormValue => {
        return {
          name: positionFormValue.name,
          description: positionFormValue.description,
          price: positionFormValue.price
        } as PriceListPosition;
      });
    this.priceListApiService.updatePriceList(this.selectedWorkshop!.id, priceList)
      .subscribe(() => this.getWorkshops())
  }

  showPriceList(change: MatSelectChange) {
    this.selectedWorkshop = change.value;
    this.priceListFA.clear()
    this.selectedWorkshop!.priceList.forEach(position => {
      const formGroup = this.generatePositionFC(position.name, position.description, position.price);
      this.priceListFA.push(formGroup);
    });
  }

  private generatePositionFC(name: string = '', description: string = '', price: number = 0.0) {
    const isDisabled: boolean = this.userType === UserType.CLIENT;
    return this.fb.group({
      name: [{value: name, disabled: isDisabled}, Validators.required],
      description: [{value: description, disabled: isDisabled}],
      price: [{value: price, disabled: isDisabled}, Validators.required],
    });
  }

  private getWorkshops() {
    if (this.userType === UserType.CLIENT){
      this.workshopApiService.getAllWorkshop()
        .subscribe((workshops: Workshop[]) => {
          this.workshops = workshops;
        });
    }else{
      this.workshopApiService.getClientWorkshop()
        .subscribe((workshops: Workshop[]) => {
          this.workshops = workshops;
        });
    }
  }
}
