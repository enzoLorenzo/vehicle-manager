import {Component} from '@angular/core';
import {BreakpointObserver, Breakpoints} from '@angular/cdk/layout';
import {Observable} from 'rxjs';
import {map, shareReplay} from 'rxjs/operators';
import {AuthService, UserType} from "../../../../core/services/auth/auth.service";

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.scss']
})
export class NavComponent {

  userType: UserType;

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );
  USER_TYPE = UserType;

  constructor(private breakpointObserver: BreakpointObserver,
              private authService: AuthService) {
    this.userType = authService.getUserType();
  }


  logout() {
    this.authService.logout();
  }
}
