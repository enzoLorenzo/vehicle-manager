import {Component, ViewChild} from '@angular/core';
import {MatTabGroup} from "@angular/material/tabs";

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent {
  username = "";

  @ViewChild('tabs') tabGroup!: MatTabGroup;

  initLogin(username: string) {
    this.username = username;
    this.tabGroup.selectedIndex = 0;
  }
}
