import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dialog-rate-repair',
  templateUrl: './dialog-rate-repair.component.html',
  styleUrls: ['./dialog-rate-repair.component.scss']
})
export class DialogRateRepairComponent implements OnInit {
  starRating = 0;
  constructor() { }

  ngOnInit(): void {
  }

}
