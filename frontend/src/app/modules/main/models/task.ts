import {Vehicle} from "./vehicle";
import {Workshop} from "./workshop";
import {RepairRating} from "./repair_rating";

export interface Repair extends Task {
}

export interface Task {
  id: number;
  description: string;
  startDate: Date;
  endDate: Date;
  taskStatus: TaskStatus;
  vehicle: Vehicle;
  workshop: Workshop;
  rating: RepairRating;
}

export interface RepairPut {
  id: number;
  description: string;
  startDate: Date;
  endDate: Date;
  taskStatus: TaskStatus;
  vehicle: Vehicle;
  workshop: Workshop;
}

export interface RepairPost {

  description: string;
  startDate: Date;
  endDate: Date;
  taskStatus: TaskStatus;
  vehicleId: number;
  workshopId: number;
}

export enum TaskStatus {
  PENDING = "PENDING",
  IN_PROGRESS = "IN_PROGRESS",
  ON_HOLD = "ON_HOLD",
  CANCELLED = "CANCELLED",
  FINISHED = "FINISHED"
}
