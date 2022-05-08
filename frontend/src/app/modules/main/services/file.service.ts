import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {VehicleImage} from "../models/vehicle";

@Injectable({
  providedIn: 'root'
})
export class FileService {

  constructor(private http: HttpClient) {
  }

  onUpload(vehicleId: number, selectedFile: File) {
    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', selectedFile, selectedFile.name);
    return this.http.post(`/vehicle-image/${vehicleId}/upload`, uploadImageData, {observe: 'response'})
  }

  getImage(imageId: number) {
    return this.http.get<VehicleImage>(`/vehicle-image/download/${imageId}`)
  }
}
