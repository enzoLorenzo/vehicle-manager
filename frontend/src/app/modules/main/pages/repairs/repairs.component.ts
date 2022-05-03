import {Component, OnInit} from '@angular/core';
import {TaskApiService} from "../../services/task-api.service";
import {Repair, TaskStatus} from "../../models/task";
import {MatTableDataSource} from "@angular/material/table";
import {MatSelectChange} from "@angular/material/select";

interface ColumnConfig {
  fieldName: string;
  header: string;
}

interface RepairDS {
  id: number;
  description: string;
  startDate: Date;
  endDate: Date;
  vehicle: string;
  taskStatus: TaskStatus;
}

interface Filter {
  label: string;
  fieldName: string;
  options: string[];
  defaultValue: string;
}

@Component({
  selector: 'app-repairs',
  templateUrl: './repairs.component.html',
  styleUrls: ['./repairs.component.scss']
})
export class RepairsComponent implements OnInit {

  displayedColumns: ColumnConfig[] = [
    {fieldName: 'id', header: "ID"},
    {fieldName: 'description', header: "DESCRIPTION"},
    {fieldName: 'vehicle', header: "VEHICLE"},
    {fieldName: 'startDate', header: "START"},
    {fieldName: 'endDate', header: "END"},
    {fieldName: 'taskStatus', header: "STATUS"},
    {fieldName: 'action', header: "ACTION"}
  ];

  columnFieldName = this.displayedColumns.map(v => v.fieldName);
  dataSourceFilters = new MatTableDataSource<RepairDS>();
  defaultValue = "All";
  filterDictionary = new Map<string, string>();

  filters: Filter[] = [];

  repairs: Repair[] = []


  constructor(private taskApi: TaskApiService) {
  }

  ngOnInit(): void {
    this.getRepairList();
  }

  applyFilter(ob: MatSelectChange, filter: any) {
    this.filterDictionary.set(filter.fieldName, ob.value);
    this.dataSourceFilters.filter = JSON.stringify(Array.from(this.filterDictionary.entries()));
  }

  private initFilterPredicate() {
    this.dataSourceFilters.filterPredicate = function (record, filter) {
      const map = new Map(JSON.parse(filter));
      let isMatch = false;
      for (let [key, value] of map) {
        isMatch = (value == "All") || (record[key as keyof RepairDS] == value);
        if (!isMatch) return false;
      }
      return isMatch;
    }
  }

  private getRepairList() {
    this.taskApi.getRepairs().subscribe((repairs: Repair[]) => {
      this.repairs = repairs;
      const repairDS = this.convertToDataSource(repairs);
      this.dataSourceFilters = new MatTableDataSource(repairDS);
      this.generateFilters();
      this.initFilterPredicate();
    });
  }

  private convertToDataSource(repairs: Repair[]) {
    return repairs.map(repair => {
      return {
        id: repair.id,
        description: repair.description,
        startDate: repair.startDate,
        endDate: repair.endDate,
        vehicle: this.getVehicleName(repair),
        taskStatus: repair.taskStatus,
      } as RepairDS
    });
  }

  private getVehicleName(repair: Repair) {
    return repair.vehicle.brand + " " + repair.vehicle.model;
  }

  private generateFilters() {
    const vehiclesNames: string[] = [this.defaultValue];
    const status: string[] = [this.defaultValue];

    this.repairs.forEach(repair => {
      vehiclesNames.push(this.getVehicleName(repair));
      status.push(repair.taskStatus)
    });

    this.filters.push({label: "VEHICLE", fieldName: 'vehicle', options: vehiclesNames, defaultValue: this.defaultValue})
    this.filters.push({label: "STATUS", fieldName: 'taskStatus', options: status, defaultValue: this.defaultValue })
  }

  showDetails(id: number) {
    const repair = this.repairs.find(value => value.id === id);
    console.log(repair)
  }

  deleteRepair(id: number) {
    this.taskApi.deleteRepair(id).subscribe(()=>{
      this.getRepairList();
    })
  }
}
