import {Repair} from "./task";

export enum VehicleType {
  CAR = "CAR",
  VAN = "VAN",
  MOTORCYCLE = "MOTORCYCLE",
  TRUCK = "TRUCK",
  CONSTRUCTION = "CONSTRUCTION",
  FARM = "FARM",
}

export interface Vehicle{
  id: number;
  registration: string;
  brand: string;
  model: string;
  generation: string;
  year: string;
  engineCapacity: string;
  horsePower: string;
  type: VehicleType;
  task: Repair[]
}

export interface VehiclePost{
  registration: string;
  brand: string;
  model: string;
  generation: string;
  year: string;
  engineCapacity: string;
  horsePower: string;
  type: VehicleType;
}
